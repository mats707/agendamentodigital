/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelos.Usuario;
/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Usuario")
public class restUsuario {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public restUsuario() {
    }

    /**
     * Retrieves representation of an instance of api.restUsuario
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String getUsuario() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        UsuarioDAO objDao = new UsuarioDAO();
        
        ArrayList<Usuario> arr;
        arr = objDao.listar();

        return objgson.toJson(arr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BuscarEmail/{email}")
    public String buscarUsuarioEmail(@PathParam("email") String email) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        UsuarioDAO objDao = new UsuarioDAO();
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        
        objDao.buscarEmail(usuario);

        return objgson.toJson(usuario);
    }
}
