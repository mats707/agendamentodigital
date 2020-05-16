$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    var listaCategorias = '';
    var ObjListaCategorias;

    carregarCategoriaServico();
    lerJson();
    
    // Função para carregar os dados da consulta nos respectivos campos
    function carregarCategoriaServico() {
        $.ajax({
            url: nameproject + '/api/CategoriaServico/Listar/', //lugar onde a servlet está
            type: "GET",
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        ObjListaCategorias = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                        alert(err);
                    }
//                        if (Obj != null) {
//                            $("#idServico").val(Obj.idServico);
//                            $("#editedNome").val(Obj.nome);
//                            $("#editedDescricao").val(Obj.descricao);
//                            $("#editedValor").val(Obj.valor);
//                            $("#idServicoDeleted").val(Obj.idServico);
//                            $("#deletedNome").val(Obj.nome);
//                            $("#deletedDescricao").val(Obj.descricao);
//                            $("#deletedValor").val(Obj.valor);
//                        }
                    listaCategorias = e.responseText;
                }
            }
        });
    }

function lerJson() {
    $("#listaCategorias").html('\
        <div class="card card-primary">                                                                                                              \n\
            <div class="card-header">                                                                                                                \n\
                <h4 class="card-title">                                                                                                          \n\
                    <a id="categoria-1" data-toggle="collapse" data-parent="#listaCategorias" href="#collapseCategoria-1">                       \n\
                        Cabelo                                                                                                                   \n\
                    </a>                                                                                                                         \n\
                </h4>                                                                                                                            \n\
                <div class="card-tools">                                                                                                             \n\
                    <a class="btn btn-info btn-sm" href="#">                                                                                         \n\
                        <i class="fas fa-pencil-alt">                                                                                                \n\
                        </i>                                                                                                                         \n\
                        Edit                                                                                                                         \n\
                    </a>                                                                                                                             \n\
                    <a class="btn btn-danger btn-sm" href="#">                                                                                       \n\
                        <i class="fas fa-trash">                                                                                                     \n\
                        </i>                                                                                                                         \n\
                        Delete                                                                                                                       \n\
                    </a>                                                                                                                             \n\
                </div>                                                                                                                               \n\
            </div>                                                                                                                                   \n\
            <div id="collapseCategoria-1" class="panel-collapse collapse in">                                                                        \n\
                <div class="card-body">                                                                                                              \n\
                    <div class="justify-content-center">                                                                                             \n\
                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                                 \n\
                        <div class="card card-secondary">                                                                                              \n\
                            <div class="card-header">                                                                                                \n\
                                <h4 class="card-title">                                                                                              \n\
                                    <a id="categoria-1-1" data-toggle="collapse" data-parent="#collapseCategoria-1" href="#collapseCategoria-1-1">   \n\
                                        Masculino                                                                                                    \n\
                                    </a>                                                                                                             \n\
                                </h4>                                                                                                                \n\
                                <div class="card-tools">                                                                                             \n\
                                    <a class="btn btn-info btn-sm" href="#">                                                                         \n\
                                        <i class="fas fa-pencil-alt">                                                                                \n\
                                        </i>                                                                                                         \n\
                                        Edit                                                                                                         \n\
                                    </a>                                                                                                             \n\
                                    <a class="btn btn-danger btn-sm" href="#">                                                                       \n\
                                        <i class="fas fa-trash">                                                                                     \n\
                                        </i>                                                                                                         \n\
                                        Delete                                                                                                       \n\
                                    </a>                                                                                                             \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                            <div id="collapseCategoria-1-1" class="panel-collapse collapse in">                                                      \n\
                                <div class="card-body">                                                                                              \n\
                                    <div class="justify-content-center">                                                                             \n\
                                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                 \n\
                                        <div class="card card-light">                                                                              \n\
                                            <div class="card-header">                                                                                \n\
                                                <h4 class="card-title">                                                                              \n\
                                                    <a id="categoria-1-1-1" data-parent="#collapseCategoria-1-1">                                    \n\
                                                        Corte                                                                                        \n\
                                                    </a>                                                                                             \n\
                                                </h4>                                                                                                \n\
                                                <div class="card-tools">                                                                             \n\
                                                    <a class="btn btn-info btn-sm" href="#" style="color: white">                                                         \n\
                                                        <i class="fas fa-pencil-alt">                                                                \n\
                                                        </i>                                                                                         \n\
                                                        Edit                                                                                         \n\
                                                    </a>                                                                                             \n\
                                                    <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                       \n\
                                                        <i class="fas fa-trash">                                                                     \n\
                                                        </i>                                                                                         \n\
                                                        Delete                                                                                       \n\
                                                    </a>                                                                                             \n\
                                                </div>                                                                                               \n\
                                            </div>                                                                                                   \n\
                                        </div>                                                                                                       \n\
                                    </div>                                                                                                           \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                        </div>                                                                                                                       \n\
                        <div class="card card-secondary">                                                                                              \n\
                            <div class="card-header">                                                                                                \n\
                                <h4 class="card-title">                                                                                              \n\
                                    <a id="categoria-1-2" data-toggle="collapse" data-parent="#collapseCategoria-1" href="#collapseSubCategoria-1-2">\n\
                                        Feminino                                                                                                     \n\
                                    </a>                                                                                                             \n\
                                </h4>                                                                                                                \n\
                                <div class="card-tools">                                                                                             \n\
                                    <a class="btn btn-info btn-sm" href="#">                                                                         \n\
                                        <i class="fas fa-pencil-alt">                                                                                \n\
                                        </i>                                                                                                         \n\
                                        Edit                                                                                                         \n\
                                    </a>                                                                                                             \n\
                                    <a class="btn btn-danger btn-sm" href="#">                                                                       \n\
                                        <i class="fas fa-trash">                                                                                     \n\
                                        </i>                                                                                                         \n\
                                        Delete                                                                                                       \n\
                                    </a>                                                                                                             \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                            <div id="collapseSubCategoria-1-2" class="panel-collapse collapse in">                                                   \n\
                                <div class="card-body">                                                                                              \n\
                                    <div class="justify-content-center">                                                                             \n\
                                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                 \n\
                                        <div class="card card-light">                                                                              \n\
                                            <div class="card-header">                                                                                \n\
                                                <h4 class="card-title">                                                                              \n\
                                                    <a id="categoria-1-2-1" data-parent="#collapseSubCategoria-1-2">                                 \n\
                                                        Corte                                                                                        \n\
                                                    </a>                                                                                             \n\
                                                </h4>                                                                                                \n\
                                                <div class="card-tools">                                                                             \n\
                                                    <a class="btn btn-info btn-sm" href="#" style="color: white">                                                         \n\
                                                        <i class="fas fa-pencil-alt">                                                                \n\
                                                        </i>                                                                                         \n\
                                                        Edit                                                                                         \n\
                                                    </a>                                                                                             \n\
                                                    <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                       \n\
                                                        <i class="fas fa-trash">                                                                     \n\
                                                        </i>                                                                                         \n\
                                                        Delete                                                                                       \n\
                                                    </a>                                                                                             \n\
                                                </div>                                                                                               \n\
                                            </div>                                                                                                   \n\
                                        </div>                                                                                                       \n\
                                    </div>                                                                                                           \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                        </div>                                                                                                                       \n\
                    </div>                                                                                                                           \n\
                </div>                                                                                                                               \n\
            </div>                                                                                                                                   \n\
        </div>                                                                                                                                       \n\
        <div class="card card-primary">                                                                                                              \n\
            <div class="card-header">                                                                                                                \n\
                <h4 class="card-title">                                                                                                              \n\
                    <a data-toggle="collapse" data-parent="#listaCategorias" href="#categoriaBarba">                                                 \n\
                        Barba                                                                                                                        \n\
                    </a>                                                                                                                             \n\
                </h4>                                                                                                                                \n\
                <div class="card-tools">                                                                                                             \n\
                    <a class="btn btn-info btn-sm" href="#">                                                                                         \n\
                        <i class="fas fa-pencil-alt">                                                                                                \n\
                        </i>                                                                                                                         \n\
                        Edit                                                                                                                         \n\
                    </a>                                                                                                                             \n\
                    <a class="btn btn-danger btn-sm" href="#">                                                                                       \n\
                        <i class="fas fa-trash">                                                                                                     \n\
                        </i>                                                                                                                         \n\
                        Delete                                                                                                                       \n\
                    </a>                                                                                                                             \n\
                </div>                                                                                                                               \n\
            </div>                                                                                                                                   \n\
            <div id="categoriaBarba" class="panel-collapse collapse in">                                                                             \n\
                <div class="card-body">                                                                                                              \n\
                    <div class="justify-content-center">                                                                                             \n\
                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                                 \n\
                        <div class="card card-secondary">                                                                                              \n\
                            <div class="card-header">                                                                                                \n\
                                <h4 class="card-title">                                                                                              \n\
                                    <a data-toggle="collapse" data-parent="#categoriaBarba" href="#subBarbaMasculino">                               \n\
                                        Masculino                                                                                                    \n\
                                    </a>                                                                                                             \n\
                                </h4>                                                                                                                \n\
                                <div class="card-tools">                                                                                             \n\
                                    <a class="btn btn-info btn-sm" href="#">                                                                         \n\
                                        <i class="fas fa-pencil-alt">                                                                                \n\
                                        </i>                                                                                                         \n\
                                        Edit                                                                                                         \n\
                                    </a>                                                                                                             \n\
                                    <a class="btn btn-danger btn-sm" href="#">                                                                       \n\
                                        <i class="fas fa-trash">                                                                                     \n\
                                        </i>                                                                                                         \n\
                                        Delete                                                                                                       \n\
                                    </a>                                                                                                             \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                            <div id="subBarbaMasculino" class="panel-collapse collapse in">                                                          \n\
                                <div class="card-body">                                                                                              \n\
                                    <div class="justify-content-center">                                                                             \n\
                                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                 \n\
                                        <div class="card card-light">                                                                              \n\
                                            <div class="card-header">                                                                                \n\
                                                <h4 class="card-title">                                                                              \n\
                                                    <a data-toggle="collapse" data-parent="#subcategoriaMasculino">                                  \n\
                                                        Corte                                                                                        \n\
                                                    </a>                                                                                             \n\
                                                </h4>                                                                                                \n\
                                                <div class="card-tools">                                                                             \n\
                                                    <a class="btn btn-info btn-sm" href="#" style="color: white">                                                         \n\
                                                        <i class="fas fa-pencil-alt">                                                                \n\
                                                        </i>                                                                                         \n\
                                                        Edit                                                                                         \n\
                                                    </a>                                                                                             \n\
                                                    <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                       \n\
                                                        <i class="fas fa-trash">                                                                     \n\
                                                        </i>                                                                                         \n\
                                                        Delete                                                                                       \n\
                                                    </a>                                                                                             \n\
                                                </div>                                                                                               \n\
                                            </div>                                                                                                   \n\
                                        </div>                                                                                                       \n\
                                    </div>                                                                                                           \n\
                                </div>                                                                                                               \n\
                                <div class="card-body">                                                                                              \n\
                                    <div class="justify-content-center">                                                                             \n\
                                        <!-- we are adding the .class so bootstrap.js collapse plugin detects it -->                                 \n\
                                        <div class="card card-light">                                                                              \n\
                                            <div class="card-header">                                                                                \n\
                                                <h4 class="card-title">                                                                              \n\
                                                    <a data-toggle="collapse" data-parent="#subcategoriaMasculino">                                  \n\
                                                        Pintura                                                                                      \n\
                                                    </a>                                                                                             \n\
                                                </h4>                                                                                                \n\
                                                <div class="card-tools">                                                                             \n\
                                                    <a class="btn btn-info btn-sm" href="#" style="color: white">                                                         \n\
                                                        <i class="fas fa-pencil-alt">                                                                \n\
                                                        </i>                                                                                         \n\
                                                        Edit                                                                                         \n\
                                                    </a>                                                                                             \n\
                                                    <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                       \n\
                                                        <i class="fas fa-trash">                                                                     \n\
                                                        </i>                                                                                         \n\
                                                        Delete                                                                                       \n\
                                                    </a>                                                                                             \n\
                                                </div>                                                                                               \n\
                                            </div>                                                                                                   \n\
                                        </div>                                                                                                       \n\
                                    </div>                                                                                                           \n\
                                </div>                                                                                                               \n\
                            </div>                                                                                                                   \n\
                        </div>                                                                                                                       \n\
                    </div>                                                                                                                           \n\
                </div>                                                                                                                               \n\
            </div>                                                                                                                                   \n\
        </div>                                                                                                                                       \n\
');
}

    function lerJson() {
        console.log(ObjListaCategorias);
        console.log("############");
        console.log(listaCategorias);
//    $("#target").html('<table id="tabUser"\n\
//class="table table-bordered"><thead><tr><th\n\
//style="width: 100%">Email</th>\n\
//<th style="width: 100%">Celular</th>\n\
//<th style="width: 100%">Perfil</th>\n\
//<th style="width: 100%">Edit</th>\n\
//<th style="width: 100%">Delete</th>\n\
//</tr></thead>' + $.map(result, function (d) {
//        return '<tr><td>' + $.map(d, function (e) {
//            return e;
//        }).join('</td><td>') + '</td>\n\
//    <td><a href="#" id="edituser" class="nav-link" data-toggle="modal" data-target="#editModal" ><i class="nav-icon fas fa-edit"></i></a></td><td><a href="#" id="deleteuser" class="nav-link" data-toggle="modal" data-target="#deleteModal" ><i class="nav-icon fas fa-trash-alt"></i></a></td></tr>'
//    }).join('\n') + '</table>');
    }

});