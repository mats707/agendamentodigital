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
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        String acao = request.getParameter("acao");

        if ("Cadastrar".equals(acao)) {

            Usuario usuario = new Usuario();
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenha(geraHash.codificaBase64(request.getParameter("senha")));
            usuario.setCelular(Long.parseLong(request.getParameter("celular")));
            String perfil = request.getParameter("perfil");
            if (perfil.equalsIgnoreCase("administrador")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
            } else if (perfil.equalsIgnoreCase("comum")) {
                usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
            } else {
                usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
            }
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            request.setAttribute("pagina", "/cadastros/admin/cadastro_usuario.jsp");
            String sqlState = usuarioDAO.cadastraNovoUsuario(usuario);
            
            if (sqlState=="0") {
                request.setAttribute("colorMsg", "green");
                return "Cadastrado com sucesso!";
            } else if ("23505".equals(sqlState)) {
                request.setAttribute("colorMsg", "red");
                return "Email ou Telefone já utilizado! Utilize outro!";
            } else {
                request.setAttribute("colorMsg", "red");
                return "Não foi possível cadastrar o usuário, tente novamente!";
            }
        } else {
            request.setAttribute("pagina", "/cadastros/admin/cadastro_usuario.jsp");
            request.setAttribute("colorMsg", "");
            return "";
        }
    }

}
