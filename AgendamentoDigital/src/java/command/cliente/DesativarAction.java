/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import dao.UsuarioDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class DesativarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "HomeAction");

        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");

        String funcaoMsg = "";
        String funcaoStatus = "";

        if (usuarioAutenticado != null) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.desativar(usuarioAutenticado);

//            if (usuarioSolicitado == usuario) {
//                if (usuarioDAO.excluir(usuarioSolicitado)) {
//                    funcaoMsg = "Deletado com sucesso!";
//                    funcaoStatus = "success";
//                } else {
//                    funcaoMsg = "Não foi possível deletar o usuário, tente novamente mais tarde!";
//                    funcaoStatus = "error";
//                }
//            } else {
//                funcaoMsg = "Usuário inválido!";
//                funcaoStatus = "error";
//            }
        } else {
            funcaoMsg = "Usuário inválido!";
            funcaoStatus = "error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }

}
