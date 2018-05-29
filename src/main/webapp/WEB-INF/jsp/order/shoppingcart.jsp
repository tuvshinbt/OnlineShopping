<%@include file="/WEB-INF/jsp/template/header.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/order/order.css">
<div class="container">
    <h2 align="center">Shopping cart</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col"></th>
            <th scope="col">Product Name</th>
            <th scope="col">Price</th>
            <th scope="col">Original Quantity</th>
            <th scope="col">Update Quantity</th>
            <th scope="col">Total Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="orderDetail" items="${shoppingcart.orderDetails}">
            <tr id="${orderDetail.product.id}">
                <input type="hidden" id="productid" name="productid" value="${orderDetail.product.id}"/>
                <td>
                    <a href="${pageContext.request.contextPath}/product/${orderDetail.product.id}"><img
                        src="${pageContext.request.contextPath}${resourcePath}${orderDetail.product.image}" alt="img" height="100" width="100"></a>
                </td>
                <td><a href="${pageContext.request.contextPath}/product/${orderDetail.product.id}"><c:out value="${orderDetail.product.name}"/></a></td>
                <td id="price"><fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="$"/></td>
                <td id="quantity"><c:out value="${orderDetail.quantity}"/>
                    <c:if test="${orderDetail.quantity le 0}"><span class="alert-danger">--Out of Stock--</span></c:if>
                </td>
                <td>
                    <input type="number" id="cartquantity2" onchange="updateQuantity(${orderDetail.product.id})" min="0"
                           style="width: 100px;cursor: default" max="${orderDetail.product.quantity}" value="${orderDetail.quantity}"/>
                </td>
                <td id="totalprice"><fmt:formatNumber value="${orderDetail.calculateTotalPrice()}"
                                                      type="currency" currencySymbol="$"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="6"></td>
        </tr>
        <tr>
            <td colspan="4"></td>
            <td>Tax ( 7% )</td>
            <td id="tax"><fmt:formatNumber value="${shoppingcart.calculateTax()}" type="currency"
                                           currencySymbol="$"/></td>
        </tr>
        <tr class="border-dark">
            <td colspan="5"></td>
            <td id="totalpricewithtax"><fmt:formatNumber value="${shoppingcart.calculateTotalPriceWithTax()}"
                                                         type="currency" currencySymbol="$"/></td>
        </tr>
        </tbody>
    </table>
    <div class="container centered">
        <sec:authorize access="!isAuthenticated()">
            <a href="${pageContext.request.contextPath}/order/guest/checkout" class="btn btn-warning">Guest Checkout</a>
        </sec:authorize>
        <a href="${pageContext.request.contextPath}/order/checkout" class="btn btn-warning">Checkout</a>
    </div>
</div>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/static/js/order/order.js"></script>

<%@include file="/WEB-INF/jsp/template/footer.jsp" %>