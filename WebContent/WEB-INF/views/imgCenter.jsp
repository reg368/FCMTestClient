<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	function searchByKey(){
		$(document.getElementById("nowPage")).val('1');
		window.document.imgForm.action='/admin/app/page/AddData/select/imgCenter';
		window.document.imgForm.submit();
	}
	function sub(){
		var chkId = $('input[type="checkbox"]:checked');
		if(chkId.size()>window.opener.getLimit_<c:out value="${divname}"/>()){
			alert("圖片選擇過多!");
		}else{
			window.document.imgForm.show.value = window.opener.getShow_<c:out value="${divname}"/>();
			window.document.imgForm.submit();
		}
	}
	function changePage(nowPage){
		$(document.getElementById("nowPage")).val(nowPage);
		window.document.imgForm.action='/admin/app/page/AddData/select/imgCenter';
		window.document.imgForm.submit();
	}
</script>
</head>
<body>
<div class="popWin">
<h2>從系統資料庫中選擇圖片</h2>
<div class="close"><a href="javascript:window.opener='x';window.close();"><img src="<c:url value='/app/images/close.png'/>"  alt="close"></a></div>


<!--form-->
<div class="popformTb"> 
<form action="/admin/app/admin/AddData/list/selectImg" method="post" name="imgForm">
<input type="hidden" name="show" value=""/>
<input type="hidden" value="${divname}" name="divname" id="divname">
<input type="hidden" value="${count}" name="count" id="count">
<input type="hidden" value="${nowPage}" name="nowPage" id="nowPage">
<div class="setbox">
<img src="<c:url value='/app/images/buttle.gif'/>" alt="&gt;">請輸入關鍵字進行查詢：
<input name="Text2"  value="${Text2}" type="text"  />
  <input name="button2" type="button" class="btn" value="查詢" onclick="searchByKey();"/>
  
<hr/>
<div class="listfunction">
    <div class="sortBy">
      <label for="display">排序依
        <select name="Display" id="Display">
          <option value=" imgUploadTime desc " selected="selected">上傳時間</option>
          <option value=" imgUploadUser desc ">上傳者</option>
        </select>
      </label>
    </div>
    <div class="page">
      <ul>
      	<script>
				var totPage = Math.ceil(<c:out value="${count}"/> / 6);
				var nowPage= <c:out value="${nowPage}"/>;
				var imgUrl = '<c:url value='/app/images/space.gif'/>';
				var start = Math.floor((nowPage-1)/5)*5+1;
				var end = Math.floor((nowPage-1)/5)*5+5;
				if(totPage>0){
					if(nowPage>1){
						var li ="<li class='prev'><a href='javascript: changePage("+(nowPage-1)+")'><img src='"+imgUrl+"' alt='prev' title='prev' /></a></li>";
						document.write(li);
					}
					if(totPage<=5){
						for (var xi=1; xi <=totPage; xi++ ){
							var li ="";
							if( xi == nowPage)
								li = "<li class='here'>" + xi + "</li>";
							else
								li = "<li><a href='javascript: changePage("+ xi +")'>" + xi + "</a></li>";
							document.write(li);
						}
					}else if(nowPage<3){
						for (var xi=1; xi <=5; xi++ ){
							var li ="";
							if( xi == nowPage)
								li = "<li class='here'>" + xi + "</li>";
							else
								li = "<li><a href='javascript: changePage("+ xi +")'>" + xi + "</a></li>";
							document.write(li);
						}
					}else if(nowPage > totPage-2){
						for (var xi=totPage-4; xi <=totPage; xi++ ){
							var li ="";
							if( xi == nowPage)
								li = "<li class='here'>" + xi + "</li>";
							else
								li = "<li><a href='javascript: changePage("+ xi +")'>" + xi + "</a></li>";
							document.write(li);
						}
					}else if(3<=nowPage && nowPage <= totPage-2){
						for (var xi=nowPage-2; xi <=nowPage+2; xi++ ){
							var li ="";
							if( xi == nowPage)
								li = "<li class='here'>" + xi + "</li>";
							else
								li = "<li><a href='javascript: changePage("+ xi +")'>" + xi + "</a></li>";
							document.write(li);
						}
					}
					
					if(totPage>nowPage){
						var li ="<li class='next'><a href='javascript: changePage("+(nowPage+1)+")'><img src='"+imgUrl+"' alt='next' title='next' /></a></li>";
						document.write(li);
					}
				}	
			</script>
      </ul>
    </div>
  </div>
<div class="uploadsearchlist">
<ul>
 <c:forEach items="${imgCenters}" var="imgCenter">
  <li>
    <label><input name="Checkboxpic" type="checkbox" class="pick" value="${imgCenter.imgseq}"/></label>
    <div class="title">${imgCenter.imgtitle}</div>
    <div class="img"><a href="#" title="點選看大圖"><img src="${imgCenter.imgfileuri}" /></a></div>
    <p>上傳者：<a href="#" title="僅看該上稿者">${imgCenter.imguploaduser}</a></p>
  </li>
 </c:forEach> 
</ul>
</div>
<div class="aCenter">
  <input name="submit1" type="button" class="btn_submit" value="插入本頁選擇" onclick="sub();"/>
  <input name="submit2" type="button" class="btn_reset"  value="關閉插入對話框" />
      </div>

</div>
</form> 
<div class="foot"></div>   
</div>


</div>
</body>
</html>
