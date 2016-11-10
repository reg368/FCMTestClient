<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" action="${funId}.jsp" mothod="post">
		<input type="hidden" name="act" value="query" />
		<table summary="資料表格">
			<tr>
				<ht:rdate1 dp1="dp1" dp2="dp2" label="交易日期區間：" description="每次查詢之起日~迄日的設定區間為30天" />
				<ht:text1 fid="bId" label="農友身分證字號：" placeholder="請輸入農友身分證字號" />
			<tr>
			<tr>
				<ht:text1 fid="permit" label="販賣執照號碼(必填)：" placeholder="請輸販賣執照號碼" must="true" />
				<ht:text1 fid="bName" label="農友姓名：" placeholder="請輸入農友姓名" />
			</tr>
			<tr>
				<ht:text1 fid="barcode" label="農藥條碼：" placeholder="請輸入農藥條碼" />
				<ht:text1 fid="pesticideName" label="農藥普通名稱：" placeholder="請輸入農藥普通名稱" />
			</tr>
		</table>
		<div class="aCenter">
			<ht:btn_query />
			<ht:btn_clear />
		</div>
	</form>
</div>
