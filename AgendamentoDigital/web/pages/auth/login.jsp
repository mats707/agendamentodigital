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
        <!-- Estilo Personalizado -->
        <link href="${site}/pages/auth/css/auth.css" rel="stylesheet">
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="${site}/index.jsp"><b>Agendamento</b>DIGITAL</a>
            </div>
            <!-- /.login-logo -->
            <div class="row" id="parent">
                <div class="col-12 card d-block border-0 py-2">
                    <button id="btnRegister"  class="btn btn-light" type="button" aria-selected="true">
                        <span>Cadastrar</span>
                    </button>
                    <button id="btnLogin" class="btn btn-light" type="button" aria-selected="true">
                        <span>Entrar</span>
                    </button>
                    <!--<a id="btnLogin" href="" class="btn btn-outline-secondary">Login</a> <!--data-toggle="collapse" data-target="#cardLogin" data-parent="#parent">Login</a>-->
                    <!--<a id="btnRegister"  href="" class="btn btn-outline-secondary">Register</a> <!--data-toggle="collapse" data-target="#cardRegister" data-parent="#parent">Register</a>-->
                    <div  id="cardLogin" class="card">
                        <div class="collapse show card-body login-card-body">
                            <p class="login-box-msg">Faça o login para acessar o site</p>

                            <form action="${site}/ControleAcesso" method="post">
                                <div class="input-group mb-3">
                                    <input id="inputEmail" name="inputEmail" type="email" class="form-control" placeholder="Email">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-envelope"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <input id="inputPassword" name="inputPassword" type="password" class="form-control" placeholder="Password">
                                    <div class="input-group-append">
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
                                <br><a id="btnRegisterLink" class="btn-sing-up-now" style="color: #007bff;text-decoration: none;cursor: pointer;">Faça o cadastro agora mesmo!</a>
                            </span>
                        </div>
                    </div>
                    <div class="card">
                        <!-- /.login-card-body -->
                        <div id="cardRegister" class="collapse py-2 card-body register-card-body">
                            <p class="login-box-msg">Faça o registro para acessar o site</p>

                            <form action="${site}/ControleAcesso" method="post">
                                <div class="input-group mb-3">
                                    <input id="inputName" name="inputName" type="text" class="form-control" placeholder="Nome completo">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-user"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <input id="inputDataNasc" name="inputDataNasc" type="date" class="form-control" placeholder="Data Nascimento">
                                </div>
                                <div class="input-group mb-3">
                                    <input id="inputCelular" name="inputCelular" type="text" class="form-control" placeholder="Celular">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-phone"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <input id="inputEmail" name="inputEmail" type="email" class="form-control" placeholder="Email">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-envelope"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <input id="inputPassword" name="inputPassword" type="password" class="form-control" placeholder="Password">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-lock"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <!-- /.col -->
                                    <div class="col-4">
                                        <button type="submit" class="btn btn-primary btn-block" name="acao" value="Entrar">Cadastrar</button>
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
                        <!-- /.register-card-body -->
                    </div>
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
        <script>
            $(document).ready(function () {
                $("#cardRegister").animate({width: 'slideUp'});
                $("#btnLogin").removeClass("btn-light");
                $("#btnLogin").addClass("btn-primary");
                $("#btnRegisterLink").click(function () {
                    $("#cardLogin").slideUp();
                    $("#cardRegister").slideDown();
                    $("#btnRegister").addClass("btn-primary");
                    $("#btnRegister").removeClass("btn-light");
                    $("#btnLogin").addClass("btn-light");
                    $("#btnLogin").removeClass("btn-primary");
                });
                $("#btnRegister").click(function () {
                    $("#cardLogin").slideUp();
                    $("#cardRegister").slideDown();
                    $("#btnRegister").addClass("btn-primary");
                    $("#btnRegister").removeClass("btn-light");
                    $("#btnLogin").addClass("btn-light");
                    $("#btnLogin").removeClass("btn-primary");
                });
                $("#btnLogin").click(function () {
                    $("#cardRegister").slideUp();
                    $("#cardLogin").slideDown();
                    $("#btnLogin").addClass("btn-primary");
                    $("#btnLogin").removeClass("btn-light");
                    $("#btnRegister").addClass("btn-light");
                    $("#btnRegister").removeClass("btn-primary");
                });
            });
        </script>

    </body>
</html>
