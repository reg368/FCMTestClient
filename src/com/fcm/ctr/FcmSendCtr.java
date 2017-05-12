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

	/* 單一裝置token 送推撥 */
	/* http://{domain}/FCMNotification/webSite/send/device */
	@RequestMapping(value = "device", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void device(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		JSONObject body = new JSONObject();
		body.put("to", PropertiesUtil.getProp("fcm.test.token.ipad"));
		//加上 priority : high    *讓app在背景或關閉時也會跳出推撥通知
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("body", "body string here");
		notification.put("title", "title string here");

		body.put("notification", notification);

		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.message.send.url"));
		System.out.println(result);

	}
	
	/* (topic)由 client app 端訂閱"topic" , server 端針對topic 送出推撥  */
	/* http://{domain}/FCMNotification/webSite/send/topic */
	@RequestMapping(value = "topic", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void topic(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		JSONObject body = new JSONObject();
		body.put("to", "/topics/"+PropertiesUtil.getProp("fcm.test.topic"));
		//加上 priority : high    *讓app在背景或關閉時也會跳出推撥通知
		body.put("priority", "high");
		
		JSONObject notification = new JSONObject();
		notification.put("body", "topic message string here");
		notification.put("title", "topic message string here");
		body.put("notification", notification);
		//body.put("data", notification);
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.message.send.url"));
		System.out.println(result);
	
	}
	
	/* 向Firebase Server 建立一群組 , 推撥時透過群組key發送訊息 (建立成功會回傳notification_key , 要保存好)  */
	/* http://{domain}/FCMNotification/webSite/send/groupCreate */
	@RequestMapping(value = "groupCreate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void groupCreate(HttpServletRequest req, HttpServletResponse res)
			throws IOException{
		
		JSONObject body = new JSONObject();
		body.put("operation", "create");
		body.put("notification_key_name", PropertiesUtil.getProp("fcm.group.notification_key_name"));
		String[] tokens = {PropertiesUtil.getProp("fcm.test.token.ipad")};
		body.put("registration_ids", tokens);
	    System.out.println("post json : "+body.toString());
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.group.url"));
		System.out.println(result);
		
	}

	
	/* 推撥群組增加成員 (需預先取得群組的notification_key) */
	/* http://{domain}/FCMNotification/webSite/send/groupAdd */
	@RequestMapping(value = "groupAdd", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void groupAdd(HttpServletRequest req, HttpServletResponse res)
			throws IOException{
		
		
		JSONObject body = new JSONObject();
		body.put("operation", "add");
		body.put("notification_key_name", PropertiesUtil.getProp("fcm.group.notification_key_name"));
		//增加的裝置token
		String[] tokens = {PropertiesUtil.getProp("fcm.test.token.ipad"),PropertiesUtil.getProp("fcm.test.token.iphone")};
		body.put("registration_ids", tokens);
		body.put("notification_key", PropertiesUtil.getProp("fcm.group.notification_key"));
		
	    System.out.println("post json : "+body.toString());
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.group.url"));
		System.out.println(result);
		
	}
	
	/* (device group)透過預先建立的群組發送推撥 (需預先取得群組的notification_key)  */
	/* http://{domain}/FCMNotification/webSite/send/groupSend */
	@RequestMapping(value = "groupSend", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void groupSend(HttpServletRequest req, HttpServletResponse res)
			throws IOException{
		
		JSONObject body = new JSONObject();
		body.put("to", PropertiesUtil.getProp("fcm.group.notification_key"));
		//加上 priority : high    *讓app在背景或關閉時也會跳出推撥通知
		body.put("priority", "high");
		
		JSONObject notification = new JSONObject();
		notification.put("body", "body group string here");
		notification.put("title", "title group string here");

		body.put("notification", notification);
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.message.send.url"));
		System.out.println(result);
	}
	
	
	public String postUrlWithJsonString(String jsonStr,String url) throws IOException{
		StringEntity entity = new StringEntity(jsonStr);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("Authorization",
				"key=" + PropertiesUtil.getProp("fcm.server.key"));
		post.setHeader("content-type", "application/json");
		post.setHeader("project_id",PropertiesUtil.getProp("fcm.sender.id"));
		post.setEntity(entity);
		
		HttpResponse response = httpClient.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		
		
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
}
