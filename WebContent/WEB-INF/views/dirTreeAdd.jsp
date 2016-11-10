<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<title>HyCMS 網站內容管理系統</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script type="text/javascript">
var limitImg_showimg = 1;
var show_showimg ='N';//不顯示主圖跟資料圖表

function checkImg_showimg(){
	var imgId = $('input[name="diimgseq_showimg"]');
	if(imgId.size() >= limitImg_showimg){
		$("#upP_showimg").attr("disabled","disabled"); 
		$("#selectUpload_showimg").css("display","none");
	}else if(imgId == null || imgId.size() < limitImg_showimg){
		$("#upP_showimg	").removeAttr("disabled"); 
	}
}
function getLimit_showimg(){
	return limitImg_showimg;
}
function getShow_showimg(){
	return show_showimg;
}
function LMYC() {
	if($("#LM1").css("display")=="none")
		$("#LM1").css("display","");
	else
		$("#LM1").css("display","none");
}
function selectUpload_showimg(){
	if($("#selectUpload_showimg").css("display")=="none")
		$("#selectUpload_showimg").css("display","");
	else
		$("#selectUpload_showimg").css("display","none");

}

function remove_showimg(doc){
	$(doc).parents('div.uploadlist').remove();
	$("#tnpic").val('');
	checkImg_showimg();
}
function initImg_showimg(imgSeq){
	$.ajax({
		url: '/admin/app/page/Shelves/Init/shelvesImgInit',
		type: 'POST', 
		data: {imgSeq : imgSeq,
			   divname :'showimg',
			   tablename:'dataset',
			   disable :''}, 
		success: function(data) {
			$(window.document.getElementById('showimg')).html(data);
			checkImg_showimg();
			}
		});
	}
	
	function sub(){
		if($("#tntype").val()=='1'&& $("#tndatasource").val()=='shelves_select')
			alert("類型為LP時請選上架模式");
		else if($("#tntype").val()=='2'&& ($("#tnurl").val() == null || $("#tnurl").val()==''))
			alert("類型為AP時請填URL");
		else{
			if($(document.treeNodeForm.diimgseq_showimg).val()!=null)
				$("#tnpic").val($(document.treeNodeForm.diimgseq_showimg).val());
			else
				$("#tnpic").val('');
			document.treeNodeForm.action='/admin/app/cms/DirTreeList/insert/dirTreeAction';
			document.treeNodeForm.submit();
		}
	}
	$(document).ready(function(){
		var msg = "<c:out value="${msg}"/>";
		if(msg != null && msg != '')
			alert(msg);
		
		if($("#tntype").val()=='1')
			$("#trid").show();
		else
			$("#trid").hide();
		
		if($("#tntype").val()=='2')
			$("#trurl").show();
		else
			$("#trurl").hide();
		
		$("#tntype").change(function(){
			
			if($("#tntype").val()=='1')
				$("#trid").show();
			else
				$("#trid").hide();
			
			if($("#tntype").val()=='2')
				$("#trurl").show();
			else
				$("#trurl").hide();
			
			if($("#tntype").val()=='3')
				$("#tndatasource").val("shelves_article");
		});
	});

</script>
</head>
<body class="layout">
<div class="content">
<h2>
			網站管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;">
			目錄樹管理<img src='<c:url value="/app/images/path_arrow.gif"/>' alt="&gt;&gt;">
			<span>資料新增</span>
		</h2>

<!-- navfunction -->
<div class="navfunction">
<div class="treeSet">
 <ul>
   <li><a href="#" onclick="javascript:history.go(-1); return false;"><img src='<c:url value="/app/images/icon_back.gif"/>' alt="返回前頁">返回前頁</a></li>		
 </ul>
 </div>
<jsp:include page="include/fontSize.jsp"/>
</div>

<!--form-->
<div class="formTb"> 
<form:form commandName="treeNode" action="" name="treeNodeForm">     
<table summary="資料表格">
              <caption>
              
              </caption>
              <tr>
                <th scope="row"><span class="must">*</span>節點名稱</th>
                <td><form:input path="tnname" cssClass="text"/><form:errors path="tnname" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row">節點圖片</th>
                <td>
                <form:input type="hidden" path="tnpic" cssClass="text"/>
                <div id='showimg'></div>
					<div class="AddPic">
					<input id="upP_showimg" name="button" type="button" class="btn_addpic" value="新增圖片" onclick="selectUpload_showimg();" />
					<div class="selectUpload" id="selectUpload_showimg" style="display: none;">
					  <ul>
					     <li><h3><a id="imgbtn1" href="javascript: void(0)" onclick="window.open('/admin/app/page/AddData/insert/uploadImgPage?divname=showimg','','location=yes,width=650,height=600')">從電腦中選擇圖片上傳<img src="/admin/app/images/icon_searchPC.gif" alt="從系統圖片庫中選擇"/></a></h3>
								<p>自硬碟中新增，所選擇的圖片將自動儲存放圖片庫</p>                                                                                                      
						 </li>
						 <li><h3><a id="imgbtn2" href="javascript: void(0)" onclick="window.open('/admin/app/page/AddData/list/imgCenter?divname=showimg','','location=yes,width=650,height=600')">從系統圖片庫中選擇<img src="/admin/app/images/icon_searchPIC.gif" alt="從系統圖片庫中選擇"/></a></h3>
								<p>自圖庫中新增，可自您個人權限可瀏覽的圖片中選擇</p>
						 </li>
					  </ul>
					</div>
					</div>                
				</td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>節點順序</th>
                <td><form:input path="tnsort" cssClass="text" value="100"/><form:errors path="tnsort" cssStyle="color : red;"/></td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>節點狀態</th>
                <td>
                	<form:select path="tnispublic">
 						<form:options items="${nodeStatus}" itemValue="codevalue" itemLabel="codeshow" />
                	</form:select>  	
                </td>
              </tr>
              <tr>
                <th scope="row"><span class="must">*</span>節點類別</th>
                <td>
                	<form:select path="tntype">
 						<form:options items="${treeType}" itemValue="codevalue" itemLabel="codeshow" />
                	</form:select>
                </td>
              </tr>
              <tr id="trid">
                <th scope="row">LP頁上架模式</th>
                <td>
                	<form:select path="tndatasource">
 						<form:options items="${dataCollection}" itemValue="codevalue" itemLabel="codeshow" />
                	</form:select>  	
                </td>
              </tr>
              <tr>
                <th scope="row">URL</th>
                <td><form:input path="tnurl" cssClass="text"/></td>
              </tr>
              <tr>
                <th scope="row">mateData說明</th>
                <td>
                <form:textarea path="tndesc" rows="8" cssClass="text"></form:textarea></td>
              </tr>
              <tr>
                <th scope="row">mateData關鍵字</th>
                <td><form:input path="tnkeyword" cssClass="text"/></td>
              </tr>
              <form:hidden path="tnid" value="${tnId}"/>
              <form:hidden path="tid" value="${tId}"/>
			  <form:hidden path="tnxpath" value="${tnXpath}"/>
			  <form:hidden path="tnlevel" value="${tnLevel}"/>
			  <form:hidden path="parent" value="${parent}"/>
            </table>           

<div class="aCenter">
        <div class="remark"><span class="must">*</span>必填欄位</div>
                <input name="submit1" type="button" onclick="sub()" class="btn_submit" value="新增存檔" /> 
                <input name="submit2" type="button" class="btn_reset"  value="取消" onclick="javascript:history.go(-1);"/>
      </div>
		</form:form>    
</div>
<jsp:include page="include/footer.jsp"/>
</div>

</body>
</html>
