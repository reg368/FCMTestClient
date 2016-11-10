package hyweb.gip.model;

import hyweb.util.cacheable.DomHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.json.JSONObject;
import org.springframework.cglib.beans.BeanMap;

public class GModelUtils {
	
	public static String ht_param_path="/htPage/htParams/htParam" ;
	
	public static void proc_ht_params(Document doc, GIPJSONModel m){	
		List<Element> list = DomHelper.getListByXpath(ht_param_path, doc) ;
		for(Element e : list){
			proc_ht_params(doc,e,m);
		}	
	}
	
	private static void proc_ht_params(Document doc,Element param, GIPJSONModel m){
		String path = param.getAttributeValue("path");
		String ref = param.getAttributeValue("ref");
		List<Element> list = DomHelper.getListByXpath(path, doc) ;
		for(Element e : list){
			proc_ht_params_action(e,m,ref);
		}	
	}
	
	private  static void proc_ht_params_action(Element elem, GIPJSONModel m ,String ref){
		String ref_value = m.optString(ref,null);
		if(ref_value!=null){
			String text = elem.getText();
			text = text.replaceAll(ref, ref_value);
			elem.setText(text);
		}
	}
	
	public static void request_to_bean(Object bean , Map<String,String[]> req) throws Exception{
		if(bean!=null){
			BeanMap m = BeanMap.create(bean);
			Iterator ite = m.keySet().iterator();
			while(ite.hasNext()){
				Object key = ite.next();
				set_bean(m,key,req);
			}
		}
	}
	
	private static void set_bean(BeanMap m, Object key , Map<String,String[]> req) throws Exception {
		if (req.containsKey(key)){
			String value = req.get(key)[0]; // 目前只支援一個變數
			Class c = m.getPropertyType((String)key);
			if( c.equals(Integer.class) || c.equals(int.class)){
				m.put(key, Integer.parseInt(value));
			} else 	if( c.equals(Long.class) || c.equals(long.class)){
				m.put(key, Long.parseLong(value));
			} else 	if( c.equals(Double.class) || c.equals(double.class)){
				m.put(key, Long.parseLong(value));
			} else 	if( c.equals(Date.class)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				m.put(key, sdf.parse(value));
			} else { // 
				m.put(key, value);
			}
		} else {
			m.put(key, null);
		}
	}
	
	
	public static  String param(JSONObject jo , Map<String,String[]>req , String name){
		System.out.println("===== jo : " + jo ) ; 
		System.out.println("===== req : " + new JSONObject (req) ) ; 
		String value = jo.optString(name,null);
		if(value!=null){
			Set<Map.Entry<String,String[]>> list = req.entrySet();
			for(Entry<String, String[]> entry : list){
				String key = entry.getKey();
				String v = entry.getValue()[0];
				value = value.replaceAll("$param__"+key, v);
				value = value.replaceAll("$user__"+key, v);
			}
		}
		return value;
	}
	
}
