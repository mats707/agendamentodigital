var nameproject = getContextPath();

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
            console.log("Invï¿½lido");
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
        text: "Seu usuario será desativado e desconectado! Para reativar será necessário entrar em contato com a empresa!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, desativar!',
        cancelButtonText: 'Não, voltar...',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire(
                    'Solicitação concluída!',
                    'Desconectando...',
                    'success'
                    );
            submit();
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Sua conta está segura :)',
                    'info'
                    );
        }
    });
}

function submit() {    
    document.getElementById("formDesativar").submit();
}