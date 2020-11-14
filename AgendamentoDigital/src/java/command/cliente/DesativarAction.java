/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import command.usuario.*;
import command.cliente.ICommand;
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
public class DesativarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "HomeAction");

        String sqlState = "0";
        String funcaoMsg = "";
        String funcaoStatus = "";

        //Instanciando Usuário
        Cliente objCliente = new Cliente();
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
        }

        if (objCliente.getUsuario() != null) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            sqlState = usuarioDAO.desativar(objCliente.getUsuario());

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

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
