<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<link href='<c:url value="/app/css/jquery-ui-timepicker-addon.css"/>' rel="stylesheet" type="text/css" />
<link href='<c:url value="/app/css/jquery-ui-1.10.1.custom.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery-ui-1.10.1.custom.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery-ui-timepicker-addon.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery-ui-sliderAccess.js'/>" type="text/javascript"></script>
<script src="<c:url value='/app/js/jquery-ui-timepicker-zh-TW.js'/>" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);

	$("#birthday").datepicker({dateFormat: 'yy/mm/dd'});
	var getUgrpids = document.infoUserForm.ugrpids.value;
	var getPostlimits = document.infoUserForm.postlimits.value;
	var getReviewlimits = document.infoUserForm.reviewlimits.value;
	selects = document.getElementById("ugrpid");
	ugrpids = getUgrpids.split(",");
	for(var i = 0; i < selects.options.length; i++ ){
		for(var j = 0; j < ugrpids.length; j++ ){
			if(selects.options[i].value==ugrpids[j])
				selects.options[i].selected=true;
		}
	}
	
	postlimit_select =document.getElementById("postlimit");
	postlimits = getPostlimits.split(",");
	for(var i = 0; i < postlimit_select.options.length; i++ ){
		for(var j = 0; j < postlimits.length; j++ ){
			if(postlimit_select.options[i].value==postlimits[j])
				postlimit_select.options[i].selected=true;
		}
	}
	reviewlimit_select = document.getElementById("reviewlimit");
	reviewlimits = getReviewlimits.split(",");
	for(var i = 0; i < reviewlimit_select.options.length; i++ ){
		for(var j = 0; j < reviewlimits.length; j++ ){
			if(reviewlimit_select.options[i].value==reviewlimits[j])
				reviewlimit_select.options[i].selected=true;
		}
	}
	
});
function setUpdateForm(){
	if(document.infoUserForm.password.value != document.infoUserForm.repassword.value){
		alert('密碼確認錯誤，請重新輸入!');
	}
	else{
	
	var emailValidator = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  if(emailValidator.test($.trim($('#email').val()))){
		  document.infoUserForm.action = "/admin/app/admin/InfoUser/update/infoUserAction";
	      document.infoUserForm.submit();
	  }else
		  alert("E-mail格式有誤");
	}
}
function setDeleteForm() {
	document.infoUserForm.action = "/admin/app/admin/InfoUser/delete/infoUserAction";
	document.infoUserForm.submit();
}
</script>
</head>
<body class="layout">
<div class="content">
<h2>使用者管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>使用者編輯</span></h2>

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
<form:form commandName="infoUser" action="" name="infoUserForm" method="POST">
<input type="hidden" id="ugrpids" name="ugrpids" value="${infoUser.ugrpid}">
<input type="hidden" id="postlimits" name="postlimits" value="${infoUser.postlimit}">
<input type="hidden" id="reviewlimits" name="reviewlimits" value="${infoUser.reviewlimit}">
<form:hidden path="userid"/>
<form:hidden path="visitcount"/>
<form:hidden path="updateuser"/>
<form:hidden path="updatetime"/>
<form:hidden path="createuser"/>
<form:hidden path="createtime"/>
<form:hidden path="nickname"/>
<form:hidden path="jobtitles"/>
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>使用者帳號</th>
                <td><form:input path="userid" cssClass="text" disabled="true"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>使用者密碼</th>
                <td><form:password path="password" cssClass="text"/><form:errors path="password" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>確認密碼</th>
                <td><input  type="password" id="repassword" name="repassword" class="text"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>姓名</th>
                <td><form:input path="name" cssClass="text"/></td><form:errors path="name" cssStyle="color : red;"/>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>連絡電話</th>
                <td><form:input path="tel" cssClass="text"/><form:errors path="tel" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>連絡E-mail</th>
                <td><form:input path="email" cssClass="text"/><form:errors path="email" cssStyle="color : red;"/></td>
              </tr>
               <tr>
                <th scope="row"><span class="must"></span>權限群組</th>
                <td>
                	<form:select multiple="true" path="ugrpid">
 									<form:options items="${ugrps}" itemValue="ugrpid" itemLabel="ugrpname" />
                	</form:select>  	
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>單位</th>
                <td>
                
                <form:hidden path="deptid" cssClass="text"/>
                <input id="deptname" name="deptname" class="text" type="text" readonly="true" 
                	value="${dept.deptname}" /><form:errors path="deptid" cssStyle="color : red;"/>
                <input type="button" value="選擇部門" onclick="javascript:window.open('<c:url value="/app/admin/Dept/insert/deptTreePage"/>',
                																	 '部門選擇',
                																	 'status=no,toolbar=no,scrollbars=yes,resizable=yes,width=300,Height=650');"/></td>
              </tr>

             
              <form:hidden path="postlimit"  value="1" />
              <!--tr>
                <th scope="row"><span class="must"></span>上稿權限</th>
                <td>
                	<form:select multiple="true" path="postlimit">
 						<form:options items="${codeMains}" itemValue="codeseq" itemLabel="codeshow" />
                	</form:select> 	
                </td>
              </tr-->
              <form:hidden path="reviewlimit"  value="1" />
              <!-- tr>
                <th scope="row"><span class="must"></span>審稿權限</th>
                <td>
                	<form:select multiple="true" path="reviewlimit">
 						<form:options items="${codeMains}" itemValue="codeseq" itemLabel="codeshow" />
                	</form:select> 	
                </td>
              </tr-->
              <tr>
                <th scope="row"><span class="must"></span>啟用狀態</th>
                <td><form:select path="status">
                	<form:option value="Y" label="啟用"/>
                	<form:option value="N" label="停用"/>
                	</form:select></td>
              </tr>
              <form:hidden path="sex"  value="M" />
              <!--tr>
                <th scope="row"><span class="must"></span>性別</th>
                <td>
                <form:radiobutton path="sex" id="male" value="M"/>男 
				<form:radiobutton path="sex" id="female" value="F"/>女
				</td>
              </tr-->
               <form:hidden path="birthday"  value="" />
              <!--tr>
                <th scope="row"><span class="must"></span>生日</th>
                <td><form:input path="birthday" cssClass="text" readonly="true"/><form:errors path="birthday" cssStyle="color : red;"/></td>
              </tr-->
              <form:hidden path="cellphone"  value="" />
              <!--tr>
                <th scope="row"><span class="must"></span>手機</th>
                <td><form:input path="cellphone" cssClass="text"/><form:errors path="cellphone" cssStyle="color : red;"/></td>
              </tr-->
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
        		<c:if test="${limits.edit}">
               		<input name="submit1" type="button" class="btn_submit" onClick="setUpdateForm();"  value="編修存檔" /> 
                </c:if>
        		<c:if test="${limits.delete}">
                	<input name="submit3" type="button" class="btn_cancel" onClick="if( confirm('是否確定刪除？') ){ setDeleteForm(); }else{ return false;}" value="刪除">                
                </c:if>
        		<c:if test="${limits.edit}">
                	<input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.back();"/>
      			</c:if>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
