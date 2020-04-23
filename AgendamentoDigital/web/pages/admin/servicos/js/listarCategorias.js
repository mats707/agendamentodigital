$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    var listaCategorias = '';
    var ObjListaCategorias;

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
                        ObjListaCategorias = Obj;
                        listaCategorias = e.responseText;
                        listarCategoriasPai();
                    }
                }
            }
        });
    }

    function listarCategoriasPai() {
        ObjListaCategorias.forEach(function (obj1) {

            var idCategoria = obj1.idCategoriaServico;
            var nome = obj1.nome;
            var descricao = obj1.descricao;
            var idCategoriaPai = obj1.categoriaPai.idCategoriaServico;


            if (idCategoriaPai == 0) { //verifica se È categoria pai (primeira)
                $("#listaCategorias").append("<option value='" + idCategoria + "' data-categoriaPai='" + idCategoriaPai + "'>" + nome + "</option>");
            }
        });
    }

    $("#listaCategorias").change(function () {

        var idCategoriaPaiSelected = "";
        
        $("#listaCategorias-" + idCategoriaPaiSelected).remove();
        
        idCategoriaPaiSelected = $("#listaCategorias :selected").attr('data-categoriapai');
        var idCategoriaSelected = $("#listaCategorias :selected").val();

        if (idCategoriaPaiSelected != "Selecione uma categoria") {
            $("#groupListaCategorias").append('<select id="listaCategorias-' + idCategoriaSelected + '" class="form-control select2 select2-danger" data-dropdown-css-class="select2-danger" style="width: 100%; display: none;">\n\
            <option selected disabled>Selecione uma subcategoria</option></select>');
        }

        ObjListaCategorias.forEach(function (obj1) {

            var idCategoria = obj1.idCategoriaServico;
            var nome = obj1.nome;
            var descricao = obj1.descricao;
            var idCategoriaPai = obj1.categoriaPai.idCategoriaServico;

            if (idCategoriaPai == idCategoriaSelected) { //verifica se È categoria pai (primeira)
                document.getElementById('listaCategorias-' + idCategoriaSelected).style.display = 'block';
                $("#listaCategorias-" + idCategoriaSelected).append("<option value='" + idCategoria + "' data-categoriaPai='" + idCategoriaPai + "'>" + nome + "</option>");
            }
        });
    });
});