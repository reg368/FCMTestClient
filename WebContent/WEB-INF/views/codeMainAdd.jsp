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
		var addRow = 0;
		$('#add').click(function(){
			addRow += 1;
			$('<tr><th>額外呈現值' + addRow + '</th><td><input name="showvalue" value="" class="text"/></td></tr>').insertBefore('tr:last');
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
			代碼管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>代碼新增</span>
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
<form:form commandName="codeMain" action="/admin/app/admin/CodeMain/insert/codeMainAction" name="codeMainForm">     
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>代碼</th>
                <td>
                	<span><c:out value="${codeId}"/></span>
                	<form:hidden path="codeid" value="${codeId}"/>
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>代碼值</th>
                <td><form:input path="codevalue" cssClass="text"/><form:errors path="codevalue" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><a href="#" id="add"><img src='<c:url value="/app/images/arrow_down.gif"/>' alt="新增欄位顯示"></a><span class="must">*</span>代碼顯示</th>
                <td><form:input path="codeshow" cssClass="text"/><form:errors path="codeshow" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>排序</th>
                <td><form:input path="codesort" cssClass="text" value="100"/><form:errors path="codesort" cssStyle="color : red;"/></td>
              </tr>
              	<input type="hidden" name="expendTid" value="<c:out value='${expendTid}'/>"/>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
        <c:if test="${limits.add}">
                <input name="submit1" type="submit" class="btn_submit" value="新增存檔" /> 
                <input name="submit2" type="reset" class="btn_reset"  value="取消" onclick="javascript:history.back();"/>
        </c:if>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
