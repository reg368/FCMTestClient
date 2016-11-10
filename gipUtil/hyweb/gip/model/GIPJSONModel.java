package hyweb.gip.model;

import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.cglib.beans.BeanMap;

public class GIPJSONModel extends JSONObject {
	
	private final static String  pre_param = "param__";
	private final static String  pre_user = "user__";

	public GIPJSONModel(){
		super();
	}
	
	public GIPJSONModel(HttpServletRequest req){
		super();
		put_request(req);
		put_user(req);
	}
		
	public void put_request(HttpServletRequest req){
		// only scan top level 
		put_request(req.getParameterMap());
	}
	
	public void put_user(HttpServletRequest req){
		// only scan top level 
		Object user_obj = new HttpSessionHandle(req).getLoginUser();
		if(user_obj!=null){
			BeanMap m = BeanMap.create(user_obj);
			put_bean(pre_user,m);
		}
	}
	
	public void put_request(Map<String,String[]> m){
		Set<Entry<String, String[]>> set =   m.entrySet();
		for(Entry<String, String[]> item : set){
			String name = item.getKey();
			String[] value = item.getValue();
			put(pre_param+name,  (value.length>1) ? value : value[0]    );		
		}
	}
	
	public void put_bean(String prefix , Map<String,Object> m){
		Set<Entry<String, Object>> set =   m.entrySet();
		for(Entry<String, Object> item : set){
			String name = item.getKey();
			Object value = item.getValue();
			if(value!=null){
				put(prefix+name, value );		
			}
		}
	}
	
}
