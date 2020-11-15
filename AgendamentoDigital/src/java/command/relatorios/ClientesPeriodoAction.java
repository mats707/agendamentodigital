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
import javax.servlet.http.HttpSession;
import modelos.PerfilDeAcesso;
import modelos.RelatorioServico;
import modelos.StatusAgendamento;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class ClientesPeriodoAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Verifica Perfil Usuario
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        String perfil = "";
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOADMIN)) {
            perfil = "admin";
        } else if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)) {
            perfil = "funcionario";
        }

        request.setAttribute("pagina", "/pages/funcionario/relatorios/relatorio.jsp");

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        StatusAgendamento status = null;
        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        String mes_String = request.getParameter("mes");
        String ano_String = request.getParameter("ano");
        String funcaoMsg;
        String funcaoStatus;
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
                    arr = objRelatorioDAO.listarClientePeriodoStatus(mes, ano, status);
                } else {
                    arr = objRelatorioDAO.listarClientePeriodo(mes, ano);
                }

                if (arr.size() != 0) {
                    funcaoMsg = "Filtro realizado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Nenhuma informação encontrada para o filtro selecionado!";
                    funcaoStatus = "info";
                }
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
                request.setAttribute("pgperfil", perfil);
                request.setAttribute("pgjs", "cliente");
                request.setAttribute("command", "Cliente");
                request.setAttribute("pgAba", "Relatório de Clientes");
                request.setAttribute("pgTitulo", "Relatório de Clientes");
                request.setAttribute("pgRelatorio", "Quantidade de serviços realizados por clientes no período de " + mes + "/" + ano);
                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return json;

            } else {
                funcaoMsg = "Verifique os campos mês e ano!\\nOs dados estão incompatíveis";
                funcaoStatus = "error";
                request.setAttribute("pagina", "/Relatorios/Servicos/Cliente");
                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return null;
            }
        } else if (status_String != null) {
            ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();

            arr = objRelatorioDAO.listarClienteStatus(status);
            request.setAttribute("pgRelatorio", "Serviços " + status);

            if (arr.size() != 0) {
                funcaoMsg = "Filtro realizado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Nenhuma informação encontrada para o filtro selecionado!";
                funcaoStatus = "info";
            }

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

            request.setAttribute("pgperfil", perfil);
            request.setAttribute("pgjs", "clientes");
            request.setAttribute("command", "Cliente");
            request.setAttribute("pgAba", "Relatório de Clientes");
            request.setAttribute("pgTitulo", "Relatório de Clientes");

            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return json;
        } else {
            funcaoMsg = "Verifique os campos mês e ano!\\nOs dados estão incompatíveis";
            funcaoStatus = "error";
            request.setAttribute("pagina", "/Relatorios/Servicos/Cliente");
            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return null;
        }

    }

    public void retornaerro() {

    }

}
