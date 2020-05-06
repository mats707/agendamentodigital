
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
    var displayNova = 'none';
    if(idElement === 0){
        listaCategorias = ['0'];
    }
    idCategoriaPaiSelected = idElement;
    alert(idCategoriaPaiSelected + " foi selecionado!");
    idCategoriaSelected = $("#" + idCategoriaPaiSelected + " :selected").val();
    
    //Usar cadastrar categoria para realizar a listagem de categoria
    $("#groupListaCategorias").append($("#" + idCategoriaPaiSelected + " :selected").text());
    document.getElementById(idCategoriaPaiSelected).style.display = displayNova;

    //Adiciona a combobox que deve ser exibida
    listaCategorias.push(idCategoriaSelected);

    //alert("listaCategorias: " + listaCategorias);
    //alert("idCategoriaPaiSelected: " + idCategoriaPaiSelected);
    //alert("idCategoriaSelected: " + idCategoriaSelected);

    //Verifica se algum item foi selecionado
    if (idCategoriaSelected != "Selecione uma categoria") {
        //alert("Ativando #listaCategorias-" + idCategoriaSelected);
        $('#listaCategorias-' + idCategoriaSelected).addClass('active');
    }
    //Esconder todas as combo box que existe
    ObjListaCategorias.forEach(function (obj) {
        var idCategoriaPai = obj.categoriaPai.idCategoriaServico;
        $('#listaCategorias-' + idCategoriaPai).removeClass('active');
    });

    for (var j = 0; j < listaCategoriasCadastradas.length; j++) {
        for (var i = 0; i < listaCategorias.length; i++) {
            console.log("listaCategoriasCadastradas = " + listaCategoriasCadastradas);
            console.log("listaCategorias = " + listaCategorias);
            if (listaCategoriasCadastradas[j] == listaCategorias[i]) {
                $('#listaCategorias-' + listaCategoriasCadastradas[j]).addClass('active');
            }
        }
    }
}