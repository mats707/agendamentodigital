
var listaServico = ['0'];
var listaServicoText = [];
var listaServicoCadastradas = [];
var idCategoriaPaiSelected = "0";
var ObjListaServico;

$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    carregarServico();

    function carregarServico() {
        $.ajax({
            url: nameproject + '/api/Servico/Listar/', //lugar onde a servlet está
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
                            listaServicoCadastradas.push(Obj[i].categoria.idCategoriaServico);
                        }
                        ObjListaServico = Obj;
                    }
                }
            }
        });
    }
});

function listarServico(element) {
    console.log("Listar Servico - element");
    console.log(element);
    console.log("Categoria Selecionada = " + $("#" + element.id + ":selected").val());
    listaServicoCadastradas = Array.from(new Set(listaServicoCadastradas));
    console.log("listaServicoCadastradas = " + listaServicoCadastradas);
    //Servico PAI
    ObjListaServico.forEach(function (obj) {
        var idServico = obj.idServico;
        var nome = obj.nome;
        var categoria = obj.categoria.idCategoriaServico;
        var idCategoriaSelected = $("#" + element.id + ":selected").val();
        if (categoria == idCategoriaSelected) { //verifica quais serviços para categoria selecionada (primeira)
            $("#listaServico-0").append("<option value='" + idServico + "' data-categoria='" + categoria + "'>" + nome + "</option>");
        }
    });
}

document.getElementById("btnLimparInfo").addEventListener('click', function () {
    limparForm(document.getElementById('divInfoServico'));
    limparServico();
});

