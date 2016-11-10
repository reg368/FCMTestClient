package hyweb.util;

import hyweb.core.kit.StringKit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamParser {
	private static final String P_REGEX = "\\$\\{([^\\.^\\}]+)[\\.]*([^\\.^\\}]*)[\\.]*([^\\}]*)\\}";
	
	public Map<String, Param> parseString(String str) {
		Map<String, Param> map = new HashMap<String, Param>();
		
		if (str == null) return map;
		
		Pattern pattern = Pattern.compile(P_REGEX);
	    Matcher matcher = pattern.matcher(str);
	    while(matcher.find()) {
	    	Param p = new Param();
	    	String key = matcher.group(0);
	    	if (!StringKit.isEmpty(key)) {
		    	p.setKey(key);
		    	p.setType(matcher.group(1));
		    	p.setName(matcher.group(2));
		    	p.setAttr(matcher.group(3));
	    	}
	    	
	    	map.put(key, p);
	    }
		return map;
	}
	
	public static class Param {
		private String key; //${session.user.id}
		private String type; //session
		private String name; //user
		private String attr; //id
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAttr() {
			return attr;
		}
		public void setAttr(String attr) {
			this.attr = attr;
		}
	}
}
