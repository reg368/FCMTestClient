<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	var limitImg_showimg = 1;
	
if(createZone){	
}else{
	var zone1Num = 0;
	var zone2Num = 0;
	var cctype1;
	var cctype2;
	var nowZoneType;
	/**
	 * 附加商品初始化
	 */
	var createZone=function(/*Integer*/dsid, /*String*/title, /*Interger*/imgSeq, /*Integer*/ sort, /*Integer*/amount, /*Integer*/ price, /*Integer*/originalPrice, /*Integer*/ ccid){
		if(typeof(ccid) == "undefined"){
			ccid ='';
		}
		var imageSrc = '/admin/app/images/';
		var removeImageSrc = '/admin/app/images/btn_remove.gif';
		var targetElm = $('#addpic' + nowZoneType);
		var zone;
		var img;
		if(1 == nowZoneType){
			++zone1Num;
			img = $('<img src="' + imageSrc + 'pic150x100.jpg"/>');
			zone = $('<div class="uploadlist">')
			.append($('<div class="img">').append(img))
			.append($('<div class="body">')
					.append($('<h3><input name="title1_' + zone1Num + '" type="text" size="15" value="' + title + '"/></h3>'))
					.append($('<p>排序<input name="ccsort1_' + zone1Num + '" type="text" size="5" value="' + sort + '" /></p>'))
					.append($('<p>原價<input name="originalprice1_' + zone1Num +'" type="text" size="5" value="' + (typeof(originalPrice) !== "undefined" ? originalPrice : '')+ '" /></p>'))
					.append($('<p>加購價<input name="price1_' + zone1Num +'" type="text" size="5" value="' + (typeof(price) !== "undefined" ? price : '')+ '" /></p>'))
					.append($('<p>數量<input name="totalamount1_' + zone1Num + '" type="text" size="5" value="' + (amount ? amount : '') + '" /></p>'))
					.append($('<input type="hidden"  name="imgseq1_' + zone1Num + '" value="' + imgSeq + '" />'))
					.append($('<input type="hidden"  name="dsid1_' + zone1Num + '" value="' + dsid + '" />'))
					.append($('<input type="hidden"  name="ccid1_' + zone1Num + '" value="' + ccid + '" />'))
			)
			.append($('<div class="setmain">').append('<img src="' + removeImageSrc + '" onclick="removeCC(this, 0)" />'));
		}else if(2 == nowZoneType){
			++zone2Num;
			img = $('<img src="' + imageSrc + 'pic150x100.jpg"/>');
			zone = $('<div class="uploadlist">')
			.append($('<div class="img">').append(img))
			.append($('<div class="body">')
					.append($('<h3><input name="title2_' + zone2Num + '" type="text" size="15" value="' + title + '"/></h3>'))
					.append($('<p>排序<input name="ccsort2_' + zone2Num + '" type="text" size="5" value="' + sort + '" /></p>'))
					.append($('<p>數量<input name="totalamount2_' + zone2Num + '" type="text" size="5" value="' + (amount ? amount : '') + '" /></p>'))
					.append($('<input type="hidden"  name="imgseq2_' + zone2Num + '" value="' + imgSeq + '" />'))
					.append($('<input type="hidden"  name="dsid2_' + zone2Num + '" value="' + dsid + '" />'))
					.append($('<input type="hidden"  name="ccid2_' + zone2Num + '" value="' + ccid + '" />'))
			)
			.append($('<div class="setmain">').append('<img src="' + removeImageSrc + '" onclick="removeCC(this,0)" />'));
		}else{
			return;
		}
		targetElm.before(zone);
		$.ajax({
			type: 'POST',
			url: '/admin/app/page/Shelves/query/image?imgseq=' + imgSeq + '&id=',
			contentType:"application/json; charset=utf-8",
			dataType: 'json',
			success: function(data) {
				if(data.length > 0){
					img.attr('src', data[0]['url']);
				}else{
					alert('圖片載入有問題');
				}
			  },
			cache: false
		});
	};
	/**
	  * 移除商品
	  */
	var removeCC=function(/*Element*/elm, /*Integer*/ sellAmount){
		if(0 == sellAmount){
			if(confirm('您正在進行刪除組合商品的行為，您確定要繼續嗎？')){
				$(elm.parentNode.parentNode).remove();
			}
		}else{
			alert('該商品已有銷售量，不能刪除。');
		}
	};

	/**
	 * 新增附加商品
	 */
	var addNewCC=function(/*Integer*/ zoneType){
		nowZoneType = zoneType;
		if(2 == zoneType){
			var uploadlistElm = $('#zone' + zoneType).find('.uploadlist');
			var choseElm = $('#stotalchoseamout');
			if(choseElm.val() == uploadlistElm.length){
				alert('你已經選擇' + choseElm.val() + '筆資料，如需再增加請先調整贈品可選數量');
				choseElm.focus();
				return;
			}
		}
		var url = '/admin/app/page/QueryAction/query/commodityShelvesAddQuery2/commodityShelvesAddQueryRes2?tnId=' + tnId;
		window.open (url, 'addCC', 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
	};

	var checkExistUploadList = function(/*Integer*/ zoneType){
		var uploadlistElm = $('#zone' + zoneType).find('.uploadlist');
		var choseElm = $('#stotalchoseamout');
		if(choseElm.val() < uploadlistElm.length){
			alert('你已經選擇' + choseElm.val() + '筆資料，本數值不能小於該值');
			choseElm.val(uploadlistElm.length);
		}
	};

	/**
	 * 限制類型選單
	 */
	var checkModelAction=function(itemObject){
		if('shelves_sales' == dataSource){
			if($("input[name='smodel']:checked").length >1){
				alert('強打模式只能勾選一種商品模式');
				itemObject.checked = false;
			}		
		}
	};

	var getLimit_showimg=function(){
		return limitImg_showimg;
	};

	var getShow_showimg=function(){
		return 'N';
	};
	
	var remove_showimg=function(doc){
		$(doc).parents('div.uploadlist').remove();
		checkImg_showimg();
	};

	var checkImg_showimg=function(){
		var imgId = $('input[name="diimgseq_showimg"]');
		if(imgId.size() >= limitImg_showimg){
			$("#upP_showimg").attr("disabled","disabled"); 
			$("#selectUpload_showimg").css("display","none");
		}else if(imgId == null || imgId.size() < limitImg_showimg){
			$("#upP_showimg	").removeAttr("disabled"); 
		}
	};

	var initImg_showimg=function(imgSeq){
		$.ajax({
			url: '/admin/app/page/Shelves/Init/shelvesImgInit',
			type: 'POST', 
			data: {imgSeq : imgSeq,
				   divname :'showimg',
				   tablename:'dataset',
				   disable :''
			}, 
			success: function(data) {
				$(window.document.getElementById('showimg')).html(data);
				checkImg_showimg();
			}
		});
	};

	var initImg_showimg = function(imgSeq){
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

	var checkMoney = function(soriginalpriceId,ssellingpriceId){
		var soriginalpriceElm = $('#' + soriginalpriceId);
		var ssellingpriceElm = $('#' + ssellingpriceId);
		var soriginalpriceVal = soriginalpriceElm.val();
		var ssellingpriceVal = ssellingpriceElm.val();
		if(soriginalpriceVal != '' && ssellingpriceVal != ''){
			var free = ssellingpriceVal/soriginalpriceVal;
			if(free < 0.5){
				alert('提醒：特價低於5折的原價 ');
			} 
		}
	};
}
zone1Num = <c:out value="${requestScope.zone1Num}"/>;
zone2Num = <c:out value="${requestScope.zone2Num}"/>;
cctype1 = eval("<c:out value="${requestScope.sCcType1Script}" escapeXml="false" />");
cctype2 = eval("<c:out value="${requestScope.sCcType2Script}" escapeXml="false" />");
$(document).ready(function(){
	initImg_showimg(<c:out value="${requestScope.simage}" escapeXml="false" />);
	$('a[name=asstartdate]').bind('click', function(){$('#sstartdate').focus();});
	$('#sstartdate').datetimepicker({dateFormat: 'yy/mm/dd'});
	$('a[name=asenddate]').bind('click', function(){$('#senddate').focus();});
	$('#senddate').datetimepicker({dateFormat: 'yy/mm/dd'});
	if(cctype1){
		nowZoneType = 1;
		for(var i = 0 ; i < cctype1.length ; i ++){
			cctype = cctype1[i];
			createZone(cctype['dsid'], cctype['title'], cctype['imgseq'], cctype['ccsort'], cctype['totalamount'], cctype['price'], cctype['originalprice'],cctype['ccid']);
		}
		hiddenAndShow2('zone1', 'zone2');
	}
	if(cctype2){
		nowZoneType = 2;
		for(var i = 0 ; i < cctype2.length ; i ++){
			cctype = cctype2[i];
			createZone(cctype['dsid'], cctype['title'], cctype['imgseq'], cctype['ccsort'], cctype['totalamount'], cctype['price'], cctype['originalprice'],cctype['ccid']);
		}
		hiddenAndShow2('zone2', 'zone1');
	}
	nowZoneType = null;
});
<c:out value="${requestScope.script}" />
</script>
<div class="content">
<h2>新增上架資料<img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;">網站單元目錄架構<img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;"><a href="#">線上購物</a><img src="<c:url value='/app/images/path_arrow.gif'/>" alt="&gt;&gt;">零售</h2>
<div class="navfunction">
<div class="treeSet">
 <ul>
   <li><a class="goback" href="javascript:void(0);"><img src="<c:url value='/app/images/icon_back.gif'/>" alt="返回前頁">返回前頁</a></li>		
 </ul>
</div>
</div>
			<div class="formTb2">
				<div class="step">
  					<ul>
  						<li class="step1"><span>Step1.</span><p>選擇上架資料</p></li>
  						<li class="step2here"><span>Step2.</span><p>輸入與設定上架資訊</p></li>
  						<li class="step3"><span>Step3.</span><p>編修儲存(上架)</p></li>
  					</ul>
				</div>
				<form id="form" action="save/shelvesCommodity" method="post">
					<c:out value="${requestScope.hidden}" escapeXml="false"/>
					<table>
						<tr>
							<th>模式</th>
							<td>單一商品上架：書籍/商品</td>
						</tr>
						<tr>
							<th><span class="must">*</span>商品資料</th>
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
							<th><span class="must">*</span>商品模式<br/>售價/數量</th>
							<td>
								<input name="smodel" id="smodel1" type="checkbox" value="1"  <c:out value="${requestScope.smodel1}"/> onclick="checkModelAction(this)" />
								<label for="smodel">紙本：</label>訂價(原價)<input id="soriginalprice1" name="soriginalprice1" type="text" value="<c:out value="${requestScope.soriginalprice1}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice1','ssellingprice1');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								售價(特價)<input id="soriginalprice1" name="ssellingprice1" type="text" value="<c:out value="${requestScope.ssellingprice1}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice1','ssellingprice1');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								數量<input name="stotalamount1" type="text" value="<c:out value="${requestScope.stotalamount1}"/>" size="6"  maxlength="6" />
								銷售代碼<input name="scode1" type="text" value="<c:out value="${requestScope.scode1}"/>" size="14"  maxlength="10" />
								<c:if test="${requestScope.siscoin == ''}">
								贈送今幣<input name="searncoin1" type="text" value="<c:out value="${requestScope.searncoin1}"/>" size="5"  maxlength="5" />
								</c:if>
								<br/>
								<input name="smodel"  id="smodel2" type="checkbox" value="2"  <c:out value="${requestScope.smodel2}"/> onclick="checkModelAction(this)"/>
								<label for="smode2">電子：</label>訂價(原價)<input id="soriginalprice2" name="soriginalprice2" type="text" value="<c:out value="${requestScope.soriginalprice2}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice2','ssellingprice2');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								售價(特價)<input id="ssellingprice2" name="ssellingprice2" type="text" value="<c:out value="${requestScope.ssellingprice2}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice2','ssellingprice2');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								數量<input name="stotalamount2" type="text" value="<c:out value="${requestScope.stotalamount2}"/>" size="6" maxlength="6"  />
								銷售代碼<input name="scode2" type="text" value="<c:out value="${requestScope.scode2}"/>" size="14"  maxlength="10" />
								<c:if test="${requestScope.siscoin == ''}">
								贈送今幣<input name="searncoin2" type="text" value="<c:out value="${requestScope.searncoin2}"/>" size="5"  maxlength="5" />
								</c:if>
								<br/>
								<input name="smodel"  id="smodel3" type="checkbox" value="3"  <c:out value="${requestScope.smodel3}"/> onclick="checkModelAction(this)" />
								<label for="smode3">其他：</label>訂價(原價)<input id="soriginalprice3" name="soriginalprice3" type="text" value="<c:out value="${requestScope.soriginalprice3}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice3','ssellingprice3');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								售價(特價)<input id="ssellingprice3" name="ssellingprice3" type="text" value="<c:out value="${requestScope.ssellingprice3}"/>" size="6" maxlength="6" onblur="checkMoney('soriginalprice3','ssellingprice3');" /><c:out value="${requestScope.siscoin}" escapeXml="false" />
								數量<input name="stotalamount3" type="text" value="<c:out value="${requestScope.stotalamount3}"/>" size="6"  maxlength="6" />
								銷售代碼<input name="scode3" type="text" value="<c:out value="${requestScope.scode3}"/>" size="14"  maxlength="10" />
								<c:if test="${requestScope.siscoin == ''}">
   								贈送今幣<input name="searncoin3" type="text" value="<c:out value="${requestScope.searncoin3}"/>" size="5"  maxlength="5" />
								</c:if>
								
								<p class="must">*無數量限制請不要輸入值，非書籍類商品請勿勾選電子</p>
							</td>
						</tr>
						<tr>
							<th>訂購條件</th>
							<td><c:out value="${requestScope.srule}" escapeXml="false" /></td>
						</tr>
						<tr>
							<th><span class="must">*</span>上架顯示</th>
							<td><c:out value="${requestScope.sispublic}" escapeXml="false" /></td>
						</tr>
						<tr>
							<th>上架起迄</th>
							<td>
								<input id="sstartdate" name="sstartdate" size="25" value="<c:out value="${requestScope.sstartdate}" escapeXml="false" />" readonly="readonly"/>
                  				<a href="javascript: void(0)" name="asstartdate"><img src="<c:url value='/app/images/icon_calendar.gif'/>" alt="日曆圖" /></a> ~
                  				<input id="senddate" name="senddate" size="25" value="<c:out value="${requestScope.senddate}" escapeXml="false" />" readonly="readonly"/>
                 				<a href="javascript: void(0)" name="asenddate"><img src="<c:url value='/app/images/icon_calendar.gif'/>" alt="日曆圖" /></a>
							</td>
						</tr>
						<tr>
							<th>重要性</th>
							<td><input name="simportance" type="text" size="10" value="<c:out value="${requestScope.simportance}" escapeXml="false" />" /></td>
						</tr>
						<%if("shelves_sales".equals(request.getParameter("t"))){%>
						<tr>
							<th>促販模組</th>
							<td>
								<ol>
									<li>
										<div><input name="scctype" id="sCcType1" type="radio" value="1"  onclick="hiddenAndShow2('zone1', 'zone2')" <c:out value="${requestScope.sCcType1}" />>贈品 / 加價購</div>
										<div id="zone1">
										<div class="AddPic" id="addpic1">
											<input type="button" class="btn_addpic" value="新增項目" onClick="addNewCC(1)" />
										</div>
										</div>
									</li>
									<li style="clear:both;">
										<div style="clear:both;">
											<input name="scctype" type="radio" value="2" onclick="hiddenAndShow2('zone2', 'zone1')" <c:out value="${requestScope.sCcType2}" />>贈品
											<input name="stotalchoseamout"  id="stotalchoseamout" type="text" size="5" value='<c:out value="${requestScope.stotalchoseamout}" />'  onblur="checkExistUploadList('2')"/>選
											<input name="senablchoseamout" type="text" size="5" value='<c:out value="${requestScope.senablchoseamout}" />' />
										</div>
										<div id="zone2" style="display:none;">
										<div class="AddPic"  id="addpic2">
											<input type="button" class="btn_addpic" value="新增項目" onClick="addNewCC(2)" /> 
										</div>
										</div>
									</li>
								</ol>
							</td>
						</tr>
						<%} %>
					</table>
					<div class="aCenter">
						<input type="button" class="btn_submit"  onclick="shelvesCommoditySave()" value="編修存檔" />
						<input type="reset" class="btn_reset"  value="取   消" />
					</div>
				</form>
			</div>	
			<div class="copyright">HyCMS :: Content Managernment System    © 2012 Copyright by Hyweb Technology Co. Inc. All right reserved.</div>
		</div>