<%@tag description="年月選單" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="label" %>
<%@attribute name="fid" %>
<%@attribute name="must" %>
<%@attribute name="start" %>
<%@attribute name="end" %>
<tr>
    <th><span class="style1">*</span> ${label}</th>
    <td >
        <select id="${fid}_y" name="${fid}_y">
            <option value="2014" <c:if test="${ fp['dp_y']==2014  }">selected</c:if> >西元2014</option>
            <option value="2015" <c:if test="${ fp['dp_y']==2015  }">selected</c:if> >西元2015</option> 
            <option value="2016" <c:if test="${ fp['dp_y']==2016  }">selected</c:if> >西元2016</option>
        </select>年
        <select id="${fid}_m" name="${fid}_m">
            <option value="1" <c:if test="${ fp['dp_m']==1  }">selected</c:if> >1</option>
            <option value="2" <c:if test="${ fp['dp_m']==2  }">selected</c:if> >2</option>
            <option value="3" <c:if test="${ fp['dp_m']==3  }">selected</c:if> >3</option> 
            <option value="4" <c:if test="${ fp['dp_m']==4  }">selected</c:if> >4</option>
            <option value="5" <c:if test="${ fp['dp_m']==5  }">selected</c:if> >5</option>
            <option value="6" <c:if test="${ fp['dp_m']==6  }">selected</c:if> >6</option>
            <option value="7" <c:if test="${ fp['dp_m']==7  }">selected</c:if> >7</option>
            <option value="8" <c:if test="${ fp['dp_m']==8  }">selected</c:if> >8</option>
            <option value="9" <c:if test="${ fp['dp_m']==9  }">selected</c:if> >9</option>
            <option value="10" <c:if test="${ fp['dp_m']==10  }">selected</c:if> >10</option>
            <option value="11" <c:if test="${ fp['dp_m']==11  }">selected</c:if> >11</option>
            <option value="12" <c:if test="${ fp['dp_m']==12  }">selected</c:if> >12</option>
        </select>月
    </td>
</tr>
