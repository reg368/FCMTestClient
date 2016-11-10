(function($){
	var setting = {
			edit: {
				drag :{
					inner: false
				},
				enable: false,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
		
	function zTreeOnClick(event, treeId, treeNode) {
	    $('#expendNode').val(treeNode.tId );
	};
	
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var node = treeObj.getNodeByTId($('#expendNode').val());
			treeObj.selectNode(node);
			
			if($('#expendNode').val() != null && $('#expendNode').val() != ""){
				var expendNode = "#" + $('#expendNode').val() + "_a";
				$(expendNode).trigger('click');
			}
		});
		
})(jQuery);
