/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.categoriaservico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CategoriaServicoDAO;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.CategoriaServico;

/**
 *
 * @author alunocmc
 */
public class BuscarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "pages/admin/listarCategoriaServico.jsp");
        
        CategoriaServico objCategoriaServico = new CategoriaServico();
        
        objCategoriaServico.setNome(request.getParameter("nome"));

        CategoriaServicoDAO categoriaServicoDAO = new CategoriaServicoDAO();

        categoriaServicoDAO.buscar(objCategoriaServico);

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        return objgson.toJson(objCategoriaServico);
    }

}
