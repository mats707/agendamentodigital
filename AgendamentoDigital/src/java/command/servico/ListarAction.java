/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ServicoDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Servico;

/**
 *
 * @author alunocmc
 */
public class ListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "pages/admin/listarServico.jsp");

        ArrayList<Servico> arr = new ArrayList<Servico>();

        ServicoDAO servicoDAO = new ServicoDAO();

        arr = servicoDAO.listar();

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        return objgson.toJson(arr);

    }

}
