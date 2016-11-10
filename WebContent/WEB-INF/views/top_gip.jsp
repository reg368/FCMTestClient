<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="true" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('div.topmenu > ul > li > a').click(function() {
			var nowmenu = $(this).parent();
			var childmenu = nowmenu.find('li');
			if(nowmenu.attr('class').indexOf('here') == -1) {
				childmenu.show();
				nowmenu.attr('class', nowmenu.attr('class') + 'here');
			}else{
				childmenu.hide();
				nowmenu.attr('class', nowmenu.attr('class').replace('here', ''));
			}
			
			var othermenu = $('div.topmenu > ul > li').not(nowmenu);
			othermenu.each(function() {
				var menu = $(this);
				menu.find('li').hide();
				menu.attr('class', menu.attr('class').replace('here', ''));
			});
		});
		$('li.childmenu').click(function() {
			$('li.childmenu').removeClass('here');
			$(this).addClass('here');
		});
	});
</script>
<title>HyCMS 網站內容管理系統</title>
</head>
<body>
<div class="top">
<h1><a href="<c:url value='/app/admin/Home'/>" target="_parent"><img src="<c:url value='/app/images/logo.png'/>" alt="HyCMS" /></a></h1>

<div class="toplink">
<div class="topinfo"><span class="user">${InfoUser.name}</span>(${InfoUser.userid}, ${deptname}) 上次<span><fmt:formatDate pattern="yyyy/MM/dd" value="${InfoUserLogin.lastvisit}" /></span> 由 <span>${InfoUserLogin.lastip}</span> 第 <span>${InfoUser.visitcount}</span>次登入</div>
<div class="nav">
	<ul>
    <li class="PImainten"><a href="<c:url value='/app/admin/InfoUser/view/infoUserOneselfPage' /> " target='mainFrame' title="個人資料維護">個人資料維護</a></li>
    <li class="logout"><a href="<c:url value='/app/login/logout'/>" title="登出" target="_parent">登出</a>	</li>
    </ul>		
</div>
</div>

<div class="topmenu">
	<ul>
		<c:forEach items="${apCats}" var="apCat" varStatus="i">
			<li class="${apCat.apcaticonurl}"><a href="#" title="${apCat.apcatcname}"><img alt="${apCat.apcatcname}" src="/admin/app/images/space.gif" /></a>
				<ul>
					<c:forEach items="${aps}" var="ap">
						<c:if test="${apCat.apcatid == ap.apcat}">
							<li class="childmenu" style="display:none;"><a href="<c:url value='${ap.apurl}'/>" target=mainFrame><c:out value="${ap.apcname}" /></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</li>	
		</c:forEach>
	</ul>
</div>
</div>
</body>
</html>
