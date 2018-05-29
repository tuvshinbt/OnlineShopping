<%@include file="/WEB-INF/jsp/template/header.jsp" %>

<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/order/order.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/paymentdetail/css/styles.css">
<div>
    <div class="container">
        <div class="container">
            <h1 align="center">Review Order</h1>
            <c:if test="${errorMessages != null}">
                <div class="alert-danger">
                    Order not in stock!
                    <br/>
                    <c:forEach var="errorMessage" items="${errorMessages}">
                        <c:out value="${errorMessage}"/><br/>
                    </c:forEach>
                    Please review your order. Go back to Shopping Cart if you want to update your order.
                    <br/>
                    <a href="${pageContext.request.contextPath}/order/shoppingcart" class="btn btn-warning">Back to Cart</a>
                </div>
            </c:if>
            <div class="col-xs-8">
                <h2 class="sub-header">Order Details</h2>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Product Name</th>
                            <th scope="col">Price</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Total Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="orderdetail" items="${shoppingcart.orderDetails}">
                            <tr>
                                <td><a href=""><c:out value="${orderdetail.product.name}"/></a></td>
                                <td><fmt:formatNumber value="${orderdetail.price}" type="currency"
                                                      currencySymbol="$"/></td>
                                <td><c:out value="${orderdetail.quantity}"/></td>
                                <td><fmt:formatNumber value="${orderdetail.calculateTotalPrice()}"
                                                      type="currency" currencySymbol="$"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="4"></td>
                        </tr>
                        <tr>
                            <td colspan="2"></td>
                            <td>Tax ( 7% )</td>
                            <td id="tax"><fmt:formatNumber value="${shoppingcart.calculateTax()}" type="currency"
                                                           currencySymbol="$"/></td>
                        </tr>
                        <tr class="border-dark">
                            <td colspan="3"></td>
                            <td id="totalpricewithtax"><fmt:formatNumber
                                    value="${shoppingcart.calculateTotalPriceWithTax()}"
                                    type="currency" currencySymbol="$"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-xs-4">
                <h2 class="sub-header">Shipping Details</h2>
                <div class="table-responsive">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>Name:</td>
                            <td><c:out
                                    value="${checkoutorder.customer.firstName} ${checkoutorder.customer.lastName}"/></td>
                        </tr>
                        <tr>
                            <td>Street:</td>
                            <td><c:out value="${checkoutorder.address.street}"/></td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td><c:out value="${checkoutorder.address.city}"/></td>
                        </tr>
                        <tr>
                            <td>State:</td>
                            <td><c:out value="${checkoutorder.address.state}"/></td>
                        </tr>
                        <tr>
                            <td>Zipcode:</td>
                            <td><c:out value="${checkoutorder.address.zipcode}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <br/>
    <div class="container">
        <h2>Use existing Credit Card</h2>
        <div class="alert-danger"><c:out value="${wrongcvv}"/></div>
        <c:forEach var="card" items="${cards}">
            <div class="col-sm-2 addborder" id="card${card.id}">
                <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#remove${card.id}">
                    Remove
                </button>
                <br/>
                <br/><c:out value="${card.cardType}"/> <br/> **** **** **** <c:out value="${card.last4Digit}"/>
                <br/><br/>
                <button class="btn btn-info btn-lg" type="button" data-toggle="modal" data-target="#${card.id}">
                    Use this Card
                </button>
            </div>
            <div class="modal fade" id="remove${card.id}" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Are you sure you want to remove this card!
                                <br/>
                                <c:out value="${card.cardType}"/>
                                <br/>
                                **** **** **** <c:out value="${card.last4Digit}"/>
                            </h4>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-danger" onclick="removeCard(${card.id})" data-dismiss="modal">
                                Remove Card
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="${card.id}" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">
                                Using this Card
                                <br/>
                                <c:out value="${card.cardType}"/>
                                <br/>
                                **** **** **** <c:out value="${card.last4Digit}"/>
                            </h4>
                        </div>
                        <form:form action="${pageContext.request.contextPath}/order/checkout/submit" method="post">
                            <div class="modal-body">
                                <p>
                                    Please verify your CVV
                                    <input type="text" name="cvv" class="form-control" autofocus/>
                                </p>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="existing" value="existing"/>
                                <input type="hidden" name="cardId" value="${card.id}"/>
                                <input type="submit" class="btn btn-info btn-lg" value="Submit Payment" />
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <br/>
    <div class="container">
        <h2>Use a different Credit Card</h2>
        <div class="creditCardForm">
            <div class="heading">
                <h1>Confirm Purchase</h1>
            </div>
            <div class="alert-danger"><c:out value="${badcard}"/></div>
            <div class="payment">
                <form:form id="submit-payment" modelAttribute="paymentForm" method="post"
                           action="${pageContext.request.contextPath}/order/checkout/submit">
                    <form:hidden path="cardType" id="card-type"/>
                    <div class="form-group owner">
                        <label for="owner">Card Holder</label>
                        <form:input path="cardHolderName" class="form-control" id="owner"/>
                    </div>
                    <div class="form-group CVV">
                        <label for="cvv">CVV</label>
                        <form:input path="cvv" class="form-control" id="cvv"/>
                    </div>
                    <div class="form-group" id="card-number-field">
                        <label for="cardNumber">Card Number</label>
                        <form:input path="cardNumber" class="form-control" id="cardNumber"/>
                    </div>
                    <div class="form-group" id="expiration-date">
                        <label>Expiration Date</label>
                        <select name="month">
                            <option value="01">January</option>
                            <option value="02">February</option>
                            <option value="03">March</option>
                            <option value="04">April</option>
                            <option value="05">May</option>
                            <option value="06">June</option>
                            <option value="07">July</option>
                            <option value="08">August</option>
                            <option value="09">September</option>
                            <option value="10">October</option>
                            <option value="11">November</option>
                            <option value="12">December</option>
                        </select>
                        <select name="year">
                            <option value="2018"> 2018</option>
                            <option value="2019"> 2019</option>
                            <option value="2020"> 2020</option>
                            <option value="2021"> 2021</option>
                            <option value="2020"> 2022</option>
                            <option value="2021"> 2023</option>
                            <option value="2021"> 2024</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Zipcode</label>
                        <form:input path="cardZipcode" class="form-control"/>
                    </div>
                    <div class="form-group" id="credit_cards">
                        <img src="${pageContext.request.contextPath}/static/paymentdetail/images/visa.jpg" id="visa">
                        <img src="${pageContext.request.contextPath}/static/paymentdetail/images/mastercard.jpg" id="mastercard">
                    </div>
                    <div class="form-group" id="pay-now">
                        <button type="submit" class="btn btn-default" id="confirm-purchase">Confirm</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/static/paymentdetail/js/script.js"></script>
<script src="${pageContext.request.contextPath}/static/js/order/order.js"></script>

<%@include file="/WEB-INF/jsp/template/footer.jsp" %>

