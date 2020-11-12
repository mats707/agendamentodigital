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
import modelos.StatusAgendamento;

/**
 *
 * @author Rafael Pereira
 */
public class MaisAgendadoPeriodoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("pagina", "/pages/admin/relatorios/relatorio.jsp");
        
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        StatusAgendamento status = null;
        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        String funcaoMsg = "";
        String funcaoStatus = "";
        String mes_String = request.getParameter("mes");
        String ano_String = request.getParameter("ano");
        String status_String = request.getParameter("status");

        if (status_String != null) {
            switch (status_String) {
                case "AGUARDANDOATENDIMENTO":
                    status = StatusAgendamento.AGUARDANDOATENDIMENTO;
                    break;
                case "FINALIZADO":
                    status = StatusAgendamento.FINALIZADO;
                    break;
                case "CANCELADO":
                    status = StatusAgendamento.CANCELADO;
                    break;
                default:
                    status_String = null;
                    break;
            }
        }

        if (mes_String != null && ano_String != null) {
            int mes = Integer.parseInt(mes_String);
            int ano = Integer.parseInt(ano_String);
            if (mes >= 1 && mes <= 12 && ano_String.length() == 4 && ano >= 2019 && ano <= 2999) {

                ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
                if (status_String != null) {
                    arr = objRelatorioDAO.listarMaisAgendadoPeriodoStatus(mes, ano, status);
                    request.setAttribute("pgRelatorio", "Serviços " + status + " no periodo de " + mes + "/" + ano);
                } else {
                    arr = objRelatorioDAO.listarMaisAgendadoPeriodo(mes, ano);
                    request.setAttribute("pgRelatorio", "Serviços utilizados no periodo de " + mes + "/" + ano);
                }
                if (arr.size() != 0) {
                    funcaoMsg = "Filtro realizado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Filtro realizado com sucesso, porém não dados para o mesmo!";
                    funcaoStatus = "info";
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
                request.setAttribute("pgjs", "maisAgendado");
                request.setAttribute("command", "MaisAgendado");
                request.setAttribute("pgAba", "Relatorio de serviço mais agendado");
                request.setAttribute("pgTitulo", "Relatorios de Serviços");

                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return json;
            } else {
                funcaoMsg = "Verifique os campos mes e ano<br> Os dados estão incopativeis";
                funcaoStatus = "error";
                request.setAttribute("pagina", "/Relatorios/Servicos/MaisAgendado");
                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return null;

            }
        } else if (status_String != null) {
            ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();

            arr = objRelatorioDAO.listarMaisAgendadoStatus(status);
            request.setAttribute("pgRelatorio", "Serviços " + status);

            if (arr.size() != 0) {
                funcaoMsg = "Filtro realizado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Filtro realizado com sucesso, porém não dados para o mesmo!";
                funcaoStatus = "info";
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

            request.setAttribute("pagina", "/pages/funcionario/relatorios/relatorio.jsp");
            request.setAttribute("pgjs", "maisAgendado");
            request.setAttribute("command", "MaisAgendado");
            request.setAttribute("pgAba", "Relatorio de serviço mais agendado");
            request.setAttribute("pgTitulo", "Relatorios de Serviços");

            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return json;
        } else {
            funcaoMsg = "Verifique os campos mes e ano<br> Os dados estão incopativeis";
            funcaoStatus = "error";
            request.setAttribute("pagina", "/Relatorios/Servicos/MaisAgendado");
            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return null;

        }

    }

}
