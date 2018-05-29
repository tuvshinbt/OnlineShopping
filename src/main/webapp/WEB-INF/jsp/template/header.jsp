<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/include.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Online Shop | Home</title>

    <!-- jQuery library -->
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <!-- Font awesome -->
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <!-- SmartMenus jQuery Bootstrap Addon CSS -->
    <link href="${pageContext.request.contextPath}/static/css/jquery.smartmenus.bootstrap.css" rel="stylesheet">
    <!-- product view slider -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.simpleLens.css">
    <!-- slick slider -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/slick.css">
    <!-- price picker slider -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/nouislider.css">
    <!-- Theme color -->
    <link id="switcher" href="${pageContext.request.contextPath}/static/css/theme-color/default-theme.css" rel="stylesheet">
    <!-- Top Slider CSS -->
    <link href="${pageContext.request.contextPath}/static/css/sequence-theme.modern-slide-in.css" rel="stylesheet" media="all">
    <!-- Main style sheet -->
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
    <!-- Google Font -->
    <link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>

    <link href="${pageContext.request.contextPath}/static/css/custom.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/static/js/smoke/themes/gebo.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/js/smoke/smoke.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/js/sticky/sticky.css" rel="stylesheet">

</head>
<body>
<!-- SCROLL TOP BUTTON -->
<a class="scrollToTop" href="#"><i class="fa fa-chevron-up"></i></a>
<!-- END SCROLL TOP BUTTON -->

<!-- Start header section -->
<header id="aa-header">
    <!-- start header top  -->
    <div class="aa-header-top">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="aa-header-top-area">

                        <!--  header top right -->
                        <div class="aa-header-top-right">
                            <div class="navbar-collapse collapse">
                                <!-- Left nav -->
                                <ul class="nav navbar-nav">

                                    <sec:authorize access="isAuthenticated()">
                                        <li>
                                            <a href="#" style="cursor: default">
                                                Welcome:
                                                <sec:authorize access="hasRole('ROLE_CUSTOMER')">Customer </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_VENDOR')">Vendor </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_ADMIN')">Admin </sec:authorize>
                                                    ${signedUser.username} <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                                                    <li><a href="${pageContext.request.contextPath}/order/customer/all/1">Orders</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                                    <li>
                                                        <a href="javascript:void(0);">
                                                            User Management <span class="caret"></span>
                                                        </a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="${pageContext.request.contextPath}/admin/user/admin">Manage Admin</a></li>
                                                            <li><a href="${pageContext.request.contextPath}/admin/user/vendor">Manage vendor</a></li>
                                                            <li><a href="${pageContext.request.contextPath}/admin/user/customer">Manage Customer</a></li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <a href="${pageContext.request.contextPath}/admin/product">Product <span class="caret"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="${pageContext.request.contextPath}/admin/category">Category</a></li>
                                                        </ul>
                                                    </li>
                                                </sec:authorize>
                                                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_VENDOR')">
                                                    <li><a href="${pageContext.request.contextPath}/report/reportFilter">Report</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_VENDOR')">
                                                    <li><a href="${pageContext.request.contextPath}/vendor/product">Own products</a></li>
                                                </sec:authorize>
                                            </ul>
                                        </li>
                                        <li>
                                            <a href="#">Profile <span class="caret"></span></a>
                                            <ul class="dropdown-menu">
                                                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                                                    <li><a href="${pageContext.request.contextPath}/profile/edit/">Edit Profile</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                                    <li><a href="${pageContext.request.contextPath}/profile/admin/edit/">Edit Profile</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_VENDOR')">
                                                    <li><a href="${pageContext.request.contextPath}/profile/vendor/edit/">Edit Profile</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasAnyRole('ROLE_VENDOR', 'ROLE_CUSTOMER')">
                                                    <li><a href="${pageContext.request.contextPath}/profile/card/edit/">Edit Card Info</a></li>
                                                </sec:authorize>
                                                <li><a href="${pageContext.request.contextPath}/profile/edit/password">Edit Password</a></li>
                                                <li><a href="${pageContext.request.contextPath}/logout">LogOut</a></li>
                                            </ul>
                                        </li>

                                    </sec:authorize>
                                    <sec:authorize access="!isAuthenticated()">
                                        <li>
                                            <a href="#">SignUp <span class="caret"></span></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="${pageContext.request.contextPath}/signup">Customer SignUp</a></li>
                                                <li><a href="${pageContext.request.contextPath}/vendor/signup">Vendor SignUp</a></li>
                                            </ul>

                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                                    </sec:authorize>
                                </ul>
                            </div><!--/.nav-collapse -->
                        </div>
                        <!-- / header top right -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- / header top  -->


    <!-- start header bottom  -->
    <div class="aa-header-bottom">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="aa-header-bottom-area">
                        <!-- logo  -->
                        <div class="aa-logo">
                            <!-- Text based logo -->
                            <a href="${pageContext.request.contextPath}/">
                                <span class="fa fa-shopping-cart"></span>
                                <p>Online<strong>Shop</strong> <span>Your Shopping</span></p>
                            </a>
                        </div>
                        <!-- / logo  -->
                        <!-- cart box -->
                        <div class="aa-cartbox">
                            <a class="aa-cart-link" href="${pageContext.request.contextPath}/order/shoppingcart">
                                <span class="fa fa-shopping-basket"></span>
                                <span class="aa-cart-title">CART</span>
                            </a>

                            <div class="aa-cartbox-summary " id="cartTotalBox">
                                <c:choose>
                                    <c:when test="${shoppingcart == null || shoppingcart.orderDetails.isEmpty()}">
                                        <p>No Items in Shopping Cart!</p>
                                    </c:when>
                                    <c:otherwise>
                                        <ul>
                                            <c:forEach var="orderDetail" items="${shoppingcart.orderDetails}">
                                                <li id="cart${orderDetail.product.id}">
                                                    <a class="aa-cartbox-img" href="${pageContext.request.contextPath}/product/${orderDetail.product.id}"><img
                                                            src="${pageContext.request.contextPath}${resourcePath}${orderDetail.product.image}"
                                                            alt="img"></a>
                                                    <div class="aa-cartbox-info">
                                                        <h4><a href="#"></a></h4>
                                                        <span class="cartTotal"><span class="cartItemPrice"><fmt:formatNumber value="${orderDetail.price}" type="currency"
                                                                          currencySymbol="$"/></span> x <c:out
                                                            value="${orderDetail.quantity}"/></span>
                                                    </div>
                                                    <span class="aa-remove-product"
                                                       onclick="removeCart(${orderDetail.product.id})" />
                                                    <span class="fa fa-times"></span>
                                                    </a>
                                                        <%--<a class="aa-remove-product" href=""--%>
                                                        <%--ng-click="removeFromCart(item.cartItemId,'${_csrf.parameterName}=${_csrf.token}')">--%>
                                                        <%--<span class="fa fa-times"></span>--%>
                                                        <%--</a>--%>
                                                </li>

                                            </c:forEach>
                                            <li>
                                                <span class="aa-cartbox-total-title">Total</span>
                                                <span class="aa-cartbox-total-price" id="cartTotalPrices"><fmt:formatNumber
                                                        value="${shoppingcart.calculateTotalPrice()}" type="currency"
                                                        currencySymbol="$"/></span>
                                            </li>
                                        </ul>
                                        <a class="aa-cartbox-checkout aa-primary-btn" href="${pageContext.request.contextPath}/order/shoppingcart">Shopping
                                            Cart</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <!-- / cart box -->
                        <!-- search box -->
                        <form action="${pageContext.request.contextPath}/product/search" method="get">
                            <div class="aa-search-box">
                                <input type="text" name="name" value="${param.name}" placeholder="Product Name">
                                <button type="submit" style="width: 50px; height: 40px" class="fa fa-search"></button>
                            </div>
                        </form>
                        <!-- / search box -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- / header bottom  -->
