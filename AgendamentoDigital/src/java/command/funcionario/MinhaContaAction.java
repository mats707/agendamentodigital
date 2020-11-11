/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.funcionario;

import command.usuario.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Funcionario;

/**
 *
 * @author alunocmc
 */
public class MinhaContaAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("pagina", "/pages/funcionario/minhaConta.jsp");

        Funcionario objFuncionario = new Funcionario();

        HttpSession sessaoUsuario = request.getSession();
        objFuncionario = (Funcionario) sessaoUsuario.getAttribute("funcionario");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.buscar(objFuncionario);
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.buscar(objFuncionario);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.buscarId(objFuncionario.getUsuario());     
        
        request.setAttribute("objFuncionario", objFuncionario);

        return "";

    }

}
