<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="hyweb.gip.model.session.impl.HttpSessionHandle,hyweb.gip.pojo.mybatis.table.InfoUser" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
InfoUser nowLogin = (InfoUser)new HttpSessionHandle(request).get("InfoUser");
String postLimit = nowLogin.getPostlimit();
postLimit = hyweb.core.kit.StringKit.isEmpty(postLimit) ? "" : "," + postLimit + ",";
%>
<html>
<head>
<title>資料上稿</title>
<link href='<c:url value="/app/css/design.css"/>' rel="stylesheet" type="text/css" />
<script src="<c:url value='/app/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
<script>
	$(document).ready(function(){
		$('.mode_L li').each(function(index){
			$('.mode_L li:eq(' + index + ')').bind("click", function(){
				$('#form1').attr("action", $('.mode_L li img:eq(' + index + ')').attr("id"));
				$('.mode_L li img:eq(' + index + ')').attr("src", "/admin/app/images/mode_L1_active.jpg");
				$('.mode_L li img').each(function(){
					$('.mode_L li img').not($('.mode_L li img:eq(' + index + ')')).attr("src", "/admin/app/images/mode_L1.jpg");
				});
			});
		});
		
		$('input[name=submit2]').bind("click", function(){
			$('.mode_L li img').each(function(){
				$(this).attr("src", "/admin/app/images/mode_L1.jpg");
			});
			$('#form1').attr("action", "javascript:alert('請選擇一種資料模式！');");
		});
	});
</script>
</head>
<body class="layout">
	<div class="content">
		<h2>
			<a href="#">資料上稿</a><img src="/admin/app/images/path_arrow.gif" alt="&gt;&gt;"><span>新增資料</span>
		</h2>

		<!-- navfunction -->
		<div class="navfunction">
			<div class="treeSet">
				<ul>
					<li><a href="/admin/app/page/QueryAction/query/dataSetQuery/dataSetList"><img src="/admin/app/images/icon_treeSearch.gif" alt="查詢">資料查詢</a></li>
					<li><a href="/admin/app/page/ListShowFormAction/list/dataSetList"><img src="/admin/app/images/icon_treeAdd.gif" alt="新增">返回條列</a></li>
				</ul>
			</div>

		</div>

		<!--form-->
		<div class="formTb2">
			<div class="step">
				<ul>
					<li class="step1here"><span>Step1.</span>
					<p>選擇上稿資料模式</p></li>
					<li class="step2"><span>Step2.</span>
					<p>依欄位輸入與編輯資料</p></li>
					<li class="step3"><span>Step3.</span>
					<p>編修儲存(完成或儲存草稿)</p></li>
				</ul>
			</div>
			<form action="javascript:alert('請選擇一種資料模式！');" id="form1">
				<table>
					<tr>
						<th><span class="must">*</span>資料模式選擇</th>
						<td>
							<ul class="mode_L">
							<%if(postLimit.indexOf(",45,")!=-1){ %>
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/articleAdd/dataSetList"/>
										<div class="data">
											<h3>文章</h3>
											<p>新增期刊、書籍的文章類型的資料</p>
									</div></li>
							 <%}if(postLimit.indexOf(",57,")!=-1) {%>
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/blogAdd/dataSetList"/> 
										<div class="data">
											<h3>部落格</h3>
											<p>新增部落格文章資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",56,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/adAdd/dataSetList"/>
									<div class="data">
										<h3>廣告</h3>
										<p>新增站內、站外廣告資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",58,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/booksAdd/dataSetList"/>
									<div class="data">
										<h3>書籍資訊</h3>
										<p>新增期刊、特刊、書籍等書目資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",61,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/lectureAdd/dataSetList"/>
									<div class="data">
										<h3>講座</h3>
										<p>新增講座、課程、活動資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",62,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/periodicalAdd/dataSetList"/>
									<div class="data">
										<h3>期刊訂閱</h3>
										<p>新增期刊訂閱類型資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",60,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/commodityAdd/dataSetList"/>
									<div class="data">
										<h3>一般商品</h3>
										<p>新增非書籍類之一般商品資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",63,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/websiteAdd/dataSetList"/>
									<div class="data">
										<h3>網站資訊</h3>
										<p>新增非書籍類之一般商品資料</p>
									</div></li>
								<%}if(postLimit.indexOf(",59,")!=-1) {%>	
								<li><img name="Radio1" src="/admin/app/images/mode_L1.jpg" id="/admin/app/page/AddShowFormAction/add/characterAdd/dataSetList"/>
									<div class="data">
										<h3>人物</h3>
										<p>新增非書籍類之一般商品資料</p>
									</div></li>
									<%} %>
							</ul>
						</td>
					</tr>
				</table>
				<div class="aCenter">
					<div class="remark">
						<span class="must">*</span>星號標示項目為必填欄位
					</div>
					<input name="submit1" type="submit" class="btn_submit" value="下一步" /> 
					<input name="submit2" type="reset" class="btn_reset" value="取   消" onclick="javascript:history.back();"/>
				</div>
			</form>
		</div>

		<!--copyright 共用區-->
		<div class="copyright">HyCMS :: Content Managernment System © 2012
			Copyright by Hyweb Technology Co. Inc. All right reserved.</div>

	</div>

</body>
</html>