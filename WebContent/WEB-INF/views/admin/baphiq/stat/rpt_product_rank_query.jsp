<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" action="${funId}.jsp" mothod="post">
		<input type="hidden" name="act" value="query" />
		<table summary="資料表格">
			<ht:autoComplete fid="pesticideName" label="農藥普通名稱(必填)：" must="true" />
			<ht:autoComplete fid="domManufName" label="製藥商：" />
			<ht:rmy fid="dp" label="交易月份區間：" />
			<ht:gcity fid="cityId" label="地區：" />
		</table>
		<div class="aCenter">
			<input id="do_query" name="do_query" type="button" value="查詢" /> <input
				id="do_clear" name="do_clear" type="button" value="重設" />
		</div>
	</form>
</div>