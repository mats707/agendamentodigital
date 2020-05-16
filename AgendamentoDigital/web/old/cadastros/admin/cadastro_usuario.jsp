<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>        
        <title>Cadastro Usuário - Agendamento Digital</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href= "${site}/old/util/imagens/favicon.png"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${site}/old/vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link href="${site}/old/estilos/util.css" rel="stylesheet" type="text/css"/>
        <link href="${site}/old/estilos/estiloLogin.css" rel="stylesheet" type="text/css"/>
        <!--===============================================================================================-->
    </head>
    <body>	
        <div class="limiter">
            <div class="container-login100 image-bg">                     

                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                    <form class="login100-form validate-form" method="POST" id="formRegister" name="formRegister" action="${site}/old/CadastrarUsuario">
                        <span class="login100-form-title p-b-49">
                            Cadastrar Usuário
                        </span>

                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Necessário Email">
                            <span class="label-input100">Email</span>
                            <input class="input100" id="email" type="email" name="email" placeholder="Digite seu email">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-23" data-validate="Necessário Senha">
                            <span class="label-input100">Senha</span>
                            <input class="input100" id="senha" type="password" name="senha" placeholder="Digite sua senha">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        
                        <div class="wrap-input100 validate-input m-b-23" data-validate="Necessário Senha">
                            <span class="label-input100">Celular</span>
                            <input class="input100" id="celular" type="text" name="celular" placeholder="Apenas números"  maxLenght="15">

                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        
                        <div class="wrap-input100 validate-input" data-validate="Necessário Perfil">
                            <span class="label-input100">Perfil</span>
                            <div class="input100">
                                <div class="custom-control custom-radio custom-control-inline center-radio-input100">
                                    <input type="radio" id="perfil1" name="perfil" class="custom-control-input" value="COMUM">
                                    <label class="custom-control-label" for="perfil1">Comum</label>
                                    <input type="radio" id="perfil2" name="perfil" class="custom-control-input" value="ADMINISTRADOR">
                                    <label class="custom-control-label" for="perfil2">Administrador</label>
                                </div>
                            </div>
                            <span class="focus-input100" data-symbol="&#xf106;"></span>
                        </div>

                        <p class="email-invalid">
                            <c:if test="${msg!=null}">
                                <font color="${colorMsg}">${msg}</font>
                            </c:if>
                        </p>

                        <div class="text-right p-t-8 p-b-31">
                            <a></a>
                        </div>

                        <div class="container-login100-form-btn" id="btncadastrar">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block">
                                    <input id="cadastrarUsuario" type="submit" value="Cadastrar" name="acao"/>
                                </a>
                            </div>
                        </div>

                        <div class="text-right p-t-8 p-b-5">
                            <a></a>
                        </div>

                        <div class="container-login100-form-btn" id="btnvoltar">
                            <div class="wrap-login100-form-btn">
                                <div class="signin100-form-bgbtn login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block" href="${site}/old/paginas/admin/principal.jsp">
                                    <!--<input id="cadastrarUsuario" type="principal" href="/AgendamentoDigital/paginas/principal.jsp" value="Voltar para página principal"/>-->
                                    Voltar para página principal
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div id="dropDownSelect1"></div>

        <!--===============================================================================================-->
        <script src="cadastro_usuario.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../scripts/jquery.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../vendor/animsition/js/animsition.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../vendor/bootstrap/js/popper.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../vendor/select2/select2.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../vendor/daterangepicker/moment.min.js" type="text/javascript"></script>
        <script src="../../vendor/daterangepicker/daterangepicker.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="../../vendor/countdowntime/countdowntime.js" type="text/javascript"></script>
        <!--===============================================================================================-->

    </body>
</html>