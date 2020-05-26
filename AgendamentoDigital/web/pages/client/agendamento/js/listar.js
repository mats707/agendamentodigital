//Categorias
var listaCategorias = ['0'];
var listaCategoriasText = [];
var listaCategoriasCadastradas = [];
var idCategoriaPaiSelected = "0";
var ObjListaCategorias;
//Servicos
var listaServico = ['0'];
var listaServicoText = [];
var listaServicoCadastradas = [];
var idCategoriaPaiSelected = "0";
var ObjListaServico;
var nameproject = "/AgendamentoDigital";


$(document).ready(function () {

    carregarCategoriaServico();

    function carregarCategoriaServico() {
        $.ajax({
            url: nameproject + '/api/CategoriaServico/Listar/', //lugar onde a servlet est√°
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
                        for (var i = 0; i < Obj.length; i++) {
                            listaCategoriasCadastradas.push(Obj[i].categoriaPai.idCategoriaServico);
                        }
                        ObjListaCategorias = Obj;
                        idCategoriaPaiSelected = "0";
                        listarCategorias();
                    }
                }
            }
        });
    }

    function listarCategorias() {

        listaCategoriasCadastradas = Array.from(new Set(listaCategoriasCadastradas));
        console.log("listaCategoriasCadastradas = " + listaCategoriasCadastradas);
        $("#groupListaCategorias").append('<select id="listaCategorias-0" class="form-control select2 select2-danger" data-dropdown-css-class="select2-danger" onchange="exibeFilho(this);">\n\
            <option selected disabled>Selecione uma subcategoria</option></select>');
        //Categorias PAI
        ObjListaCategorias.forEach(function (obj) {
            var idCategoria = obj.idCategoriaServico;
            var nome = obj.nome;
            var descricao = obj.descricao;
            var idCategoriaPai = obj.categoriaPai.idCategoriaServico;
            if (idCategoriaPai == 0) { //verifica se È categoria pai (primeira)
                $("#listaCategorias-" + idCategoriaPai).append("<option value='" + idCategoria + "' data-categoriaPai='" + idCategoriaPai + "'>" + nome + "</option>");
            }
        });
        //Categoria FILHAS
        ObjListaCategorias.forEach(function (obj) {
            var idCategoria = obj.idCategoriaServico;
            var nome = obj.nome;
            var descricao = obj.descricao;
            var idCategoriaPai = obj.categoriaPai.idCategoriaServico;
            if (idCategoriaPai != 0) { //verifica se È categoria filha (secund·rias)
                var element = document.getElementById('listaCategorias-' + idCategoriaPai);
                if (element == null) {
                    $("#groupListaCategorias").append('<select id="listaCategorias-' + idCategoriaPai + '" class="form-control select2 select2-danger" data-dropdown-css-class="select2-danger" style="display: none;" onchange="exibeFilho(this);">\n\
                    <option selected disabled>Selecione uma subcategoria</option></select>');
                }
                $("#listaCategorias-" + idCategoriaPai).append("<option value='" + idCategoria + "' data-categoriaPai='" + idCategoriaPai + "'>" + nome + "</option>");
            }
        });
    }
});

