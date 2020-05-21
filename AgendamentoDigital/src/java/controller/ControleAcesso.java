package controller;

import dao.ClienteDAO;
import dao.PessoaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;
import dao.UsuarioDAO;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import util.geraHash;

@WebServlet(name = "ControleAcesso", urlPatterns = {"/ControleAcesso"})
public class ControleAcesso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Entrar")) {
                Usuario usuario = new Usuario();
                usuario.setEmail(request.getParameter("inputEmail"));
                usuario.setSenha(geraHash.codificaBase64(request.getParameter("inputPassword")));
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuarioAutenticado = usuarioDAO.autenticaUsuario(usuario);

                Cliente cliente = new Cliente();
                ClienteDAO clienteDAO = new ClienteDAO();

                //se o usuario existe no banco de dados
                if (usuarioAutenticado != null && usuario.logar(usuarioAutenticado)) {

                    cliente.setUsuario(usuarioAutenticado);
                    clienteDAO.listarCompletoId(cliente);

                    //cria uma sessao para o usuario
                    HttpSession sessaoUsuario = request.getSession();
                    sessaoUsuario.setAttribute("usuarioAutenticado", usuarioAutenticado);
                    sessaoUsuario.setAttribute("cliente", cliente);

                    //redireciona para a pagina principal
                    response.sendRedirect(direcionar(usuarioAutenticado.getPerfil()));

                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    request.setAttribute("msg", "Email ou Senha Incorreto!");
                    rd.forward(request, response);
                }
            } else if (acao.equals("Sair")) {
                HttpSession sessaoUsuario = request.getSession();
                sessaoUsuario.removeAttribute("usuarioAutenticado");
                sessaoUsuario.removeAttribute("cliente");
                response.sendRedirect("index.jsp");

            } else if (acao.equals("Validar")) {

                //cria uma sessao para resgatar o usuario
                HttpSession sessaoUsuario = request.getSession();
                Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");

                //se o usuario existe no banco de dados
                if (usuarioAutenticado != null) {
                    response.sendRedirect(direcionar(usuarioAutenticado.getPerfil()));
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }

            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("erro.jsp");
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

    private String direcionar(PerfilDeAcesso perfil) {

        //redireciona para a pagina principal
        switch (perfil) {
            case FUNCIONARIOADMIN:
                return ("pages/admin/home.jsp");
            case FUNCIONARIOCOMUM:
                return ("pages/user/index3.jsp");
            case CLIENTECOMUM:
                return ("pages/client/home.jsp");
            default:
                return ("");
        }
    }
}
