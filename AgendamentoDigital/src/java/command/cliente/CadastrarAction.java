/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import command.cliente.*;
import dao.ClienteDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.PerfilDeAcesso;
import modelos.Cliente;
import builder.cliente.ClienteBuilder;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import javax.servlet.http.HttpSession;
import modelos.Pessoa;
import modelos.Usuario;
import util.Util;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    String nome = "";
    String dataNascimento = "";
    String celular = "";
    String email = "";
    String password = "";
    String chkpassword = "";

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        //Verifica Usuario logado
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)) {
            request.setAttribute("pagina", "/pages/funcionario/clientes/cadastrar.jsp");
        } else {
            request.setAttribute("pagina", "auth/login.jsp");
        }
        nome = request.getParameter("inputName");
        dataNascimento = request.getParameter("inputDataNasc");
        celular = request.getParameter("inputCelular");
        email = request.getParameter("inputEmail");
        password = request.getParameter("inputPassword");
        chkpassword = request.getParameter("inputChkPassword");

        String funcaoMsg = "";
        String funcaoStatus = "";

        if (Util.isInteger(celular) && Util.isValidEmailAddress(email) && nome != null && dataNascimento != null) {
            Cliente cliente = ClienteBuilder.novoClienteBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, password, celular).constroi();

            if (geraHash.checkPassword(chkpassword, cliente.getUsuario().getSenha())) {

                //Realiza o cadastro do usuário com passagem por referência - Na função será atribuído ao objeto o ID que foi gerado após o cadastro
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String sqlStateUsuario = usuarioDao.cadastraNovoUsuario(cliente.getUsuario());

                if ("0".equals(sqlStateUsuario)) {

                    //Instância Pessoa através da classe Cliente, utilizando passagem por ref. será atribuído ao objeto o ID que foi gerado após o cadastro
                    Pessoa objPessoa = new Pessoa();
                    objPessoa.setNome(cliente.getNome());
                    objPessoa.setDataNascimento(cliente.getDataNascimento());
                    objPessoa.setUsuario(cliente.getUsuario());

                    PessoaDAO pessoaDao = new PessoaDAO();
                    String sqlStatePessoa = pessoaDao.cadastrar(objPessoa);

                    if ("0".equals(sqlStatePessoa)) {
                        //Atribui o ID da Pessoa (que possuí Usuário) no objeto Cliente
                        //Apesar de ser herança e não ter o campo pessoa dentro de cliente, no banco de dados teremos o campo 'pessoa'
                        cliente.setIdPessoa(objPessoa.getIdPessoa());

                        ClienteDAO clienteDao = new ClienteDAO();

                        String sqlStateCliente = clienteDao.cadastrar(cliente);

                        if ("0".equals(sqlStateCliente)) {
                            funcaoStatus = "success";
                            funcaoMsg = "Cadastrado com sucesso!";
                        } else {
                            pessoaDao.deletar(objPessoa);
                            usuarioDao.deletar(cliente.getUsuario());
                            funcaoStatus = "error";
                            funcaoMsg = "Cliente inválido! Entre em contato com o suporte.";
                        }
                    } else {

                        usuarioDao.deletar(cliente.getUsuario());

                        funcaoStatus = "error";
                        funcaoMsg = "Cliente inválido! Entre em contato com o suporte.";
                    }
                } else if ("23505".equals(sqlStateUsuario)) {
                    funcaoStatus = "error";
                    funcaoMsg = "Tente outro email ou celular!";
                } else {
                    funcaoStatus = "error";
                    funcaoMsg = "Não foi possível cadastrar, tente novamente ou entre em contato com o suporte!";
                }

            } else {
                funcaoStatus = "warning";
                funcaoMsg = "Senhas diferentes!";
            }
        } else {
            funcaoStatus = "";
            funcaoMsg = "";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
