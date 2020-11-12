$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    $.fn.setCursorPosition = function (pos) {
        this.each(function (index, elem) {
            if (elem.setSelectionRange) {
                elem.setSelectionRange(pos, pos);
            } else if (elem.createTextRange) {
                var range = elem.createTextRange();
                range.collapse(true);
                range.moveEnd('character', pos);
                range.moveStart('character', pos);
                range.select();
            }
        });
        return this;
    };

    $("input#inputNrcontato").on('click : focus', function () {
        $("input#inputNrcontato").setCursorPosition(0);
    });
    $("input#inputNrdoc").on('click : focus', function () {
        $("input#inputNrdoc").setCursorPosition(0);
    });
    $("input#inputDataNasc").on('click : focus', function () {
        $("input#inputDataNasc").setCursorPosition(0);
    });
    $("input#cep").on('click : focus', function () {
        $("input#cep").setCursorPosition(0);
    });

    $('input#inputDataNasc').mask('99/99/9999');
    $('input#cep').mask('99999-999');



    campoemail();

    function campoemail() {

        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/api/TipoContato/BuscarNome/Email', //lugar onde a servlet est·
            type: 'GET',
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {
                        $("input#inputEmail").attr("pattern", String(Obj.pattern));
                        $("input#inputEmail").attr("maxlength", String(Obj.maxlength));
                    }
                }

            }
        });

    }

    enchesexo();

    function enchesexo() {
        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/listarSexo', //lugar onde a servlet est·
            type: 'POST',
            data: {
                oper: "1"
            },
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {

                        for (var i = 0; i < Obj.length; i++) {
                            $("#cmbsexo").append("<option value='" + Obj[i].idSexo + "'>" + Obj[i].nome + "</option>");
                        }
                    }
                }

            }
        });

    }

    enchetipodocumento();

    function enchetipodocumento() {
        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/api/TipoDocumento/Listar', //lugar onde a servlet est·
            type: 'GET',
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {

                        for (var i = 0; i < Obj.length; i++) {
                            $("#cmbtipodocumento").append("<option value='" + Obj[i].idTipoDocumento + "'>" + Obj[i].nome + "</option>");
                        }
                    }
                }

            }
        });

    }

    enchetipocontato();

    function enchetipocontato() {
        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/api/TipoContato/Listar', //lugar onde a servlet est·
            type: 'GET',
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {

                        for (var i = 0; i < Obj.length; i++) {
                            $("#cmbtipocontato").append("<option value='" + Obj[i].idTipoContato + "'>" + Obj[i].nome + "</option>");
                        }
                    }
                }

            }
        });

    }


    encheestadocivil();

    function encheestadocivil() {
        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/api/EstadoCivil/Listar', //lugar onde a servlet est·
            type: 'GET',
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {

                        for (var i = 0; i < Obj.length; i++) {
                            $("#cmbestadocivil").append("<option value='" + Obj[i].idEstadoCivil + "'>" + Obj[i].nome + "</option>");
                        }
                    }
                }

            }
        });

    }


    encheescolaridade();

    function encheescolaridade() {
        //fazer a chamado da servlet
        $.ajax({
            url: nameproject + '/api/Escolaridade/Listar', //lugar onde a servlet est·
            type: 'GET',
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {

                        for (var i = 0; i < Obj.length; i++) {
                            $("#cmbescolaridade").append("<option value='" + Obj[i].idEscolaridade + "'>" + Obj[i].nome + "</option>");
                        }
                    }
                }

            }
        });

    }

    //Focar no input quando clicar no span
    $('#span-search').click(function () {
        $('#inputBuscarProfissao').focus();
    });

    //Permiti buscar o item novamente e limpa o valor anterior
    $('#span-edit').click(function () {

        $('#inputBuscarProfissao').val("");
        $("#inputProfissao").attr("data-id", "");
        $("#inputProfissao").attr("data-nome", "");
        habilita("inputBuscarProfissao");
        $('#inputBuscarProfissao').focus();
        limpaCampos();

    });

    // Atribui evento e funÁ„o para limpeza dos campos
    $('#inputBuscarProfissao').on('input', limpaCampos);

    $("#inputBuscarProfissao").autocomplete({
        minLength: 2,
        source: function (request, response) {
            $.ajax({
                url: nameproject + '/api/Profissao/BuscarNome/' + $("#inputBuscarProfissao").val(), //lugar onde a servlet est·
                dataType: "json",
                success: function (data) {
                    response(data);
                }
            });
        },
        focus: function (event, ui) {
            $("#inputBuscarProfissao").val(ui.item.nome);

            return false;
        },
        select: function (event, ui) {
            $("#inputBuscarProfissao").val(ui.item.nome);
            $("#inputProfissao").attr("data-id", ui.item.idProfissao);
            carregarDados();
            return false;
        }
    })
            .autocomplete("instance")._renderItem = function (ul, item) {
        return $("<li>")
                .append("<a><b> " + item.nome + " </b></a><br>")
                .appendTo(ul);
    };

    // FunÁ„o para carregar os dados da consulta nos respectivos campos
    function carregarDados() {
        var dados = $("#inputProfissao").attr("data-id");

        if (dados !== "" && dados.length >= 2) {
            $.ajax({
                url: nameproject + '/api/Profissao/BuscarId/' + dados, //lugar onde a servlet est·
                dataType: "json",
                success: function (data) {

                    //Atribui valores ao campo
                    $("#inputProfissao").attr("data-id", data.id);
                    $("#inputProfissao").attr("data-nome", data.nome);
                    $('#inputProfissao').val(data.nome);

                    //desativa o campo input para n√£o permitir a entrada
                    desabilita("inputBuscarProfissao");
                    $('#inputBuscarProfissao').val("Profiss√£o selecionada");

                }
            });
        }


    }
