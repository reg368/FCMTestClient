<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="p" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<title>HyCMS 網站內容管理系統</title>
</head>
<body class="layout">
	<div class="content">
		<h2>
			AP類別管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"/><span>AP類別列表</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
					<c:if test="${limits.add}">
						<li><a href="/admin/app/admin/ApCat/insert/apCatPage"><img
								src='<c:url value="/app/images/icon_treeAdd.gif"/>' alt="AP類別新增"/>AP類別新增</a></li>
					</c:if>
					<li><a href="#" onclick="javascript:history.go(-1);"><img
							src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁"/>返回前頁</a></li>
				</ul>
			</div>
			<jsp:include page="include/fontSize.jsp"/>
		</div>

      <div class="listfunction">
		<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
	</div>

	<!-- 列表 -->
	<div class="listTb">
		<table summary="資料表格">

			<tr>
				<th class="aCenter">AP類別代碼</th>
				<th class="aCenter">AP類別中文名稱</th>
				<th class="aCenter">AP類別英文名稱</th>
				<th class="aCenter">排序</th>
			    <th class="aCenter">圖示路徑</th>
			</tr>

			<c:forEach items="${apCats}" var="apCat">
				<tr>
					<td><a href="/admin/app/admin/ApCat/view/apCatPage?apCatId=${apCat.apcatid}"><c:out value="${apCat.apcatid}" /></a></td>
					<td><c:out value="${apCat.apcatcname}" /></td>
					<td><c:out value="${apCat.apcatename}" /></td>
					<td><c:out value="${apCat.apcatsort}" /></td>
					<td><c:out value="${apCat.apcaticonurl}" /></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<div class="listfunction">
		<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
	</div>

	  <jsp:include page="include/footer.jsp"/>

	</div>

</body>
</html>
