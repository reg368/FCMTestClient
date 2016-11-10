package hyweb.util;

import hyweb.core.kit.StringKit;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlHelper {
	/**
	 * 類似jstl的core url <br />
	 * 例如：http://xxx.xxx.xxx/&lt;serverName&gt;/{path} <br />
	 * 如果要獲取其它站台的URL，請勿使用
	 * @param path
	 * @return
	 */
	public static String getUrl(String path) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUriString();
	}
	
	public static String getQueryString(Map<String, String[]> map, String... notName) {
		String queryString = "";
		List<String> list = Arrays.asList(notName);
		for (String key : map.keySet()) {
			if (!list.contains(key)) {
				String[] args = map.get(key);
				for (String arg : args) {
					if (!"".equals(queryString)) {
						queryString += "&";
					}
					try {
						queryString += key + "=" + URLEncoder.encode(arg, "UTF-8");
					} catch (Exception e) {
						
					}
				}
			}
		}
		
		return queryString;
	}
	
	public static String delParam(String args, String paramName) {
		if (StringKit.isBlank(args)) {
			return args;
		}
		String regex = paramName+"=[^&]*&?";
		return args.replaceAll(regex, "");
	}
}
