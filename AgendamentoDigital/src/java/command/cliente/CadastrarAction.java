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
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "auth/login.jsp");

        String nome = request.getParameter("inputName");
        String dataNascimento = request.getParameter("inputDataNasc");
        String celular = request.getParameter("inputCelular");
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        String chkpassword = request.getParameter("inputChkPassword");

        if (email != null && password != null && chkpassword != null && celular != null) {
            Cliente cliente = ClienteBuilder.novoClienteBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, password, celular).constroi();

            if (geraHash.codificaBase64(chkpassword).equals(cliente.getUsuario().getSenha())) {
                
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

                        ClienteDAO cienteDao = new ClienteDAO();

                        String sqlStateCliente = cienteDao.cadastrar(cliente);

                        if (sqlStateCliente == "0") {
                            request.setAttribute("colorMsg", "success");
                            return "Cadastrado com sucesso!";
                        } else {
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
