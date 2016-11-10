<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${fn:length(rows)>0}">
<c:set var="now" value="<%=new java.util.Date()%>" />
<fmt:formatDate var="statDate" pattern="yyyy/MM/dd" value="${rows[0].ct}" />
	<div>
		<ht:ad_nav50 url="num_track.jsp" />
		<div class="excel">
			<a href="num_track_export.jsp?ejo=${ejo}">匯出報表</a>
		</div>
	</div>
	<hr />
	<div class="excel">
		<span>統計截止日期：${statDate}</span> <span>報表製表日期：<fmt:formatDate
				pattern="yyyy/MM/dd" value="${now}" /></span>
	</div><br/>
	<div>
		<table class="TB_COLLAPSE" align="center" style="border-style: solid">
			<thead>
				<tr>
					<th>農藥普通名稱</th>
					<th>劑型</th>
					<th>含量</th>
					<th>農藥條碼</th>
					<th>製造日期</th>
					<th>農藥批號</th>
					<th>製造商</th>
					<th>統一編號</th>
					<th>總出貨量</th>
				</tr>
			</thead>
			<tr>
				<td>${row.pesticideName}</td>
				<td>${row.formName}(${row.formCode})</td>
				<td>${row.contents}</td>
				<td>${row.barcode}</td>
				<td>${fp.makedt}</td>
				<td>${fp.makeid}</td>
				<td>${row.domManufName}</td>
				<td>${row.domManufId}</td>
				<td>${row.qty}</td>
			</tr>
		</table>
	</div>


	<div class="listTb">
		<table summary="資料表格">
			<tr align="center">
				<th>編號</th>
				<th>業者名稱</th>
				<th>業者地區</th>
				<th>客戶名稱</th>
				<th>數量</th>
			</tr>
			<c:forEach varStatus="s" items="${rows}" var="crow">
				<tr align="center">
					<td>${s.count+(page-1)*numPage}</td>
					<td>${crow.cName}(${crow.oldId})</td>
					<td>${crow.cityIdText}</td>
					<td>${crow.bcName}(${crow.bcId})</td>
					<td><a
						href="num_track.jsp?act=detail&barcode=${fp.barcode}&bcid=${crow.bcId}&makedt=${fp.makedt}&makeid=${fp.makeid}&qty=${crow.qty}&cname=${crow.cName}(${crow.oldId})&bcname=${crow.bcName}(${crow.bcId})&statDate=${statDate}">${crow.qty}</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="page">
		<html:ad_page url="num_track.jsp" total="${total}" nowPage="${page}"
			numPage="${numPage}" size="10" args="&ejo=${ejo}" />
	</div>
</c:if>
<c:if test="${fn:length(rows)==0  && fn:length(fp.act)>0}">查無資料</c:if>
