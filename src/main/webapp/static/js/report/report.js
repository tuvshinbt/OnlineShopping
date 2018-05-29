function myFunctionReport(checkboxId, selectId) {
    if ($(checkboxId).is(':checked')) {
        $(selectId).prop('disabled', true);
    } else {
        $(selectId).prop('disabled', false);
    }
}

$(document).ready(function () {

    $("#allVendor").click(function () {
        myFunctionReport('#allVendor', '#vendor');
    });

    $("#allCategory").click(function () {
        myFunctionReport('#allCategory', '#category');
    });


})
//     $("#begin_Date").t.keypress(function (event) {
// //press enter button
//         if (event.which == 13) {
//             $("#your-other-input").focus();
//         }
//     })
