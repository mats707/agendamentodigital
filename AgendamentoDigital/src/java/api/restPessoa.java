/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PessoaDAO;
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
import modelos.Pessoa;
/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Pessoa")
public class restPessoa {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public restPessoa() {
    }

    /**
     * Retrieves representation of an instance of api.restPessoa
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String getPessoa() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        PessoaDAO objDao = new PessoaDAO();
        
        ArrayList<Pessoa> arr;
        arr = objDao.listar();

        return objgson.toJson(arr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BuscarNome/{nome}")
    public String buscarPessoaNome(@PathParam("nome") String nome) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        PessoaDAO objDao = new PessoaDAO();
        
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        
        objDao.buscar(pessoa);

        return objgson.toJson(pessoa);
    }
}
