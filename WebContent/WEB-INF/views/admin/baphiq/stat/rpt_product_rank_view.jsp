<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funName", "農藥商品藥劑總量統計");
	request.setAttribute("funId", "rpt_product_rank");
%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<script type="text/javascript">
	$(function() {
		$('#do_query').click(function() {
			var ret = true;
			if (ret) {
				$("#form1").submit();
			}
		});
		$("#do_clear").click(function() {
			window.location.href = "${funId}.jsp";
		});
	});
</script>
</head>
<body>
	<div class="content">
		<h2>目前位置：報表與統計分析模組 &gt;&nbsp; ${funName}</h2>
		<p>${funName}</p>
		<jsp:include page="${funId}_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>
