package com.fcm.ctr;

import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;
import hyweb.jo.org.json.JSONObject;
import hyweb.util.PropertiesUtil;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
@RequestMapping("/SendMessage")
public class SendMessageCtr {
	
	/*    http://{domain}/FCMNotification/webSite/SendMessage/requireGPS    */
	//    由client  藉由 server  發出請求 要求device 取得位置資訊
	/*
			"token":"1234",
			"packagename":"james.com.fcmtest"	
	 * */
	private DeviceInfoService deviceInfoService = 
			(DeviceInfoService)SpringLifeCycle.getBean("DeviceInfoServiceImpl");
	
	 @RequestMapping(value="requireGPS", 
	            method = {RequestMethod.POST})
	    public void requireGPS(
	    		@RequestParam(value = "token", required = false) String token,
	    		HttpServletRequest req,HttpServletResponse res) throws IOException{
		
		 DeviceInfo info  = deviceInfoService.selectByClientToken(token);
		 
		 if(info != null ){
			 
			 	PrintWriter out = res.getWriter();
			 
			 	JSONObject body = new JSONObject();
				body.put("to", info.getDevicetoken());
				//加上 priority : high    *讓app在背景或關閉時也會跳出推撥通知
				body.put("priority", "high");
				JSONObject notification = new JSONObject();
				notification.put("msg", "require GPS");
				notification.put("clientToken", info.getClienttoken());

				body.put("notification", notification);
				
				String result = new FcmSendCtr().postUrlWithJsonString(body.toString(), PropertiesUtil.getProp("fcm.message.send.url"));
				System.out.println(result);
				
				info.setLastsenddate(Calendar.getInstance().getTime());
				info.setLastsendtoken(info.getClienttoken());
				deviceInfoService.insert(info);
				
				out.write(result);
		 }

	 }
	 
	 /*    http://{domain}/FCMNotification/webSite/SendMessage/respondGPS    */
		//    由client  藉由 server  發出請求 要求device 取得位置資訊
		/*
		 * {
				"token":"1234",
				"packagename":"james.com.fcmtest"	
			}
		 * */
		 @RequestMapping(value="respondGPS", 
		            method = {RequestMethod.POST})
		    public void respondGPS(
		    		@RequestParam(value = "token", required = false) String token,
		    		@RequestParam(value = "longitude", required = false) String longitude,
		    		@RequestParam(value = "latitude", required = false) String latitude,
		    		HttpServletRequest req,HttpServletResponse res) throws IOException{
			
			 DeviceInfo info  = deviceInfoService.selectByDeviceToken(token);
			 
			 if(info != null ){
				 
				 	PrintWriter out = res.getWriter();
				 
				 	JSONObject body = new JSONObject();
					body.put("to", info.getClienttoken());
					//加上 priority : high    *讓app在背景或關閉時也會跳出推撥通知
					body.put("priority", "high");
					JSONObject notification = new JSONObject();
					notification.put("longitude", longitude);
					notification.put("latitude", latitude);
					notification.put("deviceToken", info.getDevicetoken());

					body.put("notification", notification);
					
					String result = new FcmSendCtr().postUrlWithJsonString(body.toString(), PropertiesUtil.getProp("fcm.message.send.url"));
					System.out.println(result);
					
					info.setLastsenddate(Calendar.getInstance().getTime());
					info.setLastsendtoken(info.getDevicetoken());
					deviceInfoService.insert(info);
					
					out.write(result);
			 }

		 }
	
}
