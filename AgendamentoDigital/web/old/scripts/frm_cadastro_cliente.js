var nameproject = "/AgendamentoDigital";

function limpa_formulario_cep() {
    //Limpa valores do formulário de cep.
    document.getElementById('cep').value = ("");
    document.getElementById('rua').value = ("");
    document.getElementById('bairro').value = ("");
    document.getElementById('cidade').value = ("");
    document.getElementById('estado').value = ("");
    document.getElementById('numero').value = ("");
    document.getElementById('complemento').value = ("");
    desabilita('numero');
    desabilita('complemento');
}

function meu_callback(conteudo) {
    if (!("erro" in conteudo)) {
        //Atualiza os campos com os valores.
        document.getElementById('rua').value = (conteudo.logradouro);
        document.getElementById('bairro').value = (conteudo.bairro);
        document.getElementById('cidade').value = (conteudo.localidade);
        document.getElementById('estado').value = (conteudo.uf);

        document.getElementById('numero').value = ("");
        document.getElementById('complemento').value = ("");

        habilita('numero');
        $("numero").focus();
        
        habilita('complemento');
        
    } //end if.
    else {
        //CEP não Encontrado.
        limpa_formulario_cep();
        alert("CEP não encontrado.");
        $("numero").removeAttr("readonly");
        $("complemento").removeAttr("readonly");
    }
}

function pesquisacep(valor) {

    //Nova variável "cep" somente com dígitos.
    var cep = valor.replace(/\D/g, '');

    //Verifica se campo cep possui valor informado.
    if (cep !== "") {

        //Expressão regular para validar o CEP.
        var validacep = /^[0-9]{8}$/;

        //Valida o formato do CEP.
        if (validacep.test(cep)) {

            //Preenche os campos com "..." enquanto consulta webservice.
            document.getElementById('rua').value = "...";
            document.getElementById('bairro').value = "...";
            document.getElementById('cidade').value = "...";
            document.getElementById('estado').value = "...";
            document.getElementById('numero').value = "...";
            document.getElementById('complemento').value = "...";

            //Cria um elemento javascript.
            var script = document.createElement('script');

            //Sincroniza com o callback.
            script.src = '//viacep.com.br/ws/' + cep + '/json/?callback=meu_callback';

            //Insere script no documento e carrega o conteúdo.
            document.body.appendChild(script);

        } //end if.
        else {
            //cep é inválido.
            limpa_formulario_cep();
            alert("Formato de CEP inválido.");
        }
    } //end if.
    else {
        //cep sem valor, limpa formulário.
        limpa_formulario_cep();
    }
}

function formatarCampo(mascara, documento) {
    var i = documento.value.length;
    var saida = mascara.substring(0, 1);
    var texto = mascara.substring(i);

    if (texto.substring(0, 1) != saida) {
        documento.value += texto.substring(0, 1);
    }
}


function idade() {
    var data = document.getElementById("inputDataNasc").value;
    var dia = data.substr(0, 2);
    var mes = data.substr(3, 2);
    var ano = data.substr(6, 4);
    var d = new Date();
    var ano_atual = d.getFullYear(),
            mes_atual = d.getMonth() + 1,
            dia_atual = d.getDate();

    ano = +ano,
            mes = +mes,
            dia = +dia;

    var idade = ano_atual - ano;

    if (mes_atual < mes || mes_atual == mes && dia_atual < dia) {
        idade--;
    }
    return idade;
}


function exibe(i) {
    $(i).removeAttr("readonly");
}

function readOnly(i) {
    $(i).attr("readonly", "readonly");
}

function desabilita(i) {
    document.getElementById(i).disabled = true;
}
function habilita(i)
{
    document.getElementById(i).disabled = false;
}


function showhide(id)
{
    var date = document.getElementById(id);
    var div = document.getElementById("idade");
    var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])      [\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;

    div.innerHTML = '';

    if (!(date.value.match(RegExPattern))) {
        div.style.display = "contents";
        div.innerHTML = 'DATA INVÁLIDA! Tente outra data! (dd/mm/yyyy)';
        date.focus();
    } else {
        if (idade() >= 18) {
            div.style.display = "contents";
            div.innerHTML = div.innerHTML + 'Você tem ' + idade() + ' anos!';
        } else if (idade() < 18) {
            div.style.display = "contents";
            div.innerHTML = div.innerHTML + 'Necessário ter mais de 18 anos!<br>(Idade atual: ' + idade() + ' anos)';
        }
    }
}

function Onlynumbers(e)
{
    var tecla = new Number();
    if (window.event) {
        tecla = e.keyCode;
    } else if (e.which) {
        tecla = e.which;
    } else {
        return true;
    }
    if (!(tecla >= 48 && tecla <= 57)) {
        return false;
    }
}

function Onlychars(e)
{
    var tecla = new Number();
    if (window.event) {
        tecla = e.keyCode;
    } else if (e.which) {
        tecla = e.which;
    } else {
        return true;
    }
    if (tecla >= 33 && tecla <= 64
            || tecla >= 91 && tecla <= 93
            || tecla >= 123 && tecla <= 159
            || tecla >= 162 && tecla <= 191
            ) {
        return false;
    }
}