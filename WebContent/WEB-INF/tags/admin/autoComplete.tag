<%@tag description="權限縣市選單" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fid" %>
<%@attribute name="placeholder" %>
<%@attribute name="description" %>
<%@attribute name="must" %>
<c:set var="dataId" value="${fid}${'_data'}"/>
<script type="text/javascript">
    $(function(){
       var ${dataId} = ${fp[dataId]} ;
       $('#${fid}').autocomplete({source: ${dataId} , delay: 100 }) ; 
    });
</script>
<tr>
     <th><c:if test="${must}"><span class="style1">* </span></c:if>${label}</th>
    <td >
        <input id="${fid}" name="${fid}"  value="${fp[fid]}" />
    </td>
</tr>
