<%--
  Created by IntelliJ IDEA.
  User: pigrick
  Date: 5/5/2018
  Time: 6:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>


<c:set value="${result.getNumber() + 1}" var="currentIndex"/>
<c:choose>
    <c:when test="${1 < (currentIndex - 5)}">
        <c:set value="${currentIndex - 5}" var="beginIndex"/>
    </c:when>
    <c:otherwise>
        <c:set value="${1}" var="beginIndex"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${(beginIndex + 10) < result.getTotalPages()}">
        <c:set value="${beginIndex + 10}" var="endIndex"/>
    </c:when>
    <c:otherwise>
        <c:set value="${result.getTotalPages()}" var="endIndex"/>
    </c:otherwise>
</c:choose>

<%--<div class="container">--%>
    <ul class="pagination">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="javascript:void(0);">&lt;&lt;</a></li>
                <li class="disabled"><a href="javascript:void(0);">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:void(0);" onclick="module.list(1)">&lt;&lt;</a></li>
                <li><a href="javascript:void(0);" onclick="module.list('${currentIndex-1}')">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="javascript:void(0);" onclick="module.list('${i}')"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:void(0);" onclick="module.list('${i}')"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex ge result.totalPages}">
                <li class="disabled"><a href="javascript:void(0);">&gt;</a></li>
                <li class="disabled"><a href="javascript:void(0);">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:void(0);" onclick="module.list('${currentIndex+1}')" >&gt;</a></li>
                <li><a href="javascript:void(0);" onclick="module.list('${result.totalPages}')">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
<%--</div>--%>