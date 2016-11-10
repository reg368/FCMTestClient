<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hyweb.gip.model.session.impl.HttpSessionHandle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
HttpSessionHandle hsh = new HttpSessionHandle(request);
hsh.invalidate();
response.sendRedirect("/admin/app/login/login");

%>
