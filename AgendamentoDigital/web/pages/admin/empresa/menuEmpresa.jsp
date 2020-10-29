<%-- 
    Document   : maisagendado
    Created on : 01/09/2020, 14:29:36
    Author     : Rafael Pereira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamento Digital | Minha Empresa</title>
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
        <!--JQUERY timepicker-->
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
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
                                <h1>Empresa</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">Minha empresa</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <!-- /.container-fluid -->


                    <!-- Main content -->
                    <div class="content">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <h3 class="card-title">Meus Dados</h3>
                                            <div class="card-tools">
                                                <!--div class="input-group input-group-sm" style="width: 150px;">
                                                    <nput type="text" name="table_search" class="form-control float-right" placeholder="Search">
    
                                                    <div class="input-group-append">
                                                        <button type="submit" class="btn btn-default"><i class="fas fa-search"></i></button>
                                                    </div-->
                                            </div>                                                                                    
                                        </div>
                                        <!-- informações da minha empresa-->
                                        <form action="${site}/AtualizarEmpresa" method="POST">
                                            <div>
                                                <label>Nome da Empresa:</label>
                                                <input id="nome" name="nome">
                                            </div>  
                                            <div>
                                                <label>Horario de abertura</label>
                                                <input id="horaInicialTrabalho" disabled readonly>
                                                <label>para</label>
                                                <input class="timepicker" id="timepickerAbertura" name="timePickerAbertura" >
                                            </div>  
                                            <div>
                                                <label>Horario de encerramento:</label>
                                                <input id="horaFinalTrabalho" disabled readonly>
                                                <label>para</label>
                                                <input class="timepicker" id="timepickerEncerramento" name="timepickerEncerramento">
                                            </div>  
                                            <div>
                                                <label>Tempo minimo de serviço:</label>
                                                <input id="intervaloAgendamentoGeralServico" type="number" required placeholder="0" step="5" min="5" max="1440" name="intervaloAgendamentoGeralServico" >
                                                <label>Minutos</label>
                                            </div>  
                                            <div>
                                                <label>Dias úteis:</label>
                                                <!--input id="diaSemanaTrabalho"-->
                                                <input type="checkbox" id="DiaSemana0" name="diasemana" value="0" >
                                                <label>Domingo</label>
                                                <input type="checkbox" id="DiaSemana1" name="diasemana" value="1">
                                                <label>Segunda-feira</label>
                                                <input type="checkbox" id="DiaSemana2" name="diasemana" value="2">
                                                <label>Terça-feira</label>
                                                <input type="checkbox" id="DiaSemana3" name="diasemana" value="3">
                                                <label>Quarta-feira</label>
                                                <input type="checkbox" id="DiaSemana4" name="diasemana" value="4">
                                                <label>Quinta-Feira</label>
                                                <input type="checkbox" id="DiaSemana5" name="diasemana" value="5">
                                                <label>Sexta</label>
                                                <input type="checkbox" id="DiaSemana6" name="diasemana" value="6">
                                                <label>Sabado</label>

                                            </div>  
                                            <div id="groupTelefone">
                                            </div>  

                                            <div>
                                                <label>E-mail:</label>
                                                <input type="email" id="email" name="email">
                                            </div> 
                                            <div class="card card-success">
                                                <div class="card-body">
                                                    <button type="submit" class="btn btn-success btn-block">Alterar</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <!-- /.card-header -->
                                    <div id="target" class="card-body p-0"></div>
                                    <!-- /.card-body -->


                                </div>

                                <!-- /.card -->
                                <!-- /.col -->
                            </div>
                        </div>
                        <!-- /.row -->
                    </div><!-- /.container-fluid -->

            </div>

            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <!-- Footer -->
        <jsp:include page="../footer.jsp"/>

    </div>
    <!-- ./wrapper -->
</body>

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

<script>
    $(function () {
        //Initialize Select2 Elements
        $('.select2').select2();

        //Initialize Select2 Elements
        $('.select2bs4').select2({
            theme: 'bootstrap4'
        });

        $('[data-mask]').inputmask();

        $('.timepicker').timepicker({
            timeFormat: 'HH:mm ',
            interval: 30,
            minTime: '0',
            maxTime: '23:59',
            defaultTime: '00',
            startTime: "00:00",
            dynamic: true,
            dropdown: true,
            scrollbar: true

        });

    });

    sweet("${funcaoMsg}", "${funcaoStatus}", 2000);
    console.log("${funcaoMsg}");
</script>

<script src="${site}/pages/admin/empresa/menuEmpresa.js" type="text/javascript"></script>

<script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="${site}/plugins/raphael/raphael.min.js"></script>
<script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
<script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>

<!-- PAGE PLUGINS -->
<!-- jQuery Mapael -->
<script src="${site}/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="${site}/plugins/raphael/raphael.min.js"></script>
<script src="${site}/plugins/jquery-mapael/jquery.mapael.min.js"></script>
<script src="${site}/plugins/jquery-mapael/maps/usa_states.min.js"></script>
<!-- JQuery TimePicker-->
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
</html>
