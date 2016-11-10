<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>HyCMS 網站內容管理系統</title>
<link href="<c:url  value='/app/css/design.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url  value='/app/js/jquery-1.9.1.min.js'/>"></script>
</head>
<body>
<!-- div class="leftCol">
  <h2>檢驗管理</h2>
  <ul class="submenu">
    <li><a href="TestOrg_List.htm">檢驗機構管理構管理構管理</a></li>
    <li><a href="TestItem_List.htm">檢驗作物管理</a></li>
    <li><a href="TestItemReport_List.htm">檢驗紀錄管理</a></li>
  </ul>
  <h2>契作管理</h2>
  <ul class="submenu">
    <li><a href="Farmer_List.htm">契作戶管理</a></li>
    <li><a href="Card_Query.htm">農藥購買紀錄</a></li>
    <li><a href="Import_ID.htm">收貨管理</a></li>
  </ul>
  <h2>報表管理</h2>
  <ul class="submenu">
    <li><a href="applyOrg_Add.htm">報表查詢管理</a></li>
  </ul>
  <h2>系統管理</h2>
  <ul class="submenu">
    <li><a href="User_Mod.htm">個人資料維護</a></li>
    <li><a href="User_List.htm">使用者管理</a></li>
    <li><a href="Org_List.htm">組織管理</a></li>
    <li><a href="Group_list.htm">權限管理</a></li>
  </ul>
</div-->

<div class="leftCol">
<c:forEach items="${apCats}" var="apCat" varStatus="i">
<h2>${apCat.apcatcname}</h2>
<ul class="submenu">
<c:forEach items="${aps}" var="ap">
<c:if test="${apCat.apcatid == ap.apcat}">
<li><a href="<c:url value='${ap.apurl}'/>" target=mainFrame><c:out value="${ap.apcname}" /></a></li>
 </c:if>  
</c:forEach>
</ul>
</c:forEach>
</div>

</body>
</html>