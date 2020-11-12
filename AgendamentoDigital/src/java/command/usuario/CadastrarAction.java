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
public class CadastrarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "pages/admin/cadastrarUsuario.jsp");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String chkPassword = request.getParameter("chksenha");
        String celular = request.getParameter("celular");
        String perfil = request.getParameter("perfil");

        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        if (email != null && senha != null && chkPassword != null && celular != null && perfil != null) {
            if (Util.isInteger(celular) && Util.isValidEmailAddress(email)) {

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(geraHash.hashPassword(senha));
                usuario.setCelular(Long.parseLong(celular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
                if (perfil.equalsIgnoreCase("administrador")) {
                    usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
                } else if (perfil.equalsIgnoreCase("funcionariocomum")) {
                    usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
                } else if (perfil.equalsIgnoreCase("clientecomum")) {
                    usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
                } else {
                    funcaoStatus = "error";
                    funcaoMsg = "Não foi possível cadastrar o usuário, tente novamente!\\nPerfil inválido";
                }

                if (geraHash.checkPassword(chkPassword, usuario.getSenha())) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();

                    sqlState = usuarioDAO.cadastraNovoUsuario(usuario);

                    if (sqlState == "0") {
                        funcaoStatus = "success";
                        funcaoMsg = "Cadastrado com sucesso!";
                    } else if ("23505".equals(sqlState)) {
                        funcaoStatus = "danger";
                        funcaoMsg = "Tente outro email ou celular!";
                    } else {
                        funcaoStatus = "danger";
                        funcaoMsg = "Não foi possível cadastrar o usuário, tente novamente!";
                    }
                } else {
                    funcaoStatus = "warning";
                    funcaoMsg = "Senhas diferente!";
                }
            } else {
                funcaoStatus = "danger";
                funcaoMsg = "Dados inválidos!";
            }
        } else {
            funcaoMsg = "Carregando...\\nAguarde um momento!";
            funcaoStatus = "info";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
