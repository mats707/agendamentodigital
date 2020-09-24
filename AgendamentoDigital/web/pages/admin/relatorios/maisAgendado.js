$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    var tabMaisAgendado = document.getElementById('tabMaisAgendado');

    for (var i; i < tabMaisAgendado.rows.length; i++)
    {
        tabMaisAgendado.rows[i].onclick = function ()
        {
            var agenda = this.cells[1].innerHTML;
            carregarMaisAgendado();
        };
    }
    // Função para carregar os dados da tabela em seus campos
    function carregarMaisAgendado() {
        $.ajax({
            url: nameproject + '/api/Relatorios/Servicos/MaisAgendado',
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
                    if (obj != null)
                    {
                        $("#idServico").val(Obj.idServico);
                        $("#nomeServico").val(Obj.nomeServico);
                        $("$contator").val(Obj.count);
                    }
                }
            }
        })
    }

});




function lerJson(result) {
    $("#target").html('<table id="tabMaisAgendado" class="table table-bordered"><thead><tr><th style="width: auto">ID</th><th style="width: 100%">Serviço</th><th style="width: auto">Vezes realizadas</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</tr>'
    }).join('\n') + '</table>');
}
