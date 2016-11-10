<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<c:url value='/app/js/jquery-migrate-1.1.1.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		if($.browser.mozilla){
			var scaleVal = 1;
			$('.listTb').attr("style", "-moz-transform: scale(" + scaleVal + ");");
			$('.formTb').attr("style", "-moz-transform: scale(" + scaleVal + ");");
			$('#zoomOut').click(function(){
				if(scaleVal.toFixed(1) != 0.1){
					scaleVal -= 0.1;
				}else{
					alert('字級已縮放為最小');
				}
				
				$('.listTb').attr("style", "-moz-transform: scale(" + scaleVal.toFixed(1) + "); -moz-transform-origin: 0 0;");
				$('.formTb').attr("style", "-moz-transform: scale(" + scaleVal.toFixed(1) + "); -moz-transform-origin: 0 0;");
			});
			
			$('#zoomIn').click(function(){
				scaleVal += 0.1;
				$('.listTb').attr("style", "-moz-transform: scale(" + scaleVal.toFixed(1) + "); -moz-transform-origin: 0 0;");
				$('.formTb').attr("style", "-moz-transform: scale(" + scaleVal.toFixed(1) + "); -moz-transform-origin: 0 0;");
			});	
		}else if($.browser.webkit){
			var scaleVal1 = 1;
			$('.listTb').attr("style", "-webkit-transform: scale(" + scaleVal1 + ");");
			$('.formTb').attr("style", "-webkit-transform: scale(" + scaleVal1 + ");");
			$('#zoomOut').click(function(){
				if(scaleVal1.toFixed(1) != 0.1){
					scaleVal1 -= 0.1;
				}else{
					alert('字級已縮放為最小');
				}
				
				$('.listTb').attr("style", "-webkit-transform: scale(" + scaleVal1.toFixed(1) + "); -webkit-transform-origin: 0 0;");
				$('.formTb').attr("style", "-webkit-transform: scale(" + scaleVal1.toFixed(1) + "); -webkit-transform-origin: 0 0;");
			});
			
			$('#zoomIn').click(function(){
				scaleVal1 += 0.1;
				$('.listTb').attr("style", "-webkit-transform: scale(" + scaleVal1.toFixed(1) + "); -webkit-transform-origin: 0 0;");
				$('.formTb').attr("style", "-webkit-transform: scale(" + scaleVal1.toFixed(1) + "); -webkit-transform-origin: 0 0;");
			});	
		}else{
			var zoomVal = 100;
			$('.listTb').attr("style", "zoom: " + zoomVal + "%");
			$('.formTb').attr("style", "zoom: " + zoomVal + "%");
			$('#zoomOut').click(function(){
				if(zoomVal != 10){
					zoomVal -= 10;	
				}else{
					alert('字級已縮放為最小');
				}
				
				$('.listTb').attr("style", "zoom: " + zoomVal + "%");
				$('.formTb').attr("style", "zoom: " + zoomVal + "%");
			});
			
			$('#zoomIn').click(function(){
				zoomVal += 10;
				$('.listTb').attr("style", "zoom: " + zoomVal + "%");
				$('.formTb').attr("style", "zoom: " + zoomVal + "%");
			});	
		}
	});
</script>
<div class="fontsize">
字級大小:
<ul>
   <li><a href="#" id="zoomOut"><img src='<c:url value="/app/images/zoomOut.gif" />' alt="縮小字級" /></a></li>
   <li><a href="#" id="zoomIn"><img src='<c:url value="/app/images/zoomIn.gif" />' alt="放大字級" /></a></li>
 </ul>
</div>