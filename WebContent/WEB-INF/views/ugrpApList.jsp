<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
function allSelect(apid){
	document.ugrpApForm.apid.value = parseInt(apid);
	document.ugrpApForm.apfunc.value = 255;
	document.ugrpApForm.action = "/admin/app/admin/Ugrp/update/ugrpApAction";
	document.ugrpApForm.submit();
}

function noSelect(apid){
	document.ugrpApForm.apid.value = parseInt(apid);
	document.ugrpApForm.apfunc.value = 0;
	document.ugrpApForm.action = "/admin/app/admin/Ugrp/update/ugrpApAction";
	document.ugrpApForm.submit();
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
<h2>權限群組Ap對應管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>權限群組Ap對應清單</span></h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   <li><a href="#" onclick="javascript:history.go(-1);">
   <img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁" onclick="history.back();">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>



<!-- 列表 -->
<div class="listTb">
<Table summary="資料表格">

<tr>
	<th class="aCenter">Ap功能名稱</th>
	<th class="aCenter">條列權限</th>
	<th class="aCenter">檢視權限</th>
	<th class="aCenter">新增權限</th>
	<th class="aCenter">修改權限</th>
	<th class="aCenter">刪除權限</th>
	<th class="aCenter">查詢權限</th>
	<th class="aCenter">列印權限</th>
	<th class="aCenter">匯出權限</th>
	<c:if test="${limits.edit}">
		<th class="aCenter">整組設定</th>
	</c:if>
</tr>                  
 					<c:forEach items="${ugrpAps}" var="ugrpAp">
						<tr>
							
							<td><a href='<c:url value="/app/admin/Ugrp/view/ugrpApPage?ugrpid=${ugrpAp.ugrpid}&apid=${ugrpAp.apid}"/>'><c:out value="${ugrpAp.apcname}"/></a></td>
							<td><div id="${ugrpAp.apid}s0"></div></td>
							<td><div id="${ugrpAp.apid}s1"></div></td>
							<td><div id="${ugrpAp.apid}s2"></div></td>
							<td><div id="${ugrpAp.apid}s3"></div></td>
							<td><div id="${ugrpAp.apid}s4"></div></td>
							<td><div id="${ugrpAp.apid}s5"></div></td>
							<td><div id="${ugrpAp.apid}s6"></div></td>
							<td><div id="${ugrpAp.apid}s7"></div></td>
							<c:if test="${limits.edit}">
								<td><input type="button" value="全有" onclick="allSelect(${ugrpAp.apid});">
								    <input type="button" value="全無" onclick="noSelect(${ugrpAp.apid});"></td>
							</c:if>
						</tr>
						<script type="text/javascript">
 					
							function init(func,apid){
								var num =  parseInt(func);
								
								for (var i = 0; i < 8; i++) {
									if ((num & (1<<i)) > 0) {
										$('#'+apid+'s'+i).html('有');
									} else {
										$('#'+apid+'s'+i).html('無');
									}
								}
							}
							
							init(<c:out value="${ugrpAp.apfunc}"/>,<c:out value="${ugrpAp.apid}"/>);
					</script>
					</c:forEach>             
</TABLE>
</div>

<form:form commandName="ugrpAp" action="" name="ugrpApForm" method="POST">
<form:hidden path="ugrpid"/>
<form:hidden path="apid"/>
<form:hidden path="apfunc"/>
</form:form>


<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
