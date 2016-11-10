<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href='<c:url value="/app/css/zTreeStyle/zTreeStyle.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery.ztree.all-3.5.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/deptTree.js'/>" type="text/javascript"></script>

<title>HyCMS 網站內容管理系統</title>
<script type="text/javascript">
	var zNodes = <c:out value="${node}" escapeXml="false"/>
	
	function getEditPage(id,name){
		window.opener.document.getElementById("deptid").value =id;
		window.opener.document.getElementById("deptname").value =name;
	    window.top.close();
	} 
</script>
</head>
<body class="layout">
 	<div id="treeFrame" align="center">
 		<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>
