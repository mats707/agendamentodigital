/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.usuario;

import dao.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class DeletarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "ListarUsuario");

        Integer id = Integer.parseInt(request.getParameter("idUsuarioDeleted"));
        String deletedEmail = request.getParameter("deletedEmail");
        String deletedCelular = request.getParameter("deletedCelular");
        String deletedPerfil = request.getParameter("deletedPerfil");

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
                if (usuarioDAO.excluir(usuarioSolicitado)) {
                    funcaoMsg = "Deletado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Não foi possível deletar o usuário, tente novamente mais tarde!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "Usuário inválido!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Usuário inválido!";
            funcaoStatus = "error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }

}
