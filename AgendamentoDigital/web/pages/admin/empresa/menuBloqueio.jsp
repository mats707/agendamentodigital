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
        <title>Agendamento Digital | Bloqueio de Agenda</title>
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
        <!-- Daterange picker -->
        <link rel="stylesheet" href="${site}/plugins/daterangepicker/daterangepicker.css">
        <!-- Datapicker css -->
        <link href="${site}/plugins/bootstrap-datepicker-1.9.0-dist/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
        <!-- Timepicker css -->
        <link href="${site}/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css"/>
        <!-- Datetime css -->
        <link href="${site}/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
        <!-- TimePicker -->
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
                                        <form action="${site}/Administrador/BloqueioAgenda/Cadastrar" method="POST">
                                            <div class="bootstrap-datetime">
                                                <div class="form-group">
                                                    <label for="datepicker">Selecione o dia do agendamento:</label>
                                                    <!-- Date Picker -->
                                                    <div id="datepicker" class="input-group date">
                                                        <input id="dataBloqueio" name="dataBloqueio" type="text" readonly class="form-control"/>
                                                        <div class="input-group-append input-group-addon">
                                                            <span class="input-group-text">
                                                                <i class="far fa-calendar"></i>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- /.form group -->
                                            </div>
                                            <div class="form-group">
                                                <label for="timepicker">Selecione o horário do agendamento:</label>
                                                <div id="timepicker" class="input-group date">
                                                    <input class="timepicker" id="horaBloqueio" autocomplete="off" name="horaBloqueio" placeholder="${startTime}">
                                                    <div class="input-group-append input-group-addon">
                                                        <span class="input-group-text">
                                                            <label class="far fa-clock"></label>
                                                        </span>
                                                    </div>
                                                </div>


                                            </div>
                                            <!-- /.input group -->

                                            <div>
                                                <label>Duracao:</label>
                                                <input id="duracaoBloqueio" type="number" required placeholder="0" step="5" min="5" max="1440" name="duracaoBloqueio" >
                                                <label>Minutos</label>
                                            </div>  
                                            <div class="form-group">
                                                <label>Funcionários</label>
                                                <select id="listaFuncionarios" name="listaFuncionarios" class="select2" multiple="multiple" data-placeholder="Selecione um funcionário" style="width: 100%;">
                                                </select>
                                            </div>
                                            <div class="card card-success">
                                                <div class="card-body">
                                                    <button type="submit" class="btn btn-success btn-block">Cadastrar</button>
                                                </div>
                                            </div>
                                        </form>

                                    </div>
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

<!--MEU JS -->
<script charset="ISO-8529" src="${site}/pages/admin/empresa/menuBloqueio.js" type="text/javascript"></script>
<script>
    $(function () {
        //Initialize Select2 Elements
        $('.select2').select2();
        //Initialize Select2 Elements
        $('.select2bs4').select2({
            theme: 'bootstrap4'
        });
        $('[data-mask]').inputmask();

        var meses = 2;
        var dataAtual = new Date();
        var dataMaxima = new Date(new Date().setMonth(dataAtual.getMonth() + meses));
        console.log(dataAtual);
        console.log(dataMaxima);
        //Initialize DatePicker
        $('#datepicker').datepicker({
            format: "dd/mm/yyyy"
            , showMeridian: true
            , autoclose: false //Whether or not to close the datepicker immediately when a date is selected.
            , clearBtn: true //If true, displays a “Clear” button at the bottom of the datepicker to clear the input value. If “autoclose” is also set to true, this button will also close the datepicker.
            , datesDisabled: ['18/09/2020', '25/09/2020'] //Array of date strings or a single date string formatted in the given date format
            , daysOfWeekDisabled: [0] //Days of the week that should be disabled. Values are 0 (Sunday) to 6 (Saturday). Multiple values should be comma-separated. Example: disable weekends: '06' or '0,6' or [0,6].
            , disableTouchKeyboard: true //If true, no keyboard will show on mobile devices
            , maxViewMode: 1 //Set a maximum limit for the view mode. Accepts: 0 or “days” or “month”, 1 or “months” or “year”, 2 or “years” or “decade”, 3 or “decades” or “century”, and 4 or “centuries” or “millenium”.
            , minViewMode: 0 //Set a minimum limit for the view mode. Accepts: 0 or “days” or “month”, 1 or “months” or “year”, 2 or “years” or “decade”, 3 or “decades” or “century”, and 4 or “centuries” or “millenium”. 
            , orientation: "auto" //A space-separated string consisting of one or two of “left” or “right”, “top” or “bottom”, and “auto” (may be omitted); for example, “top left”, “bottom” (horizontal orientation will default to “auto”), “right” (vertical orientation will default to “auto”), “auto top”. Allows for fixed placement of the picker popup.
            , todayBtn: "linked"
            , language: "pt-BR"
            , todayHighlight: true
            , startDate: dataAtual //today
            , endDate: dataMaxima //Até o máximo de meses permitido
            , leftArrow: '<i class="fas fa-long-arrow-left"></i>' //The templates used to generate some parts of the picker. Each property must be a string with only text, or valid html. You can use this property to use custom icons libs.
            , rightArrow: '<i class="fas fa-long-arrow-right"></i>' //The templates used to generate some parts of the picker. Each property must be a string with only text, or valid html. You can use this property to use custom icons libs.
            , multidate: false //Enable multidate picking. Each date in month view acts as a toggle button, keeping track of which dates the user has selected in order. If a number is given, the picker will limit how many dates can be selected to that number, dropping the oldest dates from the list when the number is exceeded. true equates to no limit. The input’s value (if present) is set to a string generated by joining the dates, formatted, with multidateSeparator.
        });
        //Initilizar DateTimePicker
        $('.timepicker').timepicker({
            timeFormat: 'HH:mm ',
            interval: 30,
            minTime: '${startTime}',
            maxTime: '${maxTime}',
            defaultTime: '00',
            startTime: '00:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true

        });
    });
    sweet("${funcaoMsg}", "${funcaoStatus}", 2000);
    //alert(${funcaoMsg});
    console.log("${funcaoMsg}");</script>

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
<!-- Date Picker -->
<script src="${site}/plugins/bootstrap-datepicker-1.9.0-dist/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${site}/plugins/bootstrap-datepicker-1.9.0-dist/locales/bootstrap-datepicker.pt-BR.min.js" type="text/javascript"></script>
<script src="${site}/plugins/popper/popper.min.js" type="text/javascript"></script>
<!-- DateTime Picker -->
<script src="${site}/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<!-- Time Picker -->
<script src="${site}/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
<script src="${site}/dist/js/timepicker.js" type="text/javascript"></script>
<script type="text/javascript"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

</html>
