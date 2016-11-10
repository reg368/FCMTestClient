<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
if(createLectureOrder){
}else{
	var checkForm=function(){
		if($('#senablectr_1').attr("checked")){
			var normalprice = $('#normalprice');
			if(normalprice.val() == ''){
				normalprice.focus();
				return '[一般價]需給值';
			}else{
				if(isNaN(normalprice.val())){
					normalprice.focus();
					return '[一般價]需為數字';					
				}
			}
		}

		if($('#senablectr_2').attr("checked")){
			var subscriberprice = $('#subscriberprice');
			if(subscriberprice.val() == ''){
				subscriberprice.focus();
				return '[訂戶價]需給值';
			}else{
				if(isNaN(subscriberprice.val())){
					subscriberprice.focus();
					return '[訂戶價]需為數字';
				}
			}
		}

		if($('#senablectr_3').attr("checked")){
			var earlybirdprice = $('#earlybirdprice');
			if(earlybirdprice.val() == ''){
				earlybirdprice.focus();
				altMsg = '[早鳥價]需給值';
				return false;
			}else{
				if(isNaN(earlybirdprice.val())){
					earlybirdprice.focus();
					return '[早鳥價]需為數字';					
				}
			}
		}

		//總數量
		var totalLO = $('#lectureOrderNum').val();
		for(var lo = 1 ; lo <= totalLO ; lo++){
			var totalquota = $('#totalquota_' + lo);
			if(totalquota && totalquota.length == 1){
				var totalquotaVal = totalquota.val();
				if(totalquotaVal && totalquotaVal != ''){
					if(isNaN(totalquotaVal)){
						totalquota.focus();
						return '[總數量]需為數值';
					}
				}else{
					totalquota.focus();
					return '請輸入[總數量]';
				}
			}else{
				return '本程式執行有錯誤[數量總數]數有誤,連儘速聯絡管理廠商';
			}
			//名稱
			var title = $('#title_' + lo);
			if(title && title.length == 1){
				var titleVal = title.val();
				if(!titleVal || titleVal == ''){
					title.focus();
					return '請輸入[梯次名稱]';
				}
			}else{
				return '本程式執行有錯誤[梯次名稱]數有誤,連儘速聯絡管理廠商';
			}
			//代碼
			var code = $('#code_' + lo);
			if(code && code.length == 1){
				var codeVal = code.val();
				if(!codeVal || codeVal == ''){
					code.focus();
					return '請輸入[梯次代碼]';
				}
			}else{
				return '本程式執行有錯誤[梯次代碼]數有誤,連儘速聯絡管理廠商';
			}

			var totalLL = $('#lecturelocationNum_' + lo).val();
			for(var ll = 1 ; ll <= totalLL ; ll++){
				var sstartdate = $('#sstartdate_' + lo + '_' + ll);
				if(!sstartdate || sstartdate.val() == ''){
					sstartdate.focus();
					return '請輸入[辦理起迄]的起始日';
				}
				var senddate = $('#senddate_' + lo + '_' + ll);
				if(!senddate || senddate.val() == ''){
					senddate.focus();
					return '請輸入[辦理起迄]的結束日';
				}
			}
		}
		return null;
	};
	var shelvesLectureSave=function(){
		if (checkData() == false) {
			return false;
		}
		var checkMsg = checkForm();
		if(checkMsg == null){
			$.ajaxDivUpdate({
				divId: 'layoutContent',
				url: urlMap[dataSource][sDataType == 0 ? 'save0' : 'save1'] + tnId ,//in shelvesType.jsp
				param:$("#form").serialize(),
				type: 'post'
			});			
		}else{
			alert(checkMsg);
		}
	};

	var createLectureOrder=function(/*Json*/val){
		var lectureOrderNumElm = $('#lectureOrderNum');
		var lectureOrderNum = parseInt(lectureOrderNumElm.val(), 10) + 1;
		var calendarPic='<c:url value="/app/images/icon_calendar.gif"/>';
		var memberonlyCheck = getCheck(val['memberonly']);
		var priceenableCheck = getCheck(val['priceenable']);
		var senablectr_1 = $('#senablectr_1')[0].checked ? '' : 'disabled';
		var senablectr_2 = $('#senablectr_2')[0].checked ? '' : 'disabled';
		var senablectr_3 = $('#senablectr_3')[0].checked ? '' : 'disabled';
		var llElms = null;
		if(val['ll']){
			for(var i = 0 ; i < val['ll'].length ; i++){
				if(llElms == null){
					llElms = createLectureLocation(true, lectureOrderNum, val['ll'][i]);
				}else{
					llElms.append(createLectureLocation(true, lectureOrderNum, val['ll'][i]));
				}
			}
		}else{
			llElms = createLectureLocation(true, lectureOrderNum, getLLJson(null));
		}
		var zone = $('<table id="locationTable_' + lectureOrderNum + '">')
			.append($('<input type="hidden" name="loid_' + lectureOrderNum +'" value="' + val['loid'] +'" />'))
			.append($('<caption>梯次#' + lectureOrderNum + '</caption>'))
			.append($('<tr>')
				.append($('<th><span class="must">*</span>梯次名稱</th>'))
				.append($('<td><input name="title_' + lectureOrderNum + '" id="title_' + lectureOrderNum + '" class="text" value="' + val['title'] +'"/></td>'))
			)
			.append($('<tr>')
				.append($('<th><span class="must">*</span>梯次代碼</th>'))
				.append($('<td><input name="code_' + lectureOrderNum + '" id="code_' + lectureOrderNum + '" class="text" value="' + val['code'] +'"/></td>'))
			)
			.append($('<tr>')
				.append($('<th>辦理資訊</th>'))
				.append(llElms)
			)
			.append($('<tr>')
				.append($('<th>報名起迄</th>'))
				.append($('<td>')
					.append($('<ol>')
						.append($('<li><!--<input name="priceenable_' + lectureOrderNum +'" id="priceenable1_' + lectureOrderNum +'" value="1" type="checkbox" checked="checked" disabled="disabled" />-->一般<input name="normalstartdate_' + lectureOrderNum +'" id="normalstartdate_' + lectureOrderNum +'" type="text" value="' + val['normalstartdate'] +'" size="20" ' + senablectr_1 +' /><img src="' + calendarPic + '" onclick="calendarBind(\'normalstartdate_' + lectureOrderNum + '\')" />~<input name="normalenddate_' + lectureOrderNum +'" id="normalenddate_' + lectureOrderNum +'" type="text" value="' + val['normalenddate'] +'" size="20" ' + senablectr_1 +' /><img src="' + calendarPic + '" onclick="calendarBind(\'normalenddate_' + lectureOrderNum + '\')" /><input name="memberonly_' + lectureOrderNum +'" type="checkbox"  value="1" '+ priceenableCheck[0] +' />限訂戶</li>'))
						.append($('<li><!--<input name="priceenable_' + lectureOrderNum +'" id="priceenable2_' + lectureOrderNum +'" value="2" type="checkbox" ' + memberonlyCheck[1] + '/>-->訂戶<input name="subscriberstartdate_' + lectureOrderNum +'" id="subscriberstartdate_' + lectureOrderNum +'" type="text" value="' + val['subscriberstartdate'] +'" size="20" ' + senablectr_2 + '/><img src="' + calendarPic + '" onclick="calendarBind(\'subscriberstartdate_' + lectureOrderNum + '\')" />~<input name="subscriberenddate_' + lectureOrderNum +'" id="subscriberenddate_' + lectureOrderNum +'" type="text" value="' + val['subscriberenddate'] +'" size="20" ' + senablectr_2 +' /><img src="' + calendarPic + '" onclick="calendarBind(\'subscriberenddate_' + lectureOrderNum + '\')" /><input name="memberonly_' + lectureOrderNum +'" type="checkbox"  value="2" checked="checked" disabled="disabled" />限訂戶</li>'))
						.append($('<li><!--<input name="priceenable_' + lectureOrderNum +'" id="priceenable3_' + lectureOrderNum +'" value="3" type="checkbox" ' + memberonlyCheck[2] + '/>-->早鳥<input name="earlybirdstartdate_' + lectureOrderNum +'" id="earlybirdstartdate_' + lectureOrderNum +'" type="text" value="' + val['earlybirdstartdate'] +'" size="20" ' + senablectr_3 + '/><img src="' + calendarPic + '" onclick="calendarBind(\'earlybirdstartdate_' + lectureOrderNum + '\')" />~<input name="earlybirdenddate_' + lectureOrderNum +'" id="earlybirdenddate_' + lectureOrderNum +'" type="text" value="' + val['earlybirdenddate'] +'" size="20" ' + senablectr_3 +' /><img src="' + calendarPic + '" onclick="calendarBind(\'earlybirdenddate_' + lectureOrderNum + '\')" /><input name="memberonly_' + lectureOrderNum +'" type="checkbox"  value="3" ' + priceenableCheck[2] + '/>限訂戶</li>'))
					)
				)
			)
			.append($('<tr>')
				.append($('<th>人數</th>'))
				.append($('<td>')
					.append($('<ol>')
						.append($('<li><span class="must">*</span>總名額<input name="totalquota_' + lectureOrderNum + '" id="totalquota_' + lectureOrderNum + '" type="text" size="7" value="' + val['totalquota'] +'" /> 人</li>'))
						.append($('<li>訂戶上限<input name="memberquota_' + lectureOrderNum + '" type="text" size="7" value="' + val['memberquota'] +'" /> 人</li>'))
					)
				)
			);
		lectureOrderNumElm.val(lectureOrderNum);
		$('#addLOCtr').before(zone);
		$('#sstartdate_' + lecturelocationIdx).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#senddate_' + lecturelocationIdx).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#normalstartdate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#normalenddate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#subscriberstartdate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#subscriberenddate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#earlybirdstartdate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
		$('#earlybirdenddate_' + lectureOrderNum).datetimepicker({dateFormat: 'yy/mm/dd'});
	};

	var createLectureLocation=function(/*boolean*/ retJquery, /*Integer*/ loIdx, /*Json*/val){
		var lecturelocationNumElm = $('#lecturelocationNum_' + loIdx);
		if(lecturelocationNumElm.length == 0){
			$('#lectureOrderNum').after($('<input type="hidden" id="lecturelocationNum_' + loIdx + '" name="lecturelocationNum_' + loIdx + '" value="0" />'));
			lecturelocationNumElm = $('#lecturelocationNum_' + loIdx);
		}
		var lecturelocationNum = parseInt(lecturelocationNumElm.val()) + 1;
		var calendarPic='<c:url value="/app/images/icon_calendar.gif"/>';
		var addPic='<c:url value="/app/images/add.png"/>';
		var idx = loIdx + '_' + lecturelocationNum;
		var sstartdateId ='sstartdate_' + idx;
		var senddateId ='senddate_' + idx;
		var zone = ($('<ol>')
			.append($('<li>' + lecturelocationNum + '.<input name="note_' + idx + '" type="text" value="' + val['note'] +'" /><input type="hidden" name="llid_' + idx + '" value="' + val['llid'] +'"/ ></li>'))
			.append($('<li><span class="must">*</span>辦理起迄<input id="' + sstartdateId + '" name="' + sstartdateId + '" type="text" value="' + val['sstartdate'] +'" size="20" /><img src="' + calendarPic + '" onclick="calendarBind(\'' + sstartdateId + '\')" />~<input id="' + senddateId + '" name="' + senddateId + '" type="text" value="' + val['senddate'] +'" size="20" /><img src="' + calendarPic + '" onclick="calendarBind(\'' + senddateId + '\')" /></li>'))
			.append($('<li>名稱：<input name="title_' + idx + '" type="text" class="text" value="' + val['title'] +'" alt="請輸入上課地點名稱" /></li>'))
			.append($('<li>地址：<input name="address_' + idx + '" type="text" class="text" value="' + val['address'] +'"  alt="請輸入上課地點地址"/><br/>地圖：<input name="mapurl_' + idx + '" type="text" class="text" value="' + val['mapurl'] +'" alt="請輸入地圖網址" /></li>'))
		);
		if(retJquery){
			var lectureSize = -1;
			if (pageInfo[loIdx-1]) {
				if (pageInfo[loIdx-1]['ll']) {
					lectureSize = pageInfo[loIdx-1]['ll'].length;	
				}
			}
			if (lectureSize == lecturelocationNum) {
				zone.append($('<a href="javascript:void(0);" id="addLLCtr_' + loIdx + '"  onclick="createLectureLocation(false, ' + loIdx + ', getLLJson(null));">')
					.append($('<img src="' + addPic + '" />'))
					.append($('<span>新增一組辦理資訊</span>'))
				);
			}
		}
		lecturelocationNumElm.val(lecturelocationNum);
		if(retJquery){
			lecturelocationIdx = idx;
			return zone;
		}else{
			$('#addLLCtr_' + loIdx).before(zone);
			$('#' + sstartdateId).datetimepicker({dateFormat: 'yy/mm/dd'});
			$('#' + senddateId).datetimepicker({dateFormat: 'yy/mm/dd'});
		}
	};
	var getLOJson=function(json){
		if(json){
			return json;
		}else{
			return {"loid":"","sid":"","title":"","code":"","normalstartdate":"","normalenddate":"","subscriberstartdate":"","subscriberenddate":"","earlybirdstartdate":"","earlybirdenddate":"","memberonly":"","priceenable":"","memberquota":"","totalquota":"","ll":null};
		}
	};

	var getLLJson=function(json){
		if(json){
			return json;
		}else{
			return {"llid":"","sid":"","loid":"","note":"","title":"","sstartdate":"","senddate":"","address":"","mapurl":""}; 
		}
	};

	var getCheck=function(ckeckedStr){
		if(ckeckedStr){
			var ret = [];
			ckeckedStr = ',' + ckeckedStr + ',';
			for(var i = 1 ; i <=3 ; i++){
				ret.push(ckeckedStr.indexOf(i) >= 0 ? 'checked' : '');
			}
			return ret;
		}else{
			return ['','','']; 
		}
	};

	var calendarBind=function(bindedId){
		$('#' + bindedId + '').focus();
	};

	var linkedCheckBox=function(obj, type, startend){
		var typeArr = ['', 'normal', 'subscriber', 'earlybird'];
		var priceCtrStr = '#senablectr_' + type;
		var priceCtrObj = $(priceCtrStr);
		var len = $('#lectureOrderNum').val();
		if('#' + priceCtrObj.attr('id') == priceCtrStr){//由價格觸發
			for(var i = 1 ; i <= len ; i++){
				var sDateObj = $('#' + typeArr[type] + 'startdate' + '_' + i);
				var eDateObj = $('#' + typeArr[type] + 'enddate' + '_' + i);
				var priceObj = $('#' + typeArr[type] + 'price');
				sDateObj.prop('disabled', !obj.checked);
				eDateObj.prop('disabled', !obj.checked);
				priceObj.prop('disabled', !obj.checked);
				if(! obj.checked){
					sDateObj.val('');
					eDateObj.val('');
					priceObj.val('');
				}
			}
		}else{//由日期觸發
		}
	};

	var getLimit_showimg=function(){
		return limitImg_showimg;
	}
	var getShow_showimg=function(){
		return 'N';
	}
	var remove_showimg=function(doc){
		$(doc).parents('div.uploadlist').remove();
		checkImg_showimg();
	}
	var checkImg_showimg=function(){
		var imgId = $('input[name="diimgseq_showimg"]');
		if(imgId.size() >= limitImg_showimg){
			$("#upP_showimg").attr("disabled","disabled"); 
			$("#selectUpload_showimg").css("display","none");
		}else if(imgId == null || imgId.size() < limitImg_showimg){
			$("#upP_showimg	").removeAttr("disabled"); 
		}
	}
	var initImg_showimg=function(imgSeq){
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
		};

	var lecturelocationIdx = null;
	var initInfo = null;
}
initInfo = '<c:out value="${requestScope.pageInfo}" escapeXml="false" />';
var pageInfo;
$(document).ready(function(){
	pageInfo = getLOJson(initInfo.length == 0 ? null : JSON.parse(initInfo));
	if(pageInfo.length){
		for(var i = 0 ; i < pageInfo.length ; i++){
			createLectureOrder(pageInfo[i]);
		}
	}else{
		createLectureOrder(pageInfo);
	}
	$('#sstartdate').datetimepicker({dateFormat: 'yy/mm/dd'});
	$('#senddate').datetimepicker({dateFormat: 'yy/mm/dd'});
});

