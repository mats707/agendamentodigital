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
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class ClienteAction implements ICommand{
    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //Verifica Perfil Usuario
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        String perfil = "";
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOADMIN)) {
            perfil = "admin";
        } else if (usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)){
            perfil = "funcionario";
        }
        
        request.setAttribute("pagina", "/pages/funcionario/relatorios/relatorio.jsp");
        
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        String funcaoMsg = "";
        String funcaoStatus = "";

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
        arr = objRelatorioDAO.listarCliente();
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
            json.remove("idServico");
            arrJson.add(json);
        }

        String json = arrJson.toString();
        request.setAttribute("pgperfil", perfil);
        request.setAttribute("pgjs", "cliente");
        request.setAttribute("command", "Cliente");
        request.setAttribute("pgAba", "Relatório de Clientes");
        request.setAttribute("pgTitulo", "Relatórios de Clientes");
        request.setAttribute("pgRelatorio", "Serviços mais utilizados total");
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return json;

    }
}
