/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ServicoDAO;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelos.Servico;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("Servico")
public class restServico {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restServico
     */
    public restServico() {
    }

    /**
     * Retrieves representation of an instance of services.restServico
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("BuscarNome/{nome}")
    public String buscarServicoNome(@PathParam("nome") String nome) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ServicoDAO objDao = new ServicoDAO();
        
        Servico servico = new Servico();
        servico.setNome(nome);
        
        objDao.buscar(servico);

        return objgson.toJson(servico);
    }

    /**
     * PUT method for updating or creating an instance of restServico
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
