<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>        
        <title>Esqueci minha senha - Agendamento Digital</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="util/imagens/favicon.png"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link href="estilos/util.css" rel="stylesheet" type="text/css"/>
        <link href="estilos/estiloLogin.css" rel="stylesheet" type="text/css"/>
        <!--===============================================================================================-->
        <c:set var="site" value="${'/AgendamentoDigital'}" scope="session"  />
    </head>
    <body>	
        <div class="limiter">
            <div class="container-login100 image-bg">                     

                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                    <form class="login100-form validate-form" method="POST" id="formLogin" name="formLogin" action="/AgendamentoDigital/ControleAcesso">
                        <span class="login100-form-title p-b-49">
                            Alterar Senha
                        </span>

                        <div class="wrap-input100 validate-input m-b-10" data-validate = "Necessário Email">
                            <span class="label-input100">Usuário</span>
                            <input class="input100" id="inputEmail" type="username" name="inputUsername" placeholder="Digite seu usuário">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-10" data-validate="Necessário Senha">
                            <span class="label-input100">Senha atual</span>
                            <input class="input100" id="inputPasswordAtual" type="password" name="inputPassword" placeholder="Digite sua senha">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-10" data-validate="Necessário Senha">
                            <span class="label-input100">Nova senha</span>
                            <input class="input100" id="inputPasswordNew" type="password" name="inputPassword" placeholder="Digite sua senha">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-10" data-validate="Necessário Senha">
                            <span class="label-input100">Repita a nova senha</span>
                            <input class="input100" id="inputPasswordNewConfirm" type="password" name="inputPassword" placeholder="Digite sua senha">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>



                        <p class="login-invalid p-b-49">
                            <c:if test="${msg!=null}">
                                <font color="red">${msg}</font>
                            </c:if>
                        </p>

                        <div class="container-login100-form-btn p-b-10" id="btnlogar">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block">
                                    <input type="submit" value="Alterar Senha" name="acao"/>
                                </a>
                            </div>
                        </div>

                        <div class="container-login100-form-btn" id="btnvoltar">
                            <div class="wrap-login100-form-btn">
                                <div class="signin100-form-bgbtn login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block" href="${site}/index.jsp">
                                    Cancelar
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div id="dropDownSelect1"></div>

        <!--===============================================================================================-->
        <script src="scripts/jquery.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="vendor/animsition/js/animsition.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="vendor/bootstrap/js/popper.js" type="text/javascript"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="vendor/select2/select2.min.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="vendor/daterangepicker/moment.min.js" type="text/javascript"></script>
        <script src="vendor/daterangepicker/daterangepicker.js" type="text/javascript"></script>
        <!--===============================================================================================-->
        <script src="vendor/countdowntime/countdowntime.js" type="text/javascript"></script>
        <!--===============================================================================================-->

    </body>
</html>