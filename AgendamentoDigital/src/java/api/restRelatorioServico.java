/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.*;
import dao.RelatoriosDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelos.RelatorioServico;

/**
 * REST Web Service
 *
 * @author Rafael Pereira
 */
@Path("/Relatorios")
public class restRelatorioServico {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restRelatorioServico
     */
    public restRelatorioServico() {
    }

    /**
     * Retrieves representation of an instance of api.restRelatorioServico
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Servicos/MaisAgendado")
    public String getMaisAgendado() throws SQLException, ClassNotFoundException {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

        ArrayList<RelatorioServico> arr = new ArrayList<RelatorioServico>();
        arr = objRelatorioDAO.listarMaisAgendado();

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Servicos/MaisAgendado/{mes}/{ano}")
    public String getMaisAgendadoPeriodo(@PathParam("mes") Integer mes, @PathParam("ano") Integer ano) throws SQLException, ClassNotFoundException {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        RelatoriosDAO objRelatorioDAO = new RelatoriosDAO();

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Servicos/MaisTrabalhado")
    public String getMaisTrabalhado() throws SQLException, ClassNotFoundException {
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

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

