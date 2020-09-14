function timepicker() {

//    servico = 1 and funcionario = 1 and dataagendamento = '2020-09-12';

    var cmbListaServico = document.getElementById("listaServico");
    var indexListaServico = cmbListaServico.options[cmbListaServico.selectedIndex].value;

    var cmbListaFuncionarios = document.getElementById("listaFuncionarios");
    var indexListaFuncionarios = cmbListaFuncionarios.options[cmbListaFuncionarios.selectedIndex].value;

    var inputDate = document.getElementById("inputDate")
    var valueInputDate = inputDate.value;
    var dayValueInputDate = valueInputDate.substring(0, 2);
    var monthValueInputDate = valueInputDate.substring(3, 5);
    var yearValueInputDate = valueInputDate.substring(6, 10);

    console.log("indexListaServico: " + indexListaServico);
    console.log("indexListaFuncionarios: " + indexListaFuncionarios);
    console.log("valueInputDate: " + valueInputDate);

    valueInputDate = formatDate(yearValueInputDate, monthValueInputDate, dayValueInputDate);

    carregarHorarios(indexListaServico, indexListaFuncionarios, valueInputDate);
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
function carregarHorarios(indexListaServico, indexListaFuncionarios, valueInputDate) {
    if (isNumber(indexListaServico) && isNumber(indexListaFuncionarios) && valueInputDate != null) {

        var urlApi = nameproject + '/api/Agendamento/HorariosDisponiveis/Servico/' + indexListaServico + '/Funcionario/' + indexListaFuncionarios + '/' + valueInputDate; //lugar onde a servlet est√°
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
                            $("#listaHorarios").append("<option value='" + arrHorarioDisponivel[i].minutos + "'>" + arrHorarioDisponivel[i].hora + "</option>");
                        }
                        document.getElementById("groupListaHorarios").style.display = "block";
                    } else {
                        document.getElementById("listaHorarios").innerHTML = "<option selected disabled>Selecione um horario</option>";
                    }
                }
            }
        });
    }
}

function validarHorarios(arrHorariosOcupados) {

    //INICIO
    //arrHorariosOcupados = ["09:00", "12:00"]; //Hor·rios ocupados por agendamento
    //arrHorariosOcupados = [{"duracaoServico": "240", "hora": "12:00"}]; //Hor·rios ocupados por agendamento
    var horaInicial = 8; //Trocar para var JSP em horas
    var horaFinal = 17; //Trocar para var JSP em horas
    var intervaloAgendamento = 30; //Trocar para var JSP em minutos
    var horaInicialMin = horaInicial * 60; //Converte a hora inicial de trabalho para minutos
    var horaFinalMin = horaFinal * 60; //Converte a hora final de trabalho para minutos

    var duracaoServico = parseInt(document.getElementById("duracao").value);

    if (duracaoServico >= intervaloAgendamento) {
        intervaloAgendamento = duracaoServico;
    }

    var arrHorasMinutos = [];

    for (m = horaInicialMin; m < horaFinalMin; m = m + intervaloAgendamento) {
        var horaExibicao = Math.floor(m / 60);
        var minutosExibicao = m % 60;
        arrHorasMinutos.push({minutos: parseInt(m), hora: ('00' + horaExibicao).toString().substr(-2) + ':' + ('00' + minutosExibicao).toString().substr(-2)});
    }
    for (i = 0; i < arrHorariosOcupados.length; i++) {
        console.log(arrHorariosOcupados[i].hora);
        arrHorasMinutos = $.grep(arrHorasMinutos, function (e) {
            return e.hora != arrHorariosOcupados[i].hora;
        });
    }
    return arrHorasMinutos;

}