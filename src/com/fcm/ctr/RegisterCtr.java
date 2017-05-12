package com.fcm.ctr;

import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;
import hyweb.jo.org.json.JSONObject;
import hyweb.util.SpringLifeCycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

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
	
	/*    http://{domain}/FCMNotification/webSite/register/registed     */
	//    註冊 : 由 client app 發送註冊訊息(包含 自己的token 和  穿戴裝置的token
	/*
	 * {
			"clientpaltform":"android",
			"clienttoken":"12345",
			"devicetoken":"12345"
			"packagename":"james.com.fcmtest"
		}
	 * */
	private DeviceInfoService deviceInfoService = 
			(DeviceInfoService)SpringLifeCycle.getBean("DeviceInfoServiceImpl");
	
	 @RequestMapping(value="registed", 
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
			  json.put("message", "fail : json format is incorrect  ");
			  out.write(json.toString());
			  return;
		  }
		
		  try {
			  DeviceInfo device =  gson.fromJson(jb.toString(),DeviceInfo.class);
			  if(device.getClienttoken() != null && device.getDevicetoken() != null){
				  
				  DeviceInfo client = deviceInfoService.selectByClientToken(device.getClienttoken());
				  if(client != null && client.getClienttoken() != null){
					  json.put("status", "N");
					  json.put("message", "fail : client token exist");
					  out.write(json.toString());
					  return;
				  }
				  
				  device.setCreatedate(Calendar.getInstance().getTime());
				  deviceInfoService.insert(device);
				  
				  json.put("status", "Y");
				  json.put("message", "register success");
				  out.write(json.toString());
				  return;
				  
			  }else{
				  json.put("status", "N");
				  json.put("seq", "N");
				  json.put("message", "fail : token incorrect ");
				  out.write(json.toString());
				  return;
			  }

		  }catch(Exception e){
			  json.put("status", "N");
			  json.put("message", "fail : json format is incorrect or insert / update fail ");
			  out.write(json.toString());
			  e.printStackTrace(System.out);
			  return;
		  }

	 }
	
}
