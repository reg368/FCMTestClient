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
	
	var apfunc = "";
	for(var i = 0; i < 8; i++) {
		apfunc = $('input[name=s'+i+']:checked').val() + apfunc;
	}
	
	document.ugrpApForm.apfunc.value = parseInt(apfunc, 2);
	document.ugrpApForm.action = "/admin/app/admin/Ugrp/update/ugrpApAction";
	document.ugrpApForm.submit();
}
function setDeleteForm() {
	document.ugrpApForm.action = "/admin/app/admin/Ugrp/delete/ugrpApAction";
	document.ugrpApForm.submit();
}

$(document).ready(function(){
	var apfunc = document.ugrpApForm.apfunc.value;
	var num =  parseInt(apfunc);
	
	for (var i = 0; i < 8; i++) {
		if ((num & (1<<i)) > 0) {
			$('#s'+i+'Y').prop('checked', true);
		} else {
			$('#s'+i+'N').prop('checked', true);
		}
	}
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>權限群組Ap對應管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>權限群組Ap對應編輯</span></h2>

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
<form action="" name="ugrpApForm" method="POST">
<input type="hidden" name="ugrpid" id="ugrpid" value="${ugrpAp.ugrpid}"/>
<input type="hidden" name="apid" id="apid" value="${ugrpAp.apid}"/>
<input type="hidden" name="apfunc" id="apfunc" value="${ugrpAp.apfunc}"/>
<table summary="資料表格">
              <tr>
                <th scope="row"><span class="must"></span>Ap功能名稱</th>
                <td>
                	${ugrpAp.apcname}
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>條列權限</th>
                <td>
                	<input type="radio" name="s0" id='s0N' value="0"/>無
                	<input type="radio" name="s0" id='s0Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>檢視權限</th>
                <td>
                	<input type="radio" name="s1" id='s1N' value="0"/>無
                	<input type="radio" name="s1" id='s1Y'  value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>新增權限</th>
                <td>
                	<input type="radio" name="s2" id='s2N' value="0"/>無
                	<input type="radio" name="s2" id='s2Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>修改權限</th>
                <td>
                	<input type="radio" name="s3" id='s3N' value="0"/>無
                	<input type="radio" name="s3" id='s3Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>刪除權限</th>
                <td>
                	<input type="radio" name="s4" id='s4N' value="0"/>無
                	<input type="radio" name="s4" id='s4Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>查詢權限</th>
                <td>
                	<input type="radio" name="s5" id='s5N' value="0"/>無
                	<input type="radio" name="s5" id='s5Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>列印權限</th>
                <td>
                	<input type="radio" name="s6" id='s6N' value="0"/>無
                	<input type="radio" name="s6" id='s6Y' value="1"/>有
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>匯出權限</th>
                <td>
                	<input type="radio" name="s7" id='s7N' value="0"/>無
                	<input type="radio" name="s7" id='s7Y' value="1"/>有
				</td>
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
                	<input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>
      </div>
		</form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
