package hyweb.gip.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JOConfig extends JSONObject {
	
	public JOConfig(JSONObject jo) {
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
	
}
