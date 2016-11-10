<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${fn:length(rows)>0}">
	<div>
		<ht:ad_nav50 url="${funId}.jsp" />
		<div class="excel">
			<a href="${funId}_export.jsp?ejo=${ejo}">匯出報表</a>
		</div>
	</div>
	<hr />
	<div class="excel">
		統計截止日期：
		<fmt:formatDate pattern="yyyy/MM/dd" value="${fp.statDate}" />
	</div>
	<div class="listTb">
		<table summary="資料表格">
			<tr align="center">
				<th>編號</th>
				<th>業者名稱</th>
				<th>地區</th>
				<th>異常品項數量</th>
			</tr>
			<c:forEach varStatus="s" items="${rows}" var="crow">
				<c:set var="permit"
					value="${fn:length(crow.newId)==6 ? crow.newId : crow.oldId}" />
				<tr align="center">
					<td>${s.count+(page-1)*numPage}</td>
					<td>${crow.cName}(${permit})</td>
					<td>${crow.cityIdText}</td>
					<td><a
						href="${funId}.jsp?act=detail&baphiqId=${crow.baphiqId}&yymm=${fp.dp}&cName=${crow.cName}&permit=${permit}&qty=${crow.qty}">${crow.qty}</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="page">
		<ht:ad_page url="${funId}.jsp" total="${total}" nowPage="${page}"
			numPage="${numPage}" size="10" args="&ejo=${ejo}" />
	</div>
</c:if>
<c:if test="${fn:length(rows)==0  && fn:length(fp.act)>0}">查無資料</c:if>