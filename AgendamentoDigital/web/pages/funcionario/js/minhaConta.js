var nameproject = "/" + window.location.pathname.split('/')[1] + "/";
$(document).ready(function () {
    var alturaFormulario = document.getElementById("panel").offsetHeight;
    document.getElementById("colImg").style.height = alturaFormulario + "px";
});


var btnCust = '';
//var btnCust = '<button type="button" class="btn btn-secondary" title="Add picture tags" ' +
//        'onclick="alert(\'Call your custom code here.\')">' +
//        '<i class="fas fa-tags"></i>' +
//        '</button>';

function editarDados(element) {
    console.log(element);
    var itemId = element.id;
    console.log(itemId);

    switch (itemId) {
        case "itemNome":
            if (document.getElementById("inputName").readOnly === true) {
                document.getElementById("inputName").readOnly = false;
                document.getElementById("groupNome").style.display = 'flex';
                document.getElementById(itemId).classList.add("active");
            } else {
                document.getElementById("inputName").readOnly = true;
                document.getElementById("groupNome").style.display = 'none';
                document.getElementById(itemId).classList.remove("active");
            }
            break;
        case "itemDataNascimento":
            if (document.getElementById("inputDataNasc").readOnly === true) {
                document.getElementById("inputDataNasc").readOnly = false;
                document.getElementById("groupDataNascimento").style.display = 'flex';
                document.getElementById(itemId).classList.add("active");
            } else {
                document.getElementById("inputDataNasc").readOnly = true;
                document.getElementById("groupDataNascimento").style.display = 'none';
                document.getElementById(itemId).classList.remove("active");
            }
            break;
        case "itemCelular":
            if (document.getElementById("inputCelular").readOnly === true) {
                document.getElementById("inputCelular").readOnly = false;
                document.getElementById("groupCelular").style.display = 'flex';
                document.getElementById(itemId).classList.add("active");
            } else {
                document.getElementById("inputCelular").readOnly = true;
                document.getElementById("groupCelular").style.display = 'none';
                document.getElementById(itemId).classList.remove("active");
            }
            break;
        default:
            console.log("Inválido");
    }

    if (document.getElementById("inputName").readOnly === true &&
            document.getElementById("inputDataNasc").readOnly === true &&
            document.getElementById("inputCelular").readOnly === true) {
        document.getElementById("btnAlterarDados").style.display = 'none';
    } else {
        document.getElementById("btnAlterarDados").style.display = 'block';
    }
}
function sweet(title, type, timer) {
    if (title == "") {
        title = "Carregando...";
    }
    if (type == "") {
        type = "info";
    }
    if (timer == null) {
        timer = 1500;
    }
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: timer
    });
    Toast.fire({
        type: type,
        title: '  ' + title
    });
}