//     FunÁ„o para limpar os campos caso a busca esteja vazia
    function limpaCampos() {
        var busca = $('#inputBuscarProfissao').val();

        if (busca == "") {
            $('#inputProfissao').val('');

        }
    }

    $("select#cmbtipodocumento").change(function () {

        var i = document.getElementById('cmbtipodocumento').value;
        $("input#inputNrdoc").attr("data-cmbtipodocumento", i);

        document.getElementById('inputNrdoc').value = "";

        if (i == 0) {
            $("input#inputNrdoc").attr("pattern", "");
            $("input#inputNrdoc").attr("maxlength", "0");
            readOnly("input#inputNrdoc");
        } else {
            $("input#inputNrdoc").attr("maxlength", "20");
            exibe("input#inputNrdoc");

            //fazer a chamado da servlet
            $.ajax({
                url: nameproject + '/api/TipoDocumento/Buscar/' + i, //lugar onde a servlet est·
                type: 'GET',
                complete: function (e, xhr, result) {
                    if (e.readyState == 4 && e.status == 200) {
                        try { //Converte a resposta HTTP JSON em um objeto JavaScript
                            var Obj = eval("(" + e.responseText + ")");
                        } catch (err) { //
                            // Mostra Aviso
                            alert("Algo de errado aconteceu!");
                        }
                        if (Obj != null) {
                            $("input#inputNrdoc").attr("pattern", String(Obj.pattern));
                            $("input#inputNrdoc").attr("maxlength", String(Obj.maxlength));
                            $("input#inputNrdoc").attr("data-mascara", String(Obj.regex));
                            $("input#inputNrdoc").mask(String(Obj.regex));
                            console.log(String(Obj.regex));
                        }
                    }

                }
            });
        }
    }).change();

    $("select#cmbtipocontato").change(function () {

        var i = document.getElementById('cmbtipocontato').value;
        $("input#inputNrcontato").attr("data-cmbtipocontato", i);

        document.getElementById('inputNrcontato').value = "";

        if (i == 0) {
            $("input#inputNrcontato").attr("pattern", "");
            $("input#inputNrcontato").attr("maxlength", "0");
            readOnly("input#inputNrcontato");
        } else {
            $("input#inputNrcontato").attr("maxlength", "20");
            exibe("input#inputNrcontato");

            //fazer a chamado da servlet
            $.ajax({
                url: nameproject + '/api/TipoContato/Buscar/' + i, //lugar onde a servlet est·
                type: 'GET',
                complete: function (e, xhr, result) {
                    if (e.readyState == 4 && e.status == 200) {
                        try { //Converte a resposta HTTP JSON em um objeto JavaScript
                            var Obj = eval("(" + e.responseText + ")");
                        } catch (err) { //
                            // Mostra Aviso
                            alert("Algo de errado aconteceu!");
                        }
                        if (Obj != null) {
                            $("input#inputNrcontato").attr("pattern", String(Obj.pattern));
                            $("input#inputNrcontato").attr("maxlength", String(Obj.maxlength));
                            $("input#inputNrcontato").attr("data-mascara", String(Obj.regex));
                            $("input#inputNrcontato").mask(String(Obj.regex));
                        }
                    }

                }
            });
        }
    }).change();

    $("#inputDataNasc").on({

        blur: function () {
            showhide('inputDataNasc');
        },

        keyup: function () {
            showhide('inputDataNasc');
        }

    });

    $('#verificaUsuario').click(function () {

        var msg = "Tente Novamente!";

        var username = document.getElementById("inputUsername").value;
        var div = document.getElementById("msgverificaUsuario");

        div.innerHTML = '';

        console.log(username);

        $.ajax({
            url: nameproject + '/api/Usuario/BuscarNome/' + username, //lugar onde a servlet est·
            type: "GET",
            complete: function (e, xhr, result) {
                if (e.readyState == 4 && e.status == 200) {
                    try { //Converte a resposta HTTP JSON em um objeto JavaScript
                        var Obj = eval("(" + e.responseText + ")");
                    } catch (err) { //
                        // Mostra Aviso
                        alert("Algo de errado aconteceu!");
                    }
                    if (Obj != null) {
                        console.log('obj: ' + String(Obj.login));
                        console.log('usuario: ' + username);
                        if (String(Obj.login) !== username) {
                            div.style.display = "contents";
                            div.style.color = "green";
                            habilita('inputPassword');
                            div.innerHTML = "Este usu·rio est· dispon√≠vel!";
                        } else {
                            div.style.display = "contents";
                            div.style.color = "red";
                            desabilita('inputPassword');
                            div.innerHTML = "Usu·rio j· foi cadastrado! Tente outro!";
                        }
                    }
                }
            }
        });

        console.log(msg);
    });

    $('#span-eye').click(function () {

        if ($("input#inputPassword").attr("data-view") == "0") {
            $("input#inputPassword").attr("type", "text");
            $("input#inputPassword").attr("data-view", "1");
            document.getElementById("eyeopenpassword").style.display = "inline";
            document.getElementById("eyeclosepassword").style.display = "none";
        } else {
            $("input#inputPassword").attr("type", "password");
            $("input#inputPassword").attr("data-view", "0");
            document.getElementById("eyeopenpassword").style.display = "none";
            document.getElementById("eyeclosepassword").style.display = "inline";

        }
    });


});