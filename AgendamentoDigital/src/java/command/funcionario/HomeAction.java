/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.funcionario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.PerfilDeAcesso;
import modelos.Usuario;

/**
 *
 * @author alunocmc
 */
public class HomeAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        //Verifica Usuario logado
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null) {
            if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)) {
                request.setAttribute("pagina", "/pages/funcionario/home.jsp");
            } else if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOADMIN)) {
                request.setAttribute("pagina", "/pages/admin/home.jsp");
            }
        } else {
                request.setAttribute("pagina", "/pages/funcionario/home.jsp");
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return funcaoMsg;

    }

}
