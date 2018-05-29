<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<body>
<%--<h1>Hello World!</h1>--%>

<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<!-- Start Promo section -->
<section id="aa-promo">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="aa-promo-area">
                    <div class="row">
                        <!-- promo left -->
                        <div class="col-md-5 no-padding">
                            <div class="aa-promo-left">
                                <div class="aa-promo-banner">
                                    <c:forEach items="${categories}" var="category" begin="0" end="0">
                                        <img style="object-fit: cover;" src="${pageContext.request.contextPath}${resourcePath}${category.image}" alt="img">
                                        <div class="aa-prom-content">
                                            <h4><a href="${pageContext.request.contextPath}/product/search?categoryId=${category.id}">${category.name}</a></h4>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <!-- promo right -->
                        <div class="col-md-7 no-padding">
                            <div class="aa-promo-right">
                                <c:forEach items="${categories}" var="category" begin="1" end="4">
                                    <div class="aa-single-promo-right">
                                        <div class="aa-promo-banner">
                                            <img style="object-fit: cover;" src="${pageContext.request.contextPath}${resourcePath}${category.image}" alt="img">
                                            <div class="aa-prom-content">
                                                <h4 style="color: #cd5c5c"><a href="${pageContext.request.contextPath}/product/search?categoryId=${category.id}" >${category.name}</a></h4>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- / Promo section -->


<!-- Products section -->

<section id="aa-product">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="aa-product-area">
                        <div class="aa-product-inner">
                            <!-- start single Product Item-->
                            <div class="tab-pane fade in active">
                                <ul class="aa-product-catg aa-popular-slider">
                                    <!-- start single product item -->
                                    <c:forEach items="${products}" var="product" begin="0" end="3">
                                        <%--<c:if test="${category eq product.category.name}">--%>
                                        <li>
                                            <figure>
                                                <a class="aa-product-img"
                                                   href="<c:url value="/product/${product.id}" />">
                                                    <img style="width: 250px; height: 300px"
                                                         src="<c:url value="${resourcePath}${product.image}" />"></a>

                                                <a class="aa-add-card-btn" style="cursor: hand" href="${pageContext.request.contextPath}/order/addToCart/${product.id}">
                                                    <span class="fa fa-shopping-cart"></span>Add To Cart
                                                </a>
                                                <figcaption>
                                                    <h4 class="aa-product-title"><a
                                                            href="<c:url value="/product/${product.id}" />">${product.name}</a>
                                                    </h4>
                                                    <span class="aa-product-price">$${product.price}0</span>
                                                </figcaption>
                                            </figure>

                                        </li>
                                        <%--</c:if>--%>
                                    </c:forEach>
                                </ul>
                                <!-- / start single Product Item -->
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- / Products section -->


<!-- Client Brand -->
<section id="aa-client-brand">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="aa-client-brand-area">
                    <ul class="aa-client-brand-slider">
                        <c:forEach items="${vendors}" var="vendor">
                            <li><a href="<c:url value="/product/search?vendorId=${vendor.id}" /> "><img width="500" height="80" src="<c:url value="${resourcePath}${vendor.image}" />" alt="${vendor.companyName}"></a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- / Client Brand -->


<%@include file="/WEB-INF/jsp/template/footer.jsp"%>


<%--<form action="/logout" method="post">
    <button type="submit">Logout</button>
</form>--%>
