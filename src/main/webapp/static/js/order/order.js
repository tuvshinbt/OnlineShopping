var timeout= null;

function updateQuantity(productid) {

    clearTimeout(timeout);
    var temp = "#"+productid;
    var jquerytr = $(temp);
    var updatedquantity = parseInt(jquerytr.find("#cartquantity2").val());
    var maxProductQuantity = jquerytr.find("#cartquantity2").attr("max");
    if(updatedquantity >  maxProductQuantity){
        jquerytr.find("#cartquantity2").val(maxProductQuantity);
        updatedquantity = maxProductQuantity;
    }
    time = setTimeout(function(){

        var price = parseFloat(jquerytr.find("#price").text().substring(1).split(",").join(""));

        var totalProductPrice = parseFloat(jquerytr.find("#totalprice").text().substring(1).split(",").join(""));
        var currentTax = parseFloat($("#tax").text().substring(1).split(",").join(""));
        var totalPriceWithTax = parseFloat($("#totalpricewithtax").text().substring(1).split(",").join(""));
        var productIdAndQuantity = {"productid" : productid, "updatedquantity" : updatedquantity};


        $.ajax({
            type : "POST",
            url : contextPath + '/order/shoppingcart/update',
            data : productIdAndQuantity,
            success : function(){
                //jquerytr.find("#quantity").text(updatedquantity);
                var newTotalProductPrice = price * updatedquantity;
                var totalProductPriceDifferent = totalProductPrice - newTotalProductPrice;
                var taxDifference = totalProductPriceDifferent * 0.07;
                var newTax = currentTax - taxDifference;
                var newTotalPriceWithTax = totalPriceWithTax - taxDifference - totalProductPriceDifferent;
                jquerytr.find("#totalprice").text("$" + newTotalProductPrice.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
                $("#tax").text("$" + newTax.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
                $("#totalpricewithtax").text("$" + newTotalPriceWithTax.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
                if(updatedquantity == 0){
                    jquerytr.remove();
                }
            }
        })
    }, 1500);

}

function removeCard(cardId){
    var temp = "#card" + cardId;

    $.ajax({
        type : "post",
        url: contextPath + '/order/removeCard',
        data : {"cardId" : cardId},
        success : function(){
            $(temp).remove();
        }
    })
}

function removeAddress(addressId){
    var temp = "#address" + addressId;
    $.ajax({
        type : "post",
        url : contextPath+ '/order/removeAddress',
        data : {"addressId" : addressId},
        success : function(){
            $(temp).remove();
        }
    })
}

$('#myModal').on('shown.bs.modal', function () {
    $('#myInput').focus()
})