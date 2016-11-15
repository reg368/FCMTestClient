package com.fcm.ctr;

import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;
import hyweb.util.SpringLifeCycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;





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
	
	
	private DeviceInfoService deviceInfoService = 
			(DeviceInfoService)SpringLifeCycle.getBean("DeviceInfoServiceImpl");
	
	 @RequestMapping(value="device",  produces="application/json; charset=utf-8",
	            method = {RequestMethod.GET, RequestMethod.POST})
	    public String appRegister(HttpServletRequest req,HttpServletResponse res) throws IOException{
		 
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		 
		 StringBuffer jb = new StringBuffer(); //jb : json body
		 String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		  Map map = (Map) gson.fromJson(jb.toString(), Object.class);
		  
		  Map contentMap = (Map)map.get("content");  
		  String packageName = (String)contentMap.get("packageName");
		  String platform = (String)contentMap.get("platform");
		  String token = (String)contentMap.get("token");
		  
		  DeviceInfo device = new DeviceInfo();
		  device.setToken(packageName);
		  device.setToken(platform);
		  device.setToken(token);
		  
		  deviceInfoService.insert(device);
		  
		  
		  return "index2";
		 
	 }
	
}
