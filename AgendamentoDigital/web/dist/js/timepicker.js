var arrHorasMinutos = [];

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function timepicker() {

//    servico = 1 and funcionario = 1 and dataagendamento = '2020-09-12';

    buscarEmpresa();

    var cmbListaFuncionarios = document.getElementById("listaFuncionarios");
    var indexListaFuncionarios = cmbListaFuncionarios.options[cmbListaFuncionarios.selectedIndex].value;

    var inputDate = document.getElementById("inputDate")
    var valueInputDate = inputDate.value;
    if (valueInputDate != '') {
        var dayValueInputDate = valueInputDate.substring(0, 2);
        var monthValueInputDate = valueInputDate.substring(3, 5);
        var yearValueInputDate = valueInputDate.substring(6, 10);

        console.log("indexListaFuncionarios: " + indexListaFuncionarios);
        console.log("valueInputDate: " + valueInputDate);

        valueInputDate = formatDate(yearValueInputDate, monthValueInputDate, dayValueInputDate);
        arrHorasMinutos = [];
    } else {
        sweet(" Selecione uma data para o agendamento", "info", 3000);
    }

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

// Funcao para carregar os dados da consulta nos respectivos campos
function carregarHorarios(indexListaFuncionarios, valueInputDate, pessoa) {
    if (isNumber(indexListaFuncionarios) && valueInputDate != '') {

        var urlApi = 'api/Agendamento/HorariosOcupados/' + pessoa + '/' + indexListaFuncionarios + '/' + valueInputDate; //lugar onde a servlet está
        var arrHorarioDisponivel = [];

        console.log(urlApi);

        $.ajax({
            url: urlApi,
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
                    document.getElementById("listaHorarios").innerHTML = "<option selected disabled>Selecione um horário</option>";
                    if (Obj.length > 0) {
                        console.log(Obj);
                        arrHorarioDisponivel = validarHorarios(Obj);
//                        document.getElementById("duracao").value = Obj.duracao.seconds / 60; //Converte os segundos para minutos
                    } else {
                        arrHorarioDisponivel = validarHorarios(null);
                        document.getElementById("listaHorarios").innerHTML = "<option selected disabled>Selecione um horário</option>";
                    }
                    for (var i = 0; i < arrHorarioDisponivel.length; i++) {
                        $("#listaHorarios").append("<option value='" + arrHorarioDisponivel[i].hora + "'>" + arrHorarioDisponivel[i].hora + "</option>");
                    }
                    document.getElementById("groupListaHorarios").style.display = "block";
                }
            }
        });
    }
}

var ObjEmpresa;

function buscarEmpresa() {
    var urlApi = 'api/Empresa/Menu/Encontrar';
    $.ajax({
        url: urlApi,
        type: "GET",
        complete: function (e, xhr, result) {
            console.log("Ajax");
            console.log(e.status);
            console.log(e.readyState);
            if (e.readyState == 4 && e.status == 200) {
                try { //Converte a resposta HTTP JSON em um objeto JavaScript
                    var Obj = eval("(" + e.responseText + ")");
                } catch (err) { //
                    // Mostra Aviso
                    sweet(" Algo de errado aconteceu!", "error", 4000);
                    sweet(" " + err, "error", 4000);
                }
                if (Obj != null) {
                    ObjEmpresa = Obj;
                    sweet(" Empresa encontrada!", "success", 4000);
                }
            }
        }
    });
}

function validarHorarios(arrHorariosOcupados) {

    console.log(ObjEmpresa);

    //Obtem informacoes da empresa
    var horaInicial = parseInt(ObjEmpresa.horaInicialTrabalho.split(":")[0]);
    var minutosInicial = parseInt(ObjEmpresa.horaInicialTrabalho.split(":")[1]);
    var horaInicialTrabalhoMinutos = (horaInicial * 60) + minutosInicial;

    var horasFinal = parseInt(ObjEmpresa.horaFinalTrabalho.split(":")[0]);
    var minutosFinal = parseInt(ObjEmpresa.horaFinalTrabalho.split(":")[1]);
    var horaFinalTrabalhoMinutos = (horasFinal * 60) + minutosFinal;

    var intervaloAgendamentoGeralServico = ObjEmpresa.intervaloAgendamentoGeralServico;

    console.log("VAR");
    console.log(horaInicial);
    console.log(minutosInicial);
    console.log(horaInicialTrabalhoMinutos);
    console.log(horasFinal);
    console.log(minutosFinal);
    console.log(horaFinalTrabalhoMinutos);
    console.log(intervaloAgendamentoGeralServico);


    var duracaoServico = parseInt(document.getElementById("duracao").value);

    if (duracaoServico >= intervaloAgendamentoGeralServico) {
        intervaloAgendamentoGeralServico = duracaoServico;
    }

    var horaMaximaServico = horaFinalTrabalhoMinutos - intervaloAgendamentoGeralServico;

    //Montagem do array de horas disponíveis, passando em minutos e no formato HH:MM
    for (m = horaInicialTrabalhoMinutos; m < horaMaximaServico; m = m + intervaloAgendamentoGeralServico) {
        var horaExibicao = Math.floor(m / 60);
        var minutosExibicao = m % 60;
        arrHorasMinutos.push({minutos: parseInt(m), hora: ('00' + horaExibicao).toString().substr(-2) + ':' + ('00' + minutosExibicao).toString().substr(-2)});
    }

    //Remove do array montado acima os horarios ocupados por esse funcionario e as horas entre o intervalo de duração
    if (arrHorariosOcupados != null) {
        ``
        for (i = 0; i < arrHorariosOcupados.length; i++) {
            var horaOcupada = arrHorariosOcupados[i].hora;
            var duracaoServicoOcupado = arrHorariosOcupados[i].duracaoServico;
            arrHorasMinutos = $.grep(arrHorasMinutos, function (e) {
                return e.hora != horaOcupada;
            });

            //Remove do array montado acima os horarios que estão entre o horario ocupado e sua duracao
            var horas = parseInt(horaOcupada.split(":")[0]);
            var minutos = parseInt(horaOcupada.split(":")[1]);
            var horaOcupadaEmMinutos = (horas * 60) + minutos;
            var duracaoServicoOcupado = parseInt(duracaoServicoOcupado) + horaOcupadaEmMinutos;

            for (j = horaOcupadaEmMinutos; j < duracaoServicoOcupado; j = j + intervaloAgendamentoGeralServico) {
                var i_hora = Math.floor(j / 60);
                var i_minutos = j % 60;
                var i_ocupado = ('00' + i_hora).toString().substr(-2) + ':' + ('00' + i_minutos).toString().substr(-2);

                arrHorasMinutos = $.grep(arrHorasMinutos, function (e) {
                    return e.hora != i_ocupado;
                });
            }
        }
    }
    console.log(arrHorasMinutos);
    return arrHorasMinutos;
}