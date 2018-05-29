<%@include file="/WEB-INF/jsp/template/header.jsp" %>
<link rel='stylesheet' type='text/css' href='/static/paymentdetail/css/receipt.css'/>


<div class="container">
    <h1>Order successfully processed! <br/> A confirmation email is sent to your inbox.</h1>
</div>
<div class="container border mbot">
    <div id="page-wrap">
        <div id="header">Receipt</div>
        <div id="identity">
            <span id="address">
                <c:choose>
                    <c:when test="${order.customer != null}">
                        <c:out value="${order.customer.firstName} ${order.customer.lastName}"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${order.guest.firstName} ${order.guest.lastName}"/>
                    </c:otherwise>
                </c:choose> <br/>
                <c:out value="${order.address.street}"/>
                <br/>
                <c:out value="${order.address.city}, ${order.address.state} ${order.address.zipcode}"/>
                <br/>
                Phone: <c:out value="${order.address.phoneNumber}"/>
            </span>
            <div id="logo">
                <img id="image" src="${pageContext.request.contextPath}/static/paymentdetail/images/OnlineShoppingLogo.jpg" alt="logo" height="88"
                     width="300"/>
            </div>
        </div>
        <div style="clear:both"></div>
        <div id="customer">
            <span id="customer-title">
                <c:choose>
                    <c:when test="${order.customer != null}">
                        <c:out value="${order.customer.firstName} ${order.customer.lastName}"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${order.guest.firstName} ${order.guest.lastName}"/>
                    </c:otherwise>
                </c:choose>
            </span>
            <table id="meta">
                <tr>
                    <td class="meta-head">Receipt #</td>
                    <td><c:out value="${order.id}"/></td>
                </tr>
                <tr>
                    <td class="meta-head">Date</td>
                    <td><span id="date"><fmt:formatDate type="date" value="${order.orderDate}"/></span></td>
                </tr>
                <tr>
                    <td class="meta-head">Amount</td>
                    <td>
                        <div><fmt:formatNumber value="${order.totalPriceWithTax}" type="currency"
                                               currencySymbol="$"/></div>
                    </td>
                </tr>
            </table>
        </div>
        <table id="items">
            <tr>
                <th colspan="2">Item</th>
                <th>Unit Cost</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            <c:forEach var="orderDetail" items="${order.orderDetails}">
                <tr class="item-row">
                    <td colspan="2" class="item-name"><c:out value="${orderDetail.product.name}"/></td>
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
                <td colspan="2" class="blank"></td>
                <td colspan="2" class="total-line">Subtotal</td>
                <td class="total-value">
                    <div><fmt:formatNumber value="${order.totalPriceWithoutTax}" type="currency"
                                           currencySymbol="$"/></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="blank"></td>
                <td colspan="2" class="total-line">Tax</td>
                <td class="total-value"><fmt:formatNumber value="${order.tax}" type="currency" currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="2" class="blank"></td>
                <td colspan="2" class="total-line">Total</td>

                <td class="total-value"><fmt:formatNumber value="${order.totalPriceWithTax}" type="currency"
                                                          currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="2" class="blank"></td>
                <td colspan="2" class="total-line balance">Amount Paid</td>
                <td class="total-value balance"><fmt:formatNumber value="${order.totalPriceWithTax}" type="currency"
                                                                  currencySymbol="$"/></td>
            </tr>
        </table>
        <br/>
        <br/>
        <h3>Billed to</h3>
        <p>
            <c:choose>
                <c:when test="${order.customer != null}">
                    <c:out value="${order.customer.firstName} ${order.customer.lastName}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${order.guest.firstName} ${order.guest.lastName}"/>
                </c:otherwise>
            </c:choose>
            <br/>
            <c:out value="${order.card.cardType}"/>
            <br/>
            **** **** **** <c:out value="${order.card.last4Digit}"/>
        </p>
    </div>
</div>

<%@include file="/WEB-INF/jsp/template/footer.jsp" %>