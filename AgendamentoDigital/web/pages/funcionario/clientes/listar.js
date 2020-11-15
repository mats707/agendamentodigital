$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    var tabCliente = document.getElementById('tabCliente');
    for (var i = 1; i < tabCliente.rows.length; i++)
    {
        tabCliente.rows[i].onclick = function ()
        {
            var email = this.cells[2].innerHTML;
            var celular = this.cells[3].innerHTML;
            carregarCliente(email, celular);
        };
    }

// Funcao para carregar os dados da consulta nos respectivos campos
    function carregarCliente(email, celular) {
        if (email !== "" && email.length >= 2 && celular !== "" && celular.length >= 2) {
            $.ajax({
                url: nameproject + '/api/Cliente/Buscar/' + email + '/' + celular, //lugar onde a servlet está
                type: "GET",
                complete: function (e, xhr, result) {
                    if (e.readyState == 4 && e.status == 200) {
                        console.log(e.responseText);
                        try { //Converte a resposta HTTP JSON em um objeto JavaScript
                            var Obj = eval("(" + e.responseText + ")");
                        } catch (err) { //
                            // Mostra Aviso
                            alert("Algo de errado aconteceu!");
                            alert(err);
                        }
                        if (Obj != null) {
                            $("#idCliente").val(Obj.idCliente);
                            $("#inputName").val(Obj.nomePessoa);
                            $("#inputDataNasc").val(moment(Obj.dataNascimento, 'MMM D, YYYY').format('YYYY-MM-DD')); //Format to Type Date
                            $("#inputCelular").val(Obj.usuario.celular);
                            $("#inputEmail").val(Obj.usuario.email);
                        }
                    }
                }
            });
        }
    }
});
function lerJson(result) {
    $("#target").html('<table id="tabCliente" class="table table-bordered"><thead><tr><th style="width: auto">ID</th><th style="width: 100%">Email</th><th style="width: auto">Celular</th><th style="width: auto">Perfil</th><th style="width: auto">Edit</th><th style="width: auto">Delete</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</td><td><a href="" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a></td><td><a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a></td></tr>'
    }).join('\n') + '</table>');
}

function lerTabela(result) {
    ObjCliente = result;
    console.log(ObjCliente);
    var page = window.location.pathname.split(/\/(.+)/)[1].split(/\/(.+)/)[1];
    var nameproject = "/" + window.location.pathname.split('/')[1] + "/";
    document.getElementById("target").innerHTML = '<table id="tabCliente" name="tabCliente" class="table table-hover"><thead class="thead-dark">\n\
        <tr><th>Nome</th>\n\
            <th>Data de Nascimento</th>\n\
            <th>E-Mail</th>\n\
            <th>Celular</th>\n\
            <th>Situação</th>\n\
            <th>Alterar</th>\n\
            <th>Ativar</th>\n\
            <th>Desativar</th>\n\
        </tr></thead></table>';
    var table = document.getElementById("tabCliente");
    if (ObjCliente != null) {
        if (ObjCliente.length > 0) {
            for (var i = 0; i < ObjCliente.length; i++) {

                var row = table.insertRow(-1); //Insere no ultimo registro
                var cell0 = row.insertCell(0); //Nome
                var cell1 = row.insertCell(1); //Data
                var cell2 = row.insertCell(2); //Email
                var cell3 = row.insertCell(3); //Celular
                var cell4 = row.insertCell(4); //Situação
                var cell5 = row.insertCell(5); //Alterar
                var cell6 = row.insertCell(6); //Ativar
                var cell7 = row.insertCell(7); //Desativar
                cell0.innerHTML = ObjCliente[i].nomePessoa;
                cell1.innerHTML = moment(ObjCliente[i].dataNascimento, 'MMM D, YYYY').format('DD/MM/YYYY');
                cell2.innerHTML = ObjCliente[i].usuario.email;
                cell3.innerHTML = ObjCliente[i].usuario.celular;
                cell4.innerHTML = (ObjCliente[i].usuario.ativo) ? "Ativado" : "Desativado";
                cell5.innerHTML = "<a id='alterarCliente'  href='#' class='nav-link' data-toggle='modal' data-target='#alterarModal' ><i class='nav-icon fas fa-edit'></i></a>";
                cell6.innerHTML = "<form id='formAtivar-" + i + "' action='" + nameproject + "Funcionario/Cliente/Ativar' method='POST'>\n\
                            <input type='hidden' name='cliente' value='" + JSON.stringify(ObjCliente[i]) + "'>\n\
                            <a id='btn-formAtivar-" + i + "' href='#' class='nav-link' onclick='sweetAtivar(this);'>\n\
                            <i class='nav-icon fas fa-check-circle'></a></form>";
                cell7.innerHTML = "<form id='formDesativar-" + i + "' action='" + nameproject + "Funcionario/Cliente/Desativar' method='POST'>\n\
                            <input type='hidden' name='cliente' value='" + JSON.stringify(ObjCliente[i]) + "'>\n\
                            <a id='btn-formDesativar-" + i + "' href='#' class='nav-link' onclick='sweetDesativar(this);'>\n\
                            <i class='nav-icon fas fa-times-circle'></a></form>";
            }
            if (page == "Funcionario/Cliente/Listar") {
                sweet("  Clientes carregados", "success", 2000);
            }
        } else {
            if (page == "Funcionario/Cliente/Listar")
                sweet(" Não há clientes cadastrados", "info", 3000);
        }
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

function sweetDesativar(element) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: 'Tem certeza?',
        text: "A conta do cliente será desativada do sistema! Não será mais possível o cliente acessar o sistema!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, desativar!',
        cancelButtonText: 'Não, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire({
                title: 'Solicitação em andamento...',
                text: 'Aguarde ( 0-2 min )',
                type: 'info',
                showConfirmButton: false
            });
            submit(element);
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Seu cliente está seguro :)',
                    'error'
                    );
        }
    });
}

function sweetAtivar(element) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: 'Tem certeza?',
        text: "A conta do cliente será ativada no sistema! O cliente passará a ter acesso ao sistema!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, ativar!',
        cancelButtonText: 'Não, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire({
                title: 'Solicitação em andamento...',
                text: 'Aguarde ( 0-2 min )',
                type: 'info',
                showConfirmButton: false
            });
            submit(element);
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Seu cliente continua desativado :)',
                    'error'
                    );
        }
    });
}

function submit(element) {
    var form = element.id.substr(4);
    console.log(form);
    document.getElementById(form).submit();
}

function formAlterarSenha() {
    var checkboxLaterarSenha = document.getElementById("chkAlterarSenha");
    var divAlterarSenha = document.getElementById("divAlterarSenha");
    if (checkboxLaterarSenha.checked) {
        divAlterarSenha.style.display = "block";
        $("#inputSenha").attr("readonly", false);
        $("#inputChkSenha").attr("readonly", false);
    } else {
        divAlterarSenha.style.display = "none";
        $("#inputSenha").attr("readonly", true);
        $("#inputChkSenha").attr("readonly", true);
    }
}