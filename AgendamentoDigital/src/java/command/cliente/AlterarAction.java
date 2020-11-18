/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import builder.cliente.ClienteBuilder;
import command.usuario.*;
import dao.ClienteDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.objects.NativeString;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.Pessoa;
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

        String nome = request.getParameter("inputName");
        String dataNascimento = request.getParameter("inputDataNasc");
        String celular = request.getParameter("inputCelular");
        String email = request.getParameter("inputEmail");
        String alterarSenha = request.getParameter("chkAlterarSenha");
        String senha = request.getParameter("inputSenha");
        String chkSenha = request.getParameter("inputChkSenha");

        //Verifica Usuario logado
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)) {
            request.setAttribute("pagina", "/Funcionario/Cliente/Listar");
            email = request.getParameter("inputEmail");
            alterarSenha = request.getParameter("chkAlterarSenha");
            senha = request.getParameter("inputSenha");
            chkSenha = request.getParameter("inputChkSenha");
        } else {
            request.setAttribute("pagina", "/Cliente/MinhaConta");
            email = usuarioAutenticado.getEmail();
        }

        //Instanciando Cliente
        Cliente objCliente = new Cliente();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();

        String funcaoMsg = "";
        String funcaoStatus = "";

        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
            objCliente.setUsuario(usuarioAutenticado);
        } else if (usuarioAutenticado != null
                && (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM) || usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM))) {
            String id = request.getParameter("idCliente");
            objCliente.setIdCliente(Integer.parseInt(id));
        } else {
            objCliente.setIdCliente(Integer.parseInt(null));
            funcaoMsg = "Conta não autenticada!";
            funcaoStatus = "error";
            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return funcaoMsg;
        }

        if (Util.isInteger(celular) && Util.isValidEmailAddress(email) && Util.isValidName(nome) && dataNascimento != null) {

            Cliente cliente = ClienteBuilder.novoClienteBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, senha, celular).constroi();
            Cliente clienteAlterado = new Cliente();
            clienteAlterado.setNome(cliente.getNome());
            clienteAlterado.setDataNascimento(cliente.getDataNascimento());
            clienteAlterado.setUsuario((Usuario) cliente.getUsuario());

            cliente.setIdCliente(objCliente.getIdCliente());

            clienteDAO.buscar(cliente);
            pessoaDAO.buscar(cliente);
            usuarioDAO.buscarId(cliente.getUsuario());

            clienteAlterado.setIdPessoa(cliente.getIdPessoa());
            clienteAlterado.getUsuario().setIdUsuario(cliente.getUsuario().getIdUsuario());
            clienteAlterado.setIdCliente(objCliente.getIdCliente());

            String sqlState = clienteDAO.alterar(clienteAlterado);

            if ("0".equals(sqlState)) {

                String sqlStateAlterarUsuario = usuarioDAO.alterarUsuario(clienteAlterado.getUsuario());

                if (sqlStateAlterarUsuario == "0") {
                    if ("on".equals(alterarSenha) && !"".equals(senha) && !"".equals(chkSenha)) {
                        clienteAlterado.getUsuario().setSenha(geraHash.hashPassword(senha));
                        if (geraHash.checkPassword(chkSenha, clienteAlterado.getUsuario().getSenha())) {
                            sqlState = usuarioDAO.alterarSenha(clienteAlterado.getUsuario());
                            if (sqlState == "0") {
                                funcaoMsg = "Dados do cliente e do usuário alterados com sucesso\\nSenha alterada com sucesso!";
                                funcaoStatus = "success";
                            } else {
                                funcaoMsg = "Dados do cliente e do usuário alterados com sucesso\\nMas não foi possível alterar a senha, tente novamente!";
                                funcaoStatus = "warning";
                            }
                        } else {
                            funcaoMsg = "Senhas diferentes!";
                            funcaoStatus = "warning";
                        }
                    } else {
                        funcaoMsg = "Dados do cliente e do usuário alterados com sucesso!";
                        funcaoStatus = "success";
                    }
                } else if ("23505".equals(sqlStateAlterarUsuario)) {
                    funcaoMsg = "Dados do cliente alterados com sucesso!\\nMas não foi possível alterar o email nem celular, tente outro por favor!";
                    funcaoStatus = "error";
                } else {
                    funcaoMsg = "Dados do cliente alterados com sucesso!\\nMas não foi possível alterar o usuário, tente novamente!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "Não foi possível alterar os dados, tente novamente!\\nValide nome e data de nascimento!";
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
