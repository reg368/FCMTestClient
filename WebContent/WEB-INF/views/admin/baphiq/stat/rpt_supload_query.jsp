<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="hyweb.jo.baphiq.upos.view.DateRange"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	JSONObject dr = DateRange.getMonth2("20160501");
	request.setAttribute("dr", dr);
%>
<script type="text/javascript">
	$(function() {
		$('#rd1').click(function() {
			set_date(this);
		});
		$('#rd2').click(function() {
			set_date(this);
		});
		$('#rd3').click(function() {
			set_date(this);
		});
		$('input:radio[name="cd"]').filter('[value="${fp.cdi}"]').attr(
				'checked', true);
		$('#rd${fp.cdi}').click();
		$('#do_query').click(function() {
			if ($('input:radio:checked[name="cd"]').val() !== undefined) {
				$('#form1').submit();
			} else {
				alert("請選時間區間");
			}
		});
	});
	function set_date(fld) {
		$('#cdi').val($(fld).val());
		$('#limited').val($(fld).attr("limited"));
		$('#dp1').val($(fld).attr("ds"));
		$('#dp2').val($(fld).attr("de"));
	}
</script>
<div class="formTb">
	<form id="form1" action="rpt_supload.jsp" mothod="post">
		<input type="hidden" name="act" value="query" />
		<table summary="資料表格">
			<ht:gcity label="地區" fid="cityId" />
			<tr>
				<th>時間區間</th>
				<td><input name="cdi" id="cdi" type="hidden" value="${fp.cdi}" />
					<c:forEach var="drow" items="${dr.rows}" varStatus="s">
						<input name="cd" id="rd${s.index+1}" type="radio"
							value="${s.index+1}"
							ds="<fmt:formatDate pattern='yyyy/MM/dd' value='${drow.ds}'/>"
							de="<fmt:formatDate pattern='yyyy/MM/dd' value='${drow.de}'/>"
							limited="<fmt:formatDate pattern='yyyy-MM-dd' value='${drow.limited}'/>" />${drow.label}
                            </c:forEach> <br /> <input name="limited" id="limited"
					type="hidden" value="${fp.limited}" /> <input type="text"
					name="dp1" id="dp1" value="${fp.dp1}" /> <input type="text"
					name="dp2" id="dp2" value="${fp.dp2}" /></td>
			</tr>
			<ht:text1 fid="cid" label="業者資訊：" placeholder="請輸入業者統編/商號" />
		</table>
		<div class="aCenter">
			<ht:btn_query />
			<ht:btn_clear />
		</div>
	</form>
</div>

