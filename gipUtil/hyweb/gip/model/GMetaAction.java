package hyweb.gip.model;

import hyweb.util.Cfg;
import hyweb.util.DaoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GMetaAction {

	private final static String web_params = "$params";
	private final static String ht_param = "param__";
	
	protected JSONObject meta;
	protected JSONObject params ; 

	public GMetaAction(String metaId) throws Exception {
		InputStreamReader isr = null;
		try {
			File f = new File(Cfg.get("META_DIR"), metaId + ".json");
			isr = new InputStreamReader(new FileInputStream(f),"UTF-8"); 
			meta = new JSONObject(new JSONTokener(isr));	
		} finally {
			if(isr!=null){
				isr.close();
			}
		}

	}

	public JSONArray json(String actId, Object... params) throws Exception {
		if(meta!=null){
			JSONObject act = meta.optJSONObject(actId);
			String sql = get_string(act,"sql");
			if(sql!=null){
				return DaoUtils.ja(sql,params);
			}
			
		}
		return null;
	}
	
	private String get_string(JSONObject act, String id) {
		if (act != null && act.has(id)) {
			Object v = act.opt(id);
			if (v instanceof JSONArray) {
				JSONArray arr = act.optJSONArray(id);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < arr.length(); i++) {
					String line = replace_string(arr.getString(i));
					sb.append(line).append('\n');
				}
				return sb.toString();
			} else {
				return replace_string(v.toString());
			}
		}
		return null;
	}
	
	private String replace_string(String line) {
		if (params != null) {
			JSONArray names = params.names();
			if (names != null) {
				for (int i = 0; i < names.length(); i++) {
					String name = names.optString(i);
					String key = ht_param + name;
					String value = params.optString(name);				
					System.out.println("----- " + key +"," + name +"," + value +" [befroe] " + line);
					line = line.replaceAll(key, value);
					System.out.println("----- " + key +"," + name +"," + value +" [after ] " + line);
				}
			}
		}
		return line;
	}
	
	protected boolean has(JSONObject jo, String key){
		return (jo!=null && jo.has(key));
	}
	
	public static void main(String[] args){
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/spring.xml");
		
	}
	
}
