<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="p" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
function changePage(){
	ajax = decodeURI(
        (RegExp('ajax=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
	if(ajax == 1){
		alert('1');
	}else{
		alert('0');
	}
}
$(document).ready(function(){
	var msg = "<c:out value="${msg}"/>";
	if(msg != null && msg != '')
		alert(msg);
});
</script>
</head>
<body class="layout">
<div class="content">
<h2>使用者管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;"><span>使用者清單</span></h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
 <c:if test="${limits.add}">
   <li><a href='<c:url value="/app/admin/InfoUser/insert/infoUserPage"/>'>
   <img src='<c:url value="/app/images/icon_treeAdd.gif"/>' alt="使用者新增">使用者新增</a></li>
 </c:if>
   <li><a href="#" onclick="history.back();">
   <img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!-- navfunction -->
<div class="listfunction">
<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
</div>

<!-- 列表 -->
<div class="listTb">
<Table summary="資料表格">

<tr>
	<th class="aCenter">帳號</th>
	<th class="aCenter">姓名</th>
	<th class="aCenter">所屬組織</th>
	<th class="aCenter">連絡電話</th>
	<!--<th class="aCenter">權限群組</th>-->
	<!--th class="aCenter">上稿權限</th-->
	<!--th class="aCenter">審稿權限</th-->
	<th class="aCenter">啟用/停用</th>
	<!--<th class="aCenter">登入次數</th>-->
	<!--<th class="aCenter">最後更新人員</th>-->
	<!--<th class="aCenter">最後更新時間</th>	-->
</tr>                  
 <c:forEach items="${infoUsers}" var="infoUser">
						<tr>
							<td>
								<a href='<c:url value="/app/admin/InfoUser/view/infoUserPage?userid=${infoUser.userid}"/>'>
									<c:out value="${infoUser.userid}"/>
								</a>
							</td>
							<td><c:out value="${infoUser.name}"/></td>
							<td>
							<c:forEach items="${depts}" var="dept">
								<c:if test="${dept.deptseq==infoUser.deptid}">
									<c:out value="${dept.deptname}"/>
								</c:if>
							</c:forEach>
							</td>
							<td><c:out value="${infoUser.tel}"/></td>
							<!--<td><c:out value="${infoUser.ugrpid}"/></td>-->
							<!--td><c:out value="${infoUser.postlimit}"/></td-->
							<!--td><c:out value="${infoUser.reviewlimit}"/></td-->
							<td><c:out value="${infoUser.status=='Y' ? '啟用':'停用'}"/></td>
							<!--<td><c:out value="${infoUser.visitcount}"/></td>-->
							<!--<td><c:out value="${infoUser.updateuser}"/></td>-->
							<!--<td><fmt:formatDate pattern="yyyy/MM/dd" value="${infoUser.updatetime}" /></td>-->
						</tr>
					</c:forEach>             
</TABLE>
</div>

<div class="listfunction">
<p:page url="" pageJump="${pageJump}"  total="${count}" args="${args}" />
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
