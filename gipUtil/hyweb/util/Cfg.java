package hyweb.util;

import hyweb.core.kit.CollectionsKit;
import hyweb.core.kit.PropertiesKit;
import hyweb.gip.pattern.handler.StringReplaceHandler;

import java.io.File;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class Cfg {
	final static Map<String, String> MAP;
	static{
		MAP = CollectionsKit.newMap();
		try {
			putProperties(PropertiesKit.loadInCurrentClassDir("config.xml", "UTF-8", Cfg.class));
			putProperties(PropertiesKit.loadByClassPath("config.xml", "UTF-8"));
			genOtherDefaultConfig();
			doReplae();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void putProperties(Properties p){
		Enumeration<String> e = (Enumeration<String>)p.propertyNames(); 
		while(e.hasMoreElements()){
			String key = e.nextElement();
			if(MAP.containsKey(key)){
				MAP.remove(key);
			}
			MAP.put(key, p.getProperty(key));
		}
	}

	private static void doReplae() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		StringReplaceHandler srh = new StringReplaceHandler();
		srh.registered(MAP);
		srh.registered(System.getProperties());
		for(String key : MAP.keySet()){
			String value = MAP.remove(key);
			MAP.put(key, srh.replaceAsMainList(value)[0]);
		}
	}

	private static void genOtherDefaultConfig() throws URISyntaxException, UnknownHostException{
		/*設定WEB-INF真實路徑*/
		MAP.put("DIR_WEB-INF", new File(Thread.currentThread().getContextClassLoader().getResource("config.xml").toURI()).getParentFile().getParentFile().toString());
		/*檔案目錄階層分隔符號*/
		MAP.put("/", System.getProperty("file.separator"));
		/*伺服器IP*/
		MAP.put("SERVER_LOCAL_IP", InetAddress.getLocalHost().getHostAddress());
	}
	/**
	 * 取得屬性
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return MAP.get(key);
	}
}
