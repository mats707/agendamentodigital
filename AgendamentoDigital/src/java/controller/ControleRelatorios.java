/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import command.relatorios.ICommand;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Pereira
 */
@WebServlet(name = "ControleRelatorios", urlPatterns = {"/ControleRelatorios",
     "/RelatoriosServico",
     "/MaisAgendados",   
     "/Relatorios/Servicos/MaisAgendado",
     "/Relatorios/Servicos/MaisAgendado/Periodo",
     "/Relatorios/Servicos/MaisTrabalhado",
     "/Relatorios/Servicos/MaisTrabalhado/Periodo"
     
})
public class ControleRelatorios extends HttpServlet {

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

            if (uri.equals(request.getContextPath() + "/Relatorios/Servicos/MaisAgendado")) {
                acao = "MaisAgendado";
            } else if (uri.equals(request.getContextPath() + "/Relatorios/Servicos/MaisAgendado/Periodo")) {
                acao = "MaisAgendadoPeriodo";
            } else if (uri.equals(request.getContextPath() + "/Relatorios/Servicos/MaisTrabalhado/Periodo")) {
                acao = "MaisTrabalhadoPeriodo";
            } else if (uri.equals(request.getContextPath() + "/Relatorios/Servicos/MaisTrabalhado")) {
                acao = "MaisTrabalhado";
            } else {
                response.sendRedirect("404.jsp");
            }

            String nomedaclasse = "command.relatorios." + acao + "Action";

            //perceba que estamos usando um FACTORY
            Class classeAction = Class.forName(nomedaclasse);

            //Aqui estamos chamando a Acao que queremos
            ICommand comando_acao = (ICommand) classeAction.newInstance();

            String objRelatorio = comando_acao.executar(request, response);

            String pagina = request.getAttribute("pagina").toString();

            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            request.setAttribute("msg", objRelatorio);
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

}
