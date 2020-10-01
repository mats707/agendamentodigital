var arrHorasMinutos = [];

function timepicker() {

//    servico = 1 and funcionario = 1 and dataagendamento = '2020-09-12';

    var cmbListaFuncionarios = document.getElementById("listaFuncionarios");
    var indexListaFuncionarios = cmbListaFuncionarios.options[cmbListaFuncionarios.selectedIndex].value;

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

// Funcao para carregar os dados da consulta nos respectivos campos
function carregarHorarios(indexListaFuncionarios, valueInputDate, pessoa) {
    if (isNumber(indexListaFuncionarios) && valueInputDate != null) {

        var urlApi = nameproject + '/api/Agendamento/HorariosOcupados/' + pessoa + '/' + indexListaFuncionarios + '/' + valueInputDate; //lugar onde a servlet estÃ¡
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
                    document.getElementById("listaHorarios").innerHTML = "<option selected disabled>Selecione um horario</option>";
                    if (Obj != null) {
                        console.log(Obj);
                        arrHorarioDisponivel = validarHorarios(Obj);
//                        document.getElementById("duracao").value = Obj.duracao.seconds / 60; //Converte os segundos para minutos
                        for (var i = 0; i < arrHorarioDisponivel.length; i++) {
                            $("#listaHorarios").append("<option value='" + arrHorarioDisponivel[i].hora + "'>" + arrHorarioDisponivel[i].hora + "</option>");
                        }
                        document.getElementById("groupListaHorarios").style.display = "block";
                    } else {
                        document.getElementById("listaHorarios").innerHTML = "<option selected disabled>Selecione um horário</option>";
                    }
                }
            }
        });
    }
}

function validarHorarios(arrHorariosOcupados) {

    //INICIO
    //arrHorariosOcupados = ["09:00", "12:00"]; //Horários ocupados por agendamento
//    arrHorariosOcupados = [
//        {
//            "duracaoServico": "30",
//            "hora": "08:00"
//        },
//        {
//            "duracaoServico": "30",
//            "hora": "11:30"
//        },
//        {
//            "duracaoServico": "240",
//            "hora": "12:00"
//        }
//    ]; //Horários ocupados por agendamento
    var horaInicial = 8; //Trocar para var JSP em horas
    var horaFinal = 17; //Trocar para var JSP em horas
    var intervaloAgendamento = 30; //Trocar para var JSP em minutos
    var horaInicialMin = horaInicial * 60; //Converte a hora inicial de trabalho para minutos
    var horaFinalMin = horaFinal * 60; //Converte a hora final de trabalho para minutos

    var duracaoServico = parseInt(document.getElementById("duracao").value);

    if (duracaoServico >= intervaloAgendamento) {
        intervaloAgendamento = duracaoServico;
    }

    var horaMaximaServico = horaFinalMin - intervaloAgendamento;

    //Montagem do array de horas disponíveis, passando em minutos e no formato HH:MM
    for (m = horaInicialMin; m < horaMaximaServico; m = m + intervaloAgendamento) {
        var horaExibicao = Math.floor(m / 60);
        var minutosExibicao = m % 60;
        arrHorasMinutos.push({minutos: parseInt(m), hora: ('00' + horaExibicao).toString().substr(-2) + ':' + ('00' + minutosExibicao).toString().substr(-2)});
    }

    //Remove do array montado acima os horarios ocupados por esse funcionario e as horas entre o intervalo de duração
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

        for (j = horaOcupadaEmMinutos; j < duracaoServicoOcupado; j = j + intervaloAgendamento) {
            var i_hora = Math.floor(j / 60);
            var i_minutos = j % 60;
            var i_ocupado = ('00' + i_hora).toString().substr(-2) + ':' + ('00' + i_minutos).toString().substr(-2);

            arrHorasMinutos = $.grep(arrHorasMinutos, function (e) {
                return e.hora != i_ocupado;
            });
        }

    }

    return arrHorasMinutos;

}