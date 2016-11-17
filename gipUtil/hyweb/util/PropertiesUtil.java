package hyweb.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {
//	public static Map<String,String> props = null;
	
	private Log logger = LogFactory.getLog("baphiqMedia");
	
	public static String getProp(String propName,String fileName,String defaultValue){
		try{
			String propValue = ResourceBundle.getBundle(fileName).getString(propName);
			if(propValue == null || "".equals(propValue)){
				propValue = defaultValue.trim();
			}
			return propValue.trim();
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static String getProp(String propName){
		return PropertiesUtil.getProp(propName, "config","");
	}
	
	public static Map<String,String> getAllProp(String fileName){
		Map<String,String> props = new HashMap<String,String>();
		ResourceBundle res = ResourceBundle.getBundle(fileName);
		Enumeration<String> keys = res.getKeys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String value = res.getString(key);
			props.put(key, value);
		}
		return props;
	}
	
	public static Map<String,String> getAllProp(){
		return PropertiesUtil.getAllProp("config");
	}
}
