$("#inputPassword").on("focusout", function () {
    if ($(this).val() != $("#inputPassword2").val()) {
        $("#inputPassword2").removeClass("valid").addClass("invalid");
    } else {
        $("#inputPassword2").removeClass("invalid").addClass("valid");
    }
});
$("#inputPassword2").on("keyup", function () {
    if ($("#inputPassword").val() != $(this).val()) {
        $(this).removeClass("valid").addClass("invalid");
    } else {
        $(this).removeClass("invalid").addClass("valid");
    }
});
$().ready(function () {
    $('[rel="tooltip"]').tooltip();
    $("#btnLogin").addClass("btn-primary");
});
function rotateCard(type) {
    var card = $('#containerLogin');
    if (type.id == "btnLogin") {
        if (card.hasClass('hover') == true) {
            card.removeClass('hover');
            $("#btnLogin").addClass("btn-primary");
            $("#btnRegister").removeClass("btn-primary");
        }
    } else if (type.id == "btnRegister") {
        if (card.hasClass('hover') == false) {
            card.addClass('hover');
            $("#btnRegister").addClass("btn-primary");
            $("#btnLogin").removeClass("btn-primary");
        }
    } else {
        console.log("ERROR: Tipo inválido!");
    }
}