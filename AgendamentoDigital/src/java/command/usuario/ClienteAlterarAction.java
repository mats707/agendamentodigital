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
public class ClienteAlterarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "/Usuario/Cliente/Listar");

        Integer id = Integer.parseInt(request.getParameter("idUsuario"));
        String editedEmail = request.getParameter("editedEmail");
        String editedPassword = request.getParameter("editedPassword");
        String editedChkPassword = request.getParameter("editedChkpassword");
        String editedCelular = request.getParameter("editedCelular");

        String funcaoMsg = "";
        String funcaoStatus = "";

        if (editedEmail != null && editedCelular != null) {
            if (id != null && Util.isInteger(editedCelular) && Util.isValidEmailAddress(editedEmail)) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(id);
                usuario.setEmail(editedEmail);
                usuario.setCelular(Long.parseLong(editedCelular.replace("(", "").replace(")", "").replace("-", "").replace("_", "").replace(" ", "")));
                usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                String sqlState = usuarioDAO.alterarUsuario(usuario);

                if (sqlState == "0") {
                    funcaoMsg = "Alterado com sucesso!";
                    funcaoStatus = "success";
                    if (!"".equals(editedPassword) && !"".equals(editedChkPassword)) {
                        usuario.setSenha(geraHash.hashPassword(editedPassword));
                        if (geraHash.checkPassword(editedChkPassword, usuario.getSenha())) {
                            sqlState = usuarioDAO.alterarSenha(usuario);
                            if (sqlState == "0") {
                                funcaoMsg = "Usuário e senha alterados com sucesso!";
                                funcaoStatus = "success";
                            } else {
                                funcaoMsg = "Usuário alterado com sucesso\\nMas não foi possível alterar a senha, tente novamente!";
                                funcaoStatus = "warning";
                            }
                        } else {
                            funcaoMsg = "Senhas diferentes!";
                            funcaoStatus = "warning";
                        }
                    }
                } else if ("23505".equals(sqlState)) {
                    funcaoMsg = "Tente outro email ou celular!";
                    funcaoStatus = "error";
                } else {
                    funcaoMsg = "Não foi possível alterar o usuário, tente novamente!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "dados inválido!";
                funcaoStatus = "error";
            }
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
