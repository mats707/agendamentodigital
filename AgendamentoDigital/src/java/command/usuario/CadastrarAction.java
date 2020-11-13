/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.usuario;

import builder.cliente.ClienteBuilder;
import builder.funcionario.FuncionarioBuilder;
import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Cliente;
import modelos.Funcionario;
import modelos.Pessoa;
import util.Util;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    String funcaoMsg = "Carregando...";
    String funcaoStatus = "info";

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "pages/admin/usuarios/cadastrarUsuario.jsp");

        String nome = Util.stringToUTF8(request.getParameter("nome"));
        String dataNascimento = request.getParameter("dataNascimento");
        String celular = request.getParameter("celular");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String chksenha = request.getParameter("chksenha");
        String perfil = request.getParameter("perfil");

        if (Util.isInteger(celular) && Util.isValidEmailAddress(email) && nome != null && dataNascimento != null && perfil != null) {

            Funcionario funcionario = new Funcionario();
            Cliente cliente = new Cliente();

            if (perfil.equalsIgnoreCase("administrador") || perfil.equalsIgnoreCase("funcionariocomum")) {
                funcionario = FuncionarioBuilder.novoFuncionarioBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, senha, celular, perfil).constroi();
                cadastrarFuncionario(funcionario, chksenha);
            } else if (perfil.equalsIgnoreCase("clientecomum")) {
                cliente = ClienteBuilder.novoClienteBuilder().comNome(nome).nascidoEm(dataNascimento).comUsuario(email, senha, celular).constroi();
                cadastrarCliente(cliente, chksenha);
            } else {
                funcaoStatus = "error";
                funcaoMsg = "Perfil inválido!\\nNão foi possível cadastrar o usuário, tente novamente!";
            }
        } else {
            funcaoMsg = "Carregando...\\nAguarde um momento!";
            funcaoStatus = "info";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }

    private void cadastrarFuncionario(Funcionario funcionario, String chksenha) {
        if (geraHash.checkPassword(chksenha, funcionario.getUsuario().getSenha())) {

            //Realiza o cadastro do usuário com passagem por referência - Na função será atribuído ao objeto o ID que foi gerado após o cadastro
            UsuarioDAO usuarioDao = new UsuarioDAO();
            String sqlStateUsuario = usuarioDao.cadastraNovoUsuario(funcionario.getUsuario());

            if ("0".equals(sqlStateUsuario)) {

                //Instância Pessoa através da classe Cliente, utilizando passagem por ref. será atribuído ao objeto o ID que foi gerado após o cadastro
                Pessoa objPessoa = new Pessoa();
                objPessoa.setNome(funcionario.getNome());
                objPessoa.setDataNascimento(funcionario.getDataNascimento());
                objPessoa.setUsuario(funcionario.getUsuario());

                PessoaDAO pessoaDao = new PessoaDAO();
                String sqlStatePessoa = pessoaDao.cadastrar(objPessoa);

                if ("0".equals(sqlStatePessoa)) {
                    //Atribui o ID da Pessoa (que possuí Usuário) no objeto Cliente
                    //Apesar de ser herança e não ter o campo pessoa dentro de cliente, no banco de dados teremos o campo 'pessoa'
                    funcionario.setIdPessoa(objPessoa.getIdPessoa());

                    FuncionarioDAO funcionarioDao = new FuncionarioDAO();

                    String sqlStateCliente = funcionarioDao.cadastrar(funcionario);

                    if ("0".equals(sqlStateCliente)) {
                        funcaoMsg = "Cadastrado com sucesso!";
                        funcaoStatus = "success";
                    } else {
                        pessoaDao.deletar(objPessoa);
                        usuarioDao.deletar(funcionario.getUsuario());
                        funcaoMsg = "Funcionário inválido! Entre em contato com o suporte.";
                        funcaoStatus = "error";
                    }
                } else {
                    usuarioDao.deletar(funcionario.getUsuario());
                    funcaoMsg = "Funcionário inválido! Entre em contato com o suporte.";
                    funcaoStatus = "error";
                }
            } else if ("23505".equals(sqlStateUsuario)) {
                funcaoMsg = "Funcionário inválido! Entre em contato com o suporte.";
                funcaoStatus = "error";
            } else {
                funcaoMsg = "Não foi possível cadastrar, tente novamente ou entre em contato com o suporte!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Senhas diferentes!";
            funcaoStatus = "warning";
        }
    }

    private void cadastrarCliente(Cliente cliente, String chksenha) {
        if (geraHash.checkPassword(chksenha, cliente.getUsuario().getSenha())) {

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

                    if (sqlStateCliente == "0") {
                        funcaoMsg = "Cadastrado com sucesso!";
                        funcaoStatus = "success";
                    } else {
                        pessoaDao.deletar(objPessoa);
                        usuarioDao.deletar(cliente.getUsuario());
                        funcaoMsg = "Cliente inválido! Entre em contato com o suporte.";
                        funcaoStatus = "error";
                    }
                } else {
                    usuarioDao.deletar(cliente.getUsuario());
                    funcaoMsg = "Cliente inválido! Entre em contato com o suporte.";
                    funcaoStatus = "error";
                }
            } else if ("23505".equals(sqlStateUsuario)) {
                funcaoMsg = "Cliente inválido! Entre em contato com o suporte.";
                funcaoStatus = "error";
            } else {
                funcaoMsg = "Não foi possível cadastrar, tente novamente ou entre em contato com o suporte!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Senhas diferentes!";
            funcaoStatus = "warning";
        }
    }
}
