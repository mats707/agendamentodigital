var objCategoria;
function buscarCategoriaServico(idCategoria) {
    
    $.ajax({
        url: 'api/CategoriaServico/Buscar/' + idCategoria, //lugar onde a servlet está
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
                    objCategoria = Obj;
                }
            }
        }
    });
}

function alterarCategoria(element) {
    var idCategoria = element.id.split('-')[2];
    console.log(element.id);
    console.log(idCategoria);
    buscarCategoriaServico(idCategoria);
    console.log(objCategoria);

    document.getElementById('sectionAlterarCategoria').style.display = 'block';
    document.getElementById('alterarNomeCategoria').value = objCategoria.nome;
    document.getElementById('alterarDescricaoCategoria').value = objCategoria.descricao;

    var categoria = document.createElement('input');
    categoria.id = 'alterarCategoria';
    categoria.type = 'hidden';
    categoria.name = categoria.id;
    categoria.value = objCategoria.idCategoriaServico;

    var categoriaPai = document.createElement('input');
    categoriaPai.id = 'alterarCategoriaPai';
    categoriaPai.type = 'hidden';
    categoriaPai.name = categoriaPai.id;
    categoriaPai.value = objCategoria.categoriaPai.idCategoriaServico;

    if (document.getElementById("alterarCategoria") !== null)
        document.getElementById('formAlterarCategoria').removeChild(document.getElementById("alterarCategoria"));
    if (document.getElementById("alterarCategoriaPai") !== null)
        document.getElementById('formAlterarCategoria').removeChild(document.getElementById("alterarCategoriaPai"));
    document.getElementById('formAlterarCategoria').appendChild(categoria);
    document.getElementById('formAlterarCategoria').appendChild(categoriaPai);
}

function deletarCategoria(element) {
    var idCategoria = element.id.split('-')[2];
    console.log(element.id);
    console.log(idCategoria);
    buscarCategoriaServico(idCategoria);
    console.log(objCategoria);

    document.getElementById('deletarNomeCategoria').value = objCategoria.nome;
    document.getElementById('deletarDescricaoCategoria').value = objCategoria.descricao;

    var categoria = document.createElement('input');
    categoria.id = 'deletarCategoria';
    categoria.type = 'hidden';
    categoria.name = categoria.id;
    categoria.value = objCategoria.idCategoriaServico;

    var categoriaPai = document.createElement('input');
    categoriaPai.id = 'deletarCategoriaPai';
    categoriaPai.type = 'hidden';
    categoriaPai.name = categoriaPai.id;
    categoriaPai.value = objCategoria.categoriaPai.idCategoriaServico;

    if (document.getElementById("deletarCategoria") !== null)
        document.getElementById('formDeletarCategoria').removeChild(document.getElementById("deletarCategoria"));
    if (document.getElementById("deletarCategoriaPai") !== null)
        document.getElementById('formDeletarCategoria').removeChild(document.getElementById("deletarCategoriaPai"));
    document.getElementById('formDeletarCategoria').appendChild(categoria);
    document.getElementById('formDeletarCategoria').appendChild(categoriaPai);
}
