
var listaCategorias = ['0'];
var listaCategoriasCadastradas = [];
var idCategoriaPaiSelected = "0";
var ObjListaCategorias;

$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
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
    const idElement = element.id;
    var displayNone = 'none';
    var displayBlock = 'block';

    if (idElement === 0) {
        listaCategorias = ['0'];
    }
    idCategoriaPaiSelected = idElement;
    console.log(idCategoriaPaiSelected + " foi selecionado!");
    idCategoriaSelected = $("#" + idCategoriaPaiSelected + " :selected").val();

    //Usar cadastrar categoria para realizar a listagem de categoria
    document.getElementById("spanListaCategorias").innerHTML += "<a id='categoria-" + idCategoriaSelected + "' data-categoriaPai='" + idCategoriaPaiSelected + "' onclick='exibeFilho(this);' href='#'>";
    document.getElementById("spanListaCategorias").innerHTML += $("#" + idCategoriaPaiSelected + " :selected").text();
    document.getElementById("spanListaCategorias").innerHTML += "</a>";
    document.getElementById(idCategoriaPaiSelected).style.display = displayNone;

    var element = document.getElementById('listaCategorias-' + idCategoriaSelected);
    if (element != null) {
        document.getElementById("spanListaCategorias").innerHTML += " > ";
        document.getElementById('listaCategorias-' + idCategoriaSelected).style.display = displayBlock;
    }

    //Adiciona a combobox que foi exibida
    listaCategorias.push(idCategoriaSelected);
    console.log(listaCategorias);
    console.log(listaCategorias[listaCategorias.length - 1]);

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

function wait(ms)
{
    var d = new Date();
    var d2 = null;
    do {
        d2 = new Date();
    } while (d2 - d < ms);
}

document.getElementById("btnLimparInfo").addEventListener('click', function () {
    limparForm(document.getElementById('divInfoServico'));
    limparCategorias();
});

document.getElementById("btnLimparDetalhe").addEventListener('click', function () {
    limparForm(document.getElementById('divDetalheServico'));
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

    //Adiciona a combobox que foi exibida
    listaCategorias = ['0'];
}

