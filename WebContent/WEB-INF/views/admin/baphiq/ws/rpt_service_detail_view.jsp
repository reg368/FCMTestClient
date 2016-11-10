<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    request.setAttribute("themes", "/BAPHIQ/admin/themes");
    request.setAttribute("theme", "/BAPHIQ/admin/themes/baphiq");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=big5" />
        <title>${appName} ::: 購肥介接訊息查詢</title>
        <link href="${themes}/css/design.css" rel="stylesheet" type="text/css" />
        <link href="${themes}/baphiq/jquery-ui.min.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${themes}/baphiq/jquery.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery.ui.datepicker-zh-TW.js"></script>
        <style type="text/css">
            .style1 {
                color: #FF0000;
            }
            .style2 {
                font-size: small;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="path">目前位置：購肥介接訊息查詢 > 介接詳細資訊</div>
            <h4>購肥介接訊息查詢</h4>

            <div class="navfunction">
                <ul>
                    <li><a href="javascript:history.back(1)">返回前頁</a></li>		
                </ul>
            </div>

            <div class="ContentTb">

                <table>

                    <tr>
                        <Th>更新日期時間</Th>
                        <TD><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${row.UpdateDateTime}"/></td>
                    </tr>

                    <tr>
                        <Th>模式</Th>
                        <TD>${row.wsType}</td>
                    </tr>

                    <tr>
                        <Th>執行WS</Th>
                        <TD>${row.wsSource}｜${row.wsName}</td>
                    </tr>

                    <tr>
                        <Th>店代號 / 名稱</Th>
                        <TD>${row.storeID} / ${row.storeName}</td>
                    </tr>

                    <tr>
                        <Th>是否成功</Th>
                        <TD>${row.isSucces}</td>
                    </tr>
                </table>

                <table>
                    <caption>訊息內容</caption>
                    <tr>
                        <th>訊息內容</th>
                        <td>${fn:escapeXml(row.Message)}</td>
                    </tr>

                </table>
            </div>
        </div>
    </body>
</html>