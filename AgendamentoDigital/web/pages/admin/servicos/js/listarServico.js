$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    var tabServico = document.getElementById('tabServico');

    for (var i = 1; i < tabServico.rows.length; i++)
    {
        tabServico.rows[i].onclick = function ()
        {
            carregarServico(this);
        };
    }

    // Função para carregar os dados da consulta nos respectivos campos
    function carregarServico(element) {
        var id = element.cells[0].innerHTML;
        var categorias = element.cells[5].innerHTML;
        if (id !== "") {
            $.ajax({
                url: nameproject + '/api/Servico/Buscar/' + id, //lugar onde a servlet está
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
                            $("#idServicoDeleted").val(Obj.idServico);
                            $("#deletedNome").val(Obj.nome);
                            $("#deletedDescricao").val(Obj.descricao);
                            //Salva a categoria que sera inserida
                            var inputCategoria = document.createElement('input');
                            inputCategoria.id = 'categoriaFinal';
                            inputCategoria.type = 'hidden';
                            inputCategoria.name = inputCategoria.id;
                            inputCategoria.value = Obj.categoria.idCategoriaServico;
                            document.getElementById('frmCadastrarServico').appendChild(inputCategoria);
                            document.getElementById("spanListaCategorias").innerHTML = "<a>" + categorias + "</a>";
                            document.getElementById("listaCategorias-0").style.display = 'none';
                            var btnVoltar = document.getElementById('btnVoltarCategoria');
                            if (btnVoltar == null) {
                                $("#groupListaCategorias").append('<span class="input-group-btn">\n\
                                    <button id="btnVoltarCategoria" class="btn btn-default" type="button" onclick="voltarCategoria();">\n\
                                    Voltar um n\u00EDvel da categoria\n\
                                    </button>\n\
                                    </span>');
                            } else {
                                document.getElementById("btnVoltarCategoria").style.display = 'block';
                            }
                            $("#editedValor").val(Obj.valor.toFixed(2));
                            $("#editedDuracao").val(Obj.duracao.seconds / 60);
                            var values = [];
                            for (var i = 0; i < Obj.funcionarios.length; i++) {
                                values.push(Obj.funcionarios[i].idFuncionario);
                            }
                            values = values.toString();
                            $.each(values.split(","), function (i, e) {
                                $("#listaFuncionarios option[value='" + e + "']").prop("selected", true);
                            });
                        }
                    }
                }
            });
        }
    }
});

function lerTabela(result) {
    ObjServico = result;
    console.log(result);
    console.log(ObjServico[0].funcionarios[0].idFuncionario);
    document.getElementById("target").innerHTML = '<table id="tabServico" class="table table-hover"><thead class="thead-dark"><tr><th>ID</th><th>Servi\xE7o</th><th>Descricao</th><th>valor</th><th>Duracao</th><th>Categoria</th><th>Funcionarios</th><th>Edit</th><th>Delete</th></tr></thead></table>';
    var table = document.getElementById("tabServico");
    if (ObjServico != null) {
        if (ObjServico.length > 0) {
            //sweet();
            for (var i = 0; i < ObjServico.length; i++) {
                var categorias = [];
                var row = table.insertRow(-1); //Insere no ultimo registro
                var cellId = row.insertCell(0);//Id Servico
                var cellServico = row.insertCell(1);//Nome Servico
                var cellDescricao = row.insertCell(2);//Descricao Servico
                var cellValor = row.insertCell(3);//Valor
                var cellDuracao = row.insertCell(4);//Duracao
                var cellCategoria = row.insertCell(5);//Categoria
                var cellFuncionarios = row.insertCell(6);//Funcionarios
                var cellEdit = row.insertCell(7);//Funcionarios
                var cellDelete = row.insertCell(8);//Funcionarios
                cellId.innerHTML = ObjServico[i].idServico;
                cellServico.innerHTML = ObjServico[i].nome;
                cellDescricao.innerHTML = ObjServico[i].descricao;
                cellValor.innerHTML = "R$ " + ObjServico[i].valor.toFixed(2);
                cellDuracao.innerHTML = (ObjServico[i].duracao.seconds / 60) + " minutos";
                cellEdit.innerHTML = '<a href="#" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a>';
                cellDelete.innerHTML = '<a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a>';

                categorias.push(ObjServico[i].categoria.nome);
                var test = 0;
                var categoria = ObjServico[i].categoria.categoriaPai;
                console.log(categorias);
                while (test == 0) {
                    if (categoria.nome != null) {
                        categorias.push(categoria.nome);
                        categoria = categoria.categoriaPai;
                    } else {
                        test = 1;
                    }
                }
                for (var countCategoria = categorias.length - 1; countCategoria >= 0; countCategoria--) {
                    cellCategoria.innerHTML += categorias[countCategoria];
                    if (countCategoria != 0) {
                        cellCategoria.innerHTML += ' > ';
                    }
                }
                console.log(ObjServico[i].funcionarios);
                var listaFuncionarios = ObjServico[i].funcionarios;
                console.log(listaFuncionarios);
                console.log(listaFuncionarios.length);
                console.log(listaFuncionarios[0].idFuncionario);
                cellFuncionarios.innerHTML += "<select id='listaFuncionarios-" + i + "' multiple class='form-control' disabled></select>";
                for (var countFuncionario = 0; countFuncionario < listaFuncionarios.length; countFuncionario++) {
                    $("#listaFuncionarios-" + i).append("<option value='" + listaFuncionarios[countFuncionario].idFuncionario + "'>" + listaFuncionarios[countFuncionario].nomePessoa + "</option>");
                }
            }
        } else {
            document.getElementById("target").innerHTML = '<h1>Não há agendamentos</h1>';
        }
    }
}

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
    $("#target").html('<table id="tabServico" class="table table-hover"><thead class="thead-dark"><tr><th>ID</th><th>Servi\xE7o</th><th>Descricao</th><th>valor</th><th>Duracao</th><th>Categoria</th><th>Funcionarios</th><th>Edit</th><th>Delete</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</td><td><a href="#" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a></td><td><a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a></td></tr>'
    }).join('\n') + '</table>');
}
