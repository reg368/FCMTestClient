<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${$grant.query}">
        <div class="ContentTb">
            <form action="cid_query.jsp" method="post">
                <input type="hidden" name="act" value="query"/>
                <table summary="資料表格">
                    <html:ad_field1 fieldId="cName" label="商業名稱：" placeholder="請輸入商業名稱"/>
                    <html:ad_field1 fieldId="uName" label="負責人姓名：" placeholder="請輸入負責人姓名"/>     
                    <html:ad_field1 fieldId="cid" label="統一編號：" placeholder="請輸統一編號"/>     
                    <html:ad_field1 fieldId="licid" label="販賣業執照號碼：" placeholder="請輸入販賣業執照號碼"/>     
                    <html:ad_gcity label="地區：" fid="cityId" />
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