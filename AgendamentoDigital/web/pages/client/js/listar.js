$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    var ObjAgendamento;

    carregarAgenda();

    function carregarAgenda() {
        idCliente = document.getElementById('target').getAttribute('data-cliente');
        $.ajax({
            url: nameproject + '/api/Agendamento/Listar/Cliente/' + idCliente, //lugar onde a servlet est√°
            type: "GET",
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        ObjAgendamento = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                        alert(err);
                    }
                    lerTabela();
                }
            }
        });
    }

    function lerTabela() {
        document.getElementById("target").innerHTML = '<table id="tabAgendamento" class="table table-bordered"><thead><tr><th style="width: 100%">Data</th><th style="width: 100%">Horario</th><th style="width: 100%">Servico</th><th style="width: 100%">Duracao</th><th style="width: 100%">Valor</th><th style="width: 100%">Funcionario</th></tr></thead></table>';
        var table = document.getElementById("tabAgendamento");
        if (ObjAgendamento != null) {
            console.log("Entrei");
            if (ObjAgendamento.length > 0) {
                //sweet();
                for (var i = 0; i < ObjAgendamento.length; i++) {
                    var row = table.insertRow(-1); //Insere no ultimo registro
                    var cellData = row.insertCell(0);//Data
                    var cellHora = row.insertCell(1);//Hora
                    var cellServico = row.insertCell(2);//Nome Servico
                    var cellDuracao = row.insertCell(3);//Duracao
                    var cellValor = row.insertCell(4);//Valor
                    var cellFuncionario = row.insertCell(5);//Funcionario
                    console.log(ObjAgendamento[i].dataAgendamento);
                    cellData.innerHTML = ObjAgendamento[i].dataAgendamento;
                    cellHora.innerHTML = ObjAgendamento[i].horaAgendamento;
                    cellServico.innerHTML = ObjAgendamento[i].servico.nome;
                    cellDuracao.innerHTML = ObjAgendamento[i].servico.duracao.seconds;
                    cellValor.innerHTML = ObjAgendamento[i].servico.valor;
                    cellFuncionario.innerHTML = ObjAgendamento[i].funcionario.nomePessoa;
                }
            } else {
                document.getElementById("target").innerHTML = '<h1>N„o h· agendamentos</h1>';
            }
        }
    }
});

function sweet() {
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: 3000
    });
    Toast.fire({
        type: 'info',
        title: ' Carregando Agendamento'
    });
}