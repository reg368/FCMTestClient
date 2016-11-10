package hyweb.gip.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JOObject extends JSONObject {
	
	public long lastModified = 0;
		
	public JOObject(JSONObject jo){
		super();
		JSONArray arr = jo.names();
		for (int i = 0; i < arr.length(); i++) {
			String name = arr.optString(i);
			try {
				put(arr.optString(i), jo.opt(name));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public JOObject(JSONTokener tokener) {
		super(tokener);
	}

	@SuppressWarnings("unchecked")
	public static Map<String,Object> to_map(JSONObject jo){
		Map<String,Object> m = new HashMap<String,Object>();
		Iterator<String> ite = jo.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			Object o = jo.opt(key);
			if(o instanceof JSONObject){
				m.put(key, to_map((JSONObject)o));
			} else if ( o instanceof JSONArray){
				m.put(key, to_list((JSONArray)o));
			} else{
				m.put(key,o);
			}
		}
		return m;
	}
	
	public static List<Object> to_list(JSONArray jo){
		List<Object> list = new ArrayList<Object>();
		int len = jo.length();
		for(int i=0; i<len; i++){
			Object o = jo.opt(i);
			if(o instanceof JSONObject){
				list.add(to_map((JSONObject)o));
			} else if ( o instanceof JSONArray){
				list.add(to_list((JSONArray)o));
			} else{
				list.add(o);
			}			
		}
		return list;
	}
	
}
