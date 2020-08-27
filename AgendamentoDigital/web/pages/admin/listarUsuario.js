$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    var tabUser = document.getElementById('tabUser');

    for (var i = 1; i < tabUser.rows.length; i++)
    {
        tabUser.rows[i].onclick = function ()
        {
            var email = this.cells[1].innerHTML;
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

                            if ("FUNCIONARIOADMIN" == Obj.perfil) {
                                document.getElementById("editedPerfil1_lbl").classList.remove("active");
                                document.getElementById("editedPerfil2_lbl").classList.add("active");
                                $('#editedPerfil1').attr('checked', true); // or 'checked'
                                $('#editedPerfil2').attr('checked', false); // or 'checked'
                                $("#deletedPerfil").val('Administrador');
                                console.log("Administrador");
                            } else {
                                document.getElementById("editedPerfil1_lbl").classList.add("active");
                                document.getElementById("editedPerfil2_lbl").classList.remove("active");
                                $('#editedPerfil1').attr('checked', false); // or 'checked'
                                $('#editedPerfil2').attr('checked', true); // or 'checked'
                                $("#deletedPerfil").val('Comum');
                                console.log("Funcionario");
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

function lerJson(result) {
    $("#target").html('<table id="tabUser" class="table table-bordered"><thead><tr><th style="width: auto">ID</th><th style="width: 100%">Email</th><th style="width: auto">Celular</th><th style="width: auto">Perfil</th><th style="width: auto">Edit</th><th style="width: auto">Delete</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</td><td><a href="#" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a></td><td><a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a></td></tr>'
    }).join('\n') + '</table>');
}
