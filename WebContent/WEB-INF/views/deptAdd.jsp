<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>
			組織管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>組織新增</span>
		</h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   <li><a href="#" onclick="javascript:history.go(-1);"><img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!--form-->
<div class="formTb"> 
<form:form commandName="dept" action="/admin/app/admin/Dept/insert/deptAction" name="deptForm">     
<table summary="資料表格">
			  <form:hidden path="depthierarchycode"/>
			  <form:hidden path="deptid" />
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>組織中文名稱</th>
                <td><form:input path="deptname" cssClass="text"/><form:errors path="deptname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>組織英文名稱</th>
                <td><form:input path="deptename" cssClass="text"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>使用狀態</th>
                <td>
                	<form:select path="deptinuse">
                		<form:option value="1" label="使用"></form:option>
                		<form:option value="0" label="停用"></form:option>
                	</form:select>
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>排序</th>
                <td><form:input path="deptsort" cssClass="text" value="100"/><form:errors path="deptsort" cssStyle="color : red;"/></td>
              </tr>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
        	<c:if test="${limits.add}">
                <input name="submit1" type="submit" class="btn_submit" value="新增存檔" /> 
                <input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>
            </c:if>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
