<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${$grant.query}">
        <div class="ContentTb">
            <form id="form1" action="afa_sale.jsp" mothod="post">
                <input type="hidden" name="act" value="query"/> 
                <table summary="資料表格">
                    <html:ad_rdate dp1="dp1" dp2="dp2" label="POS上傳日期區間："  />
                    <tr>
                        <Th>店代號</Th>
                        <TD><input name="Text1" type="text" />
                            <a href="#"><img src="images/icon_quickAdd.gif" style="vertical-align:middle;" /></a>
                        </td>
                    </tr>

                    <tr>
                        <Th>銷售單號</Th>
                        <td><input name="Text1" type="text" />&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <Th>農友身分證字號</Th>
                        <td><input name="Text1" type="text" />&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <Th>例外狀況</Th>
                        <td>
                            <Select name="" size=1>
                                <option value="All" selected="selected">全部</option>
                                <option value="Y">Y</option>
                                <option value="N">N</option>
                            </select>
                        </td>
                    </tr>




                </table>
                <div class="aCenter">
                    <input name="submit1" type="submit" value="查　　詢" />
                    <script type="text/javascript">
                        $(function () {
                            $('#do_cancel').click(function () {
                                window.location.href = "rpt_service.jsp";
                            });
                        });
                    </script>
                    </script>
                    <input id="do_cancel" name="submit2" type="button" value="取　　消" />
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>沒有查詢權限！！！</c:otherwise>
</c:choose>