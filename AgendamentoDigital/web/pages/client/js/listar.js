var nameproject = "/" + window.location.pathname.split('/')[1] + "/";
console.log(nameproject);

function carregarAgendamentos(idCliente) {
    var status = document.getElementById('selectStatus').value;
    if (status == null)
    {
        status = 'AGUARDANDOATENDIMENTO';
    }
    var URL = nameproject + 'api/Relatorios/Agendamentos/Cliente/' + idCliente + '/' + status;
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
    var page = window.location.pathname.split('/')[2]+"/"+window.location.pathname.split('/')[3];
    document.getElementById("target").innerHTML = '<table id="tabAgendamento" name="tabAgendamento" class="table table-hover"><thead class="thead-dark">\n\
        <tr><th>Serviço</th>\n\
            <th>Data</th>\n\
            <th>Horário</th>\n\
            <th>Duração</th>\n\
            <th>Valor</th>\n\
            <th>Funcionário</th>\n\
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
                cellValor.innerHTML = "R$" + ObjAgendamento[i].servico.valor.toFixed(2);
                cellFuncionario.innerHTML = ObjAgendamento[i].funcionario.nomePessoa;
                cellSituacao.innerHTML = ObjAgendamento[i].status;
                cancelarAgendamento.innerHTML =
                        "<form id='formCancelar-" + i + "' action='" + nameproject + "CancelarAgendamento' method='POST'>\n\
                            <input type='hidden' name='servico' value=" + ObjAgendamento[i].servico.idServico + ">\n\
                            <input type='hidden' name='funcionario' value=" + ObjAgendamento[i].funcionario.idFuncionario + ">\n\
                            <input type='hidden' name='horaAgendamento' value=" + ObjAgendamento[i].horaAgendamento + ">\n\
                            <input type='hidden' name='dataAgendamento' value=" + ObjAgendamento[i].dataAgendamento + ">\n\
                            <a id='btn-formCancelar-" + i + "' href='#' class='nav-link' onclick='sweetCancelar(this);'>\n\
                            <i class='nav-icon fas fa-times-circle'></a></form>";
            }
            if (page == "Funcionario/Home")
                sweet("  Agendamentos carregados", "success", 1500);
        } else {
            if (page == "Funcionario/Home")
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
        text: "Após o cancelamento esse serviço não poderá ser reagendando para esse mesmo horário!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, cancelar!',
        cancelButtonText: 'Não, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire({
                title: 'Solicitação em andamento...',
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
                    'Seu agendamento está seguro :)',
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