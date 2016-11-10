<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${fn:length(rows)>0}">
	<div>
		<ht:ad_nav url="rpt_pupload.jsp" />
		<div class="excel">
			<a href="rpt_pupload_export.jsp?ejo=${ejo}">匯出Excel</a>
		</div>
	</div>
	<hr />
	<div class="listTb">
		<table summary="資料表格">
			<tr align="center">
				<th>編號</th>
				<th>統編/證號</th>
				<th>商業名稱</th>
				<th>地區</th>
				<!--th>未回傳年月</th-->
			</tr>
			<c:forEach varStatus="s" items="${rows}" var="row">
				<tr align="center">
					<td>${s.count+(page-1)*numPage}</td>
					<td>${row.cId}</td>
					<td>${row.cName}</td>
					<td>${row.scityId}</td>
					<!--td><fmt:formatDate pattern="yyyy/MM/dd" value="${row.checkDate}"/></td-->
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="page">
		<html:ad_page url="rpt_pupload.jsp" total="${total}" nowPage="${page}"
			numPage="${numPage}" size="10" args="&ejo=${ejo}" />
	</div>

	<div class="aCenter">
		<button id="re_query">重新查詢</button>
	</div>
</c:if>
<c:if test="${fn:length(rows)==0}">查無資料</c:if>
