package com.fcm.ctr;

import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;
import hyweb.jo.org.json.JSONObject;
import hyweb.util.SpringLifeCycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;







import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
@RequestMapping("/register")
public class RegisterCtr {
	
	/*    http://{domain}/FCMNotification/webSite/register/new     */
	private DeviceInfoService deviceInfoService = 
			(DeviceInfoService)SpringLifeCycle.getBean("DeviceInfoServiceImpl");
	
	 @RequestMapping(value="new", 
	            method = {RequestMethod.GET, RequestMethod.POST})
	    public void appRegister(HttpServletRequest req,HttpServletResponse res) throws IOException{
		
		 res.setContentType("application/json ;charset=UTF-8");
		 PrintWriter out = res.getWriter();
		 JSONObject json = new JSONObject();
		 
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		 
		 StringBuffer jb = new StringBuffer(); //jb : json body
		 String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { 
			  json.put("status", "N");
			  json.put("seq", "N");
			  json.put("message", "fail : data struct is incorrect  ");
			  out.write(json.toString());
			  return;
		  }
		
		  try {
			  
			  DeviceInfo device =  gson.fromJson(jb.toString(),DeviceInfo.class);
			  
			  if((device.getToken() == null || device.getToken().trim().length() == 0) || 
					  (device.getPackageName() == null || device.getPackageName().trim().length() == 0)
					  ){
				  json.put("status", "N");
				  json.put("seq", "N");
				  json.put("message", "fail : token and packageName  couldn't empty");
				  out.write(json.toString());
				  return;
			  }
			  
			  if(!"A".equals(device.getPlatform()) && !"I".equals(device.getPlatform())){
				  json.put("status", "N");
				  json.put("seq", "N");
				  json.put("message", "fail : platform is incorrect");
				  out.write(json.toString());
				  return;
			  }
			  
			  int pk = -1;
			  pk = deviceInfoService.insert(device);
			 		  
			  if(pk != -1){
				  json.put("status", "Y");
				  json.put("seq", pk);
				  json.put("message", "success");
			  }else{
				  json.put("status", "N");
				  json.put("seq", "");
				  json.put("message", "fail");
			  }
			
			  out.write(json.toString());
			  out.flush();
			  out.close();
			  return;
			  
		  }catch(Exception e){
			  json.put("status", "N");
			  json.put("seq", "N");
			  json.put("message", "fail : data struct is incorrect ");
			  out.write(json.toString());
			  return;
		  }

	 }
	
}