function exibeFilho(element) {
    var displayBlock = "block";
    var btnVoltar = document.getElementById('btnVoltarCategoria');
    if (btnVoltar == null) {
        $("#groupListaCategorias").append('<span class="input-group-btn">\n\
        <button id="btnVoltarCategoria" class="btn btn-default" type="button" onclick="voltarCategoria();">\n\
        Voltar um n\u00EDvel da categoria\n\
        </button>\n\
        </span>');
    } else {
        document.getElementById("btnVoltarCategoria").style.display = displayBlock;
    }

    var idElement = element.id;
    var displayNone = 'none';
    var displayBlock = 'block';

    if (idElement === 0) {
        listaCategorias = ['0'];
        listaCategoriasText = [''];
    }
    idCategoriaPaiSelected = idElement;
    console.log(idCategoriaPaiSelected + " foi selecionado!");
    var idCategoriaSelected = $("#" + idCategoriaPaiSelected + " :selected").val();
    var idCategoriaSelectedText = $("#" + idCategoriaPaiSelected + " :selected").text();

    //Usar cadastrar categoria para realizar a listagem de categoria
    document.getElementById("spanListaCategorias").innerHTML += "<a id='categoria-" + idCategoriaSelected + "' data-categoriaPai='" + idCategoriaPaiSelected + "' onclick='exibeFilho(this);' href='#'>";
    document.getElementById("spanListaCategorias").innerHTML += idCategoriaSelectedText;
    document.getElementById("spanListaCategorias").innerHTML += "</a>";
    document.getElementById(idCategoriaPaiSelected).style.display = displayNone;

    var element = document.getElementById('listaCategorias-' + idCategoriaSelected);
    if (element != null) {
        document.getElementById("spanListaCategorias").innerHTML += " > ";
        document.getElementById('listaCategorias-' + idCategoriaSelected).style.display = displayBlock;
    }

    //Adiciona a combobox que foi exibida
    listaCategorias.push(idCategoriaSelected);
    listaCategoriasText.push(idCategoriaSelectedText);

    //Remove a categoria antiga
    var idAnterior = document.getElementById('categoriaFinal');
    if (idAnterior != null)
        document.getElementById('frmAgendarServico').removeChild(idAnterior);

    //Salva a categoria que sera inserida
    var inputCategoria = document.createElement('input');
    inputCategoria.id = 'categoriaFinal';
    console.log("Categoria que sera cadastrada = " + inputCategoria.id);
    inputCategoria.type = 'hidden';
    inputCategoria.name = inputCategoria.id;
    inputCategoria.value = listaCategorias[listaCategorias.length - 1];
    console.log("Valor da categoria que sera cadastrada = " + inputCategoria.value);
    //Adiciona nova categoria
    document.getElementById('frmAgendarServico').appendChild(inputCategoria);

    //Exibe Servico
    carregarServicoSelecionado(listaCategorias[listaCategorias.length - 1]);

    //alert("listaCategorias: " + listaCategorias);
    //alert("idCategoriaPaiSelected: " + idCategoriaPaiSelected);
    //alert("idCategoriaSelected: " + idCategoriaSelected);

    //Verifica se algum item foi selecionado
//    if (idCategoriaSelected != "Selecione uma categoria") {
//        //alert("Ativando #listaCategorias-" + idCategoriaSelected);
//        $('#listaCategorias-' + idCategoriaSelected).addClass('active');
//    }
//    //Esconder todas as combo box que existe
//    ObjListaCategorias.forEach(function (obj) {
//        var idCategoriaPai = obj.categoriaPai.idCategoriaServico;
//        $('#listaCategorias-' + idCategoriaPai).removeClass('active');
//    });
//
//    for (var j = 0; j < listaCategoriasCadastradas.length; j++) {
//        for (var i = 0; i < listaCategorias.length; i++) {
//            console.log("listaCategoriasCadastradas = " + listaCategoriasCadastradas);
//            console.log("listaCategorias = " + listaCategorias);
//            if (listaCategoriasCadastradas[j] == listaCategorias[i]) {
//                $('#listaCategorias-' + listaCategoriasCadastradas[j]).addClass('active');
//            }
//        }
//    }
}

document.getElementById("btnLimparInfo").addEventListener('click', function () {
    limparForm(document.getElementById('divInfoServico'));
    limparCategorias();
});

function limparForm(form) {

    // iterate over all of the inputs for the form
    // element that was passed in
    $(':input', form).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase(); // normalize case
        // it's ok to reset the value attr of text inputs,
        // password inputs, and textareas
        if (type == 'text' || type == 'password' || tag == 'textarea' || type == 'number')
            this.value = "";
        // checkboxes and radios need to have their checked state cleared
        // but should *not* have their 'value' changed
        else if (type == 'checkbox' || type == 'radio')
            this.checked = false;
        // select elements need to have their 'selectedIndex' property set to -1
        // (this works for both single and multiple select elements)
        else if (type == 'select-one')
            this.selectedIndex = 0;
        else if (type == 'select-multiple') {
            this.selectedIndex = -1;
            $(this).select2({
                placeholder: "Selecione um ou mais funcion·rios"
            });
        }
    });
}

function limparCategorias() {
    const element = document.getElementById('listaCategorias-0');
    const idElement = element.id;
    var displayNone = 'none';
    var displayBlock = 'block';

    //Ocultar ˙ltima lista exibida
    ultimaLista = document.getElementById('listaCategorias-' + listaCategorias[listaCategorias.length - 1]);
    if (ultimaLista != null) {
        ultimaLista.style.display = displayNone;
    }
    idCategoriaPaiSelected = idElement;

    //Exibir a primeira lista e limpar SPAN
    document.getElementById("spanListaCategorias").innerHTML = "";
    document.getElementById(idCategoriaPaiSelected).style.display = displayBlock;

    //Ocultar bot„o Voltar Categoria
    document.getElementById("btnVoltarCategoria").style.display = displayNone;

    //Adiciona a combobox que foi exibida
    listaCategorias = ['0'];
    listaCategoriasText = [];
}

