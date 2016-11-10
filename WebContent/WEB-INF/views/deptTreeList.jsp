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
<script src="<c:url value='/app/js/deptTree.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/leftCol.js'/>" type="text/javascript"></script>

<title>HyCMS 網站內容管理系統</title>
<script type="text/javascript">
	var zNodes = <c:out value="${node}" escapeXml="false"/>
	
	function getEditPage(id){
	   $.ajax({
	     type: "GET",
	     url: '/admin/app/admin/Dept/view/deptPage',
	     data: {deptSeq : id},
	     success: function(data) {
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
</head>
<body class="layout">

<!--div class="leftCol"-->
<div style="width:95%; margin:0 auto; overflow:auto; display:inline-block;"> 
	<div style="width:200px; float:left;">
		<h2>請點選目錄樹節點</h2>
			<div id="treeFrame"><ul id="treeDemo" class="ztree"></ul></div>
	</div> 
    <div style="margin-left:210px; ">
 	<div  class="content"  style="float:left;" >
		<h2>
			組織管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"/><span>組織列表</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
					<li><a href="#" onclick="javascript:history.go(-1);"><img
							src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁"/>返回前頁</a></li>
				</ul>
			</div>
			<jsp:include page="include/fontSize.jsp"/>
		</div>

      <div class="listfunction">
		<p:page url="/admin/app/admin/Dept/list/dept" pageJump="${pageJump}"  total="${count}" args="${args}"/>
	</div>

	<!-- 列表 -->
	<div class="listTb">
		<table summary="資料表格">

			<tr>
				<th class="aCenter">組織中文名稱</th>
				<th class="aCenter">組織英文名稱</th>
				<th class="aCenter">使用狀態</th>
				<th class="aCenter">排序</th>
			</tr>

			<c:forEach items="${depts}" var="dept">
				<tr>
<%-- 					<a href="/admin/app/admin/Dept/update/deptPage?deptSeq=${dept.deptseq}"> --%>

<!-- 					</a> -->
					
					<td><c:out value="${dept.deptname}" /></td>
					<td><c:out value="${dept.deptename}" /></td>
					<td><c:out value="${dept.deptinuse == 1 ? '使用' : '停用'}" /></td>
					<td><c:out value="${dept.deptsort}" /></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<div class="listfunction">
		<p:page url="/admin/app/admin/Dept/list/dept" pageJump="${pageJump}"  total="${count}" args="${args}" />
	</div>

	  <jsp:include page="include/footer.jsp"/>

	</div>   
    
    </div> 
</div> 





</body>
</html>
