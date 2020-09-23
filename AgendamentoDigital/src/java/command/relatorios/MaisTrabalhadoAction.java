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
public class MaisTrabalhadoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        request.setAttribute("pagina", "/pages/admin/relatorios/relatorio.jsp");
        request.setAttribute("pgjs", "maisTrabalhado");
        request.setAttribute("command", "MaisTrabalhado");
        request.setAttribute("pgAba", "Relatorio de funcionarios mais trabalhados");
        request.setAttribute("pgTitulo", "Relatorios de Funcionarios");
        request.setAttribute("pgRelatorio", "Funcionarios que mais prestaram serviços");

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
        arr = objRelatorioDAO.listarMaisTrabalhado();

        JsonArray arrJson = new JsonArray();

        for (RelatorioServico objRelatorio : arr) {
            JsonObject json = (JsonObject) objgson.toJsonTree(objRelatorio);
            json.remove("idAgendamento");
            json.remove("idCliente");
            json.remove("idFuncionario");
            json.remove("idServico");
            arrJson.add(json);
        }

        String json = arrJson.toString();

        return json;
    }

}
