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
	<br>${fp.cond}
            <!--div class="excel">統計截止日期：<fmt:formatDate pattern="yyyy/MM/dd" value="${fp.statDate}"/></div-->

	<div class="listTb">
		<table summary="資料表格">
			<tr align="center">
				<th>編號</th>
				<th>銷售日期</th>
				<th>農藥條碼</th>
				<th>普通名稱</th>
				<th>劑型</th>
				<th>含量(%)</th>
				<th>數量</th>
				<th>作物</th>
				<th>病蟲害</th>
				<th>身分證字號</th>
				<th>農友姓名</th>
				<th>資料來源</th>
			</tr>
			<c:forEach varStatus="s" items="${rows}" var="crow">
				<tr align="center">
					<td>${s.count+(page-1)*numPage}</td>
					<td><fmt:formatDate pattern="yyyy/MM/dd"
							value="${crow.purchaseDt}" /></td>
					<td>${crow.barcode}</td>
					<td>${crow.pesticideName}</td>
					<td>${crow.formCode}</td>
					<td>${crow.contents}</td>
					<td>${crow.purchaseQty}</td>
					<td>${crow.cropName}</td>
					<td>${crow.pestName}</td>
					<td class="aCenter">${crow.bId}</td>
					<td class="aCenter">${crow.bName}</td>
					<td class="aCenter"><c:if test="${crow.ds==1}">Excel</c:if>
						<c:if test="${crow.ds==2}">WebService</c:if>
						<c:if test="${crow.ds==3}">POS</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="page">
		<ht:ad_page url="${funId}.jsp" total="${total}" nowPage="${page}"
			numPage="${numPage}" size="10" args="&ejo=${ejo}" />
	</div>
</c:if>
<c:if test="${fn:length(rows)==0  && fn:length(fp.act)>0}">查無販賣業照號碼，請重新輸入</c:if>
