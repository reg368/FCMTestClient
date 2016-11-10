package hyweb.core.net;

import hyweb.core.aop.EventHandler;
import hyweb.core.aop.Eventable;
import hyweb.core.aop.WGetHandler;
import hyweb.core.kit.StreamKit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 透過網路取得網頁資料,單一實體不支援多執行序
 * 
 * @author A0074
 * @version 1.0.121111
 * @since xBox 1.0
 */
public class WGet implements Eventable {
	public final String USER_AGENT_FX = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0";
	public final String USER_AGENT_IE = "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0";
	public final String USER_AGENT_CHROME = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36";
	/* 是否持續使用該連線 */
	private boolean isKeepConn;
	/* 是否持續使用已塞入post變數 */
	private boolean isKeepParam;
	/* 是否相信所有網站的憑證 */
	private boolean isTrust;
	/* 連線最大等待時間 */
	private int timeout;
	/* 重試次數 */
	private int reTryCota;
	/* 每次重試的間隔時間 */
	private int reTryWaitTime;
	/* 目前連線對象 */
	protected URL url;
	/* cookie */
	protected Map<String, List<String>> cookiesMap;
	/* post */
	protected StringBuffer param;
	/**/
	protected WGetHandler handler;
	/**/
	protected String charset;
	/**/
	protected String referer;
	/**/
	protected SSLSocketFactory sslSocketFactory;

	/**
	 * 
	 */
	public WGet() {
		super();
		HttpURLConnection.setFollowRedirects(true);
		this.isKeepConn = false;
		this.isKeepParam = false;
		this.isTrust = false;
		this.timeout = 30 * 1000;
		this.reTryWaitTime = 2 * 1000;
		this.reTryCota = 2;
		this.charset = "UTF-8";
		this.referer = "http://www.google.com";
		this.url = null;
		this.cookiesMap = null;
		this.param = null;
		this.handler = null;
		this.sslSocketFactory = null;
	}

	/**
	 * 要使用toString, 請用此建構子
	 * 
	 * @param urlStr
	 * @throws MalformedURLException
	 */
	public WGet(String urlStr) throws MalformedURLException {
		this();
		this.url = new URL(urlStr);
	}

	/**
	 * 重複使用本conn,是的話會採用同一個sessionID與referer
	 * 
	 * @return this
	 */
	public WGet keepConn() {
		CookieHandler.setDefault(new CookieManager(null,CookiePolicy.ACCEPT_ALL));
		this.isKeepConn = true;
		return this;
	}

	public WGet setReferer(String referer) {
		this.referer = referer;
		return this;
	}

	/**
	 * 保持上次設定的post參數
	 * 
	 * @return this
	 */
	public WGet keepParam() {
		this.isKeepParam = true;
		return this;
	}

	/**
	 * 略過SSL憑證驗證,當有呼叫setKeystore時,本社定無效
	 * 
	 * @return this
	 */
	public WGet trust() {
		this.isTrust = true;
		return this;
	}

	/**
	 * 使用HTTP1.1 通訊協定的 keep alive.JDK預設開啟
	 * 
	 * @return this
	 */
	public WGet keepAlive() {
		System.setProperty("http.keepAlive", "true");
		return this;
	}

	/**
	 * 設定最大等待連線時間
	 * 
	 * @param second
	 * @return this
	 */
	public WGet setTimeout(int second) {
		this.timeout = second * 1000;
		return this;
	}

	/**
	 * 重新連線間的等待時間
	 * 
	 * @param second
	 * @return this
	 */
	public WGet setReTryWaitTime(int second) {
		this.reTryWaitTime = second * 1000;
		return this;
	}

	/**
	 * 允許失敗重連的次數
	 * 
	 * @param reTryCota
	 * @return this
	 */
	public WGet setReTryCota(int times) {
		this.reTryCota = times;
		return this;
	}

