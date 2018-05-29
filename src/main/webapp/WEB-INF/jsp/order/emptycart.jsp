<%@include file="/WEB-INF/jsp/template/header.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/order/order.css">

<div class="container">
    <h1>No items in shopping cart to checkout!</h1>
    <c:if test="${errorMessages != null}">
        <div class="alert-danger">
            Sorry, your order not in stock!
            <br/>
            <c:forEach var="errorMessage" items="${errorMessages}">
                <c:out value="${errorMessage}"/>
                <br/>
            </c:forEach>
            Please checkout our other products. Thank you.
        </div>
    </c:if>
</div>

<%@include file="/WEB-INF/jsp/template/footer.jsp" %>