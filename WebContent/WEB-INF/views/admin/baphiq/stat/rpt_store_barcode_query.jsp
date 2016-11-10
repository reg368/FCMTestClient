<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" action="${funId}.jsp" mothod="post">
		<table summary="資料表格">
			<input id="act" name="act" type="hidden" value="query" />
			<ht:my fid="dp" label="月份：" start="11112222" />
			<ht:gcity fid="cityId" label="地區：" />
		</table>
		<div class="aCenter">
			<ht:btn_query/>
			<ht:btn_clear/>
		</div>
	</form>
</div>
