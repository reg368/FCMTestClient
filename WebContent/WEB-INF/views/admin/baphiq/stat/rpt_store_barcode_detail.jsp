<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib  prefix="ht" tagdir="/WEB-INF/tags/admin" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    request.setAttribute("now", new java.util.Date());
	request.setAttribute("funId", "rpt_store_barcode");
	request.setAttribute("funName", "零售店家進銷異常統計");
%>
<!DOCTYPE html>
<html>
    <head>
		<c:import url="/WEB-INF/views/admin/baphiq/init_head.jsp" />
        <script type="text/javascript">
            $(function () {
                $('#re_query').click(function () {
                    window.location.href = 'rpt_barcode_area.jsp';
                });
            });
        </script>
    </head>
    <body>
        <div class="content">
            <div class="path">目前位置：報表與統計分析模組 &gt;  零售店家進銷異常統計</div>
            <h4>零售店家進銷異常統計</h4>
            <div class="excel">
            	<ht:btn_back/>
            </div>
            <div>
                <ht:ad_nav50 url="${funcId}.jsp"/>
            </div>
            <div class="excel">統計截止日期：<fmt:formatDate pattern="yyyy/MM/dd" value="${fp.statDate}"/></div>

            <div >
                <table class="TB_COLLAPSE" align="center" style="border-style: solid" >
                    <thead>
                        <tr>
                            <th>農藥販賣業者</th>
                            <th>販賣業執照號碼</th>
                            <th>農藥品項異常數量</th>
                        </tr>
                    </thead>
                    <tr>
                        <td>${fp.cName}</td>
                        <td>${fp.permit}</td>
                        <td>${fp.qty}</td>
                    </tr>
                </table>
            </div>

            <div class="listTb">
                <table summary="資料表格" >
                    <tr  align="center">
                        <th>編號</th>
                        <th >農藥條碼</th>
                        <th >普通名稱</th>
                        <th >劑型</th>
                        <th >含量(%)</th>
                        <th >庫存</th>
                        <th >進量</th>
                        <th >銷量</th>
                        <th >差異數量(銷-進+庫)</th>
                    </tr>
                    <c:forEach varStatus="s" items="${rows}" var="crow">
                        <tr align="center">
                            <td>${s.count+(page-1)*numPage}</td>
                            <td>${crow.barcode}</td>
                            <td>${crow.pesticideName}</td>
                            <td>${crow.formCode}</td>
                            <td>${crow.contents}</td>
                            <td>${crow.stock}</td>
                            <td>${crow.inQty}</td>
                            <td>${crow.outQty}</td>
                            <td>${crow.errQty}</td>
                        </tr> 
                    </c:forEach>
                </table>
            </div>
            <div class="page">
                <ht:ad_page url="${funcId}.jsp" total="${total}" nowPage="${page}" numPage="${numPage}" size="10" args="&ejo=${ejo}"/>
            </div>  
        </div>
    </body>
</html>

