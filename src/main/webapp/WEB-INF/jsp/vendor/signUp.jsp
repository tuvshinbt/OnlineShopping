<%--
  Created by IntelliJ IDEA.
  User: chanpiseth
  Date: 4/27/2018
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>

<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<section id="aa-myaccount">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="aa-myaccount-area">
                    <div class="col-md-6" style="float: none; margin-left: 35%; width: 30%">
                        <c:if test="${!empty message}">
                            <c:choose>
                                <c:when test="${message.type eq 'SUCCESS'}">
                                    <div class="alert alert-success" role="alert">
                                        Successfully signed up.
                                        <br/>
                                        <b>Confirmation</b> email sent to your email address.
                                        <p>Please wait to admin confirm your request. Thank you</p>
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

                            <h4>Vendor Register</h4>


                            <%--<c:url value="/signup" var="signupProcessingUrl"/>--%>
                            <%--<form:form action="${signupProcessingUrl}" method="post">--%>

                            <form:form modelAttribute="moduleForm" action="${pageContext.request.contextPath}/vendor/signup" method="POST" enctype="multipart/form-data">

                                <div class="form-group">
                                    <label for="username">User Name<span style="color: red">*</span></label>
                                    <form:errors path="username" cssStyle="color: red" />
                                    <form:input path="username" id="username" class="form-Control" />
                                </div>

                                <div class="form-group">
                                    <label for="companyName">Company name<span style="color: red">*</span></label>
                                    <form:errors path="companyName" cssStyle="color: red" />
                                    <form:input path="companyName" id="companyName" class="form-Control" />
                                </div>


                                <div class="form-group">
                                    <label for="email">Email<span style="color: red">*</span></label>
                                    <form:errors path="email" cssStyle="color: red" />
                                    <form:input type="email" path="email" id="email" class="form-Control" />
                                </div>



                                <div class="form-group">
                                    <label for="password">Password<span style="color: red">*</span></label>
                                    <form:errors path="password" cssStyle="color: red" />
                                    <form:password path="password" class="form-Control" />
                                </div>

                                <div class="form-group">
                                    <label for="password">Password<span style="color: red">*</span></label>
                                    <form:errors path="valid" cssStyle="color: red" />
                                    <form:password path="rePassword" class="form-Control" />
                                </div>

                                <script>
                                    $("#rePassword").on("change paste keyup", function() {
                                        var password = $("#password").val();
                                        var confirmPassword = $("#rePassword").val();

                                        if (password != confirmPassword){
                                            $("#register-submit").prop('disabled', true);
                                            $("#error").html("Passwords do not match!");
                                        }else{
                                            $("#register-submit").prop('disabled', false);
                                            $("#error").html("");
                                        }
                                    });
                                    function jsfunction()
                                    {
                                        //you code
                                        return false;
                                    }
                                </script>

                                <div class="form-group">
                                    <label for="file">Image<span style="color: red">*</span></label>
                                    <form:errors path="file" cssStyle="color: red" />
                                    <input type="file" name="file" id="file" class="form-Control" />
                                </div>

                                <div class="form-group owner">
                                    <label for=""><span style="color: red">REGISTRATION FEE</span></label>
                                    <input class="form-control" readonly value="$25'000">
                                </div>

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
                                    <label>Zipcode<span style="color: red">*</span></label>
                                    <form:errors path="cardZipcode" cssStyle="color: red" />
                                    <form:input path="cardZipcode" class="form-control" />
                                </div>
                                <div class="form-group" id="credit_cards">
                                    <img src="${pageContext.request.contextPath}/static/paymentdetail/images/visa.jpg" id="visa">
                                    <img src="${pageContext.request.contextPath}/static/paymentdetail/images/mastercard.jpg" id="mastercard" >
                                </div>

                                <%--<input type="submit" value="submit" id="register-submit" class="btn btn-default" onclick="module.submit()" disabled>--%>
                                <%--<a href="<c:url value="/" />" class="btn btn-default">Cancel</a>--%>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </form:form>


                            <button type="button" class="btn btn-primary" onclick="module.submit()">submit</button>
                            <a href="<c:url value="/" />" class="btn btn-default">Cancel</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

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

    module = {
        submit: function(){
            smoke.confirm('Are sure to pay $25\'000 for registration fee?',function(e){
                if (e){
                    $('#moduleForm').submit();
                }
            }, {cancel:"No", ok:"Yes"});
        }
    };
</script>

<c:if test="${!empty message}">
    <script>
        <c:if test="${message.type eq 'SUCCESS'}">
        setTimeout(window.location = "/", 5000);
        </c:if>
    </script>
</c:if>


<%@include file="/WEB-INF/jsp/template/footer.jsp"%>
