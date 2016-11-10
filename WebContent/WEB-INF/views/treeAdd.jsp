<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
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
			網站管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;">
			目錄樹管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;">
			<span>資料新增</span>
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
<form:form commandName="tree" action="/admin/app/cms/TreeList/insert/treeAction" name="treeForm">     
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>目錄樹名稱</th>
                <td><form:input path="tname" cssClass="text"/><form:errors path="tname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>目錄樹狀態</th>
                <td>
                	<form:select path="tispublic">
 						<form:options items="${formShowYN}" itemValue="codevalue" itemLabel="codeshow" />
                	</form:select>  	
                </td>
              </tr>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
                <input name="submit1" type="submit" class="btn_submit" value="新增存檔" /> 
                <input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
