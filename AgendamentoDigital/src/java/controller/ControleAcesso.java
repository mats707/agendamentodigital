package controller;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.UsuarioDAO;
import modelos.*;

@WebServlet(name = "ControleAcesso", urlPatterns = {"/ControleAcesso"})
public class ControleAcesso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
            String acao = request.getParameter("acao");
            switch (acao) {
                case "Entrar": {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(request.getParameter("inputEmail"));
                    usuario.setSenha(request.getParameter("inputPassword"));
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    Usuario usuarioAutenticado = usuarioDAO.autenticaUsuario(usuario);
                    //se o usuario existe no banco de dados
                    if (usuarioAutenticado != null && usuario.logar(usuarioAutenticado)) {

                        //cria uma sessao para o usuario
                        HttpSession sessaoUsuario = request.getSession();
                        sessaoUsuario.setAttribute("usuarioAutenticado", usuarioAutenticado);

                        //redireciona para a pagina principal
                        response.sendRedirect(direcionar(usuarioAutenticado, sessaoUsuario));

                    } else {
                        request.setAttribute("msg", "Email ou Senha Incorreto!");
                        rd.forward(request, response);
                    }
                    break;
                }
                case "Sair": {
                    HttpSession sessaoUsuario = request.getSession();
                    sessaoUsuario.removeAttribute("usuarioAutenticado");
                    sessaoUsuario.removeAttribute("cliente");
                    response.sendRedirect("index.jsp");
                    break;
                }
                case "Validar": {
                    //cria uma sessao para resgatar o usuario
                    HttpSession sessaoUsuario = request.getSession();
                    Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
                    //se o usuario existe no banco de dados
                    if (usuarioAutenticado != null) {
                        response.sendRedirect(direcionar(usuarioAutenticado, sessaoUsuario));
                    } else {
                        rd.forward(request, response);
                    }
                    break;
                }
                default:
                    break;
            }
        } catch (IOException | ServletException erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private String direcionar(Usuario usuarioAutenticado, HttpSession sessaoUsuario) {
        
        Funcionario funcionario = new Funcionario();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        
        //redireciona para a pagina principal
        switch (usuarioAutenticado.getPerfil()) {
            case FUNCIONARIOADMIN:
                funcionario.setUsuario(usuarioAutenticado);
                funcionarioDAO.buscarUsuario(funcionario);
                sessaoUsuario.setAttribute("funcionario", funcionario);
                return ("pages/admin/home.jsp");
            case FUNCIONARIOCOMUM:
                funcionario.setUsuario(usuarioAutenticado);
                funcionarioDAO.buscarUsuario(funcionario);
                sessaoUsuario.setAttribute("funcionario", funcionario);
                return ("Funcionario/Home");
            case CLIENTECOMUM:
                cliente.setUsuario(usuarioAutenticado);
                clienteDAO.buscarUsuario(cliente);
                sessaoUsuario.setAttribute("cliente", cliente);
                return ("HomeCliente");
            default:
                return ("");
        }
    }
}
