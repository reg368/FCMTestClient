<%@page import="hyweb.jo.util.JOTools"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
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
        <title>${appName} ::: 修改密碼</title>
        <link href="${themes}/css/design.css" rel="stylesheet" type="text/css" />
        <link href="${themes}/baphiq/jquery-ui.min.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${themes}/baphiq/jquery.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery.ui.datepicker-zh-TW.js"></script>
        <style type="text/css">
            .must {
                font-weight: bold;
                color: #C00;
                margin-right: 5px;
            }

            .style1 {
                color: #FF0000;
            }
        </style>



        <script type="text/javascript">
            $(function(){
   
                $("#do_pwd").click(function(){
                    var fld1 = $('#pwd1').val();
                    var fld2 = $('#pwd2').val();
                    if(fld1===fld2 && fld1.length>=6  ){
                        $('#pwd2_msg').text("");
                        $('#form1').submit();
                    } else {
                        $('#pwd2_msg').text("這些密碼不相符，請重新輸入");
                    }
                });
            });
        </script>



    </head>
    <body>

        <div class="content">
            <div class="path">目前位置：系統管理 &gt; 修改密碼</div>
            <h4>修改密碼</h4><p>新密碼規則：請至少輸入6個字元 且英文大小寫有區別</p>
            <div class="ContentTb">
                <form id="form1" method="post" action="admin.jsp">
                    <input name="act" type="hidden" value="pwd"/>
                    <table summary="資料表格" >
                        <tr>
                            <th> <span class="style1">* </span>請輸入新密碼：</th>
                            <td style="width:25%" >
                                <input id="pwd1" name="pwd1" type="password" class="text" /></td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <th> <span class="style1">* </span>確認新密碼：</th>
                            <td >

                                <input id="pwd2" name="pwd2" type="password" class="text" /></td>
                            <td   id="pwd2_msg" class="style1">${msg}</td>
                        </tr>
                    </table>
                    <div class="aCenter">
                        <input id="do_pwd" name="do_pwd" type="button" value="變更密碼" />
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