	/**
	 * 加入憑證
	 * 
	 * @param keystore
	 *            憑證檔案
	 * @param password
	 *            密碼
	 * @return this
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws KeyManagementException
	 */
	public WGet setKeystore(File keystore, String password)
			throws NoSuchAlgorithmException, CertificateException, IOException,
			KeyStoreException, KeyManagementException {
		InputStream is = null;
		try {
			is = new FileInputStream(keystore);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(is, password.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf
					.getTrustManagers()[0];
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { defaultTrustManager }, null);
			this.sslSocketFactory = context.getSocketFactory();
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return this;
	}

	/**
	 * 設定要怎麼處理各種事件
	 * 
	 * @param hander
	 *            真實實體為 WGetHandler
	 */
	@Override
	public WGet setEventHandler(EventHandler handler) {
		this.handler = (WGetHandler) handler;
		return this;
	}

	/**
	 * 數訂post用的參數
	 * 
	 * @param key
	 * @param val
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public WGet addParam(String key, String val)
			throws UnsupportedEncodingException {
		if (this.param == null) {
			this.param = new StringBuffer(128);
		} else {
			this.param.append("&");
		}
		this.param
				.append(key)
				.append("=")
				.append(val == null ? "" : URLEncoder.encode(val, this.charset));
		return this;
	}

	public WGet run(String urlStr) throws MalformedURLException {
		if (urlStr != null) {
			this.run(new URL(urlStr));
		}
		return this;
	}

	/**
	 * 再次連線,如果沒設定keepConn會取不到上次連線的環境
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public WGet run(URL url) {
		if (url == null) {
			url = this.url;
		} else {
			this.url = url;
		}
		HttpURLConnection conn = null;
		if (this.handler == null) {
			this.handler = new WGetHandlerStreamImpl(System.out);
		}
		this.handler.onBegin();
		if ("https".equals(url.getProtocol())) {
			if (this.isTrust) {
				try {
					this.passValidateCertificateChains();
				} catch (Exception e) {
					this.handler.onError(e);
					throw new RuntimeException(e);
				}
			}
		}
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setAllowUserInteraction(true);
			this.connSSL(conn);
			this.connNormal(conn);
			this.connReferer(conn);
			this.connCookies(conn);
			this.connPost(conn);
			conn.connect();
			this.connKeepSessionID(conn);
			this.connCheckCharset(conn);
			switch (conn.getResponseCode()) {
			case HttpURLConnection.HTTP_MOVED_PERM:
			case HttpURLConnection.HTTP_MOVED_TEMP:
				String redirectUrl = conn.getHeaderField("Location");
				if (this.handler.onRedirect(redirectUrl)) {
					conn.disconnect();
					this.keepConn().run(redirectUrl);
				} else {
					this.handler.onSuccess(conn.getInputStream(), this.charset, conn.getHeaderFields());
				}
				break;
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_NOT_MODIFIED:
				this.handler.onSuccess(conn.getInputStream(), this.charset, conn.getHeaderFields());
				break;
			default:
				this.handler.onFailure(conn.getErrorStream(), this.charset, conn.getResponseCode(), conn.getHeaderFields());
				break;
			}
		} catch (ConnectException e) {
			if (--this.reTryCota > 0) {
				conn = null;
				try {
					Thread.sleep(this.reTryWaitTime);
				} catch (Exception ex) {
				}
				this.run(url);
			} else {
				this.handler.onError(e);
			}
		} catch (IOException e) {
			this.handler.onError(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		if (!this.isKeepParam) {
			this.param = null;
		}
		this.handler.onEnd();
		return this;
	}

	/**
	 * 當無法由頁面資訊取得編碼時,回傳defCharset
	 * 
	 * @param defCharset
	 * @return
	 */
	public String getRequestCharset(String defCharset) {
		return this.charset == null ? defCharset : this.charset;
	}

	/**
	 * 直接取得對象網址
	 */
	@Override
	public String toString() {
		WGetHandler handler = new WGetHandlerStringImpl();
		this.setEventHandler(handler).trust().keepConn().run(this.url);
		return handler.toString();
	}

	/**
	 * 跳過憑証驗証
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void passValidateCertificateChains()
			throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

	/**
	 * 加入cookie
	 * 
	 * @param cookie
	 * @param url
	 * @return
	 */
	public WGet setCookie(List<String> cookie, URL url) {
		if (cookie != null) {
			CookieHandler.setDefault(new CookieManager(null,CookiePolicy.ACCEPT_ALL));
			if (this.cookiesMap == null) {
				this.cookiesMap = new java.util.concurrent.ConcurrentHashMap<String, List<String>>();
			}
			String host = url.getHost();
			if (this.cookiesMap.containsKey(host)) {
				this.cookiesMap.remove(host);
			}
			this.cookiesMap.put(host, cookie);
		}
		return this;
	}

	/**
	 * 取得Cookie
	 * 
	 * @param url
	 * @return
	 */
	protected List<String> getCookie(URL url) {
		return this.cookiesMap == null ? null : this.cookiesMap.get(url
				.getHost());
	}

	private void connSSL(HttpURLConnection conn) {
		if (this.sslSocketFactory != null) {
			((HttpsURLConnection) conn)
					.setSSLSocketFactory(this.sslSocketFactory);
		}
	}

	private void connNormal(HttpURLConnection conn) {
		conn.setRequestProperty("User-agent", USER_AGENT_CHROME);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "zh-tw,en-us;q=0.7,en;q=0.3");
		conn.setRequestProperty("Accept-Charse", "UTF-8,*");
		conn.setReadTimeout(this.timeout);
		conn.setDoInput(true);
		conn.setDoOutput(true);
	}

