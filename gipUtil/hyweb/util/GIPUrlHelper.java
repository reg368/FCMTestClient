package hyweb.util;

import hyweb.core.kit.ServletKit;
import hyweb.core.kit.StringKit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


public class GIPUrlHelper {
	private static final String HYPATTERN_XML_KEY = "HYPATTERN_XML";
	private static final String HYPATTERN_XSL_KEY = "HYPATTERN_XSL";
	
	private static String XD_SERVER_KEY = "HYPATTERN_PAGE_XD_SERVER";
	private static String WEB_SERVER_KEY = "HYPATTERN_PAGE_WEB_SERVER";
	
	private static final String XDMP = "xdmp/";
	private static final String XDNP = "xdnp/";
	private static final String XDLP = "xdlp/";
	private static final String XDCP = "xdcp/";
	private static final String XDALLPAGEUSE = "xdallpageuse/";
	
	public static String getAdminXmlUrl() {
		return Cfg.get(HYPATTERN_XML_KEY);
	}
	
	public static String getAdminXslUrl() {
		return Cfg.get(HYPATTERN_XSL_KEY);
	}
	
	public static String getWebUrl(String path) {
		if (path != null) {
			return Cfg.get(WEB_SERVER_KEY) + path;
		} else {
			return Cfg.get(WEB_SERVER_KEY);
		}
	}
	
	public static String getWebShortCpUrl(String siteId, String nodeid, String sid, String queryString) {
		String result = "/article-"+siteId+"-"+nodeid+"-"+sid;

		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getWebCpUrl(String siteId, String nodeid, String sid, String queryString) {
		String result = Cfg.get(WEB_SERVER_KEY)+"article-"+siteId+"-"+nodeid+"-"+sid;

		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getXdUrl(String path) {
		if (path != null) {
			return Cfg.get(XD_SERVER_KEY) + path;
		} else {
			return Cfg.get(XD_SERVER_KEY);
		}
	}
	
	public static String getXdMpUrl(String siteId, String queryString) {
		String result = Cfg.get(XD_SERVER_KEY) + XDMP + siteId;
		
		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getXdNpUrl(String siteId, String nodeid, String queryString) {
		String result = Cfg.get(XD_SERVER_KEY) + XDNP + siteId+"-"+nodeid;
		
		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getXdLpUrl(String siteId, String nodeid, String queryString) {
		String result = Cfg.get(XD_SERVER_KEY) + XDLP + siteId+"-"+nodeid;
		
		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getXdCpUrl(String siteId, String nodeid, String sid, String queryString) {
		String result = Cfg.get(XD_SERVER_KEY) + XDCP + siteId+"-"+nodeid+"-"+sid;
		
		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getXdAllPageUserUrl(String siteId, String queryString) {
		String result = Cfg.get(XD_SERVER_KEY) + XDALLPAGEUSE + siteId;
		
		if (queryString != null) {
			result += "?"+queryString;
		}
		return result;
	}
	
	public static String getDynamicImgUrl(String sid, String imgId) {
		return Cfg.get(WEB_SERVER_KEY)+"dynamic/"+sid+"/"+imgId;
	}
	
	public static String getImageUrl(String sid, String imgId) {
		return Cfg.get(WEB_SERVER_KEY)+"images/"+sid+"/"+imgId;
	}
	
	public static String getFileUrl(String sid, String fileId) {
		return Cfg.get(WEB_SERVER_KEY)+"files/"+sid+"/"+fileId;
	}

	final private static Set<String> ADMIN_IP_SET;
	static{
		ADMIN_IP_SET = new HashSet<String>();
		ADMIN_IP_SET.addAll(Arrays.asList(Cfg.get("ADMIN_IP").split(";")));
	}
	public static boolean isFromAdmin(HttpServletRequest request){
		String ip = ServletKit.getIP(request);
		return StringKit.isEmpty(ip) ? false : ADMIN_IP_SET.contains(ip);
	}
}
