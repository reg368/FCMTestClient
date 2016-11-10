<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="html" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${$grant.query}">
        <div class="ContentTb">
            <form id="form1" action="rpt_service.jsp" mothod="post">
                <input type="hidden" name="act" value="query"/> 
                <table summary="資料表格">
                    <html:ad_rdate dp1="dp1" dp2="dp2" label="日期區間："  />
                    <th>是否成功</th>
                    <td>
                        <Select name="isSucces" size=1>
                            <option value="" selected="selected">全部</option>
                            <option value="Y"  <c:if test="${fp.isSucces=='Y'}">selected</c:if> >成功</option>
                            <option value="N" <c:if test="${fp.isSucces=='N'}">selected</c:if>  >失敗</option>
                            </select>
                        </td>
                        <tr>
                            <Th>執行WS</Th>
                            <TD>
                                <select name="wsSource" id="Display1">
                                    <option value="">=全部=</option>
                                    <option value="afaWS" <c:if test="${fp.wsSource=='afaWS'}">selected</c:if> >afaWS</option>
                                <option value="baphiqPOS" <c:if test="${fp.wsSource=='baphiqPOS'}">selected</c:if>>baphiqPOS</option>
                                </select>
                                <select name="wsName" id="Display1"><!--下拉第一層後連動第二層-->
                                    <option value="">=全部=</option>
                                    <option value="">EXPORTGOODATA</option>
                                    <option value="">GETFARMERINFO</option>

                                    <option value="">SENDSALDATA</option>

                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th>店代號</th>
                            <TD><input name="storeId" type="text"  value="${fp.storeId}"/>
                                <!--a href="#"><img src="images/icon_quickAdd.gif" style="vertical-align:middle;" /></a-->
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