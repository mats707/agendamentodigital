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
import modelos.PerfilDeAcesso;
import modelos.Usuario;

/**
 *
 * @author alunocmc
 */
public class ListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "/pages/funcionario/clientes/listar.jsp");
        
        String funcaoMsg = (String) request.getAttribute("funcaoMsg");
        String funcaoStatus = (String) request.getAttribute("funcaoStatus");

        if (funcaoMsg == null || funcaoStatus == null) {
            funcaoMsg = "Carregando Clientes";
            funcaoStatus = "info";
        }

        ArrayList<Cliente> arr = new ArrayList<Cliente>();
        
        ClienteDAO clienteDAO = new ClienteDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        arr = clienteDAO.listar();
        
        for (Cliente cliente : arr) {
            pessoaDAO.buscar(cliente);
            usuarioDAO.buscarId(cliente.getUsuario());
        }

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        
        return objgson.toJson(arr);

    }

}
