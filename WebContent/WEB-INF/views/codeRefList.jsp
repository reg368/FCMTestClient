<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	function getEditPage(id){
		   $.ajax({
		     type: "GET",
		     url: '/admin/app/admin/CodeRef/view/codeRefPage',
		     data: "seq=" + id + "&expendTid=<c:out value='${expendTid}'/>",
		     success: function(data) {
		          $('.content:first').html(data);
		     },
			 cache: false
		   });
	}
	
	function getSubAddPage(){
		$.ajax({
			type:"POST",
			url:'/admin/app/admin/CodeRef/insert/codeRefPage',
			data: "refid=" + '<c:out value="${refid}"/>' + "&refvalue=" + '<c:out value="${refvalue}"/>' + "&expendTid=<c:out value='${expendTid}'/>",
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
			代碼管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;" /><span>子代碼列表</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
   						<li><a href="#" onclick="getSubAddPage();"><img src='<c:url value="/app/images/icon_modAdd.gif"/>' alt="子代碼新增"/>子代碼新增</a></li>
					<li><a href="javascript:history.back();"><img
							src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁" />返回前頁</a></li>
				</ul>
			</div>
			<jsp:include page="include/fontSize.jsp"/>
		</div>

      <div class="listfunction">
		<div class="page">
			<ul>
				<li class="first"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="first" title="Previous" /></a></li>
				<li class="prev"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="previous" title="Previous" /></a></li>
				<li class="here">1</li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li class="next"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="next" title="next" /></a></li>
				<li class="last"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="last" title="last" /></a></li>
			</ul>
		</div>
	</div>

	<!-- 列表 -->
	<div class="listTb">
		<table summary="資料表格">

			<tr>
				<th class="aCenter">子代碼值</th>
				<th class="aCenter">子代碼顯示</th>
				<th class="aCenter">排序</th>
			</tr>

			<c:forEach items="${codeRefs}" var="codeRef">
				<tr>
					<td><a href="#" onclick="getEditPage(${codeRef.seq});"><c:out value="${codeRef.subcodevalue}" /></a></td>
					<td><c:out value="${codeRef.subcodeshow}" /></td>
					<td><c:out value="${codeRef.subcodesort}" /></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<div class="listfunction">

		<div class="page">
			<ul>
				<li class="first"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="first" title="Previous" /></a></li>
				<li class="prev"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="previous" title="Previous" /></a></li>
				<li class="here">1</li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li class="next"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="next" title="next" /></a></li>
				<li class="last"><a href="#"><img src='<c:url value="/app/images/space.gif"/>'
						alt="last" title="last" /></a></li>
			</ul>
		</div>
	</div>

	  <jsp:include page="include/footer.jsp"/>

	</div>

</body>
</html>
