<%@include file="/WEB-INF/include.jsp"%>

<!-- footer -->
<footer id="aa-footer">
    <!-- footer bottom -->
    <div class="aa-footer-top">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="aa-footer-top-area">
                        <div class="row">
                            <div class="col-md-3 col-sm-6">
                                <div class="aa-footer-widget">
                                    <h3>Main Menu</h3>
                                    <ul class="aa-footer-nav">
                                        <li><a href="#">Home</a></li>
                                        <li><a href="#">Our Services</a></li>
                                        <li><a href="#">Our Products</a></li>
                                        <li><a href="#">About Us</a></li>
                                        <li><a href="#">Contact Us</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-3 col-sm-6">
                                <div class="aa-footer-widget">
                                    <div class="aa-footer-widget">
                                        <h3>Knowledge Base</h3>
                                        <ul class="aa-footer-nav">
                                            <li><a href="#">Delivery</a></li>
                                            <li><a href="#">Services</a></li>
                                            <li><a href="#">Discount</a></li>
                                            <li><a href="#">Special Offer</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 col-sm-6">
                                <div class="aa-footer-widget">
                                    <div class="aa-footer-widget">
                                        <h3>Useful Links</h3>
                                        <ul class="aa-footer-nav">
                                            <li><a href="#">Site Map</a></li>
                                            <li><a href="#">Search</a></li>
                                            <li><a href="#">Advanced Search</a></li>
                                            <li><a href="#">Suppliers</a></li>
                                            <li><a href="#">FAQ</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 col-sm-6">
                                <div class="aa-footer-widget">
                                    <div class="aa-footer-widget">
                                        <h3>Contact Us</h3>
                                        <address>
                                            <p> xx xxxxx xx, IA xxxxx, USA</p>
                                            <p><span class="fa fa-phone"></span>+1 xxx-xxx-xxxx</p>
                                            <p><span class="fa fa-envelope"></span>xxxxx@gmail.com</p>
                                        </address>
                                        <div class="aa-footer-social">
                                            <a href="#"><span class="fa fa-facebook"></span></a>
                                            <a href="#"><span class="fa fa-twitter"></span></a>
                                            <a href="#"><span class="fa fa-google-plus"></span></a>
                                            <a href="#"><span class="fa fa-youtube"></span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- footer-bottom -->
    <div class="aa-footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="aa-footer-bottom-area">
                        <p><a href="<spring:url value="/version"/>">Version</a></p>
                        <div class="aa-footer-payment">
                            <span class="fa fa-cc-mastercard"></span>
                            <span class="fa fa-cc-visa"></span>
                            <span class="fa fa-paypal"></span>
                            <span class="fa fa-cc-discover"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
<!-- / footer -->

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div id="edit-target"></div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- jQuery library -->
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<%--Form--%>
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/static/js/smoke/smoke.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/sticky/sticky.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.simpleLens.js"></script>
<script src="${pageContext.request.contextPath}/static/js/sequence.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<!-- SmartMenus jQuery plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.smartmenus.js"></script>
<!-- SmartMenus jQuery Bootstrap Addon -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.smartmenus.bootstrap.js" ></script>
<!-- To Slider JS -->
<script src="${pageContext.request.contextPath}/static/js/jquery.simpleGallery.js" ></script>
<script src="${pageContext.request.contextPath}/static/js/sequence-theme.modern-slide-in.js"  ></script>
<!-- prduct view slider -->
<script  type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.simpleGallery.js"   ></script>
<!-- slick slider -->
<script  type="text/javascript" src="${pageContext.request.contextPath}/static/js/slick.js"  ></script>
<!-- Price picker slider -->
<script  type="text/javascript" src="${pageContext.request.contextPath}/static/js/nouislider.min.js" ></script>
<!-- Custom js -->
<script src="${pageContext.request.contextPath}/static/js/custom.js" ></script>
<script  src="${pageContext.request.contextPath}/static/js/controller.js" ></script>
<!--payment js-->
<script src="${pageContext.request.contextPath}/static/paymentdetail/js/jquery.payform.min.js" charset="utf-8"></script>
<!--cart for header js-->
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/static/js/order/cart.js"></script>

</body>
</html>