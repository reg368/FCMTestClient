package com.fcm.ctr;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/register")
public class RegisterCtr {
	
	 @RequestMapping(value="device",  produces="application/json; charset=utf-8",
	            method = {RequestMethod.GET, RequestMethod.POST})
	    public String appRegister(HttpServletRequest req,HttpServletResponse res) throws IOException{
		 
		 StringBuffer jb = new StringBuffer(); //jb : json body
		 String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		  System.out.println("get : "+jb.toString());
		  
		  
		  return "index2";
		 
	 }
	
}
