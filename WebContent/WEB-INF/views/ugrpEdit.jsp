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
function setUpdateForm(){
	document.ugrpForm.action = "/admin/app/admin/Ugrp/update/ugrpAction";
	document.ugrpForm.submit();
}
function setDeleteForm() {
	document.ugrpForm.action = "/admin/app/admin/Ugrp/delete/ugrpAction";
	document.ugrpForm.submit();
}
</script>
</head>
<body class="layout">
<div class="content">
<h2>權限群組管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>權限群組編輯</span></h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   <li><a href="#" onclick="history.back();"><img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!--form-->
<div class="formTb"> 
<form:form commandName="ugrp" action="" name="ugrpForm" method="POST">
<form:hidden path="ugrpid"/>
<table summary="資料表格">
              <tr>
                <th scope="row"><span class="must">*</span>權限群組名稱</th>
                <td><form:input path="ugrpname" cssClass="text"/><form:errors path="ugrpname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>權限群組描述</th>
                <td><form:textarea rows="5" path="remark" cssClass="text"/><form:errors path="remark" cssStyle="color : red;"/></td>
              </tr>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
        		<c:if test="${limits.edit}">
                	<input name="submit1" type="submit" class="btn_submit" onClick="setUpdateForm();"  value="編修存檔" /> 
                </c:if>
        		<c:if test="${limits.delete}">
                	<input name="submit3" type="submit" class="btn_cancel" onClick="if( confirm('是否確定刪除？') ){ setDeleteForm(); }else{ return false;}" value="刪除">                
                </c:if>
                	<input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.back();"/>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
