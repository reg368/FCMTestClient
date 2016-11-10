<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		});
		$('#do_query').click(function() {
			var barcode = $('#barcode').val();
			if (barcode.length == 0) {
				alert("農藥條碼必填");
				$('#barcode').focus();
			} else {
				$('#form1').submit();
			}
		});

		$("#do_clear").click(function() {
			window.location.href = "num_track.jsp";
		});
	});
</script>
</head>
<body>
	<div class="content">
		<h2>
			報表與統計分析模組 <img src='/admin/app/images/path_arrow.gif' alt="&gt;&gt;">
			批號追蹤統計
		</h2>
		<!--h4>批號追蹤統計</h4-->
		目的：提供防檢局查詢該品項農藥流向
		<jsp:include page="num_track_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="num_track_list.jsp" flush="true" />
	</div>
</body>
</html>

