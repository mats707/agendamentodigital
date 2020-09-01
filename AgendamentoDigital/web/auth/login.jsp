<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Agendamento Digital | Log in</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <c:set var="site" value="${'/AgendamentoDigital'}" scope="session"  />

        <!-- Font Awesome -->
        <link rel="stylesheet" href="${site}/plugins/fontawesome-free/css/all.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- icheck bootstrap -->
        <link rel="stylesheet" href="${site}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${site}/dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Arima+Madurai:100,200,300,400,500,700,800,900" rel="stylesheet">
        <!-- Estilo Personalizado -->
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

        <!--<link href="http://netdna.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">-->
        <link href="${site}/auth/css/auth.css" rel="stylesheet" type="text/css">
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="${site}/index.jsp"><b>Agendamento</b>DIGITAL</a>
            </div>
            <!-- /.login-logo -->
            <div class="row" id="parent">
                <div class="col-12 card d-block border-0 py-2">
                    <button id="btnRegister" data-action="Register" class="btn btn-outline-grey" type="button" aria-selected="true" onclick="rotateCard(this);">
                        <span>Cadastrar</span>
                    </button>
                    <button id="btnLogin" data-action="Login" class="btn btn-light" type="button" aria-selected="true" onclick="rotateCard(this);">
                        <span>Entrar</span>
                    </button>
                    <!--<a id="btnLogin" href="" class="btn btn-outline-secondary">Login</a> <!--data-toggle="collapse" data-target="#cardLogin" data-parent="#parent">Login</a>-->
                    <!--<a id="btnRegister"  href="" class="btn btn-outline-secondary">Register</a> <!--data-toggle="collapse" data-target="#cardRegister" data-parent="#parent">Register</a>-->

                    <div id="containerLogin" class="card-container manual-flip">
                        <div class="card">
                            <div class="front">
                                <div class="cover">
                                    <img src="${site}/assets/img/login/cover.jpg"/>
                                </div>
                                <div class="user">
                                    <img class="img-circle" src="${site}/assets/img/login/blank_user.jpg" alt=""/>
                                </div>
                                <div class="content">
                                    <div class="main">
                                        <p class="login-box-msg">Faça o login para acessar o site</p>

                                        <form action="${site}/ControleAcesso" method="post">
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputEmail" name="inputEmail" type="email" class="form-control" placeholder="Email">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-envelope"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputPassword" name="inputPassword" type="password" class="form-control" placeholder="Password">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-lock"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-8">
                                                    <div class="icheck-primary">
                                                        <input type="checkbox" id="remember">
                                                        <label for="remember">
                                                            Lembrar de mim
                                                        </label>
                                                    </div>
                                                </div>
                                                <!-- /.col -->
                                                <div class="col-4">
                                                    <button type="submit" class="btn btn-primary btn-block" name="acao" value="Entrar">Entrar</button>
                                                </div>
                                                <!-- /.col -->
                                            </div>
                                        </form>

                                        <p class="login-invalid">
                                            <c:if test="${msg!=null}">
                                                <font color="red">${msg}</font>
                                            </c:if>
                                        </p>

                                        <!--<div class="social-auth-links text-center mb-3">
                                            <p>- OU -</p>
                                            <a href="#" class="btn btn-block btn-primary">
                                                <i class="fab fa-facebook mr-2"></i> Entre com o Facebook
                                            </a>
                                            <a href="#" class="btn btn-block btn-danger">
                                                <i class="fab fa-google-plus mr-2"></i> Entre com o Google+
                                            </a>
                                        </div>-->
                                        <!-- /.social-auth-links -->

                                        <p class="mb-4">
                                            <a href="forgot-password.html">Esqueci minha senha</a>
                                        </p>
                                        <span class="mb-1">Ainda não possui conta?
                                            <br><a id="btnRegisterLink" class="btn-sing-up-now" style="color: #007bff;text-decoration: none;cursor: pointer;" data-action="Register" onclick="rotateCard(this)">Faça o cadastro agora mesmo!</a>
                                        </span>
                                    </div>
                                </div>
                            </div> <!-- end front panel -->
                            <div class="back">
                                <div class="header">
                                    <h5 class="motto">"To be or not to be, this is my awesome motto!"</h5>
                                </div>
                                <div class="content">
                                    <div id="cardRegister" class="card-body">
                                        <p class="login-box-msg">Faça o registro para acessar o site</p>

                                        <form action="${site}/CadastrarCliente" method="post">
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputName" name="inputName" type="text" class="form-control" placeholder="Nome completo">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-user"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <input id="inputDataNasc" name="inputDataNasc" type="date" class="form-control" placeholder="Data Nascimento">
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputCelular" name="inputCelular" type="text" class="form-control" placeholder="Celular">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-phone"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputEmail" name="inputEmail" type="email" class="form-control" placeholder="Email">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-envelope"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputPassword" name="inputPassword" type="password" class="form-control" placeholder="Digite uma senha">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-lock"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-append">
                                                    <input id="inputChkPassword" name="inputChkPassword" type="password" class="form-control" placeholder="Confirme sua senha">
                                                    <div class="input-group-text">
                                                        <span class="fas fa-lock"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <!-- /.col -->
                                                <div class="col-4">
                                                    <button type="submit" class="btn btn-primary btn-block">Cadastrar</button>
                                                </div>
                                                <!-- /.col -->
                                            </div>
                                        </form>

                                        <p class="login-invalid">
                                            <c:if test="${msg!=null}">
                                                <font color="red">${msg}</font>
                                            </c:if>
                                        </p>
                                    </div>
                                </div>
                                <div class="footer">
                                    <button id="btnBackLogin" data-action="Login" class="btn btn-simple" rel="tooltip" title="Voltar para LOGIN" onclick="rotateCard(this)">
                                        <i class="fa fa-reply"></i> Voltar
                                    </button>
                                    <div class="social-links text-center">
                                        <a href="http://deepak646.blogspot.in/" class="facebook"><i class="fa fa-facebook fa-fw"></i></a>
                                        <a href="http://deepak646.blogspot.in/" class="google"><i class="fa fa-google-plus fa-fw"></i></a>
                                        <a href="http://deepak646.blogspot.in/" class="twitter"><i class="fa fa-twitter fa-fw"></i></a>
                                    </div>
                                </div>
                            </div> <!-- end back panel -->
                        </div> <!-- end card -->
                    </div> <!-- end card-container -->
                </div>
            </div>
        </div>
        <!-- /.login-box -->

        <!-- jQuery -->
        <script src="${site}/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="${site}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${site}/dist/js/adminlte.min.js"></script>
        <!-- Funções da página
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/run_prettify.js"></script>
        <script src="${site}/auth/js/login.js" type="text/javascript"></script>
    </body>
</html>
