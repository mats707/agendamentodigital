<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamento Digital | Cadastrar Categorias</title>

        <c:set var="site" value="${'/AgendamentoDigital'}" scope="application"  />
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="${site}/plugins/fontawesome-free/css/all.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Tempusdominus Bbootstrap 4 -->
        <link rel="stylesheet" href="${site}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <!-- iCheck -->
        <link rel="stylesheet" href="${site}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <!-- JQVMap -->
        <link rel="stylesheet" href="${site}/plugins/jqvmap/jqvmap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${site}/dist/css/adminlte.min.css">
        <!-- overlayScrollbars -->
        <link rel="stylesheet" href="${site}/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <!-- Daterange picker -->
        <link rel="stylesheet" href="${site}/plugins/daterangepicker/daterangepicker.css">
        <!-- summernote -->
        <link rel="stylesheet" href="${site}/plugins/summernote/summernote-bs4.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${site}/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <!-- Meu estilo -->
        <link href="${site}/pages/admin/categoria/css/cadastrar.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="hold-transition sidebar-mini layout-boxed sidebar-collapse">
        <div class="wrapper">

            <!-- Navbar & Menu-->
            <jsp:include page="../sidebar-menu.jsp"/>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>Configuração de Categorias</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">Categorias</li>
                                </ol>
                            </div>
                        </div>
                    </div><!-- /.container-fluid -->
                </section>

                <!-- Main content -->
                <section id="sectionCadastrarCategoria" class="content" style="display: none">
                    <div id="divCadastrarCategoria" class="container-fluid">
                        <div class="row align-items-bottom justify-content-center">
                            <div class="col-lg">

                                <div id="divCadastroCategorias" class="card card-info">
                                    <div class="card-header">
                                        <h3 class="card-title">Cadastrar Nova Categoria</h3>

                                        <div class="card-tools">
                                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                        </div>
                                    </div>
                                    <div class="card-body">

                                        <form id="formCategoria" action="${site}/CadastrarCategoriaServico">
                                        </form>
                                        <div class="row align-items-top justify-content-center">
                                            <div id="divNovaCategoria" class="col-lg form-group">
                                                <label for="novaCategoria">Categoria: </label>
                                                <label id="lblCategorias"></label>
                                                <div id="divCategoria" class="input-group mb-2">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-tasks"></i></span>
                                                    </div>
                                                    <input id="novaCategoria" name="novaCategoria" type="text" class="form-control" placeholder="Digite um nome" pattern="[A-Za-z]{3,50}">
                                                </div>
                                                <div id="divCategoria" class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-stream"></i></span>
                                                    </div>
                                                    <input id="descricaoCategoria" name="descricaoCategoria" type="text" class="form-control" placeholder="Digite uma descrição">
                                                </div>

                                                <div id="divButtonsCategoria" class="input-group mb-3">
                                                    <button id="btnAddCategoria" type="button" class="btn btn-block btn-outline-success btn-xs">Adicionar uma sub-categoria</button>
                                                    <button id="btnRmvCategoria" type="button" class="btn btn-block btn-outline-danger btn-xs">Removar última categoria</button>
                                                </div>
                                                <button id="btnCadastrarCategoria" type="button" class="btn btn-block btn-success btn">Cadastrar categorias</button>
                                            </div>
                                        </div>
                                        <div class="col-6" style="display: none">
                                            <div class="form-group">
                                                <label><br></label>
                                                <button id="btnCategoria" type="button" class="btn btn-block btn-outline-success btn"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>
                        </div>
                    </div>
                    <!-- /.container-fluid -->
                </section>

                <!-- Listar categorias -->
                <section id="sectionListarCategoria" class="content" style="display: none">
                    <div class="container-fluid">
                        <div class="row align-items-bottom justify-content-center">
                            <div class="col-lg">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Categorias</h3>
                                        <div class="text-right">
                                            <h8 class="card-subtitle text-muted">(clique no nome da categoria para "Expandir" ou "Diminuir")</h8>
                                        </div>
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body">
                                        <div id="listaCategorias" class="justify-content-center">
                                            <!-- Aqui serão inseridas as categorias pelo cadastrar.js -->
                                        </div>
                                        <!-- /.card-body -->
                                    </div>
                                    <!-- /.card -->
                                </div>
                                <!-- /.row -->
                            </div>
                            <!-- END LISTA CATEGORIAS-->
                        </div>
                    </div>
                </section>
            </div>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <footer class="main-footer">
            <div class="float-right d-none d-sm-block">
                <b>Version</b> 3.0.1-pre
            </div>
            <strong>Copyright &copy; 2014-2019 <a href="http://adminlte.io">AdminLTE.io</a>.</strong> All rights
            reserved.
        </footer>

        <!-- Control Sidebar -->
        <aside class="control-sidebar control-sidebar-dark">
            <!-- Control sidebar content goes here -->
        </aside>
        <!-- /.control-sidebar -->
    </div>
    <!-- ./wrapper -->

    <script src="${site}/dist/js/pages/includeHTMLNav.js" type="text/javascript"></script>
    <script src="${site}/dist/js/pages/sweetalert2Edit.js" type="text/javascript"></script>
    <!-- jQuery -->
    <script src="${site}/plugins/jquery/jquery.min.js"></script>

    <!-- jQuery UI 1.11.4 -->
    <script src="${site}/plugins/jquery-ui/jquery-ui.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
        $.widget.bridge('uibutton', $.ui.button)
    </script>
    <!-- Bootstrap 4 -->
    <script src="${site}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- ChartJS -->
    <script src="${site}/plugins/chart.js/Chart.min.js"></script>
    <!-- Sparkline -->
    <script src="${site}/plugins/sparklines/sparkline.js"></script>
    <!-- JQVMap -->
    <script src="${site}/plugins/jqvmap/jquery.vmap.min.js"></script>
    <script src="${site}/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
    <!-- jQuery Knob Chart -->
    <script src="${site}/plugins/jquery-knob/jquery.knob.min.js"></script>
    <!-- daterangepicker -->
    <script src="${site}/plugins/moment/moment.min.js"></script>
    <script src="${site}/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- Tempusdominus Bootstrap 4 -->
    <script src="${site}/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
    <!-- Summernote -->
    <script src="${site}/plugins/summernote/summernote-bs4.min.js"></script>
    <!-- overlayScrollbars -->
    <script src="${site}/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
    <!-- AdminLTE App -->
    <script src="${site}/dist/js/adminlte.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script src="${site}/dist/js/pages/dashboard.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="${site}/dist/js/demo.js"></script>
    <!-- InputMask -->
    <script src="${site}/plugins/moment/moment.min.js"></script>
    <script src="${site}/plugins/inputmask/min/jquery.inputmask.bundle.min.js"></script>
    <!-- Select2 -->
    <script src="${site}/plugins/select2/js/select2.full.min.js"></script>
    <!-- SweetAlert2 -->
    <script src="${site}/plugins/sweetalert2/sweetalert2.min.js"></script>
    <script src="${site}/plugins/sweetalert2/sweetalert2.js"></script>
    <!-- Toastr -->
    <script src="${site}/plugins/toastr/toastr.min.js"></script>
    <script>
        $(function () {
            //Initialize Select2 Elements
            $('.select2').select2({
                language: "pt-BR",
                state: "pt-BR"
            })
            
            //Initialize Select2 Elements
            $('.select2bs4').select2({
                theme: 'bootstrap4'
            })

            $('[data-mask]').inputmask()

        })
    </script>
    <script src="${site}/pages/admin/categoria/js/cadastrar.js" type="text/javascript"></script>

    <script>
        includeHTMLNav("categorias", "");
    </script>

    <!-- PAGE PLUGINS -->
    <!-- jQuery Mapael -->
    <script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
    <script src="${site}/plugins/raphael/raphael.min.js"></script>
    <script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
    <script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>

    <script type="text/javascript">
        console.log("Entrei: ${funcaoMsg}");
        const Toast = Swal.mixin({
            toast: true,
            position: 'center',
            showConfirmButton: false,
            timer: 3000
        });
        Toast.fire({
            type: '${funcaoStatus}',
            title: ' ${funcaoMsg}'
        });

    </script>
</body>
</html>
