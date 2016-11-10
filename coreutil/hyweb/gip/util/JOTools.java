package hyweb.gip.util;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;

public class JOTools {
	
    protected static Map<String,JOObject> cache;

    protected static Map<String,JOObject> cache() {
        if (cache == null) {
            cache = new HashMap<String,JOObject>();
        }
        return cache;
    }
    
    
    public static JOObject  jo(File f) throws Exception {
    	Reader reader = null ; 
    	JOObject jo = null;
        try {
            String fname = f.getAbsolutePath();
            jo  = cache().get(fname);
            if (jo==null || (f.exists() && f.lastModified() > jo.lastModified)) {
        			reader = new FileReader(f);
        			jo = new JOObject(new JSONTokener( reader));		
                    cache().put(f.getAbsolutePath(), jo);
            }
        } finally  {
        	reader.close();
        }
        return jo;
    }
    
	public static synchronized JSONObject loadString(String text){
		if(text==null || text.trim().length()==0){
			return null;
		}
		Reader reader = new StringReader(text);
		JSONTokener tk =  new JSONTokener( reader);	
		JSONObject jo;
		try {
			jo = new JSONObject(tk);
			reader.close();
			return jo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public  static  synchronized JSONObject load(File f){
		Reader reader = null ; 
		JSONObject jo = null;
		try {
			reader = new FileReader(f);
			jo = new JOObject(new JSONTokener( reader));
			reader.close();
			return jo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static synchronized JSONObject xml(String text){
		try {
			return XML.toJSONObject(text);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	
}
