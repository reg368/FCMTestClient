<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funId", "rpt_sell_store");
	request.setAttribute("funName", "零售店銷售紀錄");
%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<script type="text/javascript">
	$(function() {
		$('#do_query').click(
				function() {
					var ret = vf_empty_msg('#permit', '販賣執照號碼(必填)')
							&& vf_empty("#dp1", "請選交易日期區間起")
							&& vf_empty("#dp2", "請選交易日期區間迄");
					if (ret) {
						$("#form1").submit();
					}
				});
		$("#do_clear").click(function() {
			window.location.href = "${pageId}.jsp";
		});
	});
</script>
</head>
<body>
	<div class="content">
		<h2>目前位置：報表與統計分析模組 &gt;&nbsp; ${funName}</h2>
		<p style="font-family: 微軟正黑體">${funName}</p>
		<jsp:include page="${funId}_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>
