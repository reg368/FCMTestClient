<%@tag description="權限縣市選單" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fid" %>
<tr>
    <th>${label}</th>
    <td >
        <c:if test="${dlevel1!=''}"><input type="hidden" name="${fid}" value="${dlevel1}"/>${dlevel1_text} </c:if>
        <c:if test="${dlevel1==''}"><select name="${fid}">
                ${ht_pcity}
            </select>
        </c:if>
    </td>
</tr>
