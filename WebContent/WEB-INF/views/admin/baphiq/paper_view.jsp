<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funId", "paper");
	request.setAttribute("funName", "紙本登錄");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=big5" />
<title>紙本登錄</title>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<script type="text/javascript">
	$(function() {
		$('#re_query').click(function() {
			window.location.href = '${funId}.jsp';
		});
		$('#do_query').click(function() {
			$('#form1').submit();
		});
		$("#do_clear").click(function() {
			window.location.href = "${funId}.jsp";
		});
	});
</script>
</head>
<body>
	<div class="content">
		<h2>目前位置：紙本登錄系統</h2>
		<!--h4>紙本登錄</h4-->
		<jsp:include page="${funId}_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>

