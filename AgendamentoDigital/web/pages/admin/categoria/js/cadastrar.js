$(document).ready(function () {

    var categorias = [];
    var descricoes = [];

    var nameproject = "/AgendamentoDigital";
    var listaCategorias = '';
    var ObjListaCategorias;

    carregarCategoriaServico();

    window.onload = function () {

        (function () {
            document.getElementById('sectionCadastrarCategoria').style.display = 'block';
            document.getElementById('sectionListarCategoria').style.display = 'block';

            var counter = 0;
            var inputVal;
            var descVal;
            var addCategoria = function () {
                categorias.push(inputVal);
                descricoes.push(descVal);
                document.getElementById('lblCategorias').innerHTML = "";
                for (var i = 0; i < categorias.length; i++) {
                    categorias[i] = categorias[i].replace(/[^0-9a-zA-Z ]+/g, '');
                    console.log(categorias[i]);
                    console.log(descricoes[i]);
                    document.getElementById('lblCategorias').innerHTML += categorias[i] + " > ";
                }
            };
            var rmvCategoria = function () {
                categorias.pop();
                descricoes.pop()
                document.getElementById('lblCategorias').innerHTML = "";
                for (var i = 0; i < categorias.length; i++) {
                    categorias[i] = categorias[i].replace(/[^0-9a-zA-Z ]+/g, '');
                    descricoes[i] = descricoes[i].replace(/[^0-9a-zA-Z ]+/g, '');
                    console.log(categorias[i]);
                    console.log(descricoes[i]);
                    document.getElementById('lblCategorias').innerHTML += categorias[i] + " > ";
                }
            };
            var addInput = function () {
                console.log("Teste argumento da funcao: " + arguments[0]);
                if (counter > 0 && arguments[0] == 'False') {
                    console.log(counter);
                    var input = document.createElement('input');
                    input.id = 'input-' + counter;
                    console.log(input.id);
                    input.type = 'text';
                    input.name = input.id;
                    input.placeholder = 'Sub-categoria ' + counter + '...';
                    input.pattern = "[A-Za-z]{3,50}"
                    input.classList.add("form-control");
                    document.getElementById('divCategoria').appendChild(input);
                } else if (counter == 0 && arguments[0] == 'True') {
                    console.log(counter);
                    var input = document.createElement('input');
                    input.id = 'novaCategoria';
                    console.log(input.id);
                    input.type = 'text';
                    input.name = input.id;
                    input.placeholder = 'Digite uma categoria nova...';
                    input.pattern = "[A-Za-z]{3,50}";
                    input.classList.add("form-control");
                    document.getElementById('divCategoria').appendChild(input);
                }
                document.getElementById('descricaoCategoria').value = "";
            };
            var removeLastInput = function () {
                if (counter >= 0) {
                    var id = document.getElementById('input-' + counter);
                    document.getElementById('divCategoria').removeChild(id);
                    console.log(counter);
                    counter--;
                    if (counter > 0) {
                        addInput('False');
                    } else {
                        addInput('True');
                    }
                }
            };
            var changeDisplay = function () {
                var displayNova;
                var displayEscolher;
                var btnValue = document.getElementById('btnCategoria').value;
                var btnText = '';

                switch (btnValue) {
                    case 'Nova' :
                    {
                        displayNova = 'block';
                        displayEscolher = 'none';
                        btnText = 'Escolher uma categoria existente';
                        btnValue = 'Escolher';
                        break;
                    }
                    case 'Escolher' :
                    {
                        displayNova = 'none';
                        displayEscolher = 'block';
                        btnText = 'Adicionar nova categoria';
                        btnValue = 'Nova';
                        break;
                    }
                    default:
                    {
                        console.log("Erro");
                        console.log("displayNova: " + displayNova);
                        console.log("displayEscolher: " + displayEscolher);
                        break;
                    }
                }
                document.getElementById('divNovaCategoria').style.display = displayNova;
                document.getElementById('divEscolherCategoria').style.display = displayEscolher;
                document.getElementById('btnCategoria').innerHTML = btnText;
                document.getElementById('btnCategoria').value = btnValue;
            };
            var submitForm = function () {
                var categoria = document.getElementById('input-' + counter).value;
                if (categoria == '') {
//                    cadastrarCategorias();
                    var inputCategoria = document.createElement('input');
                    inputCategoria.id = 'categorias';
                    console.log(inputCategoria.id);
                    inputCategoria.type = 'hidden';
                    inputCategoria.name = inputCategoria.id;
                    inputCategoria.value = categorias.toString();
                    document.getElementById('formCategoria').appendChild(inputCategoria);
                    var inputDescricao = document.createElement('input');
                    inputDescricao.id = 'descricoes';
                    console.log(inputDescricao.id);
                    inputDescricao.type = 'hidden';
                    inputDescricao.name = inputDescricao.id;
                    inputDescricao.value = descricoes.toString();
                    document.getElementById('formCategoria').appendChild(inputDescricao);
                    document.getElementById("formCategoria").submit();
                } else {
                    alert('Adicione a categoria inserida ou limpe-a!');
                }
            };
            btnAddCategoria.addEventListener('click', function () {
                descVal = document.getElementById('descricaoCategoria').value;
                console.log(descVal);
                if (descVal == '') {
                    descVal = '...';
                }
                if (counter > 0) {
                    inputVal = document.getElementById('input-' + counter).value;
                    if (inputVal == '')
                        alert('Preencha a sub-categoria ' + counter + ' primeiro');
                    else {
                        var id = document.getElementById('input-' + counter);
                        document.getElementById('divCategoria').removeChild(id);
                        counter++;
                        addInput('False');
                        addCategoria();
                    }
                } else if (counter == 0) {
                    inputVal = document.getElementById('novaCategoria').value;
                    if (inputVal == '')
                        alert('Preencha a categoria principal primeiro!');
                    else {
                        var id = document.getElementById('novaCategoria');
                        document.getElementById('divCategoria').removeChild(id);
                        counter++;
                        addInput('False');
                        addCategoria();
                    }
                }
                console.log(categorias.toString());
                console.log(descricoes.toString());
            }.bind(this));
            btnRmvCategoria.addEventListener('click', function () {
                removeLastInput();
                rmvCategoria();
                console.log(categorias.toString());
                console.log(descricoes.toString());
            }.bind(this));
            btnCategoria.addEventListener('click', function () {
                changeDisplay();
            }.bind(this));
            btnCadastrarCategoria.addEventListener('click', function () {
                submitForm();
            }.bind(this));
        })();
    }

    function carregarCategoriaServico() {
        $.ajax({
            url: nameproject + '/api/CategoriaServico/Listar/', //lugar onde a servlet estÃ¡
            type: "GET",
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                        alert(err);
                    }
                    if (Obj != null) {
                        ObjListaCategorias = Obj;
                        listaCategorias = e.responseText;
                        listarCategorias();
                    }
                }
            }
        });
    }
    function listarCategorias() {
        console.log(ObjListaCategorias);
        console.log("############");
        console.log(listaCategorias);
        ObjListaCategorias.forEach(function (obj1) {

            var idCategoria = obj1.idCategoriaServico;
            var nome = obj1.nome;
            var descricao = obj1.descricao;
            var idCategoriaPai = obj1.categoriaPai.idCategoriaServico;

            var qtdFilhas = 0; //quantidade de filhas que a categoria possui

            if (idCategoriaPai == 0) { //verifica se é categoria pai (primeira)
                console.log("categoria pai (primeira)");
                console.log(idCategoria, nome);
                console.log("Possui filhas");
                ObjListaCategorias.forEach(function (obj2) {
                    if (obj2.categoriaPai.idCategoriaServico == idCategoria) { //verifica se tem categoria filha
                        qtdFilhas++;
                    }
                });
                if (qtdFilhas > 0) { //cria div da categoria pai que possui filhas
                    $("#listaCategorias").append('\
                    <div id="divCategoria-' + idCategoria + '" class="card card-primary">                                                                                         \n\
                        <div class="card-header">                                                                                                                                 \n\
                            <h4 class="card-title">                                                                                                                               \n\
                                <a id="categoria-' + idCategoria + '" data-toggle="collapse" data-parent="#listaCategorias" href="#collapseCategoria-' + idCategoria + '">        \n\
                                    ' + nome + '                                                                                                                                  \n\
                                </a>                                                                                                                                              \n\
                            </h4>                                                                                                                                                 \n\
                            <div class="card-tools">                                                                                                                              \n\
                                <a class="btn btn-info btn-sm" href="#">                                                                                                          \n\
                                    <i class="fas fa-pencil-alt">                                                                                                                 \n\
                                    </i>                                                                                                                                          \n\
                                    Edit                                                                                                                                          \n\
                                </a>                                                                                                                                              \n\
                                <a class="btn btn-danger btn-sm" href="#">                                                                                                        \n\
                                    <i class="fas fa-trash">                                                                                                                      \n\
                                    </i>                                                                                                                                          \n\
                                    Delete                                                                                                                                        \n\
                                </a>                                                                                                                                              \n\
                            </div>                                                                                                                                                \n\
                        </div>                                                                                                                                                  \n\
                        <div id="collapseCategoria-' + idCategoria + '" class="panel-collapse collapse in">                                                                           \n\
                            <div class="card-body">                                                                                                                                   \n\
                                <div id="listaCategorias-' + idCategoria + '" class="justify-content-center">                                                                         \n\
                                    <p class="mb-2"><b>Descri\u00E7\u00E3o:</b> ' + descricao + '</p>                                                                                  \n\
                                    <p class="mb-3"><b>Sub-Categorias:</b></p>                                                                                  \n\
                        </div>                                                                                                                                                         \n\
                    </div>                                                                                                                                                        \n\
                    ');
                } else {//cria div da categoria pai que não possui filhas
                    $("#listaCategorias").append('\
                    <div id="divCategoria-' + idCategoria + '" class="card card-light">                                                                                         \n\
                        <div class="card-header">                                                                                                                                 \n\
                            <h4 class="card-title">                                                                                                                               \n\
                                <a id="categoria-' + idCategoria + '" data-toggle="collapse" data-parent="#listaCategorias" href="#collapseCategoria-' + idCategoria + '">        \n\
                                    ' + nome + '                                                                                                                                  \n\
                                </a>                                                                                                                                              \n\
                            </h4>                                                                                                                                                 \n\
                            <div class="card-tools">                                                                                                                              \n\
                                <a class="btn btn-info btn-sm" href="#" style="color: white">                                                                                                          \n\
                                    <i class="fas fa-pencil-alt">                                                                                                                 \n\
                                    </i>                                                                                                                                          \n\
                                    Edit                                                                                                                                          \n\
                                </a>                                                                                                                                              \n\
                                <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                                                                        \n\
                                    <i class="fas fa-trash">                                                                                                                      \n\
                                    </i>                                                                                                                                          \n\
                                    Delete                                                                                                                                        \n\
                                </a>                                                                                                                                              \n\
                            </div>                                                                                                                                                \n\
                        </div>                                                                                                                                                  \n\
                        <div id="collapseCategoria-' + idCategoria + '" class="panel-collapse collapse in">                                                                           \n\
                            <div class="card-body">                                                                                                                                   \n\
                                <div id="listaCategorias-' + idCategoria + '" class="justify-content-center">                                                                         \n\
                                    <p class="mb-2"><b>Descri\u00E7\u00E3o:</b> ' + descricao + '</p>                                                                                  \n\
                        </div>                                                                                                                                                         \n\
                    </div>                                                                                                                                                        \n\
                    ');
                }
            }
            else { //somente as categorias filhas
                console.log("categoria filha");
                console.log(idCategoria, nome);
                console.log("Verifica se possui mais filhas");
                ObjListaCategorias.forEach(function (obj2) {
                    if (obj2.categoriaPai.idCategoriaServico == idCategoria) { //Verifica se possui mais filhas
                        qtdFilhas++;
                    }
                });
                if (qtdFilhas > 0) { //cria div da categoria filha que possui filhas. será criado abaixo de seu pai
                    $("#listaCategorias-" + idCategoriaPai).append('\
                    <div id="divCategoria-' + idCategoria + '" class="card card-secondary">                                                                                         \n\
                        <div class="card-header">                                                                                                                                 \n\
                            <h4 class="card-title">                                                                                                                               \n\
                                <a id="categoria-' + idCategoria + '" data-toggle="collapse" data-parent="#listaCategorias-' + idCategoriaPai + '" href="#collapseCategoria-' + idCategoria + '">        \n\
                                    ' + nome + '                                                                                                                                  \n\
                                </a>                                                                                                                                              \n\
                            </h4>                                                                                                                                                 \n\
                            <div class="card-tools">                                                                                                                              \n\
                                <a class="btn btn-info btn-sm" href="#">                                                                                                          \n\
                                    <i class="fas fa-pencil-alt">                                                                                                                 \n\
                                    </i>                                                                                                                                          \n\
                                    Edit                                                                                                                                          \n\
                                </a>                                                                                                                                              \n\
                                <a class="btn btn-danger btn-sm" href="#">                                                                                                        \n\
                                    <i class="fas fa-trash">                                                                                                                      \n\
                                    </i>                                                                                                                                          \n\
                                    Delete                                                                                                                                        \n\
                                </a>                                                                                                                                              \n\
                            </div>                                                                                                                                                \n\
                        </div>                                                                                                                                                  \n\
                        <div id="collapseCategoria-' + idCategoria + '" class="panel-collapse collapse in">                                                                           \n\
                            <div class="card-body">                                                                                                                                   \n\
                                <div id="listaCategorias-' + idCategoria + '" class="justify-content-center">                                                                         \n\
                                    <p class="mb-2"><b>Descri\u00E7\u00E3o:</b> ' + descricao + '</p>                                                                                  \n\
                                    <p class="mb-3"><b>Sub-Categorias:</b></p>                                                                                  \n\
                        </div>                                                                                                                                                        \n\
                    </div>                                                                                                                                                        \n\
                    ');
                } else {//cria div da categoria filha que não possui filhas. será criado abaixo de seu pai
                    $("#listaCategorias-" + idCategoriaPai).append('\
                    <div id="divCategoria-' + idCategoria + '" class="card card-light">                                                                                         \n\
                        <div class="card-header">                                                                                                                                 \n\
                            <h4 class="card-title">                                                                                                                               \n\
                                <a id="categoria-' + idCategoria + '" data-toggle="collapse" data-parent="#listaCategorias-' + idCategoriaPai + '" href="#collapseCategoria-' + idCategoria + '">        \n\
                                    ' + nome + '                                                                                                                                  \n\
                                </a>                                                                                                                                              \n\
                            </h4>                                                                                                                                                 \n\
                            <div class="card-tools">                                                                                                                              \n\
                                <a class="btn btn-info btn-sm" href="#" style="color: white">                                                                                                          \n\
                                    <i class="fas fa-pencil-alt">                                                                                                                 \n\
                                    </i>                                                                                                                                          \n\
                                    Edit                                                                                                                                          \n\
                                </a>                                                                                                                                              \n\
                                <a class="btn btn-danger btn-sm" href="#" style="color: white">                                                                                                        \n\
                                    <i class="fas fa-trash">                                                                                                                      \n\
                                    </i>                                                                                                                                          \n\
                                    Delete                                                                                                                                        \n\
                                </a>                                                                                                                                              \n\
                            </div>                                                                                                                                                \n\
                        </div>                                                                                                                                                  \n\
                        <div id="collapseCategoria-' + idCategoria + '" class="panel-collapse collapse in">                                                                           \n\
                            <div class="card-body">                                                                                                                                   \n\
                                <div id="listaCategorias-' + idCategoria + '" class="justify-content-center">                                                                         \n\
                                    <p class="mb-2"><b>Descri\u00E7\u00E3o:</b> ' + descricao + '</p>                                                                                  \n\
                        </div>                                                                                                                                                          \n\
                    </div>                                                                                                                                                        \n\
                    ');
                }
            }
        });
    }
});