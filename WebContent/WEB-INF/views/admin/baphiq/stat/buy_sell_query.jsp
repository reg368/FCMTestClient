<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${$grant.query}">
        <div class="ContentTb">
            <form id="form1" action="buy_sell.jsp" mothod="post">
                <input type="hidden" name="act" value="query"/>
                <table summary="資料表格">
                    <html:ad_rdate dp1="dp1" dp2="dp2" label="銷售日期區間：" description="只能提供查詢近一年進銷紀錄"  />
                    <html:ad_gcity label="地區" fid="cityId" />
                    <html:ad_field1 fieldId="sell" label="業者資訊：" placeholder="請輸入業者統編/證號/商號"/>
                    <html:ad_field1 fieldId="pesticideName" label="農藥普通名稱：" placeholder="請輸入農藥普通名稱"/>
                    <html:ad_field1 fieldId="barcode" label="農藥條碼：" placeholder="請輸入農藥條碼"/>
                    <html:ad_field1 fieldId="makeId" label="農藥批號：" placeholder="請輸入農藥批號"/>
                </table>
                <div class="aCenter">
                    <input name="submit1" type="submit" value="查　　詢" />
                    <input name="submit2" type="reset" value="取　　消" />
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        沒有查詢權限！！！
    </c:otherwise>
</c:choose>