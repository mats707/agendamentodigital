<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Agendamento Digital | Página Inicial</title>
        <c:set var="site" value="${'/AgendamentoDigital'}" scope="application"  />

        <!-- Font Awesome -->
        <link rel="stylesheet" href="./plugins/fontawesome-free/css/all.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="./dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <link href="dist/styles/agendamentodigital.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="hold-transition start-page">
        <!-- Automatic element centering -->
        <div class="login-box">
            <div class="login-logo">
                <a href="${site}/index2.html"><b>Agendamento</b>DIGITAL</a>
            </div>
            <div class="card">
                <form action="${site}/ControleAcesso" method="post">
                    <input type="text" id="acao" name="acao" value="Validar" hidden>
                    <button type="submit" class="btn btn-navy btn-lg btn-block">Acessar o site</button>
                </form>
            </div>

            <div class="lockscreen-footer text-center">
                Copyright &copy; 2019-2020 <b><a href="http://mafera.com" class="text-black">Mafera Soft</a></b><br>
                All rights reserved
            </div>
        </div>
        <!-- /.center -->

        <!-- jQuery -->
        <script src="${site}/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="${site}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
