/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import command.usuario.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ClienteDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Cliente;

/**
 *
 * @author alunocmc
 */
public class MinhaContaAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("pagina", "pages/client/minhaConta.jsp");

        Cliente objCliente = new Cliente();

        HttpSession sessaoUsuario = request.getSession();
        objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.buscar(objCliente);
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.buscar(objCliente);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.buscarId(objCliente.getUsuario());     
        
        request.setAttribute("objCliente", objCliente);

        return "";

    }

}
