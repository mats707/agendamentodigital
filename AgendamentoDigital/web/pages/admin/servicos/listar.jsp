<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamento Digital | Listar Serviços</title>
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
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>Lista de Serviços</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">Lista de Serviços</li>
                                </ol>
                            </div>
                        </div>
                    </div><!-- /.container-fluid -->
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Serviços Cadastrados</h3>

                                        <div class="card-tools">
                                            <div class="input-group input-group-sm" style="width: 150px;">
                                                <input type="text" name="table_search" class="form-control float-right" placeholder="Search">

                                                <div class="input-group-append">
                                                    <button type="submit" class="btn btn-default"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body p-0">
                                        <div id="target" class="table table-responsive table-valign-middle"></div>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                                <!-- /.col -->
                            </div>
                        </div>
                        <!-- /.row -->
                    </div><!-- /.container-fluid -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Footer -->
            <jsp:include page="../footer.jsp"/>

        </div>
        <!-- ./wrapper -->

        <!-- edit Modal-->
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form id="frmCadastrarServico" action="${site}/Administrador/Servico/Alterar" method="POST"><div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Alterar Serviço? </h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="row align-items-center justify-content-center">
                            <div class="register-box">

                                <div class="card card-danger">
                                    <div class="card-header">
                                        <h3 class="card-title">Informações do Serviço</h3>

                                        <div class="card-tools">
                                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                        </div>
                                    </div>
                                    <div id="divInfoServico" class="card-body">
                                        <input id="idServico" name="idServico" type="text" class="form-control" placeholder="idServico" hidden>

                                        <!-- Date dd/mm/yyyy -->
                                        <div class="form-group">
                                            <label>Nome:</label>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-tasks"></i></span>
                                                </div>
                                                <input id="editedNome" name="editedNome" type="text" class="form-control">
                                            </div>
                                            <!-- /.input group -->
                                        </div>
                                        <div class="form-group">
                                            <label>Descrição:</label>
                                            <div class="input-group mb-3">
                                                <textarea id="editedDescricao"  name="editedDescricao" class="form-control" rows="1"></textarea>
                                            </div>
                                            <!-- /.input group -->
                                        </div>

                                        <div id="groupListaCategorias" class="form-group">
                                            <label>Categoria: </label>
                                            <span id="spanListaCategorias">

                                            </span>
                                        </div>
                                        <div class="row">
                                            <button id="btnLimparInfo" type="button" class="btn btn-danger btn-block">Limpar Campos</button>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                                <div class="card card-primary">
                                    <div class="card-header">
                                        <h3 class="card-title">Detalhes</h3>

                                        <div class="card-tools">
                                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                        </div>
                                    </div>
                                    <div id="divDetalhesServico" class="card-body">
                                        <div class="form-group">
                                            <label for="editedValor">Valor</label>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-coins"></i></span>
                                                </div>
                                                <input id="editedValor" name="editedValor" class="form-control" type="money" required placeholder="0,00" step="1" min="0">
                                            </div>
                                            <!-- /.input group -->
                                        </div>
                                        <div class="form-group">
                                            <label for="editedDuracao">Duração<span class="text-muted"> (em minutos)</span></label>
                                            <input id="editedDuracao" name="editedDuracao" class="form-control" type="number" required placeholder="0" step="5" min="5" max="1440">
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
                                            <button id="btnLimparDetalhe" type="button" class="btn btn-danger btn-block">Limpar Campos</button>

                                        </div>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="modal-footer">
                            <button id="btnAlterar" type="submit" class="btn btn-success btn-block" data-msg="${funcaoMsg}">Alterar</button>
                            <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar</button>
                        </div>
                    </div>
                </form>            
            </div>
        </div>

        <!-- delete Modal-->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form id="deletarServico" action="${site}/Administrador/Servico/Deletar" method="POST">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Excluir Serviço? </h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="row align-items-center justify-content-center">
                            <p class="danger">Será deletado somente se NÃO houver agendamento com este serviço!</p>

                            <div class="register-box">
                                <div class="card">
                                    <input id="idServicoDeleted" name="idServicoDeleted" type="text" class="form-control" placeholder="idServico" hidden>
                                    <div class="input-group mb-3">
                                        <input id="deletedNome" name="deletedNome" type="nome" class="form-control" placeholder="Nome" readonly>
                                        <div class="input-group-append">
                                            <div class="input-group-text">
                                                <span class="fas fa-envelope"></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="input-group mb-3">
                                        <input id="deletedDescricao" name="deletedDescricao" type="descricao" class="form-control" placeholder="Descricao" readonly>
                                        <div class="input-group-append">
                                            <div class="input-group-text">
                                                <span class="fas fa-envelope"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button  id="btnDeletar" type="submit" class="btn btn-danger btn-block">Deletar Serviço</button>
                            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="${site}/dist/js/pages/includeHTMLNav.js" type="text/javascript"></script>
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
        <!-- Mask Money -->
        <script src="${site}/plugins/jquery-maskmoney/jquery.maskMoney.min.js" type="text/javascript"></script>
        <script>
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()

                //Initialize Select2 Elements
                $('.select2bs4').select2({
                    theme: 'bootstrap4'
                })

                $('[data-mask]').inputmask()

                //Initialize MaskMoney
                $('[type=money]').maskMoney({
                    thousands: '.',
                    decimal: ','
                })

            })
        </script>
        <script charset="ISO-8859-1" src="${site}/pages/admin/servicos/js/listarServico.js" type="text/javascript"></script>
        <script charset="ISO-8859-1" src="${site}/pages/admin/servicos/js/listarCategorias.js" type="text/javascript"></script>
        <script charset="ISO-8859-1" src="${site}/pages/admin/servicos/js/listarFuncionarios.js" type="text/javascript"></script>

        <script>
            var result = ${msg};
            console.log(result);
            includeHTMLNav("servicos", "listarServico");
            lerTabela(result);
            var callSweet = "${funcaoMsgOperation}";
            if(callSweet != ""){
                sweet(callSweet,"${funcaoStatusOperation}",6000);
            } else {
                sweet("${funcaoMsg}","${funcaoStatus}",3000);
            }
        </script>

        <!-- PAGE PLUGINS -->
        <!-- jQuery Mapael -->
        <script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
        <script src="${site}/plugins/raphael/raphael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
        <script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>

</html>
