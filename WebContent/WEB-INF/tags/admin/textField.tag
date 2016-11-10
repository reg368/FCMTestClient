<%@tag description="後台標準欄位" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fieldId" %>
<%@attribute name="size" %>
<%@attribute name="th" %>
<%@attribute name="td" %>
<%@attribute name="placeholder" %>
<tr>
<th style="${th}">${label}</th>
<td style="${td}"><input name="${fieldId}" id="${fieldId}" type="text" class="text" value="${wp[fieldId]}"  placeholder="${placeholder}" /></td>
</tr>