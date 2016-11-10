(function($){
	
if($.ryoTree){
	return;
}
	
$.ryoTree = {};
//-------------------------------------------------------

$.extend($.ryoTree, {
	treeUlId: undefined,
	settings: {
		root: '', //root節點名稱
		selectId: undefined, //預設選取的id，當為根節點時，則為0
		treeId: '2', //treeId
		expandLevel: 1, //預設展開第幾層
		ajax: false, //是否使用Ajax載入
		clickNode: function(data) {//點選節點時需要作的處理，如果回傳false則不會送出請求
			return true;
		},
		treeTypes: {//設定相關路徑
			MP: {
				url:"javascript:void(0);", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/3.png"
			},
			NP: {
				url:"javascript:void(0);",
				picUrl:"/admin/app/css/zTreeStyle/img/diy/3.png"
			},
			LP: {
				url:"javascript:void(0);", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/5.png"
			}, 
			AP: {
				url:"javascript:void(0);", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/8.png"
			}, 
			SP: {
				url:"javascript:void(0);", 
				picUrl:"/admin/app/css/zTreeStyle/img/diy/6.png"
			}
		},
		close: {//當狀態為關閉時
			showChild: false, //是否顯示下層節點
			showView: false //是否顯示節點的畫面
		},
		hidden: {//當狀態為隱藏時
			showChild: false, //是否顯示下層節點
			showView: false //是否顯示節點的畫面
		}
		
	},
	create: function(
		/*String*/divId, /*String*/treeUlId,
		/*Object ref defaults*/options) {
		
		$.ryoTree.treeUlId = treeUlId;
		var settings = $.extend(true, $.ryoTree.settings, options);
		
		var ztreeSettings = {
				async: {
					enable: settings.ajax,
					url: '/admin/app/cms/BuildTree/tree/ajax',
					autoParam:["id", "name", "level"],
					otherParam:{
						closedNode: JSON.stringify(settings.close), 
						hiddenNode: JSON.stringify(settings.hidden), 
						json: JSON.stringify(settings.treeTypes)
					}
				},
				data: {
					simpleData: {
						enable: true
					}
				}
//				callback:{
//					onExpand: getNodeId
//				}
			};
		
		$(document).ready(function(){
			var treeScript = '<ul id="' + treeUlId + '" class="ztree"></ul>';
			$(treeScript).appendTo('#' + divId);

			$.ajax({
				type: 'POST',
				url: '/admin/app/cms/BuildTree/tree',
				data: {
					root: settings.root,
					tIds: settings.treeId, 
					closedNode: JSON.stringify(settings.close), 
					hiddenNode: JSON.stringify(settings.hidden), 
					json: JSON.stringify(settings.treeTypes), 
					level: settings.expandLevel,
					ajax: settings.ajax
				},
				dataType: 'json',
				success: function(data) {
					var treeObj = $.fn.zTree.init($("#" + treeUlId), ztreeSettings, data);
					
					if (settings.selectId) {
						var node =  treeObj.getNodeByParam("id", settings.selectId);
						treeObj.selectNode(node);
						if (node && node.click) {
							eval(node.click);
						}
					}
				},
				cache: false
			});
		});
	}
});

$(document).ready(function() {
	
	if ($(window).hashchange) {
		$(window).hashchange(function() {
			var url = '';
			var start = location.href.indexOf('#');
			if (start != -1) {
				url = location.href.substring(start+1, location.href.length);
			}
			
			if (url == '__POST__DATA__') {
				return;
			}
			
			var jdata = $('body').data(url);
			if (url == '' || jdata == undefined) {
				var updateDiv = $('.content:first');
				updateDiv.html('');
			} else {
				$.ajax({
					type: jdata.type,
					url: url,
					dataType: "text",
					success: function(data) {
						var updateDiv = $('#'+jdata.divId);
						updateDiv.html(data);
					},
					error: function(jqXHR, textStatus, errorThrown) {
						alert('網頁讀取發生錯誤！');
					},
					cache: false
				});
			}
		});
		
		$(window).hashchange();
	}
	  
	/*function load(url) {
		var jdata = $.data(document.body, url);
		
		if (url == '1') {
			var updateDiv = $('.content:first');
			updateDiv.html('');
		} else {
			$.ajax({
				type: jdata.type,
				url: url,
				dataType: "text",
				success: function(data) {
					var updateDiv = $('#'+jdata.divId);
					updateDiv.html(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('網頁讀取發生錯誤！');
				},
				cache: false
			});
		}
	}

	$.history.init(function(url) {
		load(url == '' ? '1' : url);
	});*/
});

var ajaxDivUpdateGoback = function() {
	history.go(-1);
};

var ajaxDivUpdate = function(/* Object */options) {
	var settings = {
		divId: 'layoutContent',
		url: '#',
		type: 'get',
		param: {},
		dontKeepHistory: true
	};
	
	$.extend(settings, options);
/**
 * 透過ajax 取得 url中的html 並更新 divId  所在的div的innerHTML
 * divId: 要放置html的div ID
 * url: 路徑
 * type: get或post
 * param: post的參數(ex: {a:10,b:20})
 * dontKeepHistory: 是否不保存這次紀錄，可不帶此值
 */
	var urlObj = $.url(settings.url);
	
	var url = urlObj.attr('path');
	
	var param1 = $.param(urlObj.param(), true);

	if (param1 != '') {
		if (url.lastIndexOf('?') == -1) {
			url += '?'+param1;
		} else {
			url += '&'+param1;
		}
	}

	
	if (settings.type == 'post') {
		$.ajax({
			type: settings.type,
			url: url,
			data: settings.param,
			dataType: 'text',
			success: function(data) {
				var updateDiv = $('#'+settings.divId);
				updateDiv.html(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('網頁讀取發生錯誤！');
			},
			cache: false
		});
		
		return;
	}

	var param2;
	if (typeof settings.param === 'string') {
		param2 = settings.param;
	} else {
		param2 = $.param(settings.param, true);
	}
	
	if (param2 != '') {
		if (url.lastIndexOf('?') == -1) {
			url += '?'+param2;
		} else {
			url += '&'+param2;
		}
	}

	var jdata = {};
	if (settings.type) {
		jdata['type'] = settings.type;
	}
	if (settings.divId) {
		jdata['divId'] = settings.divId;
	}
	
	$('body').data(url, jdata);
	
	var topHref = $.url(location.href);
	var topUrl = topHref.attr('path');
	var queryString = topHref.attr('query');
	if (queryString != '') {
		topUrl += '?'+queryString;
	}

	location.href = topUrl + '#' + url;
	//$.history.load(url);
};
	
$.extend({
	ajaxDivUpdate: ajaxDivUpdate,
	ajaxDivUpdateGoback: ajaxDivUpdateGoback,
});
})(jQuery);

function clickNode(type, tId, tnId, xpath, dataSource, url) {
	var nodeData;
	if (type == 'MP') {
		nodeData = {type: type, tId: tId, tnId: 'root', xpath: xpath};
	} else {
		nodeData = {type: type, tId: tId, tnId: tnId, xpath: xpath, dataSource: dataSource, url: url};
	}
	
	if ($.ryoTree.settings.clickNode(nodeData) == false) {
		return false;
	}
	
	if ('javascript:void(0);' == $.trim($.ryoTree.settings.treeTypes[type].url)) {
		return false;
	}
	
	$.ajaxDivUpdate({
		divId: 'layoutContent',
		url: $.ryoTree.settings.treeTypes[type].url,
		param: nodeData,
		type: 'get'
	});
}