	private void connReferer(HttpURLConnection conn) {
		if (this.referer != null) {
			conn.setRequestProperty("Referer", this.referer);
		}
	}

	private void connCookies(HttpURLConnection conn) {
		List<String> cookies = this.getCookie(this.url);
		if (cookies != null) {
			for (String cookie : cookies) {
				conn.setRequestProperty("Cookie", cookie);
			}
		}
	}

	private void connPost(HttpURLConnection conn) throws IOException {
		if (this.param != null) {
			String paramStr = param.toString();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(paramStr.getBytes().length));
			DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			ds.writeBytes(paramStr);
			ds.flush();
			ds.close();
		}
	}

	private void connKeepSessionID(HttpURLConnection conn) {
		if (this.isKeepConn) {
			this.setCookie(conn.getHeaderFields().get("Set-Cookie"), this.url);// 因為可以分開寫，所以會有多組
			this.referer = conn.getHeaderField("http_referrer");
		}
	}

	private void connCheckCharset(HttpURLConnection conn) {
		String contentType = conn.getHeaderField("Content-Type");
		if (contentType != null) {
			for (String param : contentType.replace(" ", "").split(";")) {
				if (param.startsWith("charset=")) {
					this.charset = param.split("=", 2)[1];
					break;
				}
			}
		}
	}
}

/**
 * 預設的WGetHandler,會將結果寫到System.out
 * 
 * @author Monyem
 */
class WGetHandlerStreamImpl implements WGetHandler {
	private OutputStream os;

	public WGetHandlerStreamImpl(OutputStream os) {
		super();
		this.os = os;
	}

	@Override
	public void onBegin() {
	}

	@Override
	public void onTaskFinish() {
	}

	@Override
	public void onEnd() {
		StreamKit.close(null, this.os);
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace(System.out);
	}

	@Override
	public void onSuccess(InputStream is, String charSet, Map<String, List<String>> headerFieldsMap) throws IOException {
		StreamKit.copy(is, this.os);
	}

	@Override
	public boolean onRedirect(String redirectUrl) throws IOException {
		System.out.println("allow redirect to " + redirectUrl);
		return true;
	}

	@Override
	public void onFailure(InputStream is, String charSet, int httpCode, Map<String, List<String>> headerFieldsMap) throws IOException {
		StreamKit.copy(is, this.os);
	}
}

/**
 * 提供toString使用的WGetHandler
 * 
 * @author Monyem
 */
class WGetHandlerStringImpl implements WGetHandler {
	StringBuilder str = new StringBuilder(2048);

	public WGetHandlerStringImpl() {
		super();
	}

	@Override
	public void onBegin() {
	}

	@Override
	public void onTaskFinish() {
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace(System.out);
	}

	@Override
	public void onSuccess(InputStream is, String charSet, Map<String, List<String>> headerFieldsMap) throws IOException {
		this.str.delete(0, this.str.length());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, charSet));
			StreamKit.copy(reader, this.str, System.getProperty("line.separator", "\n"));
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	@Override
	public boolean onRedirect(String redirectUrl) throws IOException {
		System.out.println("allow redirect to " + redirectUrl);
		return true;
	}

	@Override
	public void onFailure(InputStream is, String charSet, int httpCode, Map<String, List<String>> headerFieldsMap) throws IOException {
		System.out.println(httpCode);
		this.str.delete(0, this.str.length());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, charSet));
			StreamKit.copy(reader, this.str,
					System.getProperty("line.separator", "\n"));
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public String toString() {
		return str.toString();
	}
}