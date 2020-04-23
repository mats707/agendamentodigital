$(document).ready(function () {

    var textarea = document.querySelector('textarea');

    textarea.addEventListener('keydown', autosize);

    function autosize() {
        var el = this;
        setTimeout(function () {
            el.style.cssText = 'height:auto; padding:0';
            // for box-sizing other than "content-box" use:
            // el.style.cssText = '-moz-box-sizing:content-box';
            el.style.cssText = 'height:' + el.scrollHeight + 'px';
        }, 0);
    }

    var nameproject = "/AgendamentoDigital";

    var tabUser = document.getElementById('tabUser');

    for (var i = 1; i < tabUser.rows.length; i++)
    {
        tabUser.rows[i].onclick = function ()
        {
            var nome = this.cells[0].innerHTML;
            carregarServico(nome);
        };
    }


    // Função para carregar os dados da consulta nos respectivos campos
    function carregarServico(nome) {
        if (nome !== "" && nome.length >= 2) {
            $.ajax({
                url: nameproject + '/services/Servico/BuscarNome/' + nome, //lugar onde a servlet está
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
                            $("#idServico").val(Obj.idServico);
                            $("#editedNome").val(Obj.nome);
                            $("#editedDescricao").val(Obj.descricao);
                            $("#editedValor").val(Obj.valor);
                            $("#idServicoDeleted").val(Obj.idServico);
                            $("#deletedNome").val(Obj.nome);
                            $("#deletedDescricao").val(Obj.descricao);
                            $("#deletedValor").val(Obj.valor);
                        }
                    }
                }
            });
        }
    }
});

function lerJson(result) {
    $("#target").html('<table id="tabUser" class="table table-bordered"><thead><tr><th style="width: 100%">Nome</th><th style="width: 100%">Descricao</th><th style="width: 100%">Valor</th><th style="width: 100%">Edit</th><th style="width: 100%">Delete</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</td><td><a href="#" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a></td><td><a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a></td></tr>'
    }).join('\n') + '</table>');
}