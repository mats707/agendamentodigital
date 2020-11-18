var arrHorasMinutos = [];
var listaFuncionarios = ['0'];
var listaFuncionariosCadastrados = [];
var ObjListaFuncionarios;
var listaDiasFolga = ['0', '1', '2', '3', '4', '5', '6'];


$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    carregarFuncionario();

    // Funcao para carregar os dados da consulta nos respectivos campos
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

    function listarDadosEmpresa()
    {
        $.ajax({
            url: nameproject + '/api/Empresa/Menu/Encontrar', //lugar onde a servlet está
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
                        $("#diaSemanaTrabalho").val(Obj.diaSemanaTrabalho);
                        for (var i = 0; i < Obj.diaSemanaTrabalho.length; i++)
                        {
                            switch (Obj.diaSemanaTrabalho[i])
                            {
                                case 0:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 1:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 2:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 3:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 4:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 5:
                                    listaDiasFolga.splice(i, 1);
                                    break;
                                case 6:
                                    listaDiasFolga.splice(i, 1);
                                    break;

                            }
                        }
                        $("#datepicker").daysOfWeekDisabled = listaDiasFolga;
                        $(".timepicker").startTime = Obj.horaInicialTrabalho;
                        $(".timepicker").maxTime = Obj.horaFinalTrabalho.split(':')[0] + ":" + Obj.horaFinalTrabalho.split(':')[1];

                    }

                }
            }
        });
    }
});

function changeValue() {
    var slider = document.getElementById("intervaloAgendamentoGeralServico");
    var output = document.getElementById("spanDuracao");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
}

function sweet(title, type, timer) {
    if (title !== "") {

        Swal.fire({
            showConfirmButton: true,
            timer: false,
            icon: type,
            title: 'Confirmação do cadastro!',
            text: title
        })
    }
}