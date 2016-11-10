<%@tag description="分頁大小選單" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="url" %>
<script type="text/javascript">
    $(function () {
        $("#tb_size").change(function () {
            location.href = '<c:url value="${url}"/>?page=1&numPage=' + $(this).val() + '&ejo=${ejo}';
        });
    });
</script>
<label for="tb_size">共 ${total}筆資料，每頁顯示</label>
<select id="tb_size" class="select" name="tb_size" size="1">
    <option <c:if test="${numPage=='50'}">selected</c:if>  value="50">50</option>
    <option <c:if test="${numPage=='100'}">selected</c:if>  value="100">100</option>
</select>
<label for="tb_id">筆</label>
