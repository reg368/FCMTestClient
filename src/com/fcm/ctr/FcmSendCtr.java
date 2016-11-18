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

	/* http://{domain}/FCMNotification/webSite/send/device */
	@RequestMapping(value = "device", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void device(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		JSONObject body = new JSONObject();
		body.put("to", PropertiesUtil.getProp("fcm.test.token"));
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("body", "body string here");
		notification.put("title", "title string here");

		body.put("notification", notification);

		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.message.send.url"));
		System.out.println(result);

	}
	
	/* http://{domain}/FCMNotification/webSite/send/topic */
	@RequestMapping(value = "topic", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void topic(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		JSONObject body = new JSONObject();
		body.put("to", "/topics/"+PropertiesUtil.getProp("fcm.test.topic"));
		
		JSONObject notification = new JSONObject();
		notification.put("body", "group message string here");
		notification.put("title", "group message string here");
		body.put("notification", notification);
		//body.put("data", notification);
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.message.send.url"));
		System.out.println(result);
	
	}
	
	/* http://{domain}/FCMNotification/webSite/send/groupCreate */
	@RequestMapping(value = "groupCreate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void groupCreate(HttpServletRequest req, HttpServletResponse res)
			throws IOException{
		
		JSONObject body = new JSONObject();
		body.put("operation", "create");
		body.put("notification_key_name", PropertiesUtil.getProp("fcm.group.notification_key_name"));
		String[] tokens = {PropertiesUtil.getProp("fcm.test.token")};
		body.put("registration_ids", tokens);
	    
		
		String result = postUrlWithJsonString(body.toString(),PropertiesUtil.getProp("fcm.group.create.url"));
		System.out.println(result);
		
	}

	
	/* http://{domain}/FCMNotification/webSite/send/groupAdd */
	@RequestMapping(value = "groupAdd", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void groupAdd(HttpServletRequest req, HttpServletResponse res)
			throws IOException{
		
		
	}
	
	
	private String postUrlWithJsonString(String jsonStr,String url) throws IOException{
		StringEntity entity = new StringEntity(jsonStr);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("Authorization",
				"key=" + PropertiesUtil.getProp("fcm.server.key"));
		post.setHeader("content-type", "application/json");
		post.setHeader("project_id",PropertiesUtil.getProp("fcm.test.appid"));
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
