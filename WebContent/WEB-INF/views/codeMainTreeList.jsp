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
<link href='<c:url value="/app/css/zTreeStyle/zTreeStyle.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery.ztree.all-3.5.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/codeMainTree.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/leftCol.js'/>" type="text/javascript"></script>

<title>HyCMS 網站內容管理系統</title>
<script type="text/javascript">
	var zNodes = <c:out value="${node}" escapeXml="false"/>;
	
	function getListPage(codeid){
		$.ajax({
		     type: "GET",
		     url: '/admin/app/admin/CodeMain/list/codeMainById',
		     data: "codeId=" + codeid,
		     success: function(data) {
		          $('.content:first').html(data);
		     },
			 cache: false
		   });
	}
	
	function getEditPage(id){
	   $.ajax({
	     type: "GET",
	     url: '/admin/app/admin/CodeMain/view/codeMainPage',
	     data: "codeSeq=" + id,
	     success: function(data) {
	          $('.content').html(data);
	     },
		 cache: false
	   });
	}
	
	function getAddPage(){
		$.ajax({
			type:"GET",
			url:'/admin/app/admin/CodeMain/insert/codeMainPage',
			success: function(data){
				$('.content').html(data);
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
</head>
<body class="layout">
<input type="hidden" id="expendNode" value="<c:out value='${expendTid}'/>"/>
<div style="width:95%; margin:0 auto; overflow:auto; display:inline-block;"> 
<div  style="width:200px; float:left;">
 <h2>請點選目錄樹節點</h2>
 	<div id="treeFrame">
 		<ul id="treeDemo" class="ztree"></ul>
	</div>

</div>
<div  style="margin-left:210px; ">
<div class="content"  style="float:left;"></div>
</div>
</div>
</body>
</html>
