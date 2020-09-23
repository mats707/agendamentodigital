$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    var tabMaisAgendado = document.getElementById('tabMaisTrabalhado');

    for (var i; i < tabMaisAgendado.rows.length; i++)
    {
        tabMaisAgendado.rows[i].onclick = function ()
        {
            var relatorio = this.cells[1].innerHTML;
            carregarRelatorio();
        };
    }
    // Função para carregar os dados da tabela em seus campos
    function carregarRelatorio() {
        $.ajax({
            url: nameproject + '/api/Relatorios/Servicos/MaisTrabalhado',
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
                        $("#nomeServico").val(Obj.nomeFuncionario);
                        $("$contator").val(Obj.count);
                    }
                }
            }
        })
    }

});




function lerJson(result) {
    $("#target").html('<table id="tabMaisTrabalhado" class="table table-bordered"><thead><tr><th style="width: auto">Funcionario</th><th style="width: auto">Vezes realizadas</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</tr>'
    }).join('\n') + '</table>');
}
