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
			網站管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;" />
			目錄樹管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;" />
			<span>資料清單</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
					<li><a href="/admin/app/cms/TreeList/insert/treePage"><img
							src='<c:url value="/app/images/icon_modAdd.gif"/>' alt="資料新增" />資料新增</a></li>	
					<li><a href="#" onclick="javascript:history.go(-1);"><img
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
				<th class="aCenter">目錄樹ID</th>
				<th class="aCenter">目錄樹名稱</th>
				<th class="aCenter">目錄樹狀態</th>
				<th class="aCenter">編修目錄樹</th>
			</tr>

			<c:forEach items="${trees}" var="tree">
				<tr>
					<td><c:out value="${tree.tid}" /></td>
					<td><a href="/admin/app/cms/TreeList/view/treePage?tId=${tree.tid}"><c:out value="${tree.tname}" /></a></td>
					<td><c:out value="${tree.tispublic == 1 ? '公開':'關閉'}" /></td>
					<td><input type="button" name="edit" class="btn" value="編修目錄樹" onclick="location.href='/admin/app/cms/DirTreeList/list/dirTree?tId=${tree.tid}'" /></td>
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
