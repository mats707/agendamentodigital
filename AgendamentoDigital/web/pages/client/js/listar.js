var nameproject = window.location.pathname.split('/')[1];
console.log(nameproject);

var site = "/AgendamentoDigital"

function chamaCarregar()
{
    
    var status = document.getElementById('selectStatus').value;
    carregarMaisAgendado(idCliente, status);
}
function carregarMaisAgendado(idCliente, status) {
    if (status == null)
    {
        status = 'AGUARDANDOATENDIMENTO';
    }
    var URL = 'api/Relatorios/Agendamentos/Cliente/' + idCliente + '/' + status;
    $.ajax({
        url: URL,
        type: "GET",
        complete: function (e, xrh, result) {
            if (e.readyState == 4 && e.status == 200)
            {
                try {
                    var Obj = eval("(" + e.responseText + ")");
                    console.log(e.responseTex);
                } catch (err)
                {
                    alert("Sistema encontrou um erro");
                    alert(err);
                }
                if (Obj != null)
                {
                    console.log(Obj);
                    lerTabela(Obj);
                }
            }
        }
    });
}


function lerTabela(result) {
    ObjAgendamento = result;
    console.log(ObjAgendamento);
    document.getElementById("target").innerHTML = '<table id="tabAgendamento" name="tabAgendamento" class="table table-hover"><thead class="thead-dark">\n\
        <tr><th>Servi�o</th>\n\
            <th>Data</th>\n\
            <th>Horario</th>\n\
            <th>Dura��o</th>\n\
            <th>Valor</th>\n\
            <th>Funcion�rio</th>\n\
            <th>Situação</th>\n\
            <th>Cancelar</th>\n\
        </tr></thead></table>';
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
                var cellSituacao = row.insertCell(6); //Status do servico
                var cancelarAgendamento = row.insertCell(7);
                cellServico.innerHTML = ObjAgendamento[i].servico.nome;
                cellData.innerHTML = ObjAgendamento[i].dataAgendamento;
                cellHora.innerHTML = ObjAgendamento[i].horaAgendamento;
                cellDuracao.innerHTML = ObjAgendamento[i].servico.duracao.seconds / 60 + " minutos";
                cellValor.innerHTML = ObjAgendamento[i].servico.valor;
                cellFuncionario.innerHTML = ObjAgendamento[i].funcionario.nomePessoa;
                cellSituacao.innerHTML = ObjAgendamento[i].status;
                cancelarAgendamento.innerHTML =
                        "<form id='formCancelar-" + i + "' action='" + site + "/CancelarAgendamento' method='POST'>\n\
                            <input type='hidden' name='servico' value=" + ObjAgendamento[i].servico.idServico + ">\n\
                            <input type='hidden' name='funcionario' value=" + ObjAgendamento[i].funcionario.idFuncionario + ">\n\
                            <input type='hidden' name='horaAgendamento' value=" + ObjAgendamento[i].horaAgendamento + ">\n\
                            <input type='hidden' name='dataAgendamento' value=" + ObjAgendamento[i].dataAgendamento + ">\n\
                            <a id='btn-formCancelar-" + i + "' href='#' class='nav-link' onclick='sweetCancelar(this);'>\n\
                            <i class='nav-icon fas fa-times-circle'></a></form>";
            }
            if (window.location.pathname.split('/')[2] == "HomeCliente")
                sweet("  Agendamentos carregados", "success", 1500);
        } else {
            if (window.location.pathname.split('/')[2] == "HomeCliente")
                sweet(" N�o h� agendamentos", "info", 3000);
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

function sweetCancelar(element) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
        title: 'Tem certeza?',
        text: "Ap�s o cancelamento esse servi�o n�o poder� ser reagendando para esse mesmo hor�rio!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, cancelar!',
        cancelButtonText: 'N�o, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire({
                title: 'Solicita��o em andamento...',
                text: 'Aguarde ( 0-2 min )',
                type: 'info',
                showConfirmButton: false
            });
            submit(element);
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Seu agendamento est� seguro :)',
                    'error'
                    );
        }
    });
}

function submit(element) {
    var form = element.id.substr(4);
    console.log(form);
    document.getElementById(form).submit();
}