function checkData() {
	if ($.checkNumber('input[name="simportance"]', false, '重要性') == false) return false;
	if ($.checkNumber('input[name="normalprice"]', true, '一般價') == false) return false;
	if ($.checkNumber('input[name="subscriberprice"]', false, '訂戶價') == false) return false;
	if ($.checkNumber('input[name="earlybirdprice"]', false, '早鳥價') == false) return false;
	
	if ($.checkNumber('input[name^="totalquota_"]', true, '總名額') == false) return false;
	if ($.checkNumber('input[name^="memberquota_"]', false, '訂戶上限') == false) return false;

	if ($.checkSEDate('input[name="sstartdate"]', 'input[name="senddate"]', false, '上架起迄') == false) return false;
	if ($.checkSEDate('input[name^="sstartdate_"]', 'input[name^="senddate_"]', true, '辦理起迄') == false) return false;
	if ($.checkSEDate('input[name^="normalstartdate_"]', 'input[name^="normalenddate_"]', false, '報名起迄-一般') == false) return false;
	if ($.checkSEDate('input[name^="subscriberstartdate_"]', 'input[name^="subscriberenddate_"]', false, '報名起迄-訂戶') == false) return false;
	if ($.checkSEDate('input[name^="earlybirdstartdate_"]', 'input[name^="earlybirdenddate_"]', false, '辦理起迄-早鳥') == false) return false;
	
	return true;
}
</script>
<div class="content">
	<h2>新增上架資料<img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;">網站單元目錄架構<img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;"><a href="#">線上購物</a><img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;">課程講座</h2>
	<div class="navfunction">
		<div class="treeSet">
			<ul>
				<c:if test = "${requestScope.sid !=''}">
					<li><a href="javascript:imgMain('/admin/app/page/ListShowFormAction/list/lectureOrderList?sid=<c:out value="${requestScope.sid}" />')"><img src="<c:url value='/app/images/icon_treeSearch.gif'/>" alt="檢視">檢視報名資料</a></li>
				</c:if>
				<li><a class="goback" href="javascript:void(0);"><img src="<c:url value='/app/images/icon_back.gif'/>" alt="返回前頁">返回前頁</a></li>
			</ul>
		</div>
	</div>
	<div class="formTb2">
		<form id="form" action="save/shelvesLecture" method="post">
			<c:out value="${requestScope.hidden}" escapeXml="false" />
			<table id="orderTable">
				<tr>
					<th>模式</th>
					<td>課程上架</td>
				</tr>
				<tr>
					<th>上架資料</th>
					<td>
						<input name="stitle0" class="text" value="<c:out value="${requestScope.stitle}"/>" disabled="disabled" />
						<p><a onclick="hiddenAndShow('LM1', this)" href="javascript:void(null)" target="_self">變更顯示資訊</a></p>
					</td>
				</tr>
				<tr id=LM1 style="display:none;">
					<th>上架資訊</th>
					<td>
						<table class="sec">
							<tr>
								<th>主標</th>
								<td><input name="stitle" class="text" value="<c:out value="${requestScope.stitle}" escapeXml="false" />"/></td>
							</tr>
							<tr>
								<th>副標</th>
								<td><input name="ssubtitle" class="text" value="<c:out value="${requestScope.ssubtitle}" escapeXml="false" />"/></td>
							</tr>
							<tr>
								<th>摘要(簡介)</th>
								<td>
									<textarea name="ssummary" rows="8" title="內容、本文、網頁、說明" style="width:100%"><c:out value="${requestScope.ssummary}" escapeXml="false" /></textarea>
									<p class="must">*請輸入簡介或摘要文字，可不輸入。</p>
								</td>
							</tr>
							<tr>
								<th>圖檔</th>
									<td>
										<input type="hidden" name="simage"  value="<c:out value="${requestScope.simage}" escapeXml="false" />" />
										<div id='showimg'></div>
										<div class="AddPic">
											<input id="upP_showimg" type="button" class="btn_addpic" value="新增圖片" onclick="hiddenAndShow('selectUpload_showimg')" />
											<div class="selectUpload" id="selectUpload_showimg" style="display: none;">
												<ul>
									  				<li>
									   					<h3><a id="imgbtn1" href="javascript: void(0)" onclick="window.open('/admin/app/page/AddData/insert/uploadShelvesImgPage?divname=showimg','','location=yes,width=650,height=600')">從電腦中選擇圖片上傳<img src="/admin/app/images/icon_searchPC.gif" alt="從系統圖片庫中選擇"/></a></h3>
														<p>自硬碟中新增，所選擇的圖片將自動儲存放圖片庫</p>                                                                                                      
									 				</li>
									 				<li>
										 				<h3><a id="imgbtn2" href="javascript: void(0)" onclick="window.open('/admin/app/page/AddData/list/shelvesImgCenter?divname=showimg','','location=yes,width=650,height=600')">從系統圖片庫中選擇<img src="/admin/app/images/icon_searchPIC.gif" alt="從系統圖片庫中選擇"/></a></h3>
														<p>自圖庫中新增，可自您個人權限可瀏覽的圖片中選擇</p>
									 				</li>
												</ul>
											</div>
										</div>
									</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><span class="must">*</span>商品分類</th>
					<td><c:out value="${requestScope.stype}" escapeXml="false"/></td>
				</tr>
				<tr>
					<th><span class="must">*</span>上架顯示</th>
					<td>
						<c:out value="${requestScope.sispublic}" escapeXml="false" />
					</td>
				</tr>
				<tr>
					<th>上架起迄</th>
					<td>
						<input id="sstartdate" name="sstartdate" size="25" value="<c:out value="${requestScope.sstartdate}" escapeXml="false" />" readonly="readonly"/>
						<img src="<c:url value='/app/images/icon_calendar.gif'/>" alt="日曆圖"  onclick="calendarBind('sstartdate')"/> ~
						<input id="senddate" name="senddate" size="25" value="<c:out value="${requestScope.senddate}" escapeXml="false" />" readonly="readonly"/>
						<img src="<c:url value='/app/images/icon_calendar.gif'/>" alt="日曆圖"   onclick="calendarBind('senddate')"/>
					</td>
				</tr>
				<tr>
					<th>重要性</th>
					<td><input name="simportance" type="text" size="10" value='<c:out value="${requestScope.simportance}" escapeXml="false" />'/></td>
				</tr>
				<tr>
					<th>備註</th>
					<td><textarea name="snote" title="內容、本文、網頁、說明" style="width:100%" cols="5" rows="3"><c:out value="${requestScope.snote}" escapeXml="false" /></textarea></td>
				</tr>
				<tr>
					<th>售價</th>
					<td>
						<ol>
							<li>
								<input id="senablectr_1"  name="senablectr"  type="checkbox" checked="checked" readonly  value="1"  onclick="linkedCheckBox(this, 1)" />一般價
								<input id="normalprice" name="normalprice" type="text" value='<c:out value="${requestScope.normalprice}" escapeXml="false" />' />(免費課程請設0)
							</li>
							<li>
								<input  id="senablectr_2"  name="senablectr"   type="checkbox"  value="2"  <c:out value="${requestScope.senablectr_2}"/> onclick="linkedCheckBox(this, 2)" />訂戶價
								<input id="subscriberprice"  name="subscriberprice" type="text" value='<c:out value="${requestScope.subscriberprice}" escapeXml="false" />' />
							</li>
							<li>
								<input  id="senablectr_3"  name="senablectr"  type="checkbox"  value="3" <c:out value="${requestScope.senablectr_3}"/> onclick="linkedCheckBox(this, 3)" />早鳥價
								<input id="earlybirdprice" name="earlybirdprice" type="text" value='<c:out value="${requestScope.earlybirdprice}" escapeXml="false" />' />
							</li>
						</ol>
						<p class="must">*一般價為必填，免費課程請輸入0，其他可不輸入。</p>
					</td>
				</tr>
				<tr>
					<th>梯次</th>
					<td id='order'>
						<a href="javascript:void(0);" id="addLOCtr" onclick="createLectureOrder(getLOJson(null))"><img src="<c:url value='/app/images/add.png'/>" />新增一組梯次</a>
					</td>
				</tr>
			</table>
			<div class="aCenter">
				<input type="button" class="btn_submit" onclick="shelvesLectureSave()" value="編修存檔" />
				<input type="button" class="btn_reset"  value="取   消" onclick="javascript:history.back();" />
			</div>
		</form>
	</div>	
	<div class="copyright">HyCMS :: Content Managernment System    © 2012 Copyright by Hyweb Technology Co. Inc. All right reserved.</div>
</div>