package hyweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import hyweb.core.kit.StringKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionResultHelper {
	private static String QUERY_PARAM_KEY = "__QUERY_PARAM";
	
	public static void saveRequestParam(HttpServletRequest request) throws UnsupportedEncodingException {
		
		boolean first = true;
		String[] values = null;
		String result = "";
		for (String key : request.getParameterMap().keySet()) {
			values = request.getParameterValues(key);
			if (values != null) {
				for (String value : values) {
					if (first == false) {
						result += "&";
					}
					result += key + "=" + URLEncoder.encode(StringKit.notNull(value), "UTF-8");
					
					first = false;
				}
			}
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(QUERY_PARAM_KEY, result);
	}
	
	public static String getRequestParam(String targetUrl, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String queryParam = (String) session.getAttribute(QUERY_PARAM_KEY);
		queryParam = StringKit.notNull(queryParam);
		
		String result = null;
		if (targetUrl.indexOf('?') == -1) {
			result = targetUrl + "?" + queryParam;
		} else {
			result = targetUrl + "&" + queryParam;
		}
		
		return result;
	}
}
