<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>HyCMS 網站內容管理系統</title>
		<link href="/admin/app/css/design.css" rel="stylesheet" type="text/css">
		<script src="/admin/app/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			var radioswitch = function(){
				if('sendtype1' == $("[name='sendtype']:checked").prop('id')){
					init1();
				}else{
					init2();
				}
			};
			var init1 = function(){
				$('#mailtitle').parents('tr:first').hide();
				$('#content').parents('tr:first').hide();
			};

			var init2 = function(){
				$('#mailtitle').parents('tr:first').show();
				$('#content').parents('tr:first').show();
			};
		</script>
	</head>
	<body>
		<div class="content">
			<h2>發送訊息</h2>
			<div class="formTb">
				<form action="/admin/app/page/Shelves/reports/shelvesLecture/sendMailAction" method="post">
				<c:choose>
					<c:when test="${not empty param.sid }">
						<input type="hidden" name="sid" value="<c:out value="${param.sid}" />" />
						<input type="hidden" name="title" value="<c:out value="${param.title}" />" />
						<input type="hidden" name="signNo" value="<c:out value="${param.signNo}" />" />
						<input type="hidden" name="signName" value="<c:out value="${param.signName}" />" />
						<input type="hidden" name="signMobilePhone" value="<c:out value="${param.signMobilePhone}" />" />
						<input type="hidden" name="createTime_1" value="<c:out value="${param.createTime_1}" />" />
						<input type="hidden" name="createTime_2" value="<c:out value="${param.createTime_2}" />" />
						<c:forEach items="${paramValues.lectureType}" varStatus="i" var="lectureType" >
  						<input type="hidden" name="lectureType" value="<c:out value="${lectureType}" />" />
						</c:forEach>
						<c:forEach items="${paramValues.orderstatus}" varStatus="i" var="orderstatus" >
  						<input type="hidden" name="orderstatus" value="<c:out value="${orderstatus}" />" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${paramValues.signno}" varStatus="i" var="signno" >
  						<input type="hidden" name="signno" value="<c:out value="${signno}" />" />
						</c:forEach>
					</c:otherwise>
				</c:choose>
					<table summary="資料表格">
						<tr>
							<th>訊息類型</th>
							<td>
								<label for="sendtype1"><input type="radio" name="sendtype" id="sendtype1" onclick="radioswitch(this)" value="1" checked="checked" />預設通知</label>
								<label for="sendtype2"><input type="radio" name="sendtype" id="sendtype2" onclick="radioswitch(this)" value="2"  />自訂通知</label>
							</td>
						</tr>
						<tr>
							<th>標題</th>
							<td>
								<input type="text" class="text" name="mailtitle" id="mailtitle" value="" />
							</td>	
						</tr>
						<tr>
							<th>內容</th>
							<td>
								<textarea style="width:95%;" name="content" id="content" rows="10" ></textarea>
							</td>
						</tr>
					</table>
					<div class="aCenter">
						<input type="submit" class="btn_submit" value="寄送" />&nbsp; 
						<input type="button" class="btn_reset"  value="重設" />
					</div>
				</form>
			</div>	
		</div>
		<script type="text/javascript">
			init1();
		</script>
	</body>
</html>
