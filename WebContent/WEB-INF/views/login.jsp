<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>HyCMS 網站內容管理系統</title>
<link href="<c:url  value='/app/css/login.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url  value='/app/js/jquery-1.9.1.min.js'/>"></script>
<script>
function reload(){
	var img = $('#identifyImg');
	var src = img.attr('src');
	var end = src.indexOf('?');
	var ret;
	if (end >= 0) {
		ret = src.substring(0, end);
	} else {
		ret = src;
	}
	img.attr('src', ret+"?reload=" + new Date().getTime());
}

$(document).ready(function() {
	  $('#btn_submit').click(function() {
		var code = $.trim(document.getElementById('verification').value);
		
	    $.ajax({
	      url: '/admin/app/login/checkCode',
	      type: "POST",
	      data: {checkCode : code},
	      cache: false,
	      success: function(data) {
	    	  
	    	  if(data.replace(/(^\s*)|(\s*$)/g, "") != 'true'){
         		alert('驗證碼輸入錯誤');
              }else{
            	  $("#loginform").submit();	  
            }
	      }
	    });
	    
	    return false;
	  });
	  
	  $('#userId, #userPass, #verification').keypress(function(e){
		  if(e.keyCode == 13){
			  $('#btn_submit').click();
			  return false;  
		  }
	  });
	});


</script>
</head>

<body>
<div class="login">
<div class="loginForm">
 <form method="post"  class="form_admin" action="<c:url  value='/app/login/accept'/>" name="loginform" id="loginform">
    <label for="userId">帳號：</label><input type="text" name="userid" id="userId" value="" /> <br/>  
    <label for="userPass">密碼：</label><input type="password" name="password" id="userPass" value="" /><br/>  
    <input type="image" src="<c:url value='/app/images/icon_login.gif' />"  alt="登入" hspace="10" vspace="5" border="0" align="absmiddle"  id="btn_submit"/>
          <span class="numCode">
    		<img id="identifyImg" src="<c:url  value='/app/login/getImage?reload=<%=new Date().getTime() %>'/>" alt="img" />
    		 	<a href="javascript:void(0)" onclick="reload();"><img src="<c:url  value='/app/images/icon_afresh.gif' />" /></a>
    	</span><br/>
    <label for="verification">驗證碼：</label><input type="text" name="verification" id="verification" value="" />
</form>
</div>
<!--div class="relateLink">
  </div-->
  <jsp:include page="include/footer.jsp" />
</div>

</body>
</html>
