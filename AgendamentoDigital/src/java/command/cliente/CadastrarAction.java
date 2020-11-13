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
import modelos.Pessoa;
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

        request.setAttribute("pagina", "auth/login.jsp");

        nome = request.getParameter("inputName");
        dataNascimento = request.getParameter("inputDataNasc");
        celular = request.getParameter("inputCelular");
        email = request.getParameter("inputEmail");
        password = request.getParameter("inputPassword");
        chkpassword = request.getParameter("inputChkPassword");

        if (Util.isInteger(celular) && Util.isValidEmailAddress(email) && nome != null && dataNascimento != null) {
            Cliente cliente = ClienteBuilder.novoClienteBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, password, celular).constroi();

            if (geraHash.checkPassword(chkpassword,cliente.getUsuario().getSenha())) {

                //Realiza o cadastro do usuário com passagem por referência - Na função será atribuído ao objeto o ID que foi gerado após o cadastro
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String sqlStateUsuario = usuarioDao.cadastraNovoUsuario(cliente.getUsuario());

                if (sqlStateUsuario == "0") {

                    //Instância Pessoa através da classe Cliente, utilizando passagem por ref. será atribuído ao objeto o ID que foi gerado após o cadastro
                    Pessoa objPessoa = new Pessoa();
                    objPessoa.setNome(cliente.getNome());
                    objPessoa.setDataNascimento(cliente.getDataNascimento());
                    objPessoa.setUsuario(cliente.getUsuario());

                    PessoaDAO pessoaDao = new PessoaDAO();
                    String sqlStatePessoa = pessoaDao.cadastrar(objPessoa);

                    if (sqlStatePessoa == "0") {
                        //Atribui o ID da Pessoa (que possuí Usuário) no objeto Cliente
                        //Apesar de ser herança e não ter o campo pessoa dentro de cliente, no banco de dados teremos o campo 'pessoa'
                        cliente.setIdPessoa(objPessoa.getIdPessoa());

                        ClienteDAO clienteDao = new ClienteDAO();

                        String sqlStateCliente = clienteDao.cadastrar(cliente);

                        if (sqlStateCliente == "0") {
                            request.setAttribute("colorMsg", "success");
                            return "Cadastrado com sucesso!";
                        } else {
                            pessoaDao.deletar(objPessoa);
                            usuarioDao.deletar(cliente.getUsuario());
                            request.setAttribute("colorMsg", "danger");
                            return "Cliente inválido! Entre em contato com o suporte.";
                        }
                    } else {

                        usuarioDao.deletar(cliente.getUsuario());

                        request.setAttribute("colorMsg", "danger");
                        return "Cliente inválido! Entre em contato com o suporte.";
                    }
                } else if ("23505".equals(sqlStateUsuario)) {
                    request.setAttribute("colorMsg", "danger");
                    return "Tente outro email ou celular!";
                } else {
                    request.setAttribute("colorMsg", "danger");
                    return "Não foi possível cadastrar, tente novamente ou entre em contato com o suporte!";
                }

            } else {
                request.setAttribute("colorMsg", "warning");
                return "Senhas diferentes!";
            }
        } else {
            request.setAttribute("colorMsg", "");
            return "";
        }

    }
}