package hyweb.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openid4java.OpenIDException;
import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;
import org.openid4java.util.ProxyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenidHandler extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = -5998885243419513055L;
	private static final String OPTIONAL_VALUE = "0";
	private static final String REQUIRED_VALUE = "1";
	private static final Logger LOG = LoggerFactory.getLogger(OpenidHandler.class);

	private ServletContext context;
	private ConsumerManager manager;

	/**
	 * {@inheritDoc}
	 */
	public Map goPost(HttpServletRequest req, HttpServletResponse resp, String siteId)
			throws ServletException, IOException {

		this.manager = new ConsumerManager();
		manager.setAssociations(new InMemoryConsumerAssociationStore());
		manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
		manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);		
		if ("true".equals(req.getParameter("is_return"))) {
			return processReturn(req, resp, siteId);
		}else {
			String identifier = req.getParameter("openid_identifier");	
			return this.authRequest(identifier, req, resp);
		}

	}

	private Map processReturn(HttpServletRequest req, HttpServletResponse resp, String siteId)
			throws ServletException, IOException {
		
		Map mapValue = this.verifyResponse(req);
		Identifier identifier = null;
		String email = "";
		
		if(mapValue.get("verified") != null){
			identifier = (Identifier)mapValue.get("verified");
		}
		
		if(mapValue.get("email") != null){
			email = mapValue.get("email").toString();
		}
		LOG.debug("identifier: " + identifier);
		
		Map map = new HashMap();
		if (identifier == null) {	
			map.put("url", "/memlogin-" + siteId);
			return map;
		} else {
			map.put("email", email);
			return map;
		}
	}

	// --- placing the authentication request ---
	public Map authRequest(String userSuppliedString,
			HttpServletRequest httpReq, HttpServletResponse httpResp)
			throws IOException, ServletException {
		try {
			// configure the return_to URL where your application will receive
			// the authentication responses from the OpenID provider
			// String returnToUrl = "http://example.com/openid";
			String returnToUrl = httpReq.getRequestURL().toString()
					+ "?is_return=true";
			
			// perform discovery on the user-supplied identifier
			List discoveries = manager.discover(userSuppliedString);

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			DiscoveryInformation discovered = manager.associate(discoveries);

			// store the discovery information in the user's session
			httpReq.getSession().setAttribute("openid-disc", discovered);

			// obtain a AuthRequest message to be sent to the OpenID provider
			AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

			// Simple registration example
//			addSimpleRegistrationToAuthRequest(httpReq, authReq);

			// Attribute exchange example
			addAttributeExchangeToAuthRequest(httpReq, authReq);

			if (!discovered.isVersion2()) {
				// Option 1: GET HTTP-redirect to the OpenID Provider endpoint
				// The only method supported in OpenID 1.x
				// redirect-URL usually limited ~2048 bytes
				httpResp.sendRedirect(authReq.getDestinationUrl(true));
				return null;
			} else {
				// Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)
				Map m = new HashMap();
				m.put("url", "https://www.google.com/accounts/o8/ud?openid.ns=http://specs.openid.net/auth/2.0&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" + 
									"&openid.identity=http://specs.openid.net/auth/2.0/identifier_select&openid.return_to=" + returnToUrl +
									"&openid.realm="+ returnToUrl +"&openid.assoc_handle=1.AMlYA9U6D-XtJF8nctyRSQ7prAiXR6LY1yPlDfdK4MAO-pCcvevo8hzR1_1BF90Amhu1hKuKJFHpxQ" +
									"&openid.mode=checkid_setup&openid.ns.ext1=http://openid.net/srv/ax/1.0&openid.ext1.mode=fetch_request&openid.ext1.type.email=http://axschema.org/contact/email&openid.ext1.required=email");
				return m;
			}
		} catch (OpenIDException e) {
			// present error to the user
			throw new ServletException(e);
		}
	}

	/**
	 * Simple Registration Extension example.
	 * 
	 * @param httpReq
	 * @param authReq
	 * @throws MessageException
	 * @see <a href="http://code.google.com/p/openid4java/wiki/SRegHowTo">Simple Registration HowTo</a>
	 * @see <a href="http://openid.net/specs/openid-simple-registration-extension-1_0.html">OpenID Simple Registration Extension 1.0</a>
	 */
	private void addSimpleRegistrationToAuthRequest(HttpServletRequest httpReq,
			AuthRequest authReq) throws MessageException {
		// Attribute Exchange example: fetching the 'email' attribute
		// FetchRequest fetch = FetchRequest.createFetchRequest();
		SRegRequest sregReq = SRegRequest.createFetchRequest();

		String[] attributes = { "nickname", "email", "fullname", "dob",
				"gender", "postcode", "country", "language", "timezone" };
		for (int i = 0, l = attributes.length; i < l; i++) {
			String attribute = attributes[i];
			String value = httpReq.getParameter(attribute);
			if (OPTIONAL_VALUE.equals(value)) {
				sregReq.addAttribute(attribute, false);
			} else if (REQUIRED_VALUE.equals(value)) {
				sregReq.addAttribute(attribute, true);
			}
		}

		// attach the extension to the authentication request
		if (!sregReq.getAttributes().isEmpty()) {
			authReq.addExtension(sregReq);
		}
	}

	/**
	 * Attribute exchange example.
	 * 
	 * @param httpReq
	 * @param authReq
	 * @throws MessageException
	 * @see <a href="http://code.google.com/p/openid4java/wiki/AttributeExchangeHowTo">Attribute Exchange HowTo</a>
	 * @see <a href="http://openid.net/specs/openid-attribute-exchange-1_0.html">OpenID Attribute Exchange 1.0 - Final</a>
	 */
	private void addAttributeExchangeToAuthRequest(HttpServletRequest httpReq,
			AuthRequest authReq) throws MessageException {
		String[] aliases = httpReq.getParameterValues("alias");
		String[] typeUris = httpReq.getParameterValues("typeUri");
		String[] counts = httpReq.getParameterValues("count");
		FetchRequest fetch = FetchRequest.createFetchRequest();

		for (int i = 0, l = typeUris == null ? 0 : typeUris.length; i < l; i++) {
			String typeUri = typeUris[i];
			if (StringUtils.isNotBlank(typeUri)) {
				String alias = aliases[i];
				boolean required = httpReq.getParameter("required" + i) != null;
				int count = NumberUtils.toInt(counts[i], 1);
				fetch.addAttribute(alias, typeUri, required, count);
			}
		}
	
		authReq.addExtension(fetch);
	}

	// --- processing the authentication response ---
	public Map verifyResponse(HttpServletRequest httpReq)
			throws ServletException {
		
		Map map = new HashMap();
		String email = "";
		
		try {
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response = new ParameterList(httpReq
					.getParameterMap());

			// retrieve the previously stored discovery information
			DiscoveryInformation discovered = (DiscoveryInformation) httpReq
					.getSession().getAttribute("openid-disc");

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = httpReq.getRequestURL();
			String queryString = httpReq.getQueryString();
			if (queryString != null && queryString.length() > 0)
				receivingURL.append("?").append(httpReq.getQueryString());

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(receivingURL
					.toString(), response, discovered);

			// examine the verification result and extract the verified
			// identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null) {
				AuthSuccess authSuccess = (AuthSuccess) verification
						.getAuthResponse();

				receiveSimpleRegistration(httpReq, authSuccess);

				email = receiveAttributeExchange(httpReq, authSuccess);

				map.put("verified", verified);
				map.put("email", email);
				return map; // success
			}
		} catch (OpenIDException e) {
			// present error to the user
			throw new ServletException(e);
		}
		map.put("verified", null);
		map.put("email", null);
		return map;
	}

	/**
	 * @param httpReq
	 * @param authSuccess
	 * @throws MessageException 
	 */
	private void receiveSimpleRegistration(HttpServletRequest httpReq,
			AuthSuccess authSuccess) throws MessageException {
		if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authSuccess
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegResponse) {
				SRegResponse sregResp = (SRegResponse) ext;
				for (Iterator iter = sregResp.getAttributeNames()
						.iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = sregResp.getParameterValue(name);
					httpReq.setAttribute(name, value);
				}
			}
		}
	}

	/**
	 * @param httpReq
	 * @param authSuccess
	 * @throws MessageException 
	 */
	private String receiveAttributeExchange(HttpServletRequest httpReq,
			AuthSuccess authSuccess) throws MessageException {
		
			String email = "";
		
		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			FetchResponse fetchResp = (FetchResponse) authSuccess
					.getExtension(AxMessage.OPENID_NS_AX);

			// List emails = fetchResp.getAttributeValues("email");
			// String email = (String) emails.get(0);

			List aliases = fetchResp.getAttributeAliases();
			Map attributes = new LinkedHashMap();
			for (Iterator iter = aliases.iterator(); iter.hasNext();) {
				String alias = (String) iter.next();
				List values = fetchResp.getAttributeValues(alias);
				if (values.size() > 0) {
					String[] arr = new String[values.size()];
					values.toArray(arr);
					attributes.put(alias, StringUtils.join(arr));
				}
			}
			email = attributes.get("email").toString();
			httpReq.setAttribute("attributes", attributes);
			
		}
		
		return email;
		
	}

	/**
	 * Get proxy properties from the context init params.
	 *
	 * @return proxy properties
	 */
	private static ProxyProperties getProxyProperties(ServletConfig config) {
		ProxyProperties proxyProps;
		String host = config.getInitParameter("proxy.host");
		LOG.debug("proxy.host: " + host);
		if (host == null) {
			proxyProps = null;
		} else {
			proxyProps = new ProxyProperties();
			String port = config.getInitParameter("proxy.port");
			String username = config.getInitParameter("proxy.username");
			String password = config.getInitParameter("proxy.password");
			String domain = config.getInitParameter("proxy.domain");
			proxyProps.setProxyHostName(host);
			proxyProps.setProxyPort(Integer.parseInt(port));
			proxyProps.setUserName(username);
			proxyProps.setPassword(password);
			proxyProps.setDomain(domain);
		}
		return proxyProps;
	}
}
