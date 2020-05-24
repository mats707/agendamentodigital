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
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${site}/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
        <!-- Bootstrap4 Duallistbox -->
        <link rel="stylesheet" href="${site}/plugins/bootstrap4-duallistbox/bootstrap-duallistbox.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${site}/dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <!-- Tempusdominus Bbootstrap 4 -->
        <link rel="stylesheet" href="${site}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">

        <link href="${site}/pages/admin/servicos/css/cadastrar.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="hold-transition sidebar-mini layout-boxed sidebar-collapse">
        <div class="wrapper">

            <!-- Navbar & Menu-->
            <jsp:include page="../sidebar-menu.jsp"/>

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
                        <form id="frmCadastrarServico" action="${site}/CadastrarServico" method="post">
                            <div class="row">
                                <div class="col-md-6">

                                    <div class="card card-danger">
                                        <div class="card-header">
                                            <h3 class="card-title">Informações do Serviço</h3>

                                            <div class="card-tools">
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                            </div>
                                        </div>
                                        <div id="divInfoServico" class="card-body">

                                            <!-- Date dd/mm/yyyy -->
                                            <div class="form-group">
                                                <label>Nome:</label>

                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-tasks"></i></span>
                                                    </div>
                                                    <input id="nome" name="nome" type="text" class="form-control">
                                                </div>
                                                <!-- /.input group -->
                                            </div>
                                            <div class="form-group">
                                                <label>Descrição:</label>
                                                <div class="input-group mb-3">
                                                    <textarea id="descricao"  name="descricao" class="form-control" rows="1"></textarea>
                                                </div>
                                                <!-- /.input group -->
                                            </div>

                                            <div id="groupListaCategorias" class="form-group">
                                                <label>Categoria: </label>
                                                <span id="spanListaCategorias">

                                                </span>
                                            </div>
                                            <div class="row">
                                                <!-- /.col -->
                                                <div class="col-4">
                                                    <button id="btnLimparInfo" type="button" class="btn btn-danger btn-block">Limpar Campos</button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /.card-body -->
                                    </div>
                                    <!-- /.card -->
                                </div>
                                <!-- /.col (left) -->

                                <div class="col-md-6">
                                    <div class="card card-primary">
                                        <div class="card-header">
                                            <h3 class="card-title">Detalhes</h3>

                                            <div class="card-tools">
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                            </div>
                                        </div>
                                        <div id="divDetalhesServico" class="card-body">
                                            <div class="form-group">
                                                <label for="valor">Valor</label>

                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-coins"></i></span>
                                                    </div>
                                                    <input id="valor" name="valor" class="form-control" type="money" required placeholder="0,00" step="1" min="0">
                                                </div>
                                                <!-- /.input group -->
                                            </div>
                                            <div class="form-group">
                                                <label for="duracao">Duração<span class="text-muted"> (em minutos)</span></label>
                                                <input id="duracao" name="duracao" class="form-control" type="number" required placeholder="0" step="5" min="5" max="1440">
                                                <!--<input id="duracao" type="range" min="5" max="1440" value="5" step="5" class="custom-range custom-range-danger" oninput="var str = document.getElementById('spanDuracao').innerHTML = this.value<31?this.value+' minutos':'infinity'">
                                                <p><span id="spanDuracao"></span></p>-->
                                            </div>
                                            <div class="form-group">
                                                <label>Funcionários</label>
                                                <select id="listaFuncionarios" name="listaFuncionarios" class="select2" multiple="multiple" data-placeholder="Selecione um ou mais funcionários" style="width: 100%;">
                                                </select>
                                            </div>
                                            <!-- /.form-group -->
                                            <div class="row">
                                                <!-- /.col -->
                                                <div class="col-4">
                                                    <button id="btnLimparDetalhe" type="button" class="btn btn-danger btn-block">Limpar Campos</button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /.card-body -->
                                    </div>
                                    <!-- /.card -->
                                </div>

                                <!-- /.col (right) -->
                            </div>
                            <!-- /.col -->
                            <div class="card card-success">
                                <div class="card-body">
                                    <button type="submit" class="btn btn-success btn-block">Cadastrar</button>
                                </div>
                            </div>
                            <!-- /.col -->
                        </form>
                    </div>
                    <!-- /.container-fluid -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Footer -->
            <jsp:include page="../footer.jsp"/>

        </div>
        <!-- ./wrapper -->

        <script src="${site}/dist/js/pages/includeHTMLNav.js" type="text/javascript"></script>
        <script src="${site}/dist/js/pages/sweetalert2Edit.js" type="text/javascript"></script>
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
        <script src="${site}/plugins/select2/js/select2.full.js" type="text/javascript"></script>
        <script src="${site}/plugins/select2/js/select2.full.min.js"></script>
        <script src="${site}/plugins/select2/js/i18n/pt-BR.js" type="text/javascript"></script>
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

                $('[data-mask]').inputmask()

                //Initialize MaskMoney
                $('[type=money]').maskMoney({
                    thousands: '.',
                    decimal: ','
                })
            })
        </script>
        <script type="text/javascript">
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
        <script src="${site}/pages/admin/servicos/js/cadastrar.js" type="text/javascript"></script>
        <script src="${site}/pages/admin/servicos/js/listarCategorias.js" type="text/javascript"></script>
        <script src="${site}/pages/admin/servicos/js/listarFuncionarios.js" type="text/javascript"></script>
        
        <!-- PAGE PLUGINS -->
        <!-- jQuery Mapael -->
        <script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="${site}/plugins/raphael/raphael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>

        <!-- Mask Money -->
        <script src="${site}/plugins/jquery-maskmoney/jquery.maskMoney.min.js" type="text/javascript"></script>
    </body>
</html>
