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
		<div class="excel" >
			<a href="${funId}_export.jsp?ejo=${ejo}">匯出報表</a>
		</div>
	</div>
	<br>${fp.cond}
            <div class="excel">
		統計截止日期：
		<fmt:formatDate pattern="yyyy/MM/dd" value="${fp.statDate}" />
	</div>

	<div class="listTb">
		<table summary="資料表格">
			<tr align="center">
				<th>編號</th>
				<th>普通名稱</th>
				<th>條碼</th>
				<th>藥商</th>
				<th>劑型</th>
				<th>含量(%)</th>
				<th>銷售數量</th>
				<th>公制換算</th>
				<th>藥劑總量</th>
			</tr>
			<c:forEach varStatus="s" items="${rows}" var="crow">
				<tr align="center">
					<td>${s.count+(page-1)*numPage}</td>
					<td>${crow.pesticideName}</td>
					<td>${crow.barcode}</td>
					<td>${fn:substring(crow.domManufName,0,4)}</td>
					<td>${crow.formCode}</td>
					<td>${crow.contents}</td>
					<td>${crow.outQty}</td>
					<td><fmt:formatNumber pattern="#,##0.000"
							value="${crow.traQty}" /></td>
					<td><fmt:formatNumber pattern="#,##0.000" value="${crow.qty}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="page">
		<ht:ad_page url="${funId}.jsp" total="${total}" nowPage="${page}"
			numPage="${numPage}" size="10" args="&ejo=${ejo}" />
	</div>
</c:if>
<c:if test="${fn:length(rows)==0  && fn:length(fp.act)>0}">查無資料，請重新輸入</c:if>

