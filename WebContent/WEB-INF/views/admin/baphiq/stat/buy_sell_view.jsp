<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("themes", "/BAPHIQ/admin/themes");
    request.setAttribute("theme", "/BAPHIQ/admin/themes/baphiq");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=big5" />
        <title>業者出貨總表</title>
        <link href="${themes}/css/design.css" rel="stylesheet" type="text/css" />
        <link href="${themes}/baphiq/jquery-ui.min.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${themes}/baphiq/jquery.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery.ui.datepicker-zh-TW.js"></script>
        <script type="text/javascript">
            $(function () {
                $('#re_query').click(function () {
                    window.location.href = 'buy_sell.jsp';
                });
            });
        </script>

    </head>
    <body>
        <div class="content">
            <div class="path">目前位置：報表與統計分析模組 > 業者出貨總表</div>
            <h4>業者出貨總表</h4>
            <jsp:include page="buy_sell_query.jsp" flush="true"/>
            </br>
            <hr>
            <jsp:include page="buy_sell_list.jsp" flush="true"/>
        </div>
    </body>
</html>

