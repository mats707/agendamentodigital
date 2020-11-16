/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import command.cliente.ICommand;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author alunocmc
 */
@WebServlet(name = "controleCliente", urlPatterns = {
    "/CadastrarCliente",
    "/Funcionario/Cliente/Alterar",
    "/Funcionario/Cliente/Cadastrar",
    "/Funcionario/Cliente/Desativar",
    "/Funcionario/Cliente/Ativar",
    "/Funcionario/Cliente/Listar",
    "/Cliente/Home",
    "/Cliente/MinhaConta"})
public class ControleCliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String uri = request.getRequestURI();
            String acao = "";
            String acao_secundaria = request.getParameter("acao");

            Object paginaAttr = request.getAttribute("pagina");

            if (paginaAttr != null) {
                acao_secundaria = null;
            }

            if (uri.equals(request.getContextPath() + "/Funcionario/Cliente/Listar")) {
                acao = "Listar";
            } else if (uri.equals(request.getContextPath() + "/Funcionario/Cliente/Cadastrar")
                    || uri.equals(request.getContextPath() + "/CadastrarCliente")) {
                acao = "Cadastrar";
            } else if (uri.equals(request.getContextPath() + "/Funcionario/Cliente/Alterar")) {
                acao = "Alterar";
            } else if (uri.equals(request.getContextPath() + "/Cliente/Home")) {
                acao = "Home";
            } else if (uri.equals(request.getContextPath() + "/Cliente/MinhaConta")) {
                acao = "MinhaConta";
                acao = validarAcaoSecundaria(acao, acao_secundaria);
            } else if (uri.equals(request.getContextPath() + "/Funcionario/Cliente/Desativar")) {
                acao = "Desativar";
            } else if (uri.equals(request.getContextPath() + "/Funcionario/Cliente/Ativar")) {
                acao = "Ativar";
            } else {
                response.sendRedirect("404.jsp");
            }

            String nomedaclasse = "command.cliente." + acao + "Action";

            //perceba que estamos usando um FACTORY
            Class classeAction = Class.forName(nomedaclasse);

            //Aqui estamos chamando a Acao que queremos
            ICommand comando_acao = (ICommand) classeAction.newInstance();

            String objCliente = comando_acao.executar(request, response);

            String pagina = request.getAttribute("pagina").toString();

            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            request.setAttribute("msg", objCliente);
            rd.forward(request, response);

        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("erro", e);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String validarAcaoSecundaria(String acao, String acao_secundaria) {
        if (null == acao_secundaria) {
            return acao;
        } else {
            switch (acao_secundaria) {
                case "AlterarCliente":
                    return "Alterar";
                case "DesativarCliente":
                    return "Desativar";
                default:
                    return acao;
            }
        }
    }

}
