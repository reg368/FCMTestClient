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
        <title>${appName} ::: 購肥介接訊息查詢</title>
        <link href="${themes}/css/design.css" rel="stylesheet" type="text/css" />
        <link href="${themes}/baphiq/jquery-ui.min.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${themes}/baphiq/jquery.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${themes}/baphiq/jquery.ui.datepicker-zh-TW.js"></script>
        <style>
            table caption {
                font-size: 80%;
                text-align: left;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="path">目前位置：購肥銷售介接查詢 &gt; 銷售詳細資訊</div>
            <h4>購肥銷售介接查詢</h4>

            <div class="navfunction">
                <ul>
                    <li><a href="javascript:history.back(1)">返回前頁</a></li>		
                </ul>
            </div>


            <div class="ContentTb">

                <table>

                    <tr>
                        <Th>POS上傳日期時間</Th>
                        <TD colspan="3"><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${main.mt}" /></td>
                    </tr>

                    <tr>
                        <Th>上傳至購肥日期時間</Th>
                        <TD><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${main.uploadDT}" /></td>
                        <Th>狀態</th>
                        <Td style="width:35%;"><c:if test="${main.status==2 || main.status==5}">成功</c:if><c:if test="${main.status==3 or main.status==6}">失敗</c:if></td>
                        </tr>

                        <tr>
                            <Th>店代號</Th>
                                <TD>${main.storeId}</td>
                        <Th>店名稱</th>
                        <TD>${main.storeName}</td>
                    </tr>

                    <tr>
                        <Th>銷售單號</Th>
                        <TD colspan="3">${main.oddNo}</td>
                    </tr>

                    <tr>
                        <Th>農友身分證</Th>
                        <TD>${main.cusNo}</td>
                        <Th>農友姓名</Th>
                        <TD>${main.cusName}</td>
                    </tr>

                    <tr>
                        <Th>銷售購買日期時間</Th>
                        <TD><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${main.purchaseDT}" /></td>
                        <Th>是否取消</Th>
                        <TD>N</td>
                    </tr>

                    <tr>
                        <Th>銷售清單</Th>
                        <TD colspan="3">
                            <c:forEach var="hrow" items="${history}">
                                <%
                                    JSONObject hrow = (JSONObject) pageContext.findAttribute("hrow");
                                    JSONObject msg = JOTools.loadString(hrow.optString("message"));
                                    String dstring = msg.optString("dstring");
                                    String lineSplit = "@@\r\n";
                                    String colSplit = "\\{!!\\}";
                                    String[] lines = dstring.split(lineSplit);
                                    pageContext.setAttribute("lines", lines);
                                %>
                                <table summary="資料表格" >
                                    <caption><fmt:formatDate pattern="yyyy/MM/dd" value="${hrow.mt}" />更新</caption>
                                    <tr>
                                        <th class="aCenter">項次</th>
                                        <th class="aCenter">店內碼</th>
                                        <th class="aCenter">數量</th>
                                    </tr>
                                    <c:forEach var="line" items="${lines}">
                                        <tr align="center">
                                            <%
                                                String line = (String) pageContext.findAttribute("line");
                                                String[] cols = dstring.split(colSplit);
                                                pageContext.setAttribute("cols", cols);
                                            %>
                                            <td>${cols[0]}</td>
                                            <td><fmt:formatNumber pattern="WSF0000000000" value="${cols[1]}"/></td>
                                            <td>${cols[2]}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:forEach>

                            <!--因為購肥也不允許銷售數量為0，所以當該單數量被扣到0時，需要將該筆商品銷售資料整筆移除-->

                            <table summary="資料表格" >
                                <caption><fmt:formatDate pattern="yyyy/MM/dd" value="${main.mt}" /></caption>
                                <tr>
                                    <th class="aCenter">項次</th>
                                    <th class="aCenter">店內碼</th>
                                    <th class="aCenter">數量</th>
                                </tr>
                                <c:forEach varStatus="s" var="drow" items="${detail}" >
                                    <tr align="center">
                                        <td>${drow.itemNo}</td>
                                        <td>${drow.strBarcode}</td>
                                        <td>${drow.purchaseQty}</td>
                                    </tr>
                                </c:forEach>
                            </table>

                        </td>
                    </tr>

                    <tr>
                        <Th>關聯更新註記</Th>
                        <TD colspan="3">
                            <ol>
                                <c:forEach varStatus="s" var="hrow1" items="${history}" >
                                    <%
                                        JSONObject hrow1 = (JSONObject) pageContext.findAttribute("hrow1");
                                        String orderId = hrow1.optString("orderId");
                                        pageContext.setAttribute("orderId", orderId.split("\\-")[0]);
                                    %>
                                    <li>${s.count} : <fmt:formatDate pattern="yyyy/MM/dd" value="${hrow1.mt}" />｜<a href="afa_sale_detail.jsp?storeId=${hrow1.storeId}&oddNo=${orderId}">${orderId}</a>｜退貨補單扣除</li>
                                    </c:forEach>


                                <c:forEach varStatus="s" var="crow" items="${crows}" >
                                    <li>${s.count} : <fmt:formatDate pattern="yyyy/MM/dd" value="${crow.mt}" />｜<a href="afa_sale_detail.jsp?storeId=${crow.storeId}&oddNo=${crow.oddNo}">${crow.oddNo}</a>｜扣除退貨數量</li>
                                    </c:forEach>
                            </ol>
                        </td>
                    </tr>
                </table>

            </div>

        </div>
    </body>

</html>