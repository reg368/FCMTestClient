(function($){
	var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$('#result').bind("click", function(){
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeObj.getSelectedNodes();
				alert( JSON.parse(JSON.stringify(nodes).substring(1, JSON.stringify(nodes).length-1)).id);
			});
		});
})(jQuery);