function voltarCategoria() {

    //Propriedades de display
    var displayNone = 'none';
    var displayBlock = 'block';

    //Obter as ultimas listas que foram exibidas
    console.log("LISTA");
    console.log(listaCategorias);
    console.log(listaCategoriasText);
    if (listaCategoriasText.length >= 1) {

        if (listaCategoriasText.length == 1)
            document.getElementById("btnVoltarCategoria").style.display = displayNone;
        else
            document.getElementById("btnVoltarCategoria").style.display = displayBlock;

        var ultimaCategoria = document.getElementById('listaCategorias-' + listaCategorias[listaCategorias.length - 1]);
        var penultimaCategoria = document.getElementById('listaCategorias-' + listaCategorias[listaCategorias.length - 2]);

        //Exibir a penultima lista
        if (ultimaCategoria != null) {
            var idUltimaCategoria = ultimaCategoria.id;
            document.getElementById(idUltimaCategoria).style.display = displayNone;
        }
        var idPenultimaCategoria = penultimaCategoria.id;
        document.getElementById(idPenultimaCategoria).style.display = displayBlock;
        document.getElementById(idPenultimaCategoria).selectedIndex = 0;

        //Remove a ultima lista que foi exibida
        listaCategorias.pop();
        listaCategoriasText.pop();

        //Alterar SPAN
        var spanLista = '';
        for (var i = 0; i < listaCategoriasText.length; i++) {
            spanLista += listaCategoriasText[i] + " > ";
        }
        document.getElementById("spanListaCategorias").innerHTML = spanLista;

        console.log("listaCategoriasText");
        console.log(listaCategoriasText);
    } else {
        document.getElementById("btnVoltarCategoria").style.display = displayNone;
    }
    //Exibe Servico
    carregarServicoSelecionado(listaCategorias[listaCategorias.length - 1]);
}

//SERVICOS
//LISTA SERVICO POR CATEGORIA SELECIONADA
function carregarServicoSelecionado(id) {

    var idElement = id;
    console.log("carregarServicoSelecionado");
    console.log(idElement);
    document.getElementById("groupListaServicos").style.display = "none";
    document.getElementById("listaServico").innerHTML = "<option selected disabled>Selecione um servico</option>";
    document.getElementById("groupDataHora").style.display = "none";
    document.getElementById("groupListaFuncionarios").style.display = "none";
    document.getElementById("listaFuncionarios").innerHTML = "<option selected disabled>Selecione um funcionario</option><option value='0'>Qualquer Funcionario</option>";
    document.getElementById("nome").value = "";
    document.getElementById("descricao").value = "";
    document.getElementById("valor").value = "";
    document.getElementById("duracao").value = "";

    $.ajax({
        url: nameproject + '/api/Servico/Listar/Categoria/' + idElement, //lugar onde a servlet est·
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
                    if (Obj.length > 0) {
                        sweet();
                        for (var i = 0; i < Obj.length; i++) {
                            $("#listaServico").append("<option value='" + Obj[i].idServico + "'>" + Obj[i].nome + "</option>");
                        }
                        document.getElementById("groupListaServicos").style.display = "block";
                        sweet();

                    } else {
                        document.getElementById("groupListaServicos").style.display = "none";
                        document.getElementById("listaServico").innerHTML = "<option selected disabled>Selecione um servico</option>";
                        document.getElementById("groupDataHora").style.display = "none";
                    }
                }
            }
        }
    });
}

function exibeServico() {
    var servicoSelecionado = document.getElementById("listaServico").value;
    console.log(servicoSelecionado);
    document.getElementById("groupListaFuncionarios").style.display = "none";
    document.getElementById("groupDataHora").style.display = "none";
    document.getElementById("listaFuncionarios").innerHTML = "<option selected disabled>Selecione um funcionario</option><option value='0'>Qualquer Funcionario</option>";
    document.getElementById("nome").value = "";
    document.getElementById("descricao").value = "";
    document.getElementById("valor").value = "";
    document.getElementById("duracao").value = "";
    $.ajax({
        url: nameproject + '/api/Servico/Buscar/' + servicoSelecionado, //lugar onde a servlet est·
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
                    document.getElementById("nome").value = Obj.nome;
                    document.getElementById("descricao").value = Obj.descricao;
                    document.getElementById("valor").value = Obj.valor.toFixed(2).toString().replace(".", ",");
                    document.getElementById("duracao").value = Obj.duracao.seconds / 60; //Converte os segundos para minutos
                    for (var i = 0; i < Obj.funcionarios.length; i++) {
                        $("#listaFuncionarios").append("<option value='" + Obj.funcionarios[i].idFuncionario + "'>" + Obj.funcionarios[i].nomePessoa + "</option>");
                    }
                    document.getElementById("groupListaFuncionarios").style.display = "block";
                    document.getElementById("groupDataHora").style.display = "block";
                } else {
                    document.getElementById("groupListaFuncionarios").style.display = "none";
                    document.getElementById("groupDataHora").style.display = "none";
                    document.getElementById("listaFuncionarios").innerHTML = "<option selected disabled>Selecione um funcionario</option><option value='0'>Qualquer Funcion·rio</option>";
                }
            }
        }
    });

}

function sweet() {
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: 3000
    });
    Toast.fire({
        type: 'info',
        title: ' Carregando Servi\xE7o'
    });
}