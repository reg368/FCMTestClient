package hyweb.util;

import hyweb.gip.pojo.custom.CustomRequest;

import javax.servlet.http.HttpServletRequest;

public class GetIP {
	public String getIpAddr(HttpServletRequest request) {
		CustomRequest req = new CustomRequest(request);
	       String ip = req.getHeader("x-forwarded-for");      
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	          ip = req.getHeader("Proxy-Client-IP");      
	      }      
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	          ip = req.getHeader("WL-Proxy-Client-IP");      
	       }      
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	           ip = req.getRemoteAddr();      
	      }      
	     return ip;      
	}

}
