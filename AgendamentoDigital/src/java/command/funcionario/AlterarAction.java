/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.funcionario;

import command.usuario.*;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.objects.NativeString;
import modelos.Funcionario;
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

        request.setAttribute("pagina", "MinhaConta");

        String nome = request.getParameter("inputName");
        String dataNascimento = request.getParameter("inputDataNasc");
        String celular = request.getParameter("inputCelular");

        String formatDataNascimento = "yyyy-MM-dd";

        //Instanciando Funcionario
        Funcionario objFuncionario = new Funcionario();

        String funcaoMsg;
        String funcaoStatus;

        //Verifica Usuario Funcionario
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objFuncionario = (Funcionario) sessaoUsuario.getAttribute("funcionario");
            objFuncionario.setUsuario(usuarioAutenticado);
        } else {
            objFuncionario.setIdFuncionario(Integer.parseInt(null));
            funcaoMsg = "Conta não autenticada!";
            funcaoStatus = "error";
            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return funcaoMsg;
        }

        if (Util.isInteger(celular) && Util.isValidName(nome) && dataNascimento != null) {
            objFuncionario.setDataNascimento(Util.getValidDate(dataNascimento, formatDataNascimento));
            objFuncionario.setNome(nome);
            objFuncionario.getUsuario().setCelular(Long.parseLong(celular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));

            PessoaDAO pessoaDAO = new PessoaDAO();
            String sqlState = pessoaDAO.alterar(objFuncionario);

            if ("0".equals(sqlState)) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                sqlState = usuarioDAO.alterarCelular(objFuncionario.getUsuario());
                if ("0".equals(sqlState)) {
                    funcaoMsg = "Alterado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Não foi possível alterar seus dados, tente novamente!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "Não foi possível alterar seus dados, tente novamente!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Dados inválidos!";
            funcaoStatus = "error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }

}
