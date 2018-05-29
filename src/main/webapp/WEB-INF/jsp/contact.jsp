<%@include file="/WEB-INF/include.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: Tamir Ganbat
  Date: 5/6/2018
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<section id="aa-myaccount">
    <div class="container">
        <div class="aa-myaccount-area">
            <div class="col-md-6" style="float: none; width: 45%">
                <div class="aa-myaccount-login">
                    <h4>Contact</h4>

                    <div class="form-group">
                        <label for="phone">Phone : </label>
                        <label id="phone">+1 (123) 456-789</label>
                    </div>
                    <div class="form-group">
                        <label for="address">Address : </label>
                        <label id="address">501 West Jefferson Ave, Fairfield IA, 52557</label>
                    </div>
                    <div class="form-group">
                        <label for="email">Email : </label>
                        <label id="email">mongolianteampm@gmail.com</label>
                    </div>
                </div>
            </div>
            <div class="col-md-6" style="float: left; width: 45%">
                <div class="aa-myaccount-login" style="text-align: -webkit-center;">
                    <div id="map" style="width:80%;height:300px"/>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
    function myMap() {
        var myCenter = new google.maps.LatLng(41.007222,-91.965833);
        var mapCanvas = document.getElementById("map");
        var mapOptions = {center: myCenter, zoom: 5};
        var map = new google.maps.Map(mapCanvas, mapOptions);
        var marker = new google.maps.Marker({position:myCenter});
        marker.setMap(map);

        var infowindow = new google.maps.InfoWindow({
            content: "The Mongolian Team Online Store!"
        });
        infowindow.open(map,marker);
    }

</script>

<script src="https://maps.googleapis.com/maps/api/js?v=5.exp&sensor=true&callback=myMap"></script>
<%@include file="/WEB-INF/jsp/template/footer.jsp"%>