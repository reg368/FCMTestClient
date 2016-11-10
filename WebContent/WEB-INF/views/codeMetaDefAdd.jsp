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
			代碼群組管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>代碼群組新增</span>
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
<form:form commandName="codeMetaDef" action="/admin/app/admin/CodeMetaDef/insert/codeMetaDefAction" name="codeMetaDefForm">     
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>代碼群組ID</th>
                <td><form:input path="codemetaid" cssClass="text"/><form:errors path="codemetaid" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>代碼群組名稱</th>
                <td><form:input path="codemetaname" cssClass="text"/><form:errors path="codemetaname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照資料表</th>
                <td><form:input path="codemetatblname" cssClass="text" value="CodeMain"/><form:errors path="codemetatblname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row">對照欄位</th>
                <td><form:input path="codemetasrcfld" cssClass="text" value="codeId"/></td>
              </tr>
              <tr>
                <th scope="row">對照代碼</th>
                <td><form:input path="codemetasrcitem" cssClass="text"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照Value欄</th>
                <td><form:input path="codemetavaluefld" cssClass="text" value="codeValue"/><form:errors path="codemetavaluefld" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照Value欄名稱</th>
                <td><form:input path="codemetavaluefldname" cssClass="text" value="代碼值"/><form:errors path="codemetavaluefldname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照顯示欄</th>
                <td><form:input path="codemetashowfld" cssClass="text" value="codeShow"/><form:errors path="codemetashowfld" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照顯示欄名稱</th>
                <td><form:input path="codemetashowfldname" cssClass="text" value="顯示值"/><form:errors path="codemetashowfldname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row">對照排序欄</th>
                <td><form:input path="codemetasortfld" cssClass="text" value="codeSort"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>對照排序欄名稱</th>
                <td><form:input path="codemetasortfldname" cssClass="text" value="排序值"/><form:errors path="codemetasortfldname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row">代碼群組類別</th>
                <td><form:input path="codemetatype" cssClass="text" value="無分類"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>是否顯示</th>
                <td>
                	<form:radiobutton path="showornot" value="0"/>否
					<form:radiobutton path="showornot" value="1"/>是
					<form:errors path="showornot" cssStyle="color : red;"/>
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>排序</th>
                <td><form:input path="codemetasort" cssClass="text" value="100"/><form:errors path="codemetasort" cssStyle="color : red;"/></td>
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
