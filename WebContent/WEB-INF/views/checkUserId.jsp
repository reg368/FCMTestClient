<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hyweb.gip.pojo.custom.CustomRequest,
				hyweb.gip.dao.service.InfoUserService,
				hyweb.util.SpringLifeCycle" %>
<%
 
    response.setContentType("text/html");
	CustomRequest req = new CustomRequest(request);
	String userid = req.getParameter("userid");
	InfoUserService infoUserCustom = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");
    if(userid != null && userid.length()!=0){
    	System.out.println("userid = "+userid);
    	if(infoUserCustom.selectUserId(userid)==0){
    		out.println("false");
        }else{
        	out.println("true");
        }
    }else{
    	out.println("false");
    }
 
%>