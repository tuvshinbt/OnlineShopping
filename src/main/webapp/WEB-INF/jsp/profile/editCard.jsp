<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<script>
    module = {
        delete: function(id){
            smoke.confirm('Are sure to delete this!',function(e){
                if (e){
                    $.post('${pageContext.request.contextPath}/profile/removeCard', 'cardId='+id, function(msg){
                        var message = msg.message || msg;
                        var type = 'st-'+(msg.type || 'success').toLowerCase();
                        $.sticky(message, {autoclose: 10000, position:'top-right', type: type});
                        if (type = 'success'){
                            $('#card'+id).remove();
                        }
                    });
                }
            }, {cancel:"Cancel", ok:"Delete"});
        }
    };
</script>

<section id="aa-myaccount">
    <div class="container" style="margin-top: 35px">
        <h2 class="text-center">Remove Card</h2>
        <div class="row">
            <c:forEach var="card" items="${cards}">
                <div class="col-sm-2" id="card${card.id}" style="    border: 2px solid black; padding: 10px; margin: 10px; text-align: center; border-radius: 5px;">
                    <button class="btn btn-danger" type="button" onclick="module.delete('${card.id}')">
                        Remove
                    </button>
                    <br/>
                    <br/>
                    <c:out value="${card.cardType}"/> <br/> **** **** **** <c:out value="${card.last4Digit}"/>
                    <br/>
                </div>
            </c:forEach>
        </div>
        <h2 class="text-center">Add new card</h2>
        <div class="row">
            <div class="col-md-12">
                <div class="aa-myaccount-area">
                    <div class="col-md-6" style="float: none; margin-left: 35%; width: 30%">
                        <c:if test="${!empty message}">
                            <c:choose>
                                <c:when test="${message.type eq 'SUCCESS'}">
                                    <div class="alert alert-success" role="alert">
                                        Successfully changed your credit card.
                                        <br/>
                                        It will redirect to home page in 5 sec
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-danger" role="alert">
                                        <spring:message code="${message.message}"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <div class="aa-myaccount-login">
                            <h4>Add card</h4>
                            <div class="alert-danger"><c:out value="${badcard}"/></div>
                            <form:form modelAttribute="editCard" action="${pageContext.request.contextPath}/profile/card/edit" method="post" >

                                <%--CARD INFO--%>
                                <form:hidden path="cardType" id="card-type"/>
                                <div class="form-group owner">
                                    <label for="cardHolderName">Card Holder<span style="color: red">*</span></label>
                                    <form:errors path="cardHolderName" cssStyle="color: red" />
                                    <form:input path="cardHolderName" class="form-control" id="cardHolderName"/>
                                </div>
                                <div class="form-group CVV">
                                    <label for="cvv">CVV<span style="color: red">*</span></label>
                                    <form:errors path="cvv" cssStyle="color: red" />
                                    <form:input path="cvv" class="form-control" id="cvv"/>
                                </div>
                                <div class="form-group" id="card-number-field">
                                    <label for="cardNumber">Card Number<span style="color: red">*</span></label>
                                    <form:errors path="cardNumber" cssStyle="color: red" />
                                    <form:input path="cardNumber" class="form-control" id="cardNumber"/>
                                </div>
                                <div class="form-group" id="expiration-date">
                                    <label>Expiration Date<span style="color: red">*</span></label>
                                    <select name="month" value="cvv">
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
                                    <label>Zipcode<span style="color: red">*</span></label>
                                    <form:errors path="zipcode" cssStyle="color: red" />
                                    <form:input path="zipcode" class="form-control" />
                                </div>
                                <div class="form-group" id="credit_cards">
                                    <img src="${pageContext.request.contextPath}/static/paymentdetail/images/visa.jpg" id="visa">
                                    <img src="${pageContext.request.contextPath}/static/paymentdetail/images/mastercard.jpg" id="mastercard" >
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" >Submit</button>
                                    <a href="<c:url value="/" />" class="btn btn-default">Cancel</a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="/WEB-INF/jsp/template/footer.jsp"%>

<script>
    $(function() {
        var cardNumberField = $('#card-number-field');
        var cardNumber = $('#cardNumber');
        var CVV = $("#cvv");
        var mastercard = $("#mastercard");
        var visa = $("#visa");

        cardNumber.payform('formatCardNumber');
        CVV.payform('formatCardCVC');

        cardNumber.keyup(function() {
            visa.removeClass('transparent');
            mastercard.removeClass('transparent');

            if ($.payform.validateCardNumber(cardNumber.val()) == false) {
                cardNumberField.addClass('has-error');
            } else {
                cardNumberField.removeClass('has-error');
                cardNumberField.addClass('has-success');
            }

            if ($.payform.parseCardType(cardNumber.val()) == 'visa') {
                mastercard.addClass('transparent');
                $("#card-type").val("Visa");
            } else if ($.payform.parseCardType(cardNumber.val()) == 'mastercard') {
                visa.addClass('transparent');
                $("#card-type").val("Mastercard");
            }
        });
    });
</script>

<c:if test="${!empty message}">
    <script>
        $.sticky('<spring:message code="${message.message}"/>', {autoclose : 5000, position: "top-right", type: "st-${fn:toLowerCase(message.type)}" });

        <c:if test="${message.type eq 'SUCCESS'}">
        window.setTimeout(function(){
            window.location.href = "/";
        }, 5000);
        </c:if>
    </script>
</c:if>