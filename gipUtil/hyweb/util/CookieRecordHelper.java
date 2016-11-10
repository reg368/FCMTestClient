package hyweb.util;

import hyweb.util.cacheable.DomHelper;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jdom2.Document;

public class CookieRecordHelper {
	private static String RECORD_LIST_KEY = "recordList";
	private static String WEB_DOMAIN_KEY = "WEB_DOMAIN";
	private static int MAX_NUM = 3;
	
	public static void insertCookieRecord(HttpServletRequest request, HttpServletResponse response, String siteId, Document xml) throws JsonParseException, JsonMappingException, IOException {
		String url = DomHelper.getStringByXpath("/hpMain/shortLink", xml);
		String title = DomHelper.getStringByXpath("/hpMain/cpTitle", xml);
		
		Cookie cookie = getCookie(request.getCookies(), RECORD_LIST_KEY+"_"+siteId);
		
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> list = null;
		if (cookie != null) {
			list = mapper.readValue(URLDecoder.decode(cookie.getValue(), "UTF-8"), List.class);
		} else {
			list = new ArrayList<Map<String, String>>();
		}
		
		if (list.size() > 0) {
			if (url.equals(list.get(0).get("url"))) {
				return;
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", url);
		map.put("title", title);
		
		list.add(0, map);
		
		if (list.size() > MAX_NUM) {
			list.remove(MAX_NUM);
		}
		
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, list);
		
		String encode = URLEncoder.encode(writer.toString(), "UTF-8");
		encode = StringUtils.replace(encode, "+", "%20");
		
		Cookie newCookie = new Cookie(RECORD_LIST_KEY+"_"+siteId, encode);
		newCookie.setDomain(Cfg.get(WEB_DOMAIN_KEY));
		response.addCookie(newCookie);
	}
	
	public static Cookie getCookie(Cookie[] cookies, String key) {
		Cookie result = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					result = cookie;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static class Record {
		private String url;
		private String title;
		
		public Record(String url, String title) {
			this.url = url;
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
}
