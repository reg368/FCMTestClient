<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ht" tagdir="/WEB-INF/tags/admin"%>
<div class="formTb">
	<form id="form1" action="num_track.jsp" mothod="post">
		<input type="hidden" name="act" value="query" />
		<table summary="資料表格">
			<!--html:ad_rdate dp1="dp1" dp2="dp2" label="銷售日期區間：" description="只能提供查詢近一年進銷紀錄"  /-->
			<ht:gcity label="地區" fid="cityId" />
			<ht:text fid="barcode" label="農藥條碼(必填)：" placeholder="請輸入農藥條碼"
				must="true" />
			<ht:text fid="makeid" label="農藥批號：" placeholder="請輸入農藥批號" />
			<ht:date dp="makedt" label="製造日期：" placeholder="請輸入製造日期" />
		</table>
		<div class="aCenter">
			<input id="do_query" name="submit1" type="button" class="btn_submit"  value="查　　詢" />
			<input id="do_clear" name="submit2" type="button" class="btn_cancel"     value="重    設" />
		</div>
	</form>
</div>
