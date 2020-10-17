var nameproject = window.location.pathname.split('/')[1];
console.log(nameproject);

var site = "/AgendamentoDigital"
function carregarMaisAgendado(idCliente) {
    var URL = 'api/Relatorios/Agendamentos/Cliente/' + idCliente;
    $.ajax({
        url: URL,
        type: "GET",
        complete: function (e, xrh, result) {
            if (e.readyState == 4 && e.status == 200)
            {
                console.log(e.responseText);
                try {
                    var Obj = eval("(" + e.responseText + ")");
                } catch (err)
                {
                    alert("Sistema encontrou um erro");
                    alert(err);
                }
                if (Obj != null)
                {
                    lerTabela(Obj);
                }
            }
        }
    });
}

function lerTabela(result) {
    ObjAgendamento = result;
    document.getElementById("target").innerHTML = '<table id="tabAgendamento" name="tabAgendamento" class="table table-hover"><thead class="thead-dark"><tr><th>Servi\xE7o</th><th>Data</th><th>Horario</th><th>Duracao</th><th>Valor</th><th>Funcionario</th><th>Edit</th></tr></thead></table>';
    var table = document.getElementById("tabAgendamento");
    if (ObjAgendamento != null) {
        if (ObjAgendamento.length > 0) {
            for (var i = 0; i < ObjAgendamento.length; i++) {

                var row = table.insertRow(-1); //Insere no ultimo registro
                var cellServico = row.insertCell(0);//Nome Servico
                var cellData = row.insertCell(1);//Data
                var cellHora = row.insertCell(2);//Hora
                var cellDuracao = row.insertCell(3);//Duracao
                var cellValor = row.insertCell(4);//Valor
                var cellFuncionario = row.insertCell(5);//Funcionario
                var cancelarAgendamento = row.insertCell(6);
                cellServico.innerHTML = ObjAgendamento[i].servico.nome;
                cellData.innerHTML = ObjAgendamento[i].dataAgendamento;
                cellHora.innerHTML = ObjAgendamento[i].horaAgendamento;
                cellDuracao.innerHTML = ObjAgendamento[i].servico.duracao.seconds / 60+ " minutos";
                cellValor.innerHTML = ObjAgendamento[i].servico.valor;
                cellFuncionario.innerHTML = ObjAgendamento[i].funcionario.nomePessoa;
                cancelarAgendamento.innerHTML =
                        "<form name='formCancelar' action='" + site + "/CancelarAgendamento' method='POST'><input type='hidden' name='horaAgendamento' value=" + ObjAgendamento[i].horaAgendamento + "><input type='hidden' name='dataAgendamento' value=" + ObjAgendamento[i].dataAgendamento + "><a href='#' onclick='submit()'><i class='nav-icon fas fa-edit'></a></form>";

            }

            sweet("  Agendamentos carregados", "success", 1500);
        } else {
            sweet(" Não há agendamentos", "info", 3000);
        }
    }

}

function sweet(title, type, timer) {
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: timer
    });
    Toast.fire({
        type: type,
        title: ' ' + title
    });
}

function submit() {
    document.formCancelar.submit();
}