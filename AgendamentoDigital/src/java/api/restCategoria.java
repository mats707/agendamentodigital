/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Categoria")
public class restCategoria {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restCategoria
     */
    public restCategoria() {
    }

    /**
     * Retrieves representation of an instance of api.restCategoria
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of restCategoria
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void putJson(String content) {
    }
}
