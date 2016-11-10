(function($){
	$(document).ready(function(){
		$('#proc').css({'visibility' : 'hidden'});
		$('#roc').bind("click", function(){
			$('#proc').css({'visibility' : 'visible'});
			$('.leftCol h2').css({'display' : 'none'});
			$('#treeFrame').css({'display' : 'none'});
			$('.leftCol').css({'width' : '1.65em'});
			$('#roc').css({'visibility' : 'hidden'});
		});
		$('#proc').bind("click", function(){
			$('#roc').css({'visibility' : 'visible'});
			$('.leftCol').css({'width' : '250px'});
			$('#treeFrame').css({'display' : 'block'});
			$('.leftCol h2').css({'display' : 'block'});
			$('#proc').css({'visibility' : 'hidden'});
		});
	});
})(jQuery);