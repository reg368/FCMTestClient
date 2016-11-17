package com.fcm.ctr;

import hyweb.jo.org.json.JSONObject;
import hyweb.util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
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
		 
		 JSONObject notification   = new JSONObject();
		 notification.put("body", "body string here");
		 notification.put("title", "title string here");
		 
		 body.put("notification", notification);
		 
		 StringEntity entity =new StringEntity(body.toString());
		 System.out.println("json : "+body.toString());
		 
		 HttpClient httpClient = HttpClientBuilder.create().build();
		 
		 HttpPost post = new HttpPost(PropertiesUtil.getProp("fcm.message.send.url"));
		 post.setHeader("Authorization", "key="+PropertiesUtil.getProp("fcm.server.key"));
		 post.setHeader("content-type", "application/json");
		 post.setEntity(entity); 
		 
		 HttpResponse response = httpClient.execute(post);
		 
		 BufferedReader rd = new BufferedReader(
	                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
		 
	 }
}
