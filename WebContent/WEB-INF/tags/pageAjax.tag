<%@tag description="建立分頁" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="total" %>
<%@attribute name="url" %>
<%@attribute name="pageJump" type="hyweb.util.PageJump" %>
<%@attribute name="args" %>
<%@attribute name="func" %>
<%@attribute name="html" %>

<c:set var="argsVal" value="${not empty args ? '&' : '' }${args}" />

<script>
	function ${func}(where){
		$.ajax({
		     type: "GET",
		     url: '${url}?page='+ where +'${argsVal}',
		     success: function(data) {
		          $('${html}').html(data);
		     },
			 cache: false
		   });
	}
</script>
<div class="page">
<ul>
	<li class="first">
		<a href="javascript:void(0);" onclick="${func}(${pageJump.firstPage});"><img src='<c:url value="/app/images/space.gif"/>' alt="first" title="Previous" /></a>
	</li>
	<li class="prev">
		<a href="javascript:void(0);" onclick="${func}(${pageJump.prevPage});"><img src='<c:url value="/app/images/space.gif"/>' alt="previous" title="Previous" /></a>
	</li>
	<c:forEach begin="${pageJump.startPage}" end="${pageJump.endPage}" varStatus="idx">
	<c:choose>
		<c:when test="${idx.index==pageJump.nowPage}">
			<li class="here">${idx.index}</li>
		</c:when>
		<c:otherwise>
			<li><a href="javascript:void(0);" onclick="${func}(${idx.index});">${idx.index}</a></li>
		</c:otherwise>
	</c:choose>
	</c:forEach>
	<c:if test="${pageJump.endPage!=pageJump.lastPage}">
	<li>...</li>
	</c:if>
	<li class="next">
		<a href="javascript:void(0);" onclick="${func}(${pageJump.nextPage});"><img src='<c:url value="/app/images/space.gif"/>' alt="next" title="next" /></a>
	</li>
	<li class="last">
		<a href="javascript:void(0);" onclick="${func}(${pageJump.lastPage});"><img src='<c:url value="/app/images/space.gif"/>' alt="last" title="last" /></a>
	</li>
</ul>
</div>