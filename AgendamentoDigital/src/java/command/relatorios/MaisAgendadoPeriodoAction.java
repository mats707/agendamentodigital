/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.relatorios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.RelatoriosDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.RelatorioServico;

/**
 *
 * @author Rafael Pereira
 */
public class MaisAgendadoPeriodoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        request.setAttribute("pagina", "/pages/admin/relatorios/maisAgendado.jsp");

        String mes_String = request.getParameter("mes");
        String ano_String = request.getParameter("ano");

        int mes = Integer.parseInt(mes_String);
        int ano = Integer.parseInt(ano_String);

        ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
        arr = objRelatorioDAO.listarMaisAgendadoPeriodo(mes, ano);

        JsonArray arrJson = new JsonArray();

        for (RelatorioServico objRelatorio : arr) {
            JsonObject json = (JsonObject) objgson.toJsonTree(objRelatorio);
            json.remove("idAgendamento");
            json.remove("idCliente");
            json.remove("idFuncionario");
            arrJson.add(json);
        }

        String json = arrJson.toString();

        return json;
    }

}
