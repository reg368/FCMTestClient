<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <div class="path">目前位置：購肥介接訊息查詢</div>
            <h4>購肥介接訊息查詢</h4>
            <jsp:include page="rpt_service_query.jsp" flush="true"/>
            </br>
            <hr>
            <jsp:include page="rpt_service_list.jsp" flush="true"/>
        </div>
    </body>
</html>