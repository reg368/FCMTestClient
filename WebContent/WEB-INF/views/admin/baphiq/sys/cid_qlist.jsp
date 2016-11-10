<%@page import="hyweb.jo.baphiq.upos.view.DateRange"%>
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
    request.setAttribute("chkDateText", DateRange.seasonFormat(chkDate));

%>
<c:choose>
    <c:when test="${$grant.list}">
        <c:if test="${fn:length(rows)>0}">
            <script type="text/javascript">
                $(function () {
                    $('#tb_size').change(function () {
                        window.location.href = "cid_query.jsp?act=query&numPage=" + $(this).val() + "&ejo=${ejo}";
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
                    <html:ad_page url="cid_query.jsp" total="${total}" nowPage="${page}" numPage="${numPage}" size="10" args="&ejo=${ejo}"/>
                </div>   
                <table summary="資料表格" >
                    <tr  align="center">
                        <th>編號</th>
                        <th>商業名稱</th>
                        <th>負責人姓名</th>
                        <th>統一編號</th>
                        <th>新證號</th>
                        <th>舊證號</th>
                        <th>地址</th>
                        <th>聯絡電話</th>
                    </tr>
                    <c:forEach var="row" items="${rows}" varStatus="s">
                        <%
                                JSONObject row = (JSONObject) pageContext.findAttribute("row");
                                String cidText = row.optString("cid").replaceAll("\\*", " ");
                                row.put("cidText", cidText);
                                
                        %>
                        <tr align="center">
                            <td>${s.count+(page-1)*numPage}</td>
                            <td>${row.cName}</td>
                            <td>${row.uName}</td>
                            <td>${row.cidText}</td>
                            <td>${row.newId}</td>
                            <td>${row.oldId}</td>
                            <td>${row.cAddr}</td>
                            <td>${row.tel}</td>
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