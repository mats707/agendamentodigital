var arrHorasMinutos = [];
var listaFuncionarios = ['0'];
var listaFuncionariosCadastrados = [];
var ObjListaFuncionarios;


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
});

function changeValue() {
    var slider = document.getElementById("intervaloAgendamentoGeralServico");
    var output = document.getElementById("spanDuracao");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
}


function timepicker() {



    var inputDate = document.getElementById("inputDate")
    var valueInputDate = inputDate.value;
    var dayValueInputDate = valueInputDate.substring(0, 2);
    var monthValueInputDate = valueInputDate.substring(3, 5);
    var yearValueInputDate = valueInputDate.substring(6, 10);

    console.log("indexListaFuncionarios: " + indexListaFuncionarios);
    console.log("valueInputDate: " + valueInputDate);

    valueInputDate = formatDate(yearValueInputDate, monthValueInputDate, dayValueInputDate);

    arrHorasMinutos = [];
    carregarHorarios(indexListaFuncionarios, valueInputDate, "Funcionario");
}
function isNumber(x) {
    if (isNaN(x)) {
        return false;
    }
    return true;
}

function formatDate(year, month, date) {
    date = new Date(year + "-" + month + "-" + date);
    var dateUtc = new Date(date.getTime() + date.getTimezoneOffset() * 60000);
    console.log("formatDate : " + dateUtc);
    year = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(dateUtc);
    month = new Intl.DateTimeFormat('en', {month: 'numeric'}).format(dateUtc);
    date = new Intl.DateTimeFormat('en', {day: '2-digit'}).format(dateUtc);

    var new_inputDate = `${year}-${month}-${date}`;
    console.log("Data Antiga: " + date);
    console.log("Data Nova: " + new_inputDate);

    return new_inputDate;
}