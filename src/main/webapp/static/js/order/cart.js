function removeCart(productid){
    var temp = "#cart"+productid;
    var jqueryli = $(temp);
    var cartItemPrice = parseFloat(jqueryli.find(".cartItemPrice").text().substring(1).split(",").join(""));
    var cartTotalPrice = parseFloat($("#cartTotalPrices").text().substring(1).split(",").join(""));
    $.ajax({
        type: "POST",
        url: contextPath + '/order/shoppingcart/remove',
        data: {"productid" : productid},
        success : function(){
            var difference = cartTotalPrice - cartItemPrice;
            if(difference == 0){
                $("#cartTotalBox").html("<p>No Items in Shopping Cart!</p>");
            } else {
                $("#cartTotalPrices").text("$" + difference.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
            }
            jqueryli.remove();
        }
    });
}