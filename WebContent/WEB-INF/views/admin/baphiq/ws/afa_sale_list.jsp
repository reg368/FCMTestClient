<%@page import="java.util.Date"%>
<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date chkDate = DateUtil.getFirstDayOfSeason(-1);
    request.setAttribute("chkDate", sdf.format(chkDate));

%>
<c:choose>
    <c:when test="${$grant.list}">
        <c:if test="${fn:length(rows)>0}">
            <script type="text/javascript">
                $(function () {
                    $('#tb_size').change(function () {
                        window.location.href = "paper.jsp?act=query&numPage=" + $(this).val() + "&ejo=${ejo}";
                    });

                });
            </script>
            <div class="listTb">         
                <label for="tb_size">共 ${total}筆資料，每頁顯示</label>
                <select id="tb_size" class="select" name="tb_size" size="1">
                    <option value="10" <c:if test="${numPage==10}">selected</c:if> >10</option>
                    <option value="30" <c:if test="${numPage==30}">selected</c:if> >30</option>
                    <option value="50" <c:if test="${numPage==50}">selected</c:if> >50</option>
                    <option value="300" <c:if test="${numPage==300}">selected</c:if> >300</option>
                    </select>
                    <label for="tb_id"> 筆</label>

                    <div class="page">
                    <html:ad_page url="afa_sale.jsp" total="${total}" nowPage="${page}" numPage="${numPage}" size="10" args="&ejo=${ejo}"/>
                </div>   
                <table summary="資料表格" >
                    <tr  align="center">
                        <th>POS上傳日期</th>
                        <th>店代號</th>
                        <th>銷售單號</th>
                        <th>農友身分證</th>
                        <th>上傳至購肥</th>
                        <th>是否成功</th>
                        <th>詳細訊息</th>
                        <th>例外註記</th><!--當銷售上傳數量為「負值」時，判斷為例外狀況，顯示註記為Y-->
                    </tr>

                    <c:forEach var="row" items="${rows}">
                        <tr align="center">
                            <td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${row.mt}" /></td>
                            <td>${row.oddNo}</td>
                            <td>${row.storeId}</td>
                            <td>${row.cusNo}</td>
                            <td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${row.uploadDT}" /></td>
                            <td><c:if test="${row.status>1 && row.status!=3}">Y</c:if><c:if test="${row.status==3}">N</c:if></td>
                            <td><a href="afa_sale_detail.jsp?storeId=${row.storeId}&oddNo=${row.oddNo}">檢視詳細</a></td>
                            <td><c:if test="${row.status>3}">Y</c:if><c:if test="${row.status<3 and row.status!=1}">N</c:if></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${fn:length(rows)==0}">查無資料</c:if>
    </c:when>
    <c:otherwise>
        您沒有列表權限!!!
    </c:otherwise>
</c:choose>