/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.FuncionarioDAO;
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
import modelos.Funcionario;
/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Funcionario")
public class restFuncionario {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public restFuncionario() {
    }

    /**
     * Retrieves representation of an instance of api.restFuncionario
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String getFuncionario() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        FuncionarioDAO objDao = new FuncionarioDAO();
        
        ArrayList<Funcionario> arr;
        arr = objDao.listar();

        return objgson.toJson(arr);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/ListarCompleto")
    public String getFuncionarioCompleto() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        FuncionarioDAO objDao = new FuncionarioDAO();
        
        ArrayList<Funcionario> arr;
        arr = objDao.listarCompleto();

        return objgson.toJson(arr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BuscarNome/{nome}")
    public String buscarFuncionarioNome(@PathParam("nome") String nome) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        FuncionarioDAO objDao = new FuncionarioDAO();
        
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        
        objDao.buscar(funcionario);

        return objgson.toJson(funcionario);
    }
}
