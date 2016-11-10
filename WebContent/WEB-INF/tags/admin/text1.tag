<%@tag description="後台標準單欄位欄位" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fid" %>
<%@attribute name="placeholder" %>
<%@attribute name="description" %>
<%@attribute name="must" %>
<th><c:if test="${must}"><span class="style1">* </span></c:if>${label}</th>
    <td>
        <input name="${fid}" id="${fid}" type="text" class="text" value="${fp[fid]}"  placeholder="${placeholder}" />
    ${description}
</td>