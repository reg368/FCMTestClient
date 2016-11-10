<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" name="form1" action="paper.jsp" method="post">
		<input type="hidden" name="act" value="query" />
		<table summary="資料表格">
			<tr>
				<th>地區：</th>
				<td><c:if test="${dlevel1!=''}">
						<input type="hidden" name="cityId" value="${dlevel1}" />${dlevel1_text} </c:if>
					<c:if test="${dlevel1==''}">
						<select name="cityId"> ${ht_pcity}
						</select>
					</c:if></td>
			</tr>
			<tr>
				<th>廠商資訊：<br /> <span class="style2">(統編/販賣業執照號碼/商號)</span></th>
				<td><input name="cid" type="text" placeholder="販賣業執照號碼/商號"
					style="width: 30%" value="${fp.cid}" /><br /></td>
			</tr>
		</table>
		<div class="aCenter">
			<ht:btn_query />
			<ht:btn_clear />
		</div>
	</form>
</div>
