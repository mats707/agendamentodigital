
var nameproject = "/AgendamentoDigital";

var taBloqueio = document.getElementById('tabBloqueio');
function carregarFuncionario() {
    $.ajax({
        url: nameproject + '/api/Funcionario/ListarCompleto',
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
                if (Obj !== null)
                {
//                    console.log(Obj.length)
//                    var comboFiltro = document.getElementById('filtroFuncionario');
//                    var optionFiltro = document.createElement('option');
//                    for (var x = 0; x < Obj.length; x++)
//                    {
//                        console.log("Pessoa:" + Obj[x].nomePessoa);
//                        console.log("ID:" + Obj[x].idFuncionario);
//                        optionFiltro.text = "" + Obj[x].nomePessoa;
//                        optionFiltro.value = "" + Obj[x].idFuncionario;
//                        comboFiltro.add(optionFiltro, x);
//                    }
                    var groupFiltro = document.getElementById("groupFiltro");
                    groupFiltro.innerHTML = '<select name="filtroFuncionario" id="filtroFuncionario"class="form-control select2 select2-danger" data-dropdown-css-class="select2-danger">';
                    var optionFiltro = document.getElementById("filtroFuncionario");
                    optionFiltro.innerHTML = " <option selected disabled >-- Selecione o funcionario --</option>";
                    for (var x = 0; x < Obj.length; x++)
                    {
                        optionFiltro.innerHTML += " <option value=" + Obj[x].idFuncionario + ">" + Obj[x].nomePessoa + "</option>";
                    }
                }
            }
        }
    });
}

function lerTabela(result) {
    ObjBloqueio = result;
    document.getElementById("target").innerHTML = '<table id="tabBloqueio" name="tabBloqueio" class="table table-hover"><thead class="thead-dark"><th>#</th><th>Funcionario</th><th>Data</th><th>Horario</th><th>Tempo bloqueado</th><th>Cancelar</th></tr></thead></table>';
    var table = document.getElementById("tabBloqueio");
    if (ObjBloqueio != null) {
        if (ObjBloqueio.length > 0) {
            for (var i = 0; i < ObjBloqueio.length; i++) {

                var row = table.insertRow(-1); //Insere no ultimo registro
                var numero = row.insertCell(0);
                var cellFuncionario = row.insertCell(1); //Funcionario
                var cellData = row.insertCell(2); //Data do bloqueio
                var cellHora = row.insertCell(3); //Horario do bloqueio
                var cellDuracao = row.insertCell(4); //Duracao
                var cancelar = row.insertCell(5);
                numero.innerHTML = i;
                cellFuncionario.innerHTML = ObjBloqueio[i].funcionario.nomePessoa;
                cellHora.innerHTML = ObjBloqueio[i].horaInicial;
                cellDuracao.innerHTML = ObjBloqueio[i].duracaoBloqueio + " minutos"; //Sem Bloqueio adapter .seconds / 60 
                cellData.innerHTML = ObjBloqueio[i].dataBloqueio;
                cancelar.innerHTML = "<form id='formCancelar-" + i + "' action='" + nameproject + "/CancelarBloqueio' method='POST'>"
                        + "\n\<input type='hidden' name='dataBloqueio' value=" + ObjBloqueio[i].dataBloqueio + ">"
                        + "\n\<input type='hidden' name='horaInicialBloqueio' value=" + ObjBloqueio[i].horaInicial + ">"
                        + "\n\<input type='hidden' name='funcionarioBloqueio' value=" + ObjBloqueio[i].funcionario.idFuncionario + ">"
                        + "\n\<a id='btn-formCancelar-" + i + "' href='#' class='nav-link' onclick='sweetCancelar(this);'>"
                        + "\n\<i class='nav-icon fas fa-times-circle' style='color:red'></a></form>";
            }

            sweet("  Lista de bloqueios carregados", "success", 1500);
        } else {
            sweet(" Não há bloqueios", "info", 3000);
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
        text: "Este bloqueio será apagado do sistema!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, cancelar!',
        cancelButtonText: 'Não, voltar!',
        reverseButtons: true
    }).then((result) => {
        console.log(result);
        if (result.value) {
            swalWithBootstrapButtons.fire(
                    'Solicitação concluída!',
                    'Clique em OK para prosseguir.',
                    'success'
                    );
            submit(element);
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire(
                    'Fechando...',
                    'Seu bloqueio está seguro :)',
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