var nameproject = getContextPath();
$(document).ready(function () {
    var alturaFormulario = document.getElementById("panel").offsetHeight;
    document.getElementById("colImg").style.height = alturaFormulario + "px";
});


var btnCust = '';
//var btnCust = '<button type="button" class="btn btn-secondary" title="Add picture tags" ' +
//        'onclick="alert(\'Call your custom code here.\')">' +
//        '<i class="fas fa-tags"></i>' +
//        '</button>';
$("#fotoPerfil").fileinput({
    overwriteInitial: true,
    maxFileSize: 1500,
    showClose: false,
    showCaption: false,
    showBrowse: false,
    browseOnZoneClick: true,
    removeLabel: '',
    removeIcon: '<i class="fas fa-trash-alt"></i>',
    removeTitle: 'Cancel or reset changes',
    elErrorContainer: '#kv-avatar-errors-2',
    msgErrorClass: 'alert alert-block alert-danger',
    defaultPreviewContent: '<img src="' + nameproject + '/images/user_sample/profile-picture.jpg" alt="Your Avatar" style="width: 150px"><h6 class="text-muted">Click to select</h6>',
    layoutTemplates: {main2: '{preview} ' + btnCust + ' {remove} {browse}'},
    allowedFileExtensions: ["jpg", "png", "gif"]
});

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
            console.log("Inv�lido");
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

function sweetDesativar() {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
        title: 'Tem certeza?',
        text: "Seu usuario ser� desativado, podendo ser reativado depois!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, cancelar!',
        cancelButtonText: 'Não, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire(
                    'Solicita��o concluída!',
                    'Clique em OK para prosseguir.',
                    'success'
                    );
            submit();
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Seu bloqueio est� seguro :)',
                    'error'
                    );
        }
    });
}

function submit() {    
    document.getElementById("formDesativar").submit();
}