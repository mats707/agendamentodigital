function includeHTMLNav(menuPai, pagina) {
    var itemMenu = ["home", "usuarios", "servicos", "agenda", "categorias"];
    var subitemPagina = ["cadastrarUsuario", "listarUsuario",
        "cadastrarServico", "listarServico",
        "visualizarAgenda"];

    for (i = 0; i < subitemPagina.length; i++) {
        document.getElementById(subitemPagina[i]).classList.remove("active");
        if (pagina == subitemPagina[i]) {
            document.getElementById(subitemPagina[i]).classList.add("active");
            document.getElementById("menuPai" + menuPai).classList.add("menu-open");
        }
    }

    for (i = 0; i < itemMenu.length; i++) {
        document.getElementById(itemMenu[i]).classList.remove("active");
        if (menuPai == itemMenu[i]) {
            document.getElementById(itemMenu[i]).classList.add("active");
        }
    }

}