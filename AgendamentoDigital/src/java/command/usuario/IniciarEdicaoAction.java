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
public class IniciarEdicaoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "pages/admin/alterarUsuario.jsp");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String chkPassword = request.getParameter("chksenha");
        String celular = request.getParameter("celular");
        String perfil = request.getParameter("perfil");

        if (Util.isInteger(celular) && Util.isValidEmailAddress(email) && perfil != null) {
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(geraHash.hashPassword(senha));
            usuario.setCelular(Long.parseLong(celular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
            if (perfil.equalsIgnoreCase("administrador")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
            } else if (perfil.equalsIgnoreCase("comum")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
            } else {
                usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
            }

            if (geraHash.checkPassword(chkPassword,usuario.getSenha())) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();

                String sqlState = usuarioDAO.cadastraNovoUsuario(usuario);

                if (sqlState == "0") {
                    request.setAttribute("colorMsg", "success");
                    return "Cadastrado com sucesso!";
                } else if ("23505".equals(sqlState)) {
                    request.setAttribute("colorMsg", "danger");
                    return "Tente outro email ou celular!";
                } else {
                    request.setAttribute("colorMsg", "danger");
                    return "Não foi possível alterar o usuário, tente novamente!";
                }
            } else {
                request.setAttribute("colorMsg", "warning");
                return "Senhas diferente!";
            }
        } else {
            request.setAttribute("colorMsg", "");
            return "";
        }

    }

}
