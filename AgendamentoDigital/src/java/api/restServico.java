/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.FuncionarioDAO;
import dao.ServicoDAO;
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
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Servico")
public class restServico {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restServico
     */
    public restServico() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String listar() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ServicoDAO objServicoDao = new ServicoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        ArrayList<Servico> arr = objServicoDao.listar();

        for (Servico objServico : arr) {
            for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                objFuncionarioDao.listarCompletoId(objFuncionario);
            }
        }

        return objgson.toJson(arr);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Buscar/{id}")
    public String buscar(@PathParam("id") Integer id) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ServicoDAO objServicoDao = new ServicoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        Servico objServico = new Servico();
        objServico.setIdServico(id);

        objServicoDao.buscar(objServico);

        if (objServico.getIdServico() != null) {
            for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                objFuncionarioDao.listarCompletoId(objFuncionario);
            }
        }

        return objgson.toJson(objServico);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Listar/Categoria/{id}")
    public String listarServicoPorCategoria(@PathParam("id") Integer id) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        Servico servico = new Servico();
        CategoriaServico categoria = new CategoriaServico();
        categoria.setIdCategoriaServico(id);
        servico.setCategoria(categoria);

        ServicoDAO objServicoDao = new ServicoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        ArrayList<Servico> arr = objServicoDao.listarPorCategoria(servico);

        for (Servico objServico : arr) {
            for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                objFuncionarioDao.listarCompletoId(objFuncionario);
            }
        }

        return objgson.toJson(arr);
    }
}
