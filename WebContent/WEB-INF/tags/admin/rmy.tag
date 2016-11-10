<%@tag import="java.util.Calendar"%>
<%@tag import="java.util.Date"%>
<%@tag import="hyweb.jo.util.DateUtil"%>
<%@tag description="年月選單" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fid" %>
<%@attribute name="must" %>
<%
    Date d = (Date) request.getAttribute("minDate");
    Date de = (Date) request.getAttribute("maxDate");
%>
<tr>
    <th><span class="style1">*</span> ${label}</th>
    <td >
        <select id="${fid}1_y" name="${fid}1_y">
            <%
                while (d.compareTo(de) <= 0) {
                request.setAttribute("$yy", DateUtil.year(d));
            %>
            <option value="${$yy}" <c:if test="${ fp['dp1_y']==$yy }">selected</c:if> >西元${$yy}</option>
            <%
                   d= DateUtil.add_date(d, Calendar.YEAR, 1);
                }
            %>

        </select>年
        <select id="${fid}1_m" name="${fid}1_m">
            <option value="1" <c:if test="${ fp['dp1_m']==1  }">selected</c:if> >1</option>
            <option value="2" <c:if test="${ fp['dp1_m']==2  }">selected</c:if> >2</option>
            <option value="3" <c:if test="${ fp['dp1_m']==3  }">selected</c:if> >3</option> 
            <option value="4" <c:if test="${ fp['dp1_m']==4  }">selected</c:if> >4</option>
            <option value="5" <c:if test="${ fp['dp1_m']==5  }">selected</c:if> >5</option>
            <option value="6" <c:if test="${ fp['dp1_m']==6  }">selected</c:if> >6</option>
            <option value="7" <c:if test="${ fp['dp1_m']==7  }">selected</c:if> >7</option>
            <option value="8" <c:if test="${ fp['dp1_m']==8  }">selected</c:if> >8</option>
            <option value="9" <c:if test="${ fp['dp1_m']==9  }">selected</c:if> >9</option>
            <option value="10" <c:if test="${ fp['dp1_m']==10  }">selected</c:if> >10</option>
            <option value="11" <c:if test="${ fp['dp1_m']==11  }">selected</c:if> >11</option>
            <option value="12" <c:if test="${ fp['dp1_m']==12  }">selected</c:if> >12</option>
            </select>月
            ~
            <select id="${fid}2_y" name="${fid}2_y">
            <%
                d = (Date) request.getAttribute("minDate");
                while (d.compareTo(de) <= 0) {
                request.setAttribute("$yy", DateUtil.year(d));
                System.out.println(de.compareTo(d));
            %>
            <option value="${$yy}" <c:if test="${ fp['dp2_y']==$yy }">selected</c:if> >西元${$yy}</option>
            <%
                   d= DateUtil.add_date(d, Calendar.YEAR, 1);
                }
            %>
            </select>年
            <select id="${fid}2_m" name="${fid}2_m">
            <option value="1" <c:if test="${ fp['dp2_m']==1  }">selected</c:if> >1</option>
            <option value="2" <c:if test="${ fp['dp2_m']==2  }">selected</c:if> >2</option>
            <option value="3" <c:if test="${ fp['dp2_m']==3  }">selected</c:if> >3</option> 
            <option value="4" <c:if test="${ fp['dp2_m']==4  }">selected</c:if> >4</option>
            <option value="5" <c:if test="${ fp['dp2_m']==5  }">selected</c:if> >5</option>
            <option value="6" <c:if test="${ fp['dp2_m']==6  }">selected</c:if> >6</option>
            <option value="7" <c:if test="${ fp['dp2_m']==7  }">selected</c:if> >7</option>
            <option value="8" <c:if test="${ fp['dp2_m']==8  }">selected</c:if> >8</option>
            <option value="9" <c:if test="${ fp['dp2_m']==9  }">selected</c:if> >9</option>
            <option value="10" <c:if test="${ fp['dp2_m']==10  }">selected</c:if> >10</option>
            <option value="11" <c:if test="${ fp['dp2_m']==11  }">selected</c:if> >11</option>
            <option value="12" <c:if test="${ fp['dp2_m']==12  }">selected</c:if> >12</option>
        </select>月
    </td>
</tr>
<%!
    
%>