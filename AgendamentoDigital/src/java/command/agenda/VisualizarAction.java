/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AgendamentoDAO;
import dao.FuncionarioDAO;
import dao.ServicoDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Agendamento;

/**
 *
 * @author Rafael Pereira
 */
public class VisualizarAction implements ICommand {

    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("pagina", "pages/client/listarAgendamento.jsp");

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listar();

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.listarCompletoId(objAgendamento.getFuncionario());
            objServicoDao.buscar_dados_basicos(objAgendamento.getServico());
        }

        return objgson.toJson(arr);

    }
}
