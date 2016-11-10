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
	function getEditPage(id){
		   $.ajax({
		     type: "GET",
		     url: '/admin/app/admin/CodeMain/view/codeMainPage',
		     data: "codeSeq=" + id + "&expendTid=" + $('#expendNode').val(),
		     success: function(data) {
		          $('.content:first').html(data);
		     },
			 cache: false
		   });
	}
	
	function getAddPage(){
		$.ajax({
			type:"GET",
			url:'/admin/app/admin/CodeMain/insert/codeMainPage',
			data: "codeId=" + '<c:out value="${codeId}"/>' + "&expendTid=" + $('#expendNode').val(),
			success: function(data){
				$('.content:first').html(data);
			},
			cache: false
		});
	}
	
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
			代碼管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;" /><span>代碼列表</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
					<c:if test="${limits.add}">
						<li><a href="#" onclick="getAddPage();"><img
								src='<c:url value="/app/images/icon_treeAdd.gif"/>' alt="代碼新增" />代碼新增</a></li>
					</c:if>	
					<li><a href="javascript:history.back();"><img
							src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁" />返回前頁</a></li>
				</ul>
			</div>
			<jsp:include page="include/fontSize.jsp"/>
		</div>

      <div class="listfunction">
		<p:pageAjax url="/admin/app/admin/CodeMain/list/codeMainById" pageJump="${pageJump}"  total="${count}" args="${args}" func="getPageFunc" html=".content:first"/>
	</div>

	<!-- 列表 -->
	<div class="listTb">
		<table summary="資料表格">

			<tr>
				<th class="aCenter">代碼</th>
				<th class="aCenter">代碼值</th>
				<th class="aCenter">代碼顯示</th>
				<th class="aCenter">排序</th>
			</tr>

			<c:forEach items="${codeMains}" var="codeMain">
				<tr>
					<td><a href="#" onclick="getEditPage(${codeMain.codeseq});"><c:out value="${codeMain.codeid}" /></a></td>
					<td><c:out value="${codeMain.codevalue}" /></td>
					<td><c:out value="${codeMain.codeshow}" /></td>
					<td><c:out value="${codeMain.codesort}" /></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<div class="listfunction">

		<p:pageAjax url="/admin/app/admin/CodeMain/list/codeMainById" pageJump="${pageJump}"  total="${count}" args="${args}" func="getPageFunc" html=".content:first"/>
	</div>

	  <jsp:include page="include/footer.jsp"/>

	</div>

</body>
</html>
