<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funId", "rpt_supload");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=big5" />
<title>生產進口/批發業者未回傳名單</title>
<c:import url="/WEB-INF/views/admin/baphiq/init_script.jsp" />
<style>
.ui-datepicker-calendar {
	display: none;
}
.formTb {
	min-height: 0px;
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
		<h2>目前位置：報表與統計分析模組 > 未回傳名單</h2>
		<p  style="font-family: 微軟正黑體" >生產進口/批發業者未回傳名單</p>
		<jsp:include page="${funId}_query.jsp" flush="true" />
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>
