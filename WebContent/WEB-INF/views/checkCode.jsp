<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hyweb.gip.pojo.custom.CustomRequest" %>
<%
 
    response.setContentType("text/html");
	CustomRequest req = new CustomRequest(request);
    if(req.getParameter("checkCode") != null && req.getParameter("checkCode").length()!=0){      //從 session 屬性取出 servlet 產生的驗證碼
    	if(req.getParameter("checkCode").equals(session.getAttribute("code"))){
    		out.println("true");
        }else{
        	out.println("false");
        }
    }else{
    	out.println("false");
    }
 
%>
