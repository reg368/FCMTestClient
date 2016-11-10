<%@tag description="日期範為欄位" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="dp1" %>
<%@attribute name="dp2" %>
<%@attribute name="description" %>
<script type="text/javascript">
    $(function () {
        $.datepicker.setDefaults($.extend({showMonthAfterYear: false}, $.datepicker.regional['']));
        var op = $.datepicker.regional['zh-TW'];
        op.showOn = 'button';
        op.buttonImage = '${$cp}/admin/pos/images/calendar.png';
        op.buttonImageOnly = true;
        op.changeMonth = true;
        op.changeYear = true;
        op.minDate='${minDate}';
        op.maxDate='${maxDate}';
        $('#${dp1}').datepicker(op);
        $('#${dp2}').datepicker(op);
    });
</script>
<tr>
    <th>${label}</th>
    <td colspan>
        <input name="${dp1}" type="text" style="width: 113px" id="${dp1}" value="${fp[dp1]}" placeholder="請輸查詢開始時間"/>
        ～
        <input name="${dp2}" type="text" style="width: 113px" id="${dp2}" value="${fp[dp2]}" placeholder="請輸查詢結束時間"/>
        <br/>
        ${description}
    </td>
</tr>
