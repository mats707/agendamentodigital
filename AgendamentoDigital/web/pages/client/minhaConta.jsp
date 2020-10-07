<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Agendamento Digital | Principal</title>

        <c:set var="site" value="${'/AgendamentoDigital'}" scope="application"  />
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="${site}/plugins/fontawesome-free/css/all.min.css">
        <!-- IonIcons -->
        <link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Tempusdominus Bbootstrap 4 -->
        <link rel="stylesheet" href="${site}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <!-- iCheck -->
        <!-- Theme style -->
        <link rel="stylesheet" href="${site}/dist/css/adminlte.min.css">
        <!-- overlayScrollbars -->
        <link rel="stylesheet" href="${site}/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <!-- summernote -->
        <link rel="stylesheet" href="${site}/plugins/summernote/summernote-bs4.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${site}/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <!-- BS fileinput -->
        <link href="${site}/plugins/bootstrap-fileinput/5.1.2/css/fileinput.min.css" rel="stylesheet" type="text/css"/>
        <!-- Estilo Personalizado -->
        <link href="${site}/pages/client/css/minhaConta.css" rel="stylesheet" type="text/css"/>
    </head>
    <!--
    BODY TAG OPTIONS:
    =================
    Apply one or more of the following classes to to the body tag
    to get the desired effect
    |---------------------------------------------------------|
    |LAYOUT OPTIONS | sidebar-collapse                        |
    |               | sidebar-mini                            |
    |---------------------------------------------------------|
    -->
    <body class="hold-transition sidebar-mini layout-boxed sidebar-collapse">
        <div class="wrapper">
            <!-- Navbar & Menu-->
            <jsp:include page="sidebar-menu.jsp"/>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0 text-dark">Meu Perfil</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="Home">Home</a></li>
                                    <li class="breadcrumb-item active">Minha Conta</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <!-- Main content -->
                <div class="">
                    <div class="container">
                        <h4>Formulário</h4>
                        <div id="panel" class="panel panel-default no-padding" >
                            <div class="panel-body no-padding">
                                <div class="form-horizontal">
                                    <div class="row">
                                        <div id="colImg" class="col-md-2 col-sm-2 col-xs-12 col-img">
                                            <!-- Background image -->
                                        </div>
                                        <div class="col-md-5 col-sm-5 col-xs-12  padding border-right">
                                            <p class="lead">Foto de Perfil</p>

                                            <div class="col-sm-4 text-center">
                                                <form role="form" action="${site}/ControleClienteMinhaConta" method="post" enctype="multipart/form-data">
                                                    <div class="kv-avatar">
                                                        <div class="file-loading">
                                                            <input id="avatar-2" name="avatar-2" type="file" required>
                                                        </div>
                                                    </div>
                                                    <div class="kv-avatar-hint">
                                                        <small>Select file < 1500 KB</small>
                                                    </div>
                                                    <div class="text-right">
                                                        <button type="submit" class="btn btn-primary">Submit</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>

                                        <div class="col-md-5 col-sm-5 col-xs-12  padding">
                                            <div id="meusDados" class="list-group">
                                                <a id="itemNome" href="#" class="list-group-item list-group-item-action" onclick="editarDados(this);">Nome: ${objCliente.nome}</a>
                                                <a id="itemDataNascimento" href="#" class="list-group-item list-group-item-action" onclick="editarDados(this);">
                                                    Data de Nascimento: <fmt:formatDate pattern = "dd/MM/yyyy" value="${objCliente.dataNascimento}"/></a>
                                                <a id="itemCelular" href="#" class="list-group-item list-group-item-action" onclick="editarDados(this);">Celular: ${objCliente.usuario.celular}</a>
                                            </div>
                                            <p class="lead">Alterar Meus Dados</p>
                                            <form action="${site}/AlterarCliente" method="post">
                                                <div id="groupNome" class="input-group mb-3" style="display: none">
                                                    <input id="inputName" name="inputName" type="text" class="form-control" placeholder="Nome completo" disabled>
                                                    <div class="input-group-append">
                                                        <div class="input-group-text">
                                                            <span class="fas fa-user"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="groupDataNascimento" class="input-group mb-3" style="display: none">
                                                    <input id="inputDataNasc" name="inputDataNasc" type="date" class="form-control" placeholder="Data Nascimento" disabled>
                                                </div>
                                                <!-- phone mask -->
                                                <div id="groupCelular" class="input-group mb-3" style="display: none">
                                                    <input id="inputCelular" name="inputCelular" type="text" class="form-control mask" placeholder="Celular" data-mask='telefone' disabled>
                                                    <div class="input-group-append">
                                                        <div class="input-group-text">
                                                            <span class="fas fa-phone"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row-fluid">
                                                    <button class="btn btn-default limpar">Limpar</button>
                                                    <button type="submit" class="btn btn-primary enviar">Alterar Dados</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!-- End of .container -->
                </div>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <!-- Footer -->
            <jsp:include page="footer.jsp"/>
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED SCRIPTS -->

        <!-- jQuery -->
        <script src="${site}/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="${site}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE -->
        <script src="${site}/dist/js/adminlte.js"></script>
        <!-- SweetAlert2 -->
        <script src="${site}/plugins/sweetalert2/sweetalert2.min.js"></script>
        <script src="${site}/plugins/sweetalert2/sweetalert2.js"></script>

        <script src="${site}/dist/js/pages/includeHTMLNav.js" type="text/javascript"></script>

        <!-- PAGE PLUGINS -->
        <!-- jQuery Mapael -->
        <script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="${site}/plugins/raphael/raphael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>

        <!-- the main fileinput plugin file -->
        <script src="${site}/plugins/bootstrap-fileinput/5.1.2/js/plugins/piexif.min.js" type="text/javascript"></script>
        <script src="${site}/plugins/bootstrap-fileinput/5.1.2/js/plugins/sortable.min.js" type="text/javascript"></script>
        <script src="${site}/plugins/bootstrap-fileinput/5.1.2/js/fileinput.min.js" type="text/javascript"></script>
        <script src="${site}/plugins/bootstrap-fileinput/5.1.2/themes/fa/theme.js" type="text/javascript"></script>
        <script src="${site}/plugins/bootstrap-fileinput/5.1.2/js/locales/pt-BR.js" type="text/javascript"></script>

        <script charset="ISO-8859-1" src="${site}/pages/client/js/minhaConta.js" type="text/javascript"></script>
        <script>
            const Toast = Swal.mixin({
                toast: true,
                position: 'center',
                showConfirmButton: false,
                timer: 1000
            });
            Toast.fire({
                type: '${funcaoStatus}',
                title: ' ${funcaoMsg}'
            });
            includeHTMLNav("home", "");
        </script>
    </body>
</html>
