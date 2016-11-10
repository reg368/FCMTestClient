<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" action="rpt_barcode_area.jsp" mothod="post">
		<table summary="資料表格">
			<input id="act" name="act" type="hidden" value="query" />
			<ht:rmy fid="dp" label="月份：" />
			<ht:gcity fid="cityId" label="地區：" />
			<ht:text fid="@allow" label="商品量容許值："
				description="(結果將只列出農藥品項超賣量>容許值的農藥商品)" />
		</table>
		<div class="aCenter">
			<ht:btn_query/>
			<ht:btn_clear/>
		</div>
	</form>
</div>
