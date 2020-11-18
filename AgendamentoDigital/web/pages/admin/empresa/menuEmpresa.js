//variaveis Globais
var countTelefone = 0;

$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";
    carregarEmpresa();

    // Funcao para carregar os dados da consulta nos respectivos campos
    function carregarEmpresa() {
        $.ajax({
            url: nameproject + '/api/Empresa/Menu/Encontrar', //lugar onde a servlet está
            type: "GET",
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");

                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                        alert(err);
                    }
                    if (Obj != null) {
                        $("#nome").val(Obj.nome);
                        $("#timepickerAbertura").val(Obj.horaInicialTrabalho);
                        $("#timepickerEncerramento").val(Obj.horaFinalTrabalho);
                        //$("#intervaloAgendamentoGeralServico").val(Obj.intervaloAgendamentoGeralServico);
                        $('#intervaloAgendamentoGeralServico').data('durationPicker').setValue(Obj.intervaloAgendamentoGeralServico * 60);
                        $('#periodoMinimoCancelamento').data('durationPicker').setValue(Obj.periodoMinimoCancelamento * 60);
                        $("#diaSemanaTrabalho").val(Obj.diaSemanaTrabalho);
                        for (var i = 0; i < Obj.diaSemanaTrabalho.length; i++)
                        {
                            switch (Obj.diaSemanaTrabalho[i])
                            {
                                case 0:
                                    document.getElementById("DiaSemana0").checked = true;
                                    break;
                                case 1:
                                    document.getElementById("DiaSemana1").checked = true;
                                    break;
                                case 2:
                                    document.getElementById("DiaSemana2").checked = true;
                                    break;
                                case 3:
                                    document.getElementById("DiaSemana3").checked = true;
                                    break;
                                case 4:
                                    document.getElementById("DiaSemana4").checked = true;
                                    break;
                                case 5:
                                    document.getElementById("DiaSemana5").checked = true;
                                    break;
                                case 6:
                                    document.getElementById("DiaSemana6").checked = true;
                                    break;

                            }
                        }


                        $("#telefone").val(Obj.telefone);
                        document.getElementById("groupTelefone").innerHTML = "<label>Telefone(s):</label>";
                        for (var i = 0; i < Obj.telefone.length; i++)
                        {
                            console.log("Telefone:" + i);
                            document.getElementById("groupTelefone").innerHTML += "<input id='telefone-" + i + "' value=" + Obj.telefone[i] + " name='telefone' class='form-control col-3 mask' data-mask='telefone'>";
                            countTelefone++;
                            console.log(countTelefone);
                        }

                        $("#email").val(Obj.email);

                        //timePicker
                        $("#timepickerAbertura").val(Obj.horaInicialTrabalho.split(':')[0] + ":" + Obj.horaInicialTrabalho.split(':')[1]);
                        $("#timepickerEncerramento").val(Obj.horaFinalTrabalho.split(':')[0] + ":" + Obj.horaFinalTrabalho.split(':')[1]);
                    }

                    for (var j = 0; j < countTelefone; j++) {
                        $("#telefone-" + j).focusin();
                        $("#telefone-" + j).focusout();
                    }
                    //document.getEmelmentById("nome").innerHTML = Obj.nome;
                }
            }
        });

    }
});

function changeValue() {
    var slider = document.getElementById("intervaloAgendamentoGeralServico");
    var output = document.getElementById("spanDuracao");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
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


