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
	document.codeRefForm.action = "/admin/app/admin/CodeRef/update/codeRefAction";
}

function setDeleteForm() {
	document.codeRefForm.action = "/admin/app/admin/CodeRef/delete/codeRefAction";
}
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
			代碼管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>子代碼編輯</span>
		</h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   	<li><a href="javascript:history.back();"><img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!--form-->
<div class="formTb"> 
<form:form commandName="codeRef" name="codeRefForm">
<form:hidden path="seq"/>   
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>子代碼值</th>
                <td><form:input path="subcodevalue" cssClass="text"/><form:errors path="subcodevalue" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>子代碼顯示</th>
                <td><form:input path="subcodeshow" cssClass="text"/><form:errors path="subcodeshow" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>排序</th>
                <td><form:input path="subcodesort" cssClass="text"/><form:errors path="subcodesort" cssStyle="color : red;"/></td>
              </tr>
              <form:hidden path="refid"/>
              <form:hidden path="refvalue"/>
              <input type="hidden" name="expendTid" value="<c:out value='${expendTid}'/>"/>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
            <input name="submit1" type="submit" class="btn_submit" onClick="setUpdateForm();"  value="編修存檔" />  
            <input name="submit3" type="submit" class="btn_cancel" onClick="if( confirm('是否確定刪除？') ){ setDeleteForm(); }else{ return false;}" value="刪除">                
            <input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>  
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
