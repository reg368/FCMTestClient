<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>無標題文件</title>
<link href="<c:url  value='/app/css/design.css' />" rel="stylesheet" type="text/css" />
</head>
<body>
<c:set var="now" value="<%=new java.util.Date()%>" />
<div class="top">
<h1><a href="<c:url value='/app/admin/InfoUser/list/infoUser'/>"  target="mainFrame"><img src="<c:url  value='/app/images/logo.png' />" alt="HyCMS" /></a></h1>
<div class="nav">
	<ul>
		<li><a href="#">回首頁</a></li>
		<li><a href="<c:url value='/app/admin/InfoUser/view/infoUserOneselfPage'/>" target="mainFrame">個人資料維護</a></li>
		<li><a href="<c:url value='/app/login/logout'/>" title="登出" target="_parent">登出</a></li>
	</ul>
</div>
<div class="toplink">
<span class="user">登入者：${InfoUser.name}</span>
<span class="todaytime"><fmt:formatDate pattern="'今天是'yyyy'年'MM'月'dd'日'"  value="${now}" /></span>
</div>
</div>

<div class="leftCol"></div>
<div class="rightCol"></div>
<div class="Bottom">
	億東企業股份有限公司 ©2013 YEEDON ENTERPRISE CO.,LTD.ALL RIGHTS RESERVED.<br />
&nbsp; 開發單位：凌網科技股份有限公司 </div>

</body>
</html>
