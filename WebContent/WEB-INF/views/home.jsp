<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
		<title>HyCMS 網站內容管理系統</title>
	</head>
	<frameset rows="137px,*" border="0"  frameborder="0" framespacing="0">
		<frame src="<c:url value='/app/admin/Home/top'/>" name="topFrame" frameborder="0" scrolling="no" id="topFrame"  title="topFrame" />
	
		<frameset cols="182,*" frameborder="no" border="0" framespacing="0">
  		<frame src="<c:url value='/app/admin/Home/left'/>" name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame" />
  		<frame src="<c:url value='/app/admin/InfoUser/list/infoUser?ajax=0'/>"  name="mainFrame" scrolling="auto" id="mainFrame" title="rightFrame" />
</frameset>
		
		<!--frame src="<c:url value='/app/page/ListShowFormAction/list/dataSetQuery/dataSetList'/>" name="mainFrame" id="mainFrame" title="mainFrame" /-->
		<noframes><body></body></noframes>
	</frameset>
</html>
