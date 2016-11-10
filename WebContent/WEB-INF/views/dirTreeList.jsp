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
<script src="<c:url value='/app/js/jquery-migrate-1.1.1.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery.ztree.all-3.5.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/dirTree.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/leftCol.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/purl.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery.ba-hashchange.js'/>" type="text/javascript"></script>

<title>HyCMS 網站內容管理系統</title>
<script type="text/javascript">

	$.ryoTree.create('treeFrame', 'treeDemo', {
		treeId: <c:out value="${tId}"/>,
		expandLevel: 1,
		treeTypes: {
			MP: {
				url:"/admin/app/cms/TreeList/view/treePage", 
			},
			NP: {
				url:"/admin/app/cms/DirTreeList/view/dirTreePage",
				picUrl:"/admin/app/css/zTreeStyle/img/diy/3.png"
			},
			LP: {
				url:"/admin/app/cms/DirTreeList/view/dirTreePage",
				picUrl:"/admin/app/css/zTreeStyle/img/diy/5.png"
			}, 
			AP: {
				url:"/admin/app/cms/DirTreeList/view/dirTreePage", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/8.png"
			}, 
			SP: {
				url:"/admin/app/cms/DirTreeList/view/dirTreePage", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/6.png"
			}
		},
		close: {
			showChild: true,
			showView: true
		},
		hidden: {
			showChild: true,
			showView: true
		},
	});
	
	function getAddPage(tid, tnlevel, tnid){
		$.ajax({
			type:"GET",
			url:'/admin/app/cms/DirTreeList/insert/dirTreePage',
			data:"tId=" + tid + "&tnLevel=" + tnlevel + "&parent=" + tnid,
			success: function(data){
				$('.content:first').html(data);
			},
			cache: false
		});
	}
	$(document).ready(function(){
		var msg = "<c:out value="${msg}"/>";
		if(msg != null && msg != '')
			alert(msg);
	});
</script>
</head>
<body class="layout">

<div class="leftCol">
 <h2>請點選目錄樹節點</h2>
 	<div id="treeFrame"></div>
 <div class="sideBar">
	<a id="proc"class="hide" href="#" ><img src="<c:url value='/app/images/treebar_closed_mo.png'/>" name="closed" border="0" id="closed" /></a>
	<a id="roc" class="show" href="#" ><img src="<c:url value='/app/images/treebar_opened.png'/>" name="opened" border="0" id="opened" /></a>
 </div>
</div>

	<div id="layoutContent" class="content"></div>

</body>
</html>
