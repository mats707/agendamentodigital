

var nameproject = "/AgendamentoDigital";


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


function lerJson(result) {
    $("#target").html('<table id="tabFuncionarios" class="table table-bordered"><thead><tr><th style="width: auto">Funcion�rio</th><th style="width: auto">Vezes realizadas</th></tr></thead>' + $.map(result, function (d) {
        return '<tr><td>' + $.map(d, function (e) {
            return e;
        }).join('</td><td>') + '</tr>'
    }).join('\n') + '</table>');


}