</header>
<!-- / header section -->

<!-- menu -->
<section id="menu">
    <div class="container">
        <div class="menu-area">
            <!-- Navbar -->
            <div class="navbar navbar-default" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="navbar-collapse collapse">
                    <!-- Left nav -->
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <c:forEach items="${mainCategories}" var="category">
                            <li><a href="${pageContext.request.contextPath}/product/search?categoryId=${category.id}">${category.name} <span class="caret"></span></a>
                            <c:if test="${category.childCategories ne null && not empty category.childCategories}">
                                <ul class="dropdown-menu">
                                <c:forEach items="${category.childCategories}" var="subCategory">
                                    <li><a href="${pageContext.request.contextPath}/product/search?categoryId=${subCategory.id}">${subCategory.name}
                                        <c:if test="${subCategory.childCategories ne null && not empty subCategory.childCategories}">
                                            <span class="caret">
                                        </c:if>
                                    </a>
                                    <c:if test="${subCategory.childCategories ne null && not empty subCategory.childCategories}">
                                        <ul class="dropdown-menu">
                                        <c:forEach items="${subCategory.childCategories}" var="sub2Category">
                                            <li><a href="${pageContext.request.contextPath}/product/search?categoryId=${sub2Category.id}">${sub2Category.name}</a></li>
                                        </c:forEach>
                                        </ul>
                                    </c:if>
                                    </li>
                                </c:forEach>
                                </ul>
                            </c:if>
                            </li>
                        </c:forEach>
                        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_VENDOR')">
                            <li><a href="${pageContext.request.contextPath}/report/reportFilter">Report</a></li>
                        </sec:authorize>
                        <li><a href="#">Products<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/product/list">All</a></li>
                                <sec:authorize access="hasRole('ROLE_VENDOR')">
                                    <li><a href="${pageContext.request.contextPath}/vendor/product/all">Own products</a></li>
                                    <li><a href="${pageContext.request.contextPath}/vendor/product/save">Upload</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <li><a href="${pageContext.request.contextPath}/order/customer/all/1">Orders</a></li>

                        </sec:authorize>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>
</section>
<span class="clearfix"></span>


<!-- / menu -->