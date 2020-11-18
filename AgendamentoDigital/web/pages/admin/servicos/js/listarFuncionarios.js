
var listaFuncionarios = ['0'];
var listaFuncionariosCadastrados = [];
var ObjListaFuncionarios;

$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    carregarFuncionario();

    function carregarFuncionario() {
        $.ajax({
            url: nameproject + '/api/Funcionario/ListarCompleto/', //lugar onde a servlet está
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
                            listaFuncionariosCadastrados.push(Obj[i].idFuncionarios);
                        }
                        ObjListaFuncionarios = Obj;
                        listarFuncionarios();
                    }
                }
            }
        });
    }

    function listarFuncionarios() {

        listaFuncionariosCadastrados = Array.from(new Set(listaFuncionariosCadastrados));
        console.log("listaFuncionariosCadastrados = " + listaFuncionariosCadastrados);
        //Funcionarios PAI
        ObjListaFuncionarios.forEach(function (obj) {
            var id = obj.idFuncionario;
            var nome = obj.nomePessoa;
            $("#listaFuncionarios").append("<option value='" + id + "'>" + nome + "</option>");
        });
    }
});