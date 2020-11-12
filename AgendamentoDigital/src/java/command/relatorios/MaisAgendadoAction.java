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
public class MaisAgendadoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        String funcaoMsg = "";
        String funcaoStatus = "";

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
        arr = objRelatorioDAO.listarMaisAgendado();
        if (request.getAttribute("funcaoMsg") != null) {
            funcaoMsg = request.getAttribute("funcaoMsg").toString();
            funcaoStatus = request.getAttribute("funcaoStatus").toString();
        } else if (arr.size() != 0) {
            funcaoMsg = "Busca concluida, dados encontrados!";
            funcaoStatus = "success";
        } else {
            funcaoMsg = "Busca concluida, porém não dados para o mesmo!";
            funcaoStatus = "error";
        }

        JsonArray arrJson = new JsonArray();

        for (RelatorioServico objRelatorio : arr) {
            JsonObject json = (JsonObject) objgson.toJsonTree(objRelatorio);
            json.remove("idAgendamento");
            json.remove("idCliente");
            json.remove("idFuncionario");
            arrJson.add(json);
        }

        String json = arrJson.toString();
        request.setAttribute("pagina", "/pages/admin/relatorios/relatorio.jsp");
        request.setAttribute("pgjs", "maisAgendado");
        request.setAttribute("command", "MaisAgendado");
        request.setAttribute("pgAba", "Relatorio de serviço mais agendado");
        request.setAttribute("pgTitulo", "Relatorios de Serviços");
        request.setAttribute("pgRelatorio", "Serviços mais utilizados total");
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return json;

    }

}
