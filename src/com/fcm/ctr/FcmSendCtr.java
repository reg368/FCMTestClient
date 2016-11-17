package com.fcm.ctr;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONObject_old;
import hyweb.util.PropertiesUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/send")
public class FcmSendCtr {

	
	/*    http://{domain}/FCMNotification/webSite/send/notification     */
	 @RequestMapping(value="notification", 
	            method = {RequestMethod.GET, RequestMethod.POST})
	    public void appRegister(HttpServletRequest req,HttpServletResponse res) throws IOException{

		 JSONObject body  = new JSONObject();
		 body.put("to", PropertiesUtil.getProp("fcm.test.token"));
		 body.put("priority", "high");
	 }
}
