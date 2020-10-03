$(document).ready(function () {

    $('.mask-email').mask("A", {
        translation: {
            "A": {pattern: /[\w@\-.+]/, recursive: true}
        }
    });

});

$.fn.selectRange = function (start, end) {
    return this.each(function () {
        if (this.setSelectionRange) {
            this.focus();
            this.setSelectionRange(start, end);
        } else if (this.createTextRange) {
            var range = this.createTextRange();
            range.collapse(true);
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    });
};

$("#inputEmail").on("click", function () {
    var inputEmail = document.getElementById("inputEmail");
    $('input[name=inputEmail]').selectRange(0, inputEmail.value.length);
});

$(document).on('focusin', '.mask', function () {
    var $this = $(this);
    var mask = $this.data('mask');
    makeMask(mask, $this);
});

var makeMask = function (mask, $this) {
    if (mask === 'telefone') {
        $this.inputmask({"mask": "+55 (99) [9] 9999-9999"});
    }
    if (mask === 'email') {
        $this.inputmask({"alias": "email"});
    }
}

$("#inputPassword").on("focusout", function () {
    if ($(this).val() != $("#inputChkPassword").val()) {
        $("#inputChkPassword").removeClass("valid").addClass("invalid");
    } else {
        $("#inputChkPassword").removeClass("invalid").addClass("valid");
    }
});
$("#inputChkPassword").on("keyup", function () {
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
    if (type.getAttribute('data-action') == "Login") {
        if (card.hasClass('hover') == true) {
            card.removeClass('hover');
            $("#btnLogin").addClass("btn-primary");
            $("#btnRegister").removeClass("btn-primary");
        }
    } else if (type.getAttribute('data-action') == "Register") {
        if (card.hasClass('hover') == false) {
            card.addClass('hover');
            $("#btnRegister").addClass("btn-primary");
            $("#btnLogin").removeClass("btn-primary");
        }
    }
}