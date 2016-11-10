package hyweb.gip.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MVEL {
	
	public static String fmt1 = "yyyy/MM/dd HH:ss" ; 
	 
	public static String to_date(Object d) {
	        return to_date(fmt1,d);
	 }
	    
	    public static String to_date(String fmt,Object d) {
	        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
	        if(d instanceof java.sql.Timestamp){
	        	return sdf.format(((java.sql.Timestamp)d).clone());
	        }
	        d = (d!=null) ? d : new Date();
	        return sdf.format(d);
	    }
	    
	    public static Object to_text(Object value,Object dv){
	    	return (value!=null) ? value : dv ; 
	    }
	    
	    public static int length(Object value){
	    	if(value instanceof String){
	    		return ((String) value).trim().length() ;  
	    	}
	    	return 0;
	    }
	    
	    public static int length(String value){
	    	return (value!=null) ? value.trim().length() :  0 ; 
	    }
	    
}
