<%@page import="hyweb.jo.baphiq.upos.view.DateRange"%>
<%@page import="java.util.Date"%>
<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	Date chkDate = DateUtil.getFirstDayOfSeason(-1);
	request.setAttribute("chkDate", sdf.format(chkDate));
	request.setAttribute("chkDateText", DateRange.seasonFormat(chkDate));
%>
<c:if test="${fn:length(rows)>0}">
	<script type="text/javascript">
		$(function() {
			$('#tb_size').change(
					function() {
						window.location.href = "paper.jsp?act=query&numPage="
								+ $(this).val() + "&ejo=${ejo}";
					});
			$('.paper_btn').click(function() {
				//window.location.href = "paper.jsp?act=paper&numPage=" + $(this).val();
				var elem = $(this);
				$(this).load("paper_grant.jsp", {
					baphiqId : elem.attr('baphiqId'),
					chkDate : elem.attr('chkDate'),
					dt : elem.attr('dt')
				});
				//alert(elem.attr("baphiqId") +"----"+ elem.attr("dt")+ "----" + elem.attr("chkDate"));
			});
		});
	</script>
	<div class="listTb">
		<ht:ad_nav50 url="${funId}.jsp" />
		<div class="page">
			<ht:ad_page url="paper.jsp" total="${total}" nowPage="${page}" numPage="${numPage}" size="10" args="&ejo=${ejo}" />
		</div>
		<table summary="資料表格">
			<tr align="center">
				<th>統編/證號</th>
				<th>商業名稱</th>
				<th>地區</th>
				<!--th>類型</th-->
				<th>${chkDateText}</th>
			</tr>
			<c:forEach var="row" items="${rows}">
				<%
					JSONObject row = (JSONObject) pageContext.findAttribute("row");
							String cid = row.optString("newId").trim();
							if (cid.length() == 0) {
								cid = row.optString("oldId");
							}
							row.put("cid", cid);
				%>
				<tr align="center">
					<td>${row.cid}</td>
					<td>${row.cName}</td>
					<td>${row.cityIdText}</td>
					<!--td>${row.dttext}</td-->
					<c:if test="${row.p>0}">
						<td>紙本</td>
					</c:if>
					<c:if test="${row.qty>0}">
						<td>檔案上傳</td>
					</c:if>
					<c:if test="${row.p==0 && row.qty==0}">
						<td class="paper_btn" baphiqId="${row.baphiqId}" dt="${row.dt}"
							chkDate="${chkDate}"><button>繳交紙本</button></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
<c:if test="${fn:length(rows)==0}">查無資料</c:if>
