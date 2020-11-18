$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    var tabUser = document.getElementById('tabUser');

    for (var i = 1; i < tabUser.rows.length; i++)
    {
        tabUser.rows[i].onclick = function ()
        {
            var email = this.cells[0].innerHTML;
            carregarUsuario(email);
        };
    }

    // Funcao para carregar os dados da consulta nos respectivos campos
    function carregarUsuario(email) {
        if (email !== "" && email.length >= 2) {
            $.ajax({
                url: nameproject + '/api/Usuario/BuscarEmail/' + email, //lugar onde a servlet está
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
                            $("#idUsuario").val(Obj.idUsuario);
                            $("#editedEmail").val(Obj.email);
                            $("#editedCelular").val(Obj.celular);
                            $("#idUsuarioDeleted").val(Obj.idUsuario);
                            $("#deletedEmail").val(Obj.email);
                            $("#deletedCelular").val(Obj.celular);
                            $('#editedPerfil1').attr('checked', false); // or 'checked'
                            $('#editedPerfil2').attr('checked', false); // or 'checked'
                            $('#editedPerfil3').attr('checked', false); // or 'checked'
                            if ("FUNCIONARIOADMIN" == Obj.perfil) {
                                $('#editedPerfil1').attr('checked', true); // or 'checked'
                                $('#editedPerfil2').attr('checked', false); // or 'checked'
                                $('#editedPerfil3').attr('checked', false); // or 'checked'
                                $("#deletedPerfil").val('Administrador');
                                console.log("Administrador");
                            } else if ("FUNCIONARIOCOMUM" == Obj.perfil) {
                                $('#editedPerfil1').attr('checked', false); // or 'checked'
                                $('#editedPerfil2').attr('checked', true); // or 'checked'
                                $('#editedPerfil3').attr('checked', false); // or 'checked'
                                $("#deletedPerfil").val('Funcionario');
                                console.log("Funcionario");
                            } else {
                                $('#editedPerfil1').attr('checked', false); // or 'checked'
                                $('#editedPerfil2').attr('checked', false); // or 'checked'
                                $('#editedPerfil3').attr('checked', true); // or 'checked'
                                $("#deletedPerfil").val('Cliente');
                                console.log("Cliente");
                            }
                        }
                    }
                }
            });
        }
    }
});

function sweet(title, type, timer) {
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: timer
    });
    Toast.fire({
        type: type,
        title: ' ' + title
    });
}

function lerTabela(result) {
    Obj = result;
    console.log(Obj);
    var page = window.location.pathname.split(/\/(.+)/)[1].split(/\/(.+)/)[1];
    var nameproject = "/" + window.location.pathname.split('/')[1] + "/";
    document.getElementById("target").innerHTML = '<table id="tabUser" name="tabUser" class="table table-hover"><thead class="thead-dark">\n\
        <tr><th style="width: 100%">E-Mail</th>\n\
            <th style="width: auto">Celular</th>\n\
            <th style="width: auto">Perfil</th>\n\
            <th style="width: auto">Situação</th>\n\
            <th style="width: auto">Alterar</th>\n\
            <th style="width: auto">Desativar</th>\n\
        </tr></thead></table>';
    var table = document.getElementById("tabUser");
    if (Obj != null) {
        if (Obj.length > 0) {
            for (var i = 0; i < Obj.length; i++) {

                var row = table.insertRow(-1); //Insere no ultimo registro
                var cell0 = row.insertCell(0); //Email
                var cell1 = row.insertCell(1); //Celular
                var cell2 = row.insertCell(2); //Perfil
                var cell3 = row.insertCell(3); //Situação
                var cell4 = row.insertCell(4); //Alterar
                var cell5 = row.insertCell(5); //Desativar
                cell0.innerHTML = Obj[i].email;
                cell1.innerHTML = Obj[i].celular;
                cell2.innerHTML = Obj[i].perfil;
                cell3.innerHTML = (Obj[i].ativo) ? "Ativado" : "Desativado";
                cell4.innerHTML = "<a id='alterarUsuario'  href='#' class='nav-link' data-toggle='modal' data-target='#editModal' ><i class='nav-icon fas fa-edit'></i></a>";
                cell5.innerHTML = "<a href='#' id='deleteuser' class='nav-link' data-toggle='modal' data-target='#deleteModal' ><i class='nav-icon fas fa-trash-alt'></i></a>";
            }
            if (page == "Usuario/Funcionario/Listar") {
                sweet("  Funcionários carregados", "success", 2000);
            }
        } else {
            if (page == "Usuario/Funcionario/Listar")
                sweet(" Não há funcionários cadastrados", "info", 3000);
        }
    }

}
