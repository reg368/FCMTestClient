<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("themes", "/BAPHIQ/admin/themes");
    request.setAttribute("theme", "/BAPHIQ/admin/themes/baphiq");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=big5" />
        <title>重置庫存功能</title>
        <link href="${themes}/css/design.css" rel="stylesheet" type="text/css" />
        <link href="${themes}/baphiq/jquery-ui.min.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${themes}/baphiq/jquery.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery-ui.min.js"></script>
        <style type="text/css">
            .style1 {
                color: #FF0000;
            }
            .style2 {
                font-size: small;
            }
        </style>
        <script type="text/javascript">
            $(function () {
                $('#do_reset').click(function () {
                    if (confirm('確認要重置庫存嗎？')) {
                        $("#form2").submit();
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="content">
            <div class="path">系統管理 > 重置庫存功能</div>
            <h4>重置/設定庫存功能</h4>
            <div class="ContentTb">
                <form id="form1" name="form1" method="post" action="mstock.jsp">
                    <input type="hidden" name="act" value="query" />
                    <table summary="資料表格" >
                        <html:ad_field1 fieldId="cid" label="統編/新舊證號：" placeholder="請輸統編/新舊證號"/>
                    </table>
                    <div class="aCenter">
                        <input name="submit1" type="submit" value="查　　詢" />
                    </div>
                </form>
            </div>
            <c:if test="${fn:length(row)>0}">
                <div class="listTb">
                    <table summary="資料表格" >
                        <tr  align="center">
                            <th >商業名稱</th>
                            <th >負責人姓名</th>
                            <th >統一編號</th>
                            <th >新證號</th>
                            <th >舊證號</th>
                            <th >地址</th>
                            <th >庫存狀態</th>
                            <th >時間</th>
                        </tr>
                        <tr align="center">
                            <td class="aCenter">${row.cName}</td>
                            <td>${row.uName}</td>
                            <td>${row.cid}</td>
                            <td>${row.newId}</td>
                            <td>${row.oldId}</td>
                            <td>${row.cAddr}</td>
                            <td><c:if test="${row.stock == '0' }">未上傳</c:if><c:if test="${row.stock == '1' }">已上傳</c:if> </td>
                            <td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${row.mt}"/></td>
                        </tr>

                    </table>
                    <div class="aCenter">
                        <c:if test="${row.stock == '1'}">
                            <form id="form2" name="form2" method="post" action="mstock.jsp">
                                <input type="hidden" name="act" value="do_reset" />
                                <input type="hidden" name="baphiqId" value="${row.baphiqId}" />      
                                <input type="hidden" name="stock" value="0" />    
                                <input type="hidden" name="cid" value="${fp['cid']}" /> 
                                <input type="button" id="do_reset" name="do_reset" value="重置庫存"/>
                            </form>
                        </c:if>
                    </div>

                </div>
            </c:if>
            <c:if test="${fn:length(row)==0}">
                查無資料
            </c:if>
        </div>
    </body>
</html>
