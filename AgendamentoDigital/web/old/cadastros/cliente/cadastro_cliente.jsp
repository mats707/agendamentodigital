<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cadastro de Cliente</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- JavaScript -->
        <!--<script src="//code.jquery.com/jquery-1.12.4.js"></script>-->
        <script src="../../scripts/jquery.js" type="text/javascript"></script>
        <script src="../../assets/js/core/jquery.min.js" type="text/javascript"></script>
        <script src="cadastro_cliente.js" type="text/javascript"></script>
        <script src="../../scripts/frm_cadastro_cliente.js" type="text/javascript"></script>
        <script src="../../scripts/jquery.maskedinput.js" type="text/javascript"></script>
        <script src="../../scripts/jquery.maskedinput.min.js" type="text/javascript"></script>

        <script src="../../scripts/jquery-ui.min.js" type="text/javascript"></script>
        <link href="../../scripts/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../scripts/jquery-ui.min.css" rel="stylesheet" type="text/css"/>

        <!-- Bootsrap -->
        <!--<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <!-- Meus Estilos -->
        <link href="../../estilos/cadastro_cliente.css" rel="stylesheet" type="text/css"/>

        <!-- Favicon -->
        <link rel="shortcut icon" href="../../util/imagens/favicon.png" />

    </head>
    <body>

        <form action="/AgendamentoDigital/api/Cliente/Cadastrar" method="POST" class="form-horizontal">
            <!--<fieldset>-->
                <div class="panel panel-primary">
                    <div class="panel-heading">Cadastro de Cliente</div>

                    <div class="panel-body">
                        <div class="form-group">
                            <div class="col-md-11 control-label">
                                <p class="help-block"><h11>*</h11> Campo Obrigatório </p>
                            </div>
                        </div>

                        <div class="form-group">

                            <!-- inputUsername && inputPassword-->

                            <label class="col-md-2 control-label">Usuário <h11>*</h11></label>
                            <div class="col-md-7">
                                <div class="input-group">

                                    <span id="span-user" class="input-group-addon glyphicon-span" title="Digitar Usuário"><i class="glyphicon glyphicon-user" title="Digitar Usuário"></i></span>
                                    <input id="inputUsername" name="inputUsername" class="form-control" type="username" placeholder="Digite um usuário" data-nome="">
                                    <span id="verificaUsuario" name="verificaUsuario" class="input-group-addon btn" title="VERIFICAR SE USUÁRIO ESTÁ DISPONÍVEL">Verificar</span>

                                    <span id="span-lock" class="input-group-addon glyphicon-span" title="Digitar Senha"><i class="glyphicon glyphicon-lock" title="Digitar Senha"></i></span>
                                    <span class="input-group-addon">Senha</span>
                                    <input id="inputPassword" name="inputPassword" class="form-control" placeholder="" required="" type="password" placeholder="Digite uma senha"  data-view="0" disabled>
                                    <span id="span-eye" class="input-group-addon glyphicon-span-link btn" title="Visualizar Senha">
                                        <i id="eyeopenpassword" class="glyphicon glyphicon-eye-open" style="display: none;" title="Digitar Senha"></i>
                                        <i id="eyeclosepassword" class="glyphicon glyphicon-eye-close" title="Digitar Senha"></i>
                                    </span>

                                </div>

                                <small id="msgverificaUsuario" class="col-md-2 form-text text-muted verificaUsuario"></small>
                                <!--<select required id="cmbprofissao" class="form-control">
                                    <option value="0">Selecione uma profissão</option>
                                </select>-->
                            </div>
                        </div>

                        <!-- inputName -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="prependedtext">Nome <h11>*</h11></label>  
                            <div class="col-md-8">
                                <input id="inputName" name="inputName" placeholder="Nome completo" class="form-control input-md" required="" type="text" onkeypress="return Onlychars(event);">
                            </div>
                        </div>

                        <!-- cmbtipodocumento && inputNrdoc -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="radios">Tipo de Documento <h11>*</h11></label>
                            <div class="col-md-3">
                                <select id="cmbtipodocumento" name="cmbtipodocumento" class="form-control">
                                    <option value="0">Selecione um documento</option>
                                </select>
                            </div>

                            <label class="col-md-2 control-label" for="prependedtext">Número Documento <h11>*</h11></label>  
                            <div class="col-md-3">
                                <input id="inputNrdoc" name="inputNrdoc" placeholder="Apenas números" class="form-control input-md" required="" type="text" data-cmbtipodocumento="0" data-mascara="0">
                            </div>
                        </div>

                        <!-- cmbtipocontato && inputNrcontato -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="radios">Tipo de Contato <h11>*</h11></label>
                            <div class="col-md-3">
                                <select id="cmbtipocontato"  name="cmbtipocontato" class="form-control">
                                    <option value="0">Selecione um tipo de contato</option>
                                </select>
                            </div>

                            <label class="col-md-2 control-label" for="prependedtext">Número Contato <h11>*</h11></label>  
                            <div class="col-md-3">
                                <input id="inputNrcontato" name="inputNrcontato" placeholder="Apenas números" class="form-control input-md" required="" type="text" data-cmbtipocontato="0" data-mascara="0">
                            </div>
                        </div>

                        <div class="form-group">


                            <!-- inputDataNasc -->

                            <label class="col-md-2 control-label" for="prependedtext">Nascimento <h11>*</h11></label>  
                            <div class="col-md-2">

                                <input id="inputDataNasc" name="inputDataNasc" placeholder="DD/MM/AAAA" class="form-control input-md" required="" type="text" maxlength="10">

                                <small id="idade" class="col-md-2 form-text text-muted idade"></small>

                            </div>

                            <!-- cmbsexo -->

                            <label class="col-md-1 control-label" for="radios">Sexo <h11>*</h11></label>
                            <div class="col-md-2">
                                <select id="cmbsexo" name="cmbsexo" class="form-control"></select>
                            </div>
                        </div> 

                        <!-- inputEmail -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="prependedtext">Email <h11>*</h11></label>
                            <div class="col-md-5">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                    <input id="inputEmail" name="inputEmail" class="form-control" placeholder="email@email.com" required="" type="text">
                                </div>
                            </div>
                        </div>


                        <!-- cep -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="CEP">CEP <h11>*</h11></label>
                            <div class="col-md-2">
                                <input id="cep" name="cep" placeholder="Apenas números" class="form-control input-md" required="" value="" type="search" maxlength="9" pattern="\d{5}\-\d{3}" OnKeyPress="formatarCampo('nnnnn-nnn', this);return Onlynumbers(event);">
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-primary" onclick="pesquisacep(cep.value)">Pesquisar</button>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="prependedtext">Endereço</label>
                            <!-- rua -->
                            <div class="col-md-6">
                                <div class="input-group">
                                    <span class="input-group-addon">Rua</span>
                                    <input id="rua" name="rua" class="form-control" placeholder="" required="" readonly="readonly" type="text">
                                </div>

                            </div>
                            <!-- numero -->
                            <div class="col-md-2">
                                <div class="input-group">
                                    <span class="input-group-addon">Nº <h11>*</h11></span>
                                    <input id="numero" name="numero" class="form-control" placeholder="Digite o número" required="" disabled type="text">
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="prependedtext"></label>

                            <!-- bairro -->
                            <div class="col-md-3">
                                <div class="input-group">
                                    <span class="input-group-addon">Bairro</span>
                                    <input id="bairro" name="bairro" class="form-control" placeholder="" required="" readonly="readonly" type="text">
                                </div>

                            </div>
                            <!-- cidade -->
                            <div class="col-md-3">
                                <div class="input-group">
                                    <span class="input-group-addon">Cidade</span>
                                    <input id="cidade" name="cidade" class="form-control" placeholder="" required=""  readonly="readonly" type="text">
                                </div>

                            </div>

                            <!-- estado -->
                            <div class="col-md-2">
                                <div class="input-group">
                                    <span class="input-group-addon">Estado</span>
                                    <input id="estado" name="estado" class="form-control" placeholder="" required=""  readonly="readonly" type="text">
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="prependedtext"></label>
                            <!-- complemento -->
                            <div class="col-md-4">
                                <div class="input-group">
                                    <span class="input-group-addon">Complemento</span>
                                    <input id="complemento" name="complemento" class="form-control" placeholder="Ex: Casa 3; Apto. 256" required="" disabled type="text">
                                </div>

                            </div>
                        </div>

                        <!-- cmbestadocivil -->
                        <div class="form-group">
                            <label class="col-md-2 control-label">Estado Civil <h11>*</h11></label>
                            <div class="col-md-3">
                                <select required id="cmbestadocivil" name="cmbestadocivil" class="form-control">
                                    <option value="0">Selecione um estado civil</option>
                                </select>
                            </div>

                            <!-- Prepended checkbox -->

                            <label class="col-md-1 control-label">Filhos <h11>*</h11></label>
                            <div class="col-md-3">
                                <div class="input-group">
                                    <!-- filhos -->
                                    <span class="input-group-addon">     
                                        <label class="radio-inline" for="radios-0">
                                            <input type="radio" name="filhos" id="filhos" value="nao" onclick="desabilita('filhos_qtd');" required>
                                            Não
                                        </label> 
                                        <label class="radio-inline" for="radios-1">
                                            <input type="radio" name="filhos" id="filhos" value="sim" onclick="habilita('filhos_qtd');">
                                            Sim
                                        </label>
                                    </span>
                                    <!-- filhos_qtd -->
                                    <input id="filhos_qtd" name="filhos_qtd" class="form-control" type="number" placeholder="Quantos?" min="1" max="10" disabled>

                                </div>

                            </div>
                        </div>


                        <!-- cmbescolaridade -->
                        <div class="form-group">

                            <label class="col-md-2 control-label">Escolaridade <h11>*</h11></label>
                            <div class="col-md-3">
                                <select required id="cmbescolaridade" name="cmbescolaridade" class="form-control">
                                    <option value="0">Selecione uma escolaridade</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">

                            <!-- inputBuscarProfissao && inputProfissao -->

                            <label class="col-md-2 control-label">Buscar Profissão <h11>*</h11></label>
                            <div class="col-md-8">
                                <div class="input-group">

                                    <span id="span-search" class="btn input-group-addon glyphicon-span-link" title="Buscar Profissão"><i class="glyphicon glyphicon-search" title="Buscar Profissão"></i></span>
                                    <input id="inputBuscarProfissao" name="inputBuscarProfissao" class="form-control" type="text" placeholder="Digite sua profissão">

                                    <span id="span-edit" class="btn input-group-addon glyphicon-span-link" title="Editar Profissão"><i class="glyphicon glyphicon-pencil" title="Editar Profissão"></i></span>
                                    <span class="input-group-addon">Profissão</span>
                                    <input id="inputProfissao" name="inputProfissao" class="form-control" placeholder="" required=""  readonly="readonly" type="text" data-id="" data-nome="">
                                </div>
                                <!--<select required id="cmbprofissao" class="form-control">
                                    <option value="0">Selecione uma profissão</option>
                                </select>-->
                            </div>
                        </div>

                        <!-- Button (Double) -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="Cadastrar"></label>
                            <div class="col-md-8">
                                <button id="btnCadastrar" name="btnCadastrar" class="btn btn-success" type="Submit">Cadastrar</button>
                                <button id="btnCancelar" name="btnCancelar" class="btn btn-danger" data-toggle="modal" data-target="#cancelModal" type="Button">Cancelar</button>
                                <button id="btnLimpar" name="btnLimpar" class="btn btn-secondary" type="Reset">Limpar Formulário</button>
                            </div>
                        </div>

                    </div>
                </div>


            <!--</fieldset>-->
        </form>

        <!-- cancel Modal-->
        <div class="modal fade" id="cancelModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Cancelar Cadastro de Cliente?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Selecione Sair para retornar a página principal.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar ao Cadastro</button>
                        <a class="btn btn-primary" href="../../paginas/principal.jsp">Sair</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

