<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setAttribute("funId", "num_track");
	request.setAttribute("funName", "批號追蹤統計");
%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<script type="text/javascript">
	$(function() {
		$('#re_query').click(function() {
			window.location.href = 'num_track.jsp';
			//window.history.back();
		});
	});
</script>
</head>
<body>
	<div class="content">
		<h2>目前位置：報表與統計分析模組 &gt; 批號追蹤統計</h2>
		<div class="excel">
			<ht:btn_back/>
		</div>
		<div>
			<html:ad_nav50 url="num_track.jsp" />
		</div>
		<div class="excel">
			統計截止日期：${fp.statDate}
		</div>
		<div>
			<table class="TB_COLLAPSE" align="center" style="border-style: solid">
				<thead>
					<tr>
						<th>農藥條碼</th>
						<th>業者名稱</th>
						<th>客戶名稱</th>
						<th>總出貨量</th>
					</tr>
				</thead>
				<tr>
					<td>${fp.barcode}</td>
					<td>${fp.cname}</td>
					<td>${fp.bcname}</td>
					<td>${fp.qty}</td>
				</tr>
			</table>
		</div>


		<div class="listTb">
			<table summary="資料表格">
				<tr align="center">
					<th>編號</th>
					<th>農藥批號</th>
					<th>製造日期</th>
					<th>數量</th>
				</tr>
				<c:forEach varStatus="s" items="${rows}" var="crow">
					<tr align="center">
						<td>${s.count+(page-1)*numPage}</td>
						<td>${crow.makeid}</td>
						<td>${crow.makeDt}</td>
						<td>${crow.purchaseQty}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="page">
			<html:ad_page url="num_track.jsp" total="${total}" nowPage="${page}"
				numPage="${numPage}" size="10" args="&ejo=${ejo}" />
		</div>
	</div>
</body>
</html>

