<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>        
        <title>Login - Agendamento Digital</title>
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
                    <form class="login100-form validate-form" method="POST" id="formLogin" name="formLogin" action="./ControleAcesso">
                        <span class="login100-form-title p-b-49">
                            Login
                        </span>

                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Necessário Email">
                            <span class="label-input100">Email</span>
                            <input class="input100" id="inputEmail" type="email" name="inputEmail" placeholder="Digite seu email">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Necessário Senha">
                            <span class="label-input100">Senha</span>
                            <input class="input100" id="inputPassword" type="password" name="inputPassword" placeholder="Digite sua senha">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>



                        <p class="login-invalid">
                            <c:if test="${msg!=null}">
                                <font color="red">${msg}</font>
                            </c:if>
                        </p>

                        <div class="text-right p-t-8 p-b-31">
                            <a href="forgot-password.jsp">
                                Esqueceu sua senha?
                            </a>
                        </div>

                        <div class="container-login100-form-btn" id="btnlogar">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block">
                                    <input type="submit" value="Entrar" name="acao"/>
                                </a>
                            </div>
                        </div>

                        <div class="text-right p-t-8 p-b-5">
                            <a></a>
                        </div>

                       <div class="container-login100-form-btn" id="btnvoltar">
                            <div class="wrap-login100-form-btn">
                                <div class="signin100-form-bgbtn login100-form-bgbtn"></div>
                                <a class="login100-form-btn btn btn-block" href="${site}/cadastros/cliente/cadastro_cliente.jsp">
                                    Cadastrar
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