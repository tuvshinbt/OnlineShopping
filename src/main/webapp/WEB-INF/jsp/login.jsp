<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/include.jsp"%>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<!-- Cart view section -->
<section id="aa-myaccount">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="aa-myaccount-area">
                    <div class="col-md-6" style="float: none; margin-left: 35%; width: 30%">
                        <div class="aa-myaccount-login">

                            <h4>Login</h4>

                            <c:url value="/login" var="loginProcessingUrl"/>
                            <form action="${loginProcessingUrl}" method="post">
                                <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
                                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                                    <div class="alert alert-danger" role="alert">
                                        <span class="sr-only">Failed to login:</span>
                                        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                                            <p class="text-center">
                                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                                Failed to login.
                                            </p>
                                            Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                                        </c:if>
                                    </div>
                                </c:if>
                                <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
                                <c:if test="${param.logout != null}">
                                    <div>
                                        You have been logged out.
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <label for="username">Username</label>
                                    <input type="text" id="username" name="username" class="form-control" autofocus />
                                </div>
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password"class="form-control" />
                                </div>
                                <!-- if using RememberMeConfigurer make sure remember-me matches RememberMeConfigurer#rememberMeParameter -->
                                <div class="form-group">
                                    <label class="rememberme" for="remember-me">Remember Me?</label>
                                    <input type="checkbox" id="remember-me" name="remember-me"/>
                                </div>
                                <div>
                                    <button type="submit" class="aa-browse-btn">Log in</button>
                                </div>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>
<!-- / Cart view section -->

<%@include file="/WEB-INF/jsp/template/footer.jsp"%>

