<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="html" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${$grant.list}">
        <c:if test="${fn:length(rows)>0}">
        <div>
            <html:ad_nav url="buy_sell.jsp"/>
            <div class="excel"><a href="buy_sell_export.jsp?ejo=${ejo}" >匯出Excel</a></div>
        </div>
        <hr/>
        <div class="listTb">
            銷售日期區間：${dp1}~${dp2}
            <table summary="資料表格" >
                <tr  align="center">
                    <th>編號</th>
                    <th>業者名稱</th>
                    <th>統編/證號</th>
                    <th>地區</th>
                    <th>農藥普通名稱</th>
                    <th>含量</th>
                    <th>容量</th>
                    <th>農藥條碼</th>
                    <th>農藥批號</th>
                    <!--th>進量</th-->	
                    <th>銷量</th>
                </tr>
                <c:forEach varStatus="s" items="${rows}" var="row">
                    <tr align="center">
                        <td>${s.count+(page-1)*numPage}</td>
                        <td>${row.cName}</td>
                        <td>${row.cid}</td>
                        <td>${row.scityId}</td>
                        <td>${row.pesticideName}</td>
                        <td>${row.contents}</td>
                        <td>${row.volume}</td>
                        <td>${row.barcode}</td>
                        <td>${row.makeId}</td>
                        <!--td>${row.tin}</td-->
                        <td>${row.tout}</td>
                    </tr> 
                </c:forEach>
            </table>
        </div>
        <div class="page">
            <html:ad_page url="buy_sell.jsp" total="${total}" nowPage="${page}" numPage="${numPage}" size="10" args="&ejo=${ejo}"/>
        </div>  

        <div class="aCenter">
            &nbsp;<button id="re_query"> 重新查詢</button>
        </div>
        </c:if>
        <c:if test="${fn:length(rows)==0}">查無資料</c:if>
    </c:when>
    <c:otherwise>
        您沒有列表權限!!!
    </c:otherwise>
</c:choose>
