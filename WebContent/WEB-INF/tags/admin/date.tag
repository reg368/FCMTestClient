<%@tag description="後台標準欄位" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="dp" %>
<%@attribute name="must" %>
<%@attribute name="placeholder" %>
<script type="text/javascript">
    $(function () {
        $.datepicker.setDefaults($.extend({showMonthAfterYear: false}, $.datepicker.regional['']));
        var op = $.datepicker.regional['zh-TW'];
        //op.showOn = 'button';
        //op.buttonImage = '${$cp}/admin/pos/images/calendar.png';
        //op.buttonImageOnly = true;
        op.changeMonth = true;
        op.changeYear = true;
        op.minDate = '${minDate}';
        op.maxDate = '${maxDate}';
        $('#${dp}').datepicker(op);
    });
</script>
<tr>
    <th><c:if test="${must}"><span class="style1">* </span></c:if>${label}</th>
    <td>
        <input name="${dp}" type="text" style="width: 113px" id="${dp}" value="${fp[dp]}" placeholder="${placeholder}"/>   
    </td>
</tr>