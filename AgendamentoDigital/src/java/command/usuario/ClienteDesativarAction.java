/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.usuario;

import dao.UsuarioDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class ClienteDesativarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "/Usuario/Cliente/Listar");

        Integer id = Integer.parseInt(request.getParameter("idUsuarioDeleted"));
        String deletedEmail = request.getParameter("deletedEmail");
        String deletedCelular = request.getParameter("deletedCelular");
        String deletedPerfil = request.getParameter("deletedPerfil");

        String sqlState = "0";
        String funcaoMsg = "";
        String funcaoStatus = "";

        if (id != null && deletedEmail != null && deletedCelular != null && deletedPerfil != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            usuario.setEmail(deletedEmail);
            usuario.setCelular(Long.parseLong(deletedCelular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
            if (deletedPerfil.equalsIgnoreCase("administrador")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
            } else if (deletedPerfil.equalsIgnoreCase("comum")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
            } else {
                usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioSolicitado = usuarioDAO.buscaCompleta(usuario);

            if (usuarioSolicitado == usuario) {
                //Chamada da DAO para Deletar
                sqlState = usuarioDAO.desativar(usuarioSolicitado);
                //Verifica o retorno da DAO (banco de dados)
                if ("0".equals(sqlState)) {
                    funcaoMsg = "Desativado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Não foi possível desativar o usuário, tente novamente mais tarde!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "Usuário inválido!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Dados inválido!";
            funcaoStatus = "error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }
}
