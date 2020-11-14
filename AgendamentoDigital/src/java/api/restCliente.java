/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ClienteDAO;
import dao.PessoaDAO;
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
import modelos.Cliente;
/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Cliente")
public class restCliente {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public restCliente() {
    }

    /**
     * Retrieves representation of an instance of api.restCliente
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String getCliente() throws SQLException, ClassNotFoundException {

        ArrayList<Cliente> arr = new ArrayList<Cliente>();
        
        ClienteDAO clienteDAO = new ClienteDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        arr = clienteDAO.listar();
        
        for (Cliente cliente : arr) {
            pessoaDAO.buscar(cliente);
            usuarioDAO.buscarId(cliente.getUsuario());
        }

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        return objgson.toJson(arr);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/ListarCompleto")
    public String getClienteCompleto() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ClienteDAO objDao = new ClienteDAO();
        
        ArrayList<Cliente> arr;
        arr = objDao.listarCompleto();

        return objgson.toJson(arr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BuscarNome/{nome}")
    public String buscarClienteNome(@PathParam("nome") String nome) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ClienteDAO objDao = new ClienteDAO();
        
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        
        objDao.buscar(cliente);

        return objgson.toJson(cliente);
    }
}
