<%@tag description="建立分頁" pageEncoding="UTF-8"%>
<%@tag isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="nowPage" %><!-- 從1開始 -->
<%@attribute name="total" %><!-- 資料總數 -->
<%@attribute name="numPage" %><!-- 每頁顯示幾筆 -->
<%@attribute name="size" %><!-- 顯示多少頁 -->
<%@attribute name="url" %><!-- 請求路徑 -->
<%@attribute name="args" %><!-- 其它請求參數 -->
<br/>
<c:if test="${total > 0}">
    <c:set var="img_path" value="/admin/app/images/space.gif" />
    <fmt:parseNumber var="leftSize" integerOnly="true" type="number" value="${size/2}"/>
    <fmt:parseNumber var="firstPage" integerOnly="true" type="number" value="1"/>
    <fmt:parseNumber var="lastPage" integerOnly="true" type="number" value="${(total+numPage-1)/numPage}"/>
    <fmt:parseNumber var="prevPage" integerOnly="true" type="number" value="${nowPage-1 <= 0 ? nowPage : nowPage-1}" />
    <fmt:parseNumber var="prevTen" integerOnly="true" type="number" value="${nowPage-10 <= 0 ? nowPage : nowPage-10}" />
    <fmt:parseNumber var="startPage" integerOnly="true" type="number" value="${nowPage <= leftSize ? firstPage : nowPage-leftSize}" />
    <fmt:parseNumber var="endPage" integerOnly="true" type="number" value="${startPage+(size-1) > lastPage ? lastPage : startPage+(size-1)}" />
    <c:if test="${endPage==lastPage}">
        <fmt:parseNumber var="startPage" integerOnly="true" type="number" value="${endPage-(size-1) <= firtPage ? firstPage : endPage-(size-1)}" />
        <c:if test="${startPage<1}">
            <fmt:parseNumber var="startPage" integerOnly="true" type="number" value="${firstPage}" />
        </c:if>
    </c:if>
    <fmt:parseNumber var="nextTen" integerOnly="true" type="number" value="${nowPage+10 >= lastPage ? lastPage : nowPage + 10}" />
    <fmt:parseNumber var="nextPage" integerOnly="true" type="number" value="${nowPage+1 >= lastPage ? lastPage : nowPage + 1}" />

    <ul>
        <c:if test="${nowPage>1}"><li class="first"><a href="${url}?page=${firstPage}&numPage=${numPage}${args}"><img src="${img_path}" alt="first" title="First Page" /></a></li></c:if>
        <c:if test="${nowPage>1}"><li class="prev"><a href="${url}?page=${prevPage}&numPage=${numPage}${args}"><img src="${img_path}" alt="previous" title="Previous" /></a></li></c:if>
        <!--li class="prevten"><a href="#"><img src="images/space.gif" alt="上10頁" title="上10頁" /></a></li-->
                <c:forEach varStatus="idx" begin="${startPage}" end="${endPage}">
                    <c:choose>
                        <c:when test="${idx.index==nowPage}">
                    <li class="here">${idx.index}
                        <c:if test="${idx.index==endPage && endPage<lastPage}">
                            ..
                        </c:if>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${url}?page=${idx.index}&numPage=${numPage}${args}">${idx.index}</a>
                        <c:if test="${idx.index==endPage && endPage<lastPage}">
                            ..
                        </c:if>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <!--li class="nextten"><a href="#"><img src="images/space.gif" alt="下10頁" title="下10頁" /></a></li-->
        <c:if test="${nowPage<lastPage}"><li class="next"><a href="${url}?page=${nextPage}&numPage=${numPage}${args}"><img src="${img_path}" alt="next" title="Next" /></a></li></c:if>
        <c:if test="${nowPage<lastPage}"><li class="last"><a href="${url}?page=${lastPage}&numPage=${numPage}${args}"><img src="${img_path}" alt="last" title="Last Page" /></a></li></c:if>
    </ul>
</c:if>