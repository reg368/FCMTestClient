<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("funId", "rpt_barcode_area");
	request.setAttribute("funName", "零售品項進銷總量異常統計");
%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
<script type="text/javascript">
	$(function() {
		$('#do_query').click(function() {
			var ret = vf_positive('#allow', '商品量容許值');
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
		<h2>目前位置：報表與統計分析模組 &gt;&nbsp; 零售品項進銷總量異常統計</h2>
		<!--h4>零售品項進銷總量異常統計</h4-->
		<p style="font-family: 微軟正黑體">
			根據農藥販賣業者陳報的銷售紀錄，以農藥品項的角度統計出品項進銷異常的名單。</br>
			查詢條件僅提供查詢近三年內的銷售資料，近一年之銷售紀錄將於每個月16日重新計算月結結果。<br />
		</p>
		<jsp:include page="${funId}_query.jsp" flush="true" />
		</br>
		<hr>
		<jsp:include page="${funId}_list.jsp" flush="true" />
	</div>
</body>
</html>
