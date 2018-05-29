<%@include file="/WEB-INF/include.jsp"%>

<%--
  Created by IntelliJ IDEA.
  User: chanpiseth
  Date: 4/24/2018
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>



<!-- Products section -->
        <!-- start prduct navigation -->

            <c:set var="counter" value="0" scope="page" />
            <c:forEach items="${mainCategoryNameList}" var="category">
                <c:set var="counter" value="${counter + 1}" scope="page"/>
                <c:if test="${counter eq 1}">
                    <li class="active"><a href="#${category}" data-toggle="tab">${category}</a></li>
                </c:if>
                <c:if test="${counter ne 1}">
                    <li><a href="#${category}" data-toggle="tab">${category}</a></li>
                </c:if>
            </c:forEach>


        <!-- / start prduct navigation -->



<!-- / Products section -->






<%@include file="/WEB-INF/jsp/template/footer.jsp"%>
