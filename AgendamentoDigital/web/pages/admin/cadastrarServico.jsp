<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamento Digital | Cadastrar Serviço</title>
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
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <!-- Select2 -->
        <link rel="stylesheet" href="${site}/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="${site}/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css">
        <!-- Bootstrap4 Duallistbox -->
        <link rel="stylesheet" href="${site}/plugins/bootstrap4-duallistbox/bootstrap-duallistbox.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${site}/dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <!-- Tempusdominus Bbootstrap 4 -->
        <link rel="stylesheet" href="${site}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <link href="${site}/pages/admin/css/cadastrarServico.css" rel="stylesheet" type="text/css"/>
        <!-- <c:set var="categorias" value="" scope="page"  /> -->
    </head>
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
                            <div class="col-md-6">
                                <h1 class="m-0 text-dark">Cadastrar Serviço</h1>
                            </div><!-- /.col -->
                            <div class="col-md-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">Serviço</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->

                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <!-- Main content -->
                <section class="content">
                    <div class="container-fluid">
                        <div class="row align-items-bottom justify-content-center">
                            <div class="col-10">

                                <div id="divCadastroCategorias" class="card card-info" style="display: none">
                                    <div class="card-header">
                                        <h3 class="card-title">Categoria do Serviço</h3>

                                        <div class="card-tools">
                                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                        </div>
                                    </div>
                                    <div class="card-body">

                                        <form id="formCategoria" action="${site}/api/Servico/CadastrarCategoria/${categorias}">
                                        </form>
                                        <div class="row align-items-top">
                                            <div class="col-6">
                                                <div id="divEscolherCategoria" class="form-group">
                                                    <label for="selEscolherCategoria">Escolher Categoria</label>
                                                    <select id="selEscolherCategoria" class="form-control select2 select2-gray" data-dropdown-css-class="select2-gray" style="width: 100%;">
                                                        <option value="" disabled selected>Selecione uma categoria</option>
                                                        <option>Alaska</option>
                                                        <option>California</option>
                                                        <option>Delaware</option>
                                                        <option>Tennessee</option>
                                                        <option>Texas</option>
                                                        <option>Washington</option>
                                                    </select>
                                                </div>
                                                <div id="divNovaCategoria" class="form-group">
                                                    <label for="novaCategoria">Categoria: </label>
                                                    <label id="lblCategorias"></label>
                                                    <div id="divCategoria" class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fas fa-tasks"></i></span>
                                                        </div>
                                                        <input id="novaCategoria" name="novaCategoria" type="text" class="form-control" placeholder="Digite uma categoria nova..." pattern="[A-Za-z]{3,50}">
                                                    </div>
                                                    <div id="divButtonsCategoria" class="input-group mb-3">
                                                        <button id="btnAddCategoria" type="button" class="btn btn-block btn-outline-success btn-xs">Adicionar uma sub-categoria</button>
                                                        <button id="btnRmvCategoria" type="button" class="btn btn-block btn-outline-danger btn-xs">Removar última categoria</button>
                                                    </div>
                                                    <button id="btnCadastrarCategoria" type="button" class="btn btn-block btn-success btn">Cadastrar categorias</button>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group">
                                                    <label><br></label>
                                                    <button id="btnCategoria" type="button" class="btn btn-block btn-outline-success btn"></button>
                                                </div>
                                            </div>
                                        </div>

                                        <form id="cadastrarServico" action="${site}/CadastrarServico" method="post" style="display: none">

                                            <!-- Date dd/mm/yyyy -->
                                            <div class="row align-items-center">
                                                <div class="col-12 col-sm-6">
                                                    <div class="form-group">
                                                        <label>Categoria</label>
                                                        <input type="text" class="form-control" placeholder="Digite uma categoria nova...">
                                                    </div>
                                                    <!-- /.form-group -->
                                                </div>
                                                <div class="col-6">
                                                    <button id="addNovaCategoria" type="button" class="btn btn-block btn-outline-success btn">Adicionar Nova Categoria</button>
                                                </div>

                                            </div>
                                            <div class="row align-items-center">
                                                <div class="col-6">
                                                    <button id="addCategoria" type="button" class="btn btn-block btn-outline-success btn-xs">Adicionar uma sub-categoria</button>
                                                </div>
                                                <div class="col-6">
                                                </div>
                                            </div>
                                            <div class="row align-items-center tab-custom-content">
                                                <!-- /.col -->
                                                <div class="col-4">
                                                    <button type="button" class="btn btn-light btn-block">Limpar Campos</button>
                                                </div>
                                                <!-- /.col -->
                                                <div class="col-8">
                                                    <button type="submit" class="btn btn-info btn-block">Cadastrar</button>
                                                </div>
                                                <p class="text-${colorMsg} mb-3">
                                                    <c:if test="${msg!=null}">
                                                        ${msg}
                                                    </c:if>
                                                </p>
                                            </div>
                                        </form>
                                    </div>
                                    <!-- /.card-body -->

                                </div>
                                <!-- /.card -->
                                <div id="divCadastroServico"  class="card card-danger" style="display: none">
                                    <div class="card-header">
                                        <h3 class="card-title">Cadastro de Serviços</h3>

                                        <div class="card-tools">
                                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <form id="cadastrarServico" action="${site}/CadastrarServico" method="post">
                                            <!-- Date dd/mm/yyyy -->
                                            <div class="row">
                                                <div class="col-6">
                                                    <ol id="listaCategorias">
                                                        <li>Coffee</li>
                                                        <li>Tea</li>
                                                        <li>Milk</li>
                                                    </ol> 
                                                </div>
                                                <div class="col-6">

                                                    <label>Nome:</label>

                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fas fa-tasks"></i></span>
                                                        </div>
                                                        <input id="nome" name="nome" type="text" class="form-control">
                                                    </div>
                                                    <!-- /.input group -->
                                                </div>
                                                <div class="col-6">
                                                    <label>Descrição:</label>

                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fas fa-edit"></i></span>
                                                        </div>
                                                        <input id="descricao" name="descricao" type="text" class="form-control">
                                                    </div>
                                                    <!-- /.input group -->
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-6">
                                                    <button id="addCategoria" type="button" class="btn btn-block btn-outline-success btn-xs">Adicionar +1 categoria</button>
                                                </div>
                                                <div class="col-6">
                                                    <button id="rmvCategoria" type="button" class="btn btn-block btn-outline-danger btn-xs">Remover a última categoria</button>
                                                </div>
                                            </div>
                                        </form>
                                        <div class="form-group tab-custom-content">
                                            <label>Valor</label>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-coins"></i></span>
                                                </div>
                                                <input id="valor" name="valor" type="text" class="form-control">
                                            </div>
                                            <!-- /.input group -->
                                        </div>
                                        <div class="row">
                                            <!-- /.col -->
                                            <div class="col-4">
                                                <button type="button" class="btn btn-danger btn-block">Limpar Campos</button>
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-8">
                                                <button type="submit" class="btn btn-primary btn-block">Cadastrar</button>
                                            </div>
                                            <p class="text-${colorMsg} mb-3">
                                                <c:if test="${msg!=null}">
                                                    ${msg}
                                                </c:if>
                                            </p>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>
                            <!-- /.col (left) -->
                        </div>
                    </div><!-- /.container-fluid -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <footer class="main-footer">
                <strong>Copyright &copy; 2014-2019 <a href="http://adminlte.io">AdminLTE.io</a>.</strong>
                All rights reserved.
                <div class="float-right d-none d-sm-inline-block">
                    <b>Version</b> 3.0.1-pre
                </div>
            </footer>

            <!-- Control Sidebar -->
            <aside class="control-sidebar control-sidebar-dark">
                <!-- Control sidebar content goes here -->
            </aside>
            <!-- /.control-sidebar -->
        </div>
        <!-- ./wrapper -->

        <script src="${site}/dist/js/pages/includeHTMLNav.js" type="text/javascript"></script>
        <script>
            includeHTMLNav("servicos", "cadastrarServico");
        </script>
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
        <script>
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()

                $('[data-mask]').inputmask()

            })
        </script>
        <script src="${site}/pages/admin/javascript/cadastrarServico.js" type="text/javascript"></script>

        <!-- PAGE PLUGINS -->
        <!-- jQuery Mapael -->
        <script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="${site}/plugins/raphael/raphael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>
        <script src="${site}/dist/js/pages/sweetalert2Edit.js" type="text/javascript"></script>

        <!-- SweetAlert2 -->
        <script src="${site}/plugins/sweetalert2/sweetalert2.min.js"></script>
        <script src="${site}/plugins/sweetalert2/sweetalert2.js"></script>
        <!-- Toastr -->
        <script src="${site}/plugins/toastr/toastr.min.js"></script>
        <script>
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()

                //Initialize Select2 Elements
                $('.select2bs4').select2({
                    theme: 'bootstrap4'
                })

                $('[data-mask]').inputmask()

            })
        </script> 
        <script type="text/javascript">
            var funcaoMsg = document.getElementById('btnCadastrarCategoria').getAttribute("data-msg");
            alert(funcaoMsg);
            if (funcaoMsg != '') {
                console.log("Entrei: funcaoMsg");
                window.onload = function () {
                    $(function () {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'center',
                            showConfirmButton: false,
                            timer: 3000
                        });
                        Toast.fire({
                            type: 'info',
                            title: funcaoMsg
                        });
                    });
                };
            }

        </script>
    </body>
</html>
