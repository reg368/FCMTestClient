<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="p" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
function changePage(){
	ajax = decodeURI(
        (RegExp('ajax=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
	if(ajax == 1){
		alert('1');
	}else{
		alert('0');
	}
}
$(document).ready(function(){
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>權限群組管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>權限群組清單</span></h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
 	<c:if test="${limits.add}">
   		<li><a href='<c:url value="/app/admin/Ugrp/insert/ugrpPage"/>'>
   		<img src='<c:url value="/app/images/icon_treeAdd.gif"/>' alt="權限群組新增">權限群組新增</a></li>
   </c:if>
   <li><a href="#" onclick="javascript:history.go(-1);">
   <img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁" onclick="history.back();">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!-- navfunction -->
<div class="listfunction">
<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
</div>

<!-- 列表 -->
<div class="listTb">
<Table summary="資料表格">

<tr>
	<th class="aCenter">權限群組名稱</th>
	<th class="aCenter">權限群組描述</th>
	<th class="aCenter">權限群組Ap對應管理</th>
</tr>                  
 					<c:forEach items="${ugrps}" var="ugrp">
						<tr>
							<td><a href='<c:url value="/app/admin/Ugrp/view/ugrpPage?ugrpid=${ugrp.ugrpid}"/>'><c:out value="${ugrp.ugrpname}"/></a></td>
							<td><c:out value="${ugrp.remark}"/></td>
							<td><a href='<c:url value="/app/admin/Ugrp/list/ugrpAp?ugrpid=${ugrp.ugrpid}"/>' title="權限群組Ap對應管理"><img src='<c:url value="/app/images/icon_editSelect.gif"/>' alt="權限群組Ap對應管理"/></a></td>
						</tr>
					</c:forEach>             
</TABLE>
</div>

<div class="listfunction">
<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
