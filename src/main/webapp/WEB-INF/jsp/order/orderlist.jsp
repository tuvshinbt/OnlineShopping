<%@include file="/WEB-INF/jsp/template/header.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/order/order.css">
<div class="container">
    <div class="container">
        <h1>Orders</h1>
    </div>
    <c:forEach var="order" items="${orders.content}">
        <div class="row addborder">
            <div class="col orderHeader">
                <div class="row">
                    <div class="col-sm-4">
                        Order Placed:
                        <br/>
                        <fmt:formatDate type="date" value="${order.orderDate}"/>
                    </div>
                    <div class="col-sm-4">
                        Total:
                        <br/>
                        <fmt:formatNumber value="${order.totalPriceWithTax}" type="currency" currencySymbol="$"/>
                    </div>
                    <div class="col-sm-4">
                        <a class="receiptOrder" href="${pageContext.request.contextPath}/order/customer/${order.id}">Receipt</a>
                    </div>
                </div>
            </div>
            <div class="col">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Product Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Total Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderDetail" items="${order.orderDetails}">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/product/${orderDetail.product.id}"><img
                                    src="${pageContext.request.contextPath}${resourcePath}${orderDetail.product.image}" alt="img" height="100"
                                    width="100"></a></td>
                            <td><a href="${pageContext.request.contextPath}/product/${orderDetail.product.id}"><c:out
                                    value="${orderDetail.product.name}"/></a></td>
                            <td><fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="$"/></td>
                            <td><c:out value="${orderDetail.quantity}"/></td>
                            <td><fmt:formatNumber value="${orderDetail.calculateTotalPrice()}" type="currency"
                                                  currencySymbol="$"/></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5"></td>
                    </tr>
                    <tr>
                        <td colspan="3"></td>
                        <td>Tax ( 7% )</td>
                        <td id="tax"><fmt:formatNumber value="${order.tax}" type="currency"
                                                       currencySymbol="$"/></td>
                    </tr>
                    <tr class="border-dark">
                        <td colspan="4"></td>
                        <td id="totalpricewithtax"><fmt:formatNumber value="${order.totalPriceWithTax}"
                                                                     type="currency" currencySymbol="$"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach>
    <c:url var="firstUrl" value="/order/customer/all/1"/>
    <c:url var="lastUrl" value="/order/customer/all/${orders.totalPages}"/>
    <c:url var="prevUrl" value="/order/customer/all/${currentIndex - 1}"/>
    <c:url var="nextUrl" value="/order/customer/all/${currentIndex + 1}"/>
    <div class="container">
        <ul class="pagination">
            <c:choose>
                <c:when test="${currentIndex == 1}">
                    <li class="disabled"><a href="#">&lt;&lt;</a></li>
                    <li class="disabled"><a href="#">&lt;</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${firstUrl}">&lt;&lt;</a></li>
                    <li><a href="${prevUrl}">&lt;</a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
                <c:url var="pageUrl" value="/order/customer/all/${i}"/>
                <c:choose>
                    <c:when test="${i == currentIndex}">
                        <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${currentIndex == orders.totalPages}">
                    <li class="disabled"><a href="#">&gt;</a></li>
                    <li class="disabled"><a href="#">&gt;&gt;</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${nextUrl}">&gt;</a></li>
                    <li><a href="${lastUrl}">&gt;&gt;</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<%@include file="/WEB-INF/jsp/template/footer.jsp" %>