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
            <a class="nav-link" href="${site}/ControleAcesso?acao=Sair">Sair</a>
        </li>
    </ul>
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="${site}/pages/admin/home.jsp" class="brand-link">
        <img src="${site}/dist/img/AdminLTELogo.png" alt="Agendamento Digital" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <span class="brand-text font-weight-light">Agendamento Digital</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="https://ui-avatars.com/api/?rounded=true&background=fff&color=0a5&name=${usuarioAutenticado.email}" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="${site}/BuscarEmpresa" class="d-block">${usuarioAutenticado.email}</a>
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
                    <a href="${site}/BuscarEmpresa" id="buscarEmpresa" class="nav-link">
                        <i class="nav-icon fas fa-industry"></i>
                        <p>
                            Empresa
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
                            <a href="${site}/Usuario/Funcionario/Listar" id="listarFuncionarios" class="nav-link">
                                <i class="fas fa-user-tag nav-icon"></i>
                                <p>Listar Funcionários</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/Usuario/Cliente/Listar" id="listarClientes" class="nav-link">
                                <i class="fas fa-user-tag nav-icon"></i>
                                <p>Listar Clientes</p>
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
                                <i class="fas fa-caret-right nav-icon"></i>
                                <p>Cadastrar</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/ListarServico" id="listarServico" class="nav-link">
                                <i class="fas fa-caret-right nav-icon"></i>
                                <p>Listar</p>
                            </a>
                        </li>
                    </ul>
                </li>  
                <li id="menuPaiservicos" class="nav-item has-treeview">
                    <a href="#" id="relatorios" class="nav-link">
                        <i class="nav-icon fas fa-chart-bar"></i>
                        <p>
                            Relatórios de Serviços
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="${site}/Relatorios/Servicos/Agendamentos" id="relatorioServicoAgendamentos" class="nav-link">
                                <i class="fas fa-caret-right nav-icon"></i>
                                <p>Agendamentos</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/Relatorios/Servicos/Funcionarios" id="relatorioFuncionarios" class="nav-link">
                                <i class="fas fa-caret-right nav-icon"></i>
                                <p>Funcionários</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${site}/Relatorios/Servicos/Clientes" id="relatorioClientes" class="nav-link">
                                <i class="fas fa-caret-right nav-icon"></i>
                                <p>Clientes</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a href="${site}/MenuBloqueio" id="menuBloqueio" class="nav-link">
                        <i class="nav-icon fas fa-calendar-minus"></i>
                        <p>
                            Bloqueio de agenda
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${site}/ListarBloqueio" id="listarBloqueio" class="nav-link">
                        <i class="nav-icon fas fa-calendar-minus"></i>
                        <p>
                            Listar bloqueio de agenda
                        </p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
