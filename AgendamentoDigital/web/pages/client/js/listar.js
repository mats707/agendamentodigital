$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    var ObjAgendamento;
    
});

function lerTabela(result) {
    sweet();
    ObjAgendamento = result;
    document.getElementById("target").innerHTML = '<table id="tabAgendamento" class="table table-hover"><thead class="thead-dark"><tr><th>Servi\xE7o</th><th>Data</th><th>Horario</th><th>Duracao</th><th>Valor</th><th>Funcionario</th></tr></thead></table>';
    var table = document.getElementById("tabAgendamento");
    if (ObjAgendamento != null) {
        if (ObjAgendamento.length > 0) {
            //sweet();
            for (var i = 0; i < ObjAgendamento.length; i++) {
                var row = table.insertRow(-1); //Insere no ultimo registro
                var cellServico = row.insertCell(0);//Nome Servico
                var cellData = row.insertCell(1);//Data
                var cellHora = row.insertCell(2);//Hora
                var cellDuracao = row.insertCell(3);//Duracao
                var cellValor = row.insertCell(4);//Valor
                var cellFuncionario = row.insertCell(5);//Funcionario
                cellServico.innerHTML = ObjAgendamento[i].servico.nome;
                cellData.innerHTML = ObjAgendamento[i].dataAgendamento;
                cellHora.innerHTML = ObjAgendamento[i].horaAgendamento;
                cellDuracao.innerHTML = ObjAgendamento[i].servico.duracao.seconds;
                cellValor.innerHTML = ObjAgendamento[i].servico.valor;
                cellFuncionario.innerHTML = ObjAgendamento[i].funcionario.nomePessoa;
            }
        } else {
            document.getElementById("target").innerHTML = '<h1>Não há agendamentos</h1>';
        }
    }
}

function sweet() {
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: 3000
    });
    Toast.fire({
        type: 'info',
        title: ' Carregando seus agendamentos'
    });
}