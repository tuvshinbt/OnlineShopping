<%--
  Created by IntelliJ IDEA.
  User: chanpiseth
  Date: 4/24/2018
  Time: 5:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<section id="aa-catg-head-banner">
    <%--<img src="${pageContext.request.contextPath}/static/img/fashion/fashion-header-bg-8.jpg" />" alt="fashion img">--%>
    <div class="aa-catg-head-banner-area">
        <div class="container">
            <div class="aa-catg-head-banner-content">
                <h2>${title}</h2>
            </div>
        </div>
    </div>
</section>
<c:url var="searchUrl" value="/product/list"/>

<!-- product category -->
<section id="aa-product-category">
    <div class="container">
        <div class="row">
            <div class="col-lg-9 col-md-9 col-sm-8 col-md-push-3">
                <div class="aa-product-catg-content">
                    <div class="aa-product-catg-head">
                        <div class="aa-product-catg-head-left">
                            <form action="${pageContext.request.contextPath}" class="aa-sort-form">
                                <label>Sort by</label>
                                <select onchange="sortBy(this)">
                                    <option value="${searchUrl}<my:replaceParam name='orderByPrice' value=""/>">Default</option>
                                    <option value="${searchUrl}<my:replaceParam name='orderByPrice' value="1"/>" <c:if test="${param.orderByPrice eq '1'}">selected</c:if>>Price Ascending</option>
                                    <option value="${searchUrl}<my:replaceParam name='orderByPrice' value="0"/>" <c:if test="${param.orderByPrice eq '0'}">selected</c:if>>Price Descending</option>
                                    <%--<option value="<my:replaceParam name='orderByPrice' value='0' />">Price Descending</option>--%>
                                </select>
                            </form>
                        </div>
                    </div>
                    <div class="aa-product-catg-body">
                        <ul class="aa-product-catg">
                            <!-- start single product item -->
                            <c:set var="counter" value="0" scope="page" />
                            <c:forEach items="${products}" var="product">
                                <c:set var="counter" value="${counter + 1}" scope="page"/>
                                <li id="product-li-number-${counter}">
                                    <figure>
                                        <a class="aa-product-img" href="${pageContext.request.contextPath}/product/${product.id}">
                                            <img style="width: 250px; height: 300px" src="${pageContext.request.contextPath}${resourcePath}${product.image}"></a>
                                        <a class="aa-add-card-btn" style="cursor: hand" href="${pageContext.request.contextPath}/order/addToCart/${product.id}"><span class="fa fa-shopping-cart"></span>Add To Cart</a>
                                            <%--<a class="aa-add-card-btn" style="cursor: hand" ng-click="addToCart('${product.id}','${_csrf.parameterName}=${_csrf.token}')">--%>
                                            <%--<span class="fa fa-shopping-cart"></span>Add To Cart--%>
                                            <%--</a>--%>
                                        <figcaption>
                                            <h4 class="aa-product-title"><a href="${pageContext.request.contextPath}/product=${product.id}">${product.name}</a></h4>
                                            <span class="aa-product-price">$${product.price}0</span>
                                            <span class="aa-product-descrip">${product.description}</span>
                                        </figcaption>
                                    </figure>
                                    <!-- product badge -->
                                    <%--<span class="aa-badge aa-sale" href="#">SALE!</span>--%>
                                    <!--<span class="aa-badge aa-sold-out" href="#">Sold Out!</span>
                                    <span class="aa-badge aa-hot" href="#">HOT!</span>-->
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <input type="hidden" id="current-page-number" value="1">
                    <script>
                        $(document).ready(function (){
                            var page = 1;
                            var productNumbers = ${products.size()};
                            var selectedShows = parseInt($("#numberShows").find(":selected").text(), 10);
                            var pageTotal = Math.ceil(productNumbers/selectedShows);

                            for ( var j = 1; j <= (page-1)*selectedShows; j++){
                                $("#product-li-number-"+j).hide();
                            }
                            for ( var k = page*selectedShows+1; k <= productNumbers; k++){
                                $("#product-li-number-"+k).hide();
                            }

                            for (var i = 1; i <= pageTotal; i++){
                                if (i == 1){
                                    var append_content = "<li><a id='page-number-"+ i +"' style='cursor: hand; " +
                                        "background-color:#ff6666; color:white ' onclick='pagination("+ i +","+
                                        pageTotal+",${products.size()})'>"+ i + "</a></li>";
                                }else{
                                    var append_content = "<li><a id='page-number-"+ i +"' style='cursor: hand' onclick='pagination("+
                                        i+","+pageTotal+",${products.size()})'>"+ i + "</a></li>";
                                }
                                $("#pagination-ul").append(append_content);
                            }
                            <c:if test="${products.size() gt 6}">
                            var next = "<li id='next-page-li'><a style='cursor: hand' onclick='pagination(0,"+
                                pageTotal+",${products.size()})' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>";
                            $("#pagination-ul").append(next);
                            </c:if>
                        });
                    </script>
                    <c:set value="${result.getNumber() + 1}" var="currentIndex"/>
                    <c:choose>
                        <c:when test="${1 < (currentIndex - 5)}">
                            <c:set value="${currentIndex - 5}" var="beginIndex"/>
                        </c:when>
                        <c:otherwise>
                            <c:set value="${1}" var="beginIndex"/>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${(beginIndex + 10) < result.getTotalPages()}">
                            <c:set value="${beginIndex + 10}" var="endIndex"/>
                        </c:when>
                        <c:otherwise>
                            <c:set value="${result.getTotalPages()}" var="endIndex"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="aa-product-catg-pagination">
                        <nav>
                            <ul class="pagination">
                                <c:choose>
                                    <c:when test="${currentIndex == 1}">
                                        <li class="disabled"><a href="javascript:void(0);">&lt;&lt;</a></li>
                                        <li class="disabled"><a href="javascript:void(0);">&lt;</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="${pageContext.request.contextPath}/product/list?page;">&lt;&lt;</a></li>
                                        <li><a href="${pageContext.request.contextPath}/product/list?page=${currentIndex-1}">&lt;</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
                                    <c:choose>
                                        <c:when test="${i == currentIndex}">
                                            <li class="disabled"><a href="${pageContext.request.contextPath}/product/list?page=${i}"><c:out value="${i}"/></a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${pageContext.request.contextPath}/product/list?page=${i}"><c:out value="${i}"/></a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${currentIndex ge result.totalPages}">
                                        <li class="disabled"><a href="javascript:void(0);">&gt;</a></li>
                                        <li class="disabled"><a href="javascript:void(0);">&gt;&gt;</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="${pageContext.request.contextPath}/product/list?page=${currentIndex + 1}" >&gt;</a></li>
                                        <li><a href="${pageContext.request.contextPath}/product/list?page=${result.totalPages}">&gt;&gt;</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-4 col-md-pull-9">
                <aside class="aa-sidebar">
                    <!-- single sidebar -->
                    <div class="aa-sidebar-widget">
                        <h3>Category</h3>
                        <ul class="aa-catg-nav">
                            <c:forEach items="${categories}" var="row">
                                <li><a <c:if test="${row.id eq param.categoryId}">class="selected" </c:if> href="${searchUrl}<my:replaceParam name="categoryId" value="${row.id}"/> ">${row.name}</a></li>
                                <c:if test="${row.childCategories ne null}">
                                    <ul class="aa-catg-nav">
                                        <c:forEach items="${row.childCategories}" var="cRow">
                                            <li><a <c:if test="${cRow.id eq param.categoryId}">class="selected" </c:if> href="${searchUrl}<my:replaceParam name="categoryId" value="${cRow.id}"/> ">--- ${cRow.name}</a></li>
                                            <c:if test="${cRow.childCategories ne null}">
                                                <ul class="aa-catg-nav">
                                                    <c:forEach items="${cRow.childCategories}" var="sRow">
                                                        <li><a <c:if test="${sRow.id eq param.categoryId}">class="selected" </c:if> href="${searchUrl}<my:replaceParam name="categoryId" value="${sRow.id}"/> ">------ ${sRow.name}</a></li>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="aa-sidebar-widget">
                        <h3>Vendor</h3>
                        <ul class="aa-catg-nav">
                            <c:forEach items="${vendors}" var="row">
                                <li><a <c:if test="${row.id eq param.vendorId}">class="selected" </c:if> href="${searchUrl}<my:replaceParam name="vendorId" value="${row.id}"/> ">${row.companyName}</a></li>
                            </c:forEach>
                        </ul>
                    </div>

                    <%--<!-- single sidebar -->
                    <div class="aa-sidebar-widget">
                        <h3>Tags</h3>
                        <div class="tag-cloud">
                            <c:forEach items="${tagList}" var="tag">
                                <a href="<my:replaceParam name='t' value='${tag}' />">${tag}</a>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- single sidebar -->
                    <div class="aa-sidebar-widget">
                        <h3>Shop By Price</h3>

                        <!-- price range -->
                        <div class="aa-sidebar-price-range">
                            <input type="radio" name="priceRage" max="20" onclick="PriceRangeRadio(this,'<my:replaceParam name='lp' value='0' />');">$0 - $20<br>
                            <input type="radio" name="priceRage" max="40" onclick="PriceRangeRadio(this,'<my:replaceParam name='lp' value='20' />');">$20 - $40<br>
                            <input type="radio" name="priceRage" max="60" onclick="PriceRangeRadio(this,'<my:replaceParam name='lp' value='40' />');">$40 - $60<br>
                            <input type="radio" name="priceRage" max="80" onclick="PriceRangeRadio(this,'<my:replaceParam name='lp' value='60' />');">$60 - $80<br>
                            <div>
                                <span style="cursor: hand;" onclick="customPriceRage()">Custom Price Range</span>
                                <div id="custom-price-range-div" hidden>
                                    <br>
                                    <input id="skip-value-lower" class="aa-filter-btn" style="background-color: white;
                            color: black; text-align: center; cursor: default" type="number" min="1" placeholder="min"/>
                                    <input id="skip-value-upper" class="aa-filter-btn" style="background-color: white;
                            color: black; text-align: center; cursor: default" type="number" min="1" placeholder="max"/>
                                    <button class="aa-filter-btn" type="button" onclick="customPriceRageGo('<my:replaceParam name='lp' value='' />')">Go</button>
                                </div>

                            </div>
                        </div>

                    </div>
                    <!-- single sidebar -->
                    <div class="aa-sidebar-widget">
                        <h3>Recently Views</h3>
                        <span style="color: red">*Unfinished function</span>
                        <div class="aa-recently-views">
                            <ul>
                                <li>
                                    <a href="#" class="aa-cartbox-img"><img alt="img" src="#"></a>
                                    <div class="aa-cartbox-info">
                                        <h4><a href="#">Product Name</a></h4>
                                        <p>1 x $250</p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>--%>
                </aside>
            </div>
        </div>
    </div>
</section>
<!-- / product category -->






<%@include file="/WEB-INF/jsp/template/footer.jsp"%>

