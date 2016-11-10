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
	document.codeMainForm.action = "/admin/app/admin/CodeMain/update/codeMainAction";
}

function setDeleteForm() {
	document.codeMainForm.action = "/admin/app/admin/CodeMain/delete/codeMainAction";
}

function getSubListPage(){
	$.ajax({
		type:"POST",
		url:'/admin/app/admin/CodeRef/list/codeRefById',
		data: "refid=" + '<c:out value="${codeMain.codeid}"/>' + "&refvalue=" + '<c:out value="${codeMain.codevalue}"/>' + "&expendTid=<c:out value='${expendTid}'/>",
		success: function(data){
			$('.content:first').html(data);
		},
		cache: false
	});
}

$(document).ready(function(){
	var addRow = 0;
	$('#add').click(function(){
		addRow += 1;
		$('<tr><th>增加額外呈現值' + addRow + '</th><td><input name="showvalue" value="" class="text"/></td></tr>').insertBefore('tr:last');
	});
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>
			代碼管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>代碼編輯</span>
		</h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   		<li><a href="#" onclick="getSubListPage();"><img src='<c:url value="/app/images/icon_optional.gif"/>' alt="子代碼列表">子代碼列表</a></li>	
   		<li><a href="javascript:history.back();"><img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!--form-->
<div class="formTb"> 
<form:form commandName="codeMain" name="codeMainForm">
<form:hidden path="codeseq"/>   
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>代碼</th>
                <td><form:input path="codeid" cssClass="text"/><form:errors path="codeid" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>代碼值</th>
                <td><form:input path="codevalue" cssClass="text"/><form:errors path="codevalue" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><a href="#" id="add"><img src='<c:url value="/app/images/arrow_down.gif"/>' alt="新增欄位顯示"></a><span class="must">*</span>代碼顯示</th>
                <td><form:input path="codeshow" cssClass="text"/><form:errors path="codeshow" cssStyle="color : red;"/></td>
              </tr>
              <c:forEach items="${showValue}" var="show" varStatus="i">
              	<tr>
              		<th scope="row">額外呈現值${i.count}</th>
              		<td><input name="showvalue" value="${show.showvalue}" class="text"/></td>
              	</tr> 
              </c:forEach>
              <tr>
                <th scope="row"><span class="must">*</span>排序</th>
                <td><form:input path="codesort" cssClass="text"/><form:errors path="codesort" cssStyle="color : red;"/></td>
              </tr>
              <input type="hidden" name="expendTid" value="<c:out value='${expendTid}'/>"/>
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
