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

function sweet(title, type, timer) {
    console.log(title);
    console.log(type);
    console.log(timer);
    if (title == "") {
        title = "Carregando...";
    }
    if (type == "") {
        type = "info";
    }
    if (timer == null) {
        timer = 500;
    }

    if (type == "success") {
        Swal.fire({
            position: 'center',
            showConfirmButton: true,
            title: "Sucesso",
            type: type,
            text: title
        });
    } else {
        Swal.fire({
            position: 'center',
            showConfirmButton: false,
            type: type,
            title: title,
            timer: timer
        });
    }
}

//function sweet(title, type, timer) {
//    const Toast = swal.mixin({
//        toast: true,
//        position: 'center',
//        showConfirmButton: false,
//        timer: timer
//    });
//    Toast.fire({
//        type: type,
//        title: ' ' + title
//    });
//    
//    if (title == "success") {
//        swal.fire({
//            timer: timer,
//            icon: type,
//            title: 'Sucesso!',
//            text: title
//        });
//    } else if (title != "") {
//        swal.fire({
//            icon: type,
//            title: 'Oops...',
//            text: title,
//            footer: '<a id="btn1RegisterLink" class="btn-sing-up-now" style="color: #007bff;text-decoration: none;cursor: pointer;" data-action="Register" onclick="rotateCard(this)">Deseja se cadastrar?</a>'
//        });
//    }
//}