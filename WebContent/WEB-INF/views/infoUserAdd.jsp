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


function sub(){
	//var userid = (document.getElementById('userid').value).toString().trim();
	var userid = $.trim($('#userid').val());
    $.ajax({
      url: '/admin/app/admin/InfoUser/checkUserId',
      type: "POST",
      data: {userid : userid},
      success: function(data) {
    	  if(data.replace(/(^\s*)|(\s*$)/g, "") != 'true'){
	    		  if(document.infoUserForm.password.value == 
	    			  document.infoUserForm.passwordCheck.value){
	    			  var emailValidator = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    				  if(emailValidator.test($.trim($('#email').val())))
	    			  	      document.infoUserForm.submit();
	    				  else
	    					  alert("E-mail格式有誤");
	    		  }
	    		  else
	    			  alert("密碼確認有誤");
          }else{
        	  alert("此帳號已被使用");
        }
      }
    });
}
$(document).ready(function() {
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);
	
	$("#birthday").datepicker({
		dateFormat: 'yy/mm/dd',
		defaultDate: new Date().getDay()-15*365
	});
	
	$("#userid").focusout(function() {
		//var userid = (document.getElementById('userid').value).toString().trim();
		var userid = $.trim($(this).val());
	    $.ajax({
	      url: '/admin/app/admin/InfoUser/checkUserId',
	      type: "POST",
	      data: {userid : userid},
	      success: function(data) {
	    	  if(data.replace(/(^\s*)|(\s*$)/g, "") != 'true'){
	    		  document.getElementById('msg').innerHTML="此帳號可使用";
              }else{
            	  document.getElementById('msg').innerHTML="此帳號已被使用";
            }
	      }
	    });
	});
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>使用者管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>使用者新增</span></h2>

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
<form:form commandName="infoUser" action="/admin/app/admin/InfoUser/insert/infoUserAction" name="infoUserForm" method="POST">
<form:hidden path="visitcount"/>
<form:hidden path="updateuser"/>
<form:hidden path="updatetime"/>
<form:hidden path="createuser"/>
<form:hidden path="createtime"/>
<form:errors path="updatetime" cssStyle="color : red;"/>
<form:hidden path="nickname"  value="" />
<form:hidden path="jobtitles"  value="" />
<form:hidden path="cellphone"  value="" />
<table summary="資料表格">
              <tr>
                <th scope="row"><span class="must">*</span>使用者帳號</th>
                <td><form:input path="userid" cssClass="text"/><form:errors path="userid" cssStyle="color : red;"/><div id="msg"></div></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>使用者密碼</th>
                <td><form:password path="password" cssClass="text"/><form:errors path="password" cssStyle="color : red;"/></td>
              </tr>
              <tr>
              <th scope="row"><span class="must">*</span>確認密碼</th>
              <td><input type="password" name="passwordCheck" class="text"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>姓名</th>
                <td><form:input path="name" cssClass="text"/><form:errors path="name" cssStyle="color : red;"/></td>
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
                <input id="deptname" name="deptname" class="text" type="text" value="" />
                <input type="button" value="選擇部門" onclick="javascript:window.open('<c:url value="/app/admin/Dept/insert/deptTreePage"/>',
                																	 '部門選擇',
                																	 'status=no,toolbar=no,scrollbars=yes,resizable=yes,width=300,Height=650');"/>
                <form:errors path="deptid" cssStyle="color : red;"/>
                </td>
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
        <c:if test="${limits.add}">
                <input name="submit1" type="button" class="btn_submit" value="新增存檔" onclick="sub();"/> 
                <input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.back();"/>
      	</c:if>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
