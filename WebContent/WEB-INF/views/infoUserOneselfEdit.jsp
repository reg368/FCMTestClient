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
	$("#birthday").datepicker({dateFormat: 'yy/mm/dd'});
	
});
function setUpdateForm(){
	if(document.infoUserForm.password.value != document.infoUserForm.repassword.value){
		alert('密碼確認錯誤，請重新輸入!');
	}
	else{
	document.infoUserForm.action = "/admin/app/admin/InfoUser/update/infoUserOneselfAction";
	document.infoUserForm.submit();
	}
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
<input type="hidden" id="ugrpid" name="ugrpid" value="${infoUser.ugrpid}">
<input type="hidden" id="postlimit" name="postlimit" value="${infoUser.postlimit}">
<input type="hidden" id="reviewlimit" name="reviewlimit" value="${infoUser.reviewlimit}">
<form:hidden path="userid"/>
<form:hidden path="deptid"/>
<form:hidden path="status"/>
<form:hidden path="visitcount"/>
<form:hidden path="updateuser"/>
<form:hidden path="updatetime"/>
<form:hidden path="createuser"/>
<form:hidden path="createtime"/>
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
                <td><form:input path="name" cssClass="text"/><form:errors path="name" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>暱稱</th>
                <td><form:input path="nickname" cssClass="text"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>信箱</th>
                <td><form:input path="email" cssClass="text"/><form:errors path="email" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>職稱</th>
                <td><form:input path="jobtitles" cssClass="text"/><form:errors path="jobtitles" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>性別</th>
                <td>
                <form:radiobutton path="sex" id="male" value="M"/>男 
				<form:radiobutton path="sex" id="female" value="F"/>女
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>生日</th>
                <td><form:input path="birthday" cssClass="text" readonly="true"/><form:errors path="birthday" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>電話</th>
                <td><form:input path="tel" cssClass="text"/><form:errors path="tel" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must"></span>手機</th>
                <td><form:input path="cellphone" cssClass="text"/><form:errors path="cellphone" cssStyle="color : red;"/></td>
              </tr>
            </table>           

			<div class="aCenter">
        		<div class="remark"><span class="must">*</span>必填欄位</div>
               	<input name="submit1" type="button" class="btn_submit" onClick="setUpdateForm();"  value="編修存檔" /> 
				<input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>
      		</div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
