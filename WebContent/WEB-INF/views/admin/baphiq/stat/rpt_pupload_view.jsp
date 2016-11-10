<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funId", "rpt_pupload");
	request.setAttribute("funName", "零售業者未回傳名單");
%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<style>
.ui-datepicker-calendar {
	display: none;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#re_query').click(function() {
			window.location.href = '${funId}.jsp';
		});
		$("#do_clear").click(function() {
			window.location.href = "${funId}.jsp";
		});

		$('#do_query').click(function() {
			$('#form1').submit();
		});
	});
</script>

</head>
<body>
	<div class="content">
		<h2 class="path">目前位置：報表與統計分析模組 > 未回傳名單</h2>
		<!--h4>零售業者未回傳名單</h4-->
		<jsp:include page="${funId}_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>
