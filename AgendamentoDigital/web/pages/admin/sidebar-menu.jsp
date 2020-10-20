<link rel="icon" type="image/png" href="${site}/util/imagens/favicon.png"/>
<!-- Navbar -->
<nav class="navbar navbar-expand navbar-dark main-header">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#"><i class="fas fa-bars"></i></a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="${site}/pages/admin/home.jsp" class="nav-link">Home</a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="#" class="nav-link">Contato</a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a class="nav-link" href="${site}/ControleAcesso?acao=Sair">Sair</a>
        </li>
    </ul>

    <!-- SEARCH FORM -->
    <form class="form-inline ml-3">
        <div class="input-group input-group-sm">
            <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
            <div class="input-group-append">
                <button class="btn btn-navbar" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
    </form>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
        <!-- Messages Dropdown Menu -->
        <li class="nav-item dropdown">
            <a class="nav-link" data-toggle="dropdown" href="#">
                <i class="far fa-comments"></i>
                <span class="badge badge-danger navbar-badge">3</span>
            </a>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                <a href="#" class="dropdown-item">
                    <!-- Message Start -->
                    <div class="media">
                        <img src="${site}/dist/img/user1-128x128.jpg" alt="User Avatar" class="img-size-50 mr-3 img-circle">
                        <div class="media-body">
                            <h3 class="dropdown-item-title">
                                Brad Diesel
                                <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
                            </h3>
                            <p class="text-sm">Call me whenever you can...</p>
                            <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
                        </div>
                    </div>
                    <!-- Message End -->
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                    <!-- Message Start -->
                    <div class="media">
                        <img src="${site}/dist/img/user8-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
                        <div class="media-body">
                            <h3 class="dropdown-item-title">
                                John Pierce
                                <span class="float-right text-sm text-muted"><i class="fas fa-star"></i></span>
                            </h3>
                            <p class="text-sm">I got your message bro</p>
                            <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
                        </div>
                    </div>
                    <!-- Message End -->
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                    <!-- Message Start -->
                    <div class="media">
                        <img src="${site}/dist/img/user3-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
                        <div class="media-body">
                            <h3 class="dropdown-item-title">
                                Nora Silvester
                                <span class="float-right text-sm text-warning"><i class="fas fa-star"></i></span>
                            </h3>
                            <p class="text-sm">The subject goes here</p>
                            <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
                        </div>
                    </div>
                    <!-- Message End -->
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item dropdown-footer">See All Messages</a>
            </div>
        </li>
        <!-- Notifications Dropdown Menu -->
        <li class="nav-item dropdown">
            <a class="nav-link" data-toggle="dropdown" href="#">
                <i class="far fa-bell"></i>
                <span class="badge badge-warning navbar-badge">15</span>
            </a>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                <span class="dropdown-item dropdown-header">15 Notifications</span>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                    <i class="fas fa-envelope mr-2"></i> 4 new messages
                    <span class="float-right text-muted text-sm">3 mins</span>
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                    <i class="fas fa-users mr-2"></i> 8 friend requests
                    <span class="float-right text-muted text-sm">12 hours</span>
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                    <i class="fas fa-file mr-2"></i> 3 new reports
                    <span class="float-right text-muted text-sm">2 days</span>
                </a>
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
            </div>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#">
                <i class="fas fa-th-large"></i>
            </a>
        </li>
    </ul>
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="home.jsp" class="brand-link">
        <img src="${site}/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <span class="brand-text font-weight-light">AdminLTE 3</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="${site}/dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">${usuarioAutenticado.email}</a>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
                <li class="nav-item">
                    <a href="${site}/pages/admin/home.jsp" id="home" class="nav-link">
                        <i class="nav-icon fas fa-home"></i>
                        <p>
                            Home
                        </p>
                    </a>
                </li>
                 <li class="nav-item">
                    <a href="${site}/BuscarEmpresa" id="home" class="nav-link">
                        <i class="nav-icon fas fa-home"></i>
                        <p>
                            Minha empresa
                        </p>
                    </a>
                </li>
                <li id="menuPaiusuarios" class="nav-item has-treeview">
                    <a href="#" id="usuarios" class="nav-link">
                        <i class="nav-icon far fa-user"></i>
                        <p>
                            Usuários
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="${site}/CadastrarUsuario" id="cadastrarUsuario" class="nav-link">
                                <i class="fas fa-user-plus nav-icon"></i>
                                <p>Cadastrar</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/ListarUsuario" id="listarUsuario" class="nav-link">
                                <i class="fas fa-user-tag nav-icon"></i>
                                <p>Listar</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a href="${site}/CadastrarCategoriaServico" id="categorias" class="nav-link">
                        <i class="nav-icon fas fa-tasks"></i>
                        <p>
                            Categorias
                        </p>
                    </a>
                </li>
                <li id="menuPaiservicos" class="nav-item has-treeview">
                    <a href="#" id="servicos" class="nav-link">
                        <i class="nav-icon fas fa-store"></i>
                        <p>
                            Serviços
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="${site}/CadastrarServico" id="cadastrarServico" class="nav-link">
                                <i class="fas fa-store-alt nav-icon"></i>
                                <p>Cadastrar</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/ListarServico" id="listarServico" class="nav-link">
                                <i class="fas fa-folder-open nav-icon"></i>
                                <p>Listar</p>
                            </a>
                        </li>
                    </ul>
                </li>  
                <li id="menuPaiservicos" class="nav-item has-treeview">
                    <a href="#" id="servicos" class="nav-link">
                        <i class="nav-icon fas fa-store"></i>
                        <p>
                            Relatorios de Serviços
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="${site}/Relatorios/Servicos/MaisAgendado" id="relatorioServicoMaisAgendado" class="nav-link">
                                <i class="fas fa-store-alt nav-icon"></i>
                                <p>Serviços mais agendados</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/Relatorios/Servicos/MaisTrabalhado" id="relatorioCliente" class="nav-link">
                                <i class="fas fa-folder-open nav-icon"></i>
                                <p>Funcionarios mais trabalhados</p>
                            </a>
                        </li>
                    </ul>

                    <!--<li id="menuPaiagenda" class="nav-item has-treeview">
                        <a href="#" id="agenda" class="nav-link">
                            <i class="nav-icon far fa-calendar"></i>
                            <p>
                                Agenda
                                <i class="fas fa-angle-left right"></i>
                            </p>
                        </a>
                        <ul class="nav nav-treeview">
                            <li class="nav-item">
                                <a href="${site}/AgendarServico" id="agendarServico" class="nav-link">
                                    <i class="fas fa-calendar-plus nav-icon"></i>
                                    <p>Agendar Serviço</p>
                                </a>
                            </li>
                        </ul>
                        <ul class="nav nav-treeview">
                            <li class="nav-item">
                                <a href="${site}/VisualizarAgenda" id="vizualizaragenda" class="nav-link">
                                    <i class="fas fa-calendar-times nav-icon"></i>
                                    <p>Visualizar Agenda</p>
                                </a>
                            </li>
                        </ul>
                    </li>-->
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
