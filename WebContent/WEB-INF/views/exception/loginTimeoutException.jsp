<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<title>登入失效</title>
</head>
<body>
<script type="text/javascript">
	alert("登入失效，請重新登入!");
	window.top.location.href = '<c:url value="/app/login/login"/>';
</script>
</body>
</html>