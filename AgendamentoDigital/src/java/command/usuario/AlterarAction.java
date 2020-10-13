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
import util.Util;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class AlterarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "ListarUsuario");

        Integer id = Integer.parseInt(request.getParameter("idUsuario"));
        String editedEmail = request.getParameter("editedEmail");
        String editedPassword = request.getParameter("editedPassword");
        String editedChkPassword = request.getParameter("editedChkpassword");
        String editedCelular = request.getParameter("editedCelular");
        String editedPerfil = request.getParameter("editedPerfil");

        String funcaoMsgOperation = "";
        String funcaoStatusOperation = "";

        if (id != null && Util.isInteger(editedCelular) && Util.isValidEmailAddress(editedEmail) && editedPerfil != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            usuario.setEmail(editedEmail);
            usuario.setSenha(geraHash.hashPassword(editedPassword));
            usuario.setCelular(Long.parseLong(editedCelular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
            if (editedPerfil.equalsIgnoreCase("administrador")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
            } else if (editedPerfil.equalsIgnoreCase("comum")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
            } else {
                usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
            }

            if (geraHash.checkPassword(editedChkPassword,usuario.getSenha())) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();

                String sqlState = usuarioDAO.alterarUsuario(usuario);

                if (sqlState == "0") {
                    funcaoMsgOperation = "Alterado com sucesso!";
                    funcaoStatusOperation = "success";
                } else if ("23505".equals(sqlState)) {
                    funcaoMsgOperation = "Tente outro Email ou Celular!";
                    funcaoStatusOperation = "error";
                } else {
                    funcaoMsgOperation = "Não foi possível alterar o usuário, tente novamente!";
                    funcaoStatusOperation = "error";
                }
            } else {
                funcaoMsgOperation = "Senhas diferente!";
                funcaoStatusOperation = "warning";
            }
        } else {
            funcaoMsgOperation = "Usuário inválido!";
            funcaoStatusOperation = "error";
        }
        request.setAttribute("funcaoMsgOperation", funcaoMsgOperation);
        request.setAttribute("funcaoStatusOperation", funcaoStatusOperation);
        return funcaoMsgOperation;

    }

}
