/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CategoriaServicoDAO;
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

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/CategoriaServico")
public class restCategoriaServico {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restServico
     */
    public restCategoriaServico() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String listarCategoria() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        CategoriaServicoDAO objDao = new CategoriaServicoDAO();
        
        ArrayList<CategoriaServico> arr;
        arr = objDao.listar();

        return objgson.toJson(arr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/BuscarNome/{nome}")
    public String buscarCategoriaNome(@PathParam("nome") String nome) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        CategoriaServicoDAO objDao = new CategoriaServicoDAO();

        CategoriaServico categoriaServico = new CategoriaServico();
        categoriaServico.setNome(nome);

        objDao.buscar(categoriaServico);

        String json = objgson.toJson(categoriaServico);

        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/CadastrarCategoria/{categorias}/{descricoes}")
    public String cadastrarCategoriaNome(@PathParam("categorias") String stringCategorias, @PathParam("descricoes") String stringDescricoes) throws SQLException, ClassNotFoundException {

        String result = "{}";

        //Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        CategoriaServicoDAO categoriaServicoDAO = new CategoriaServicoDAO();

        String[] categorias;
        String[] descricoes;
        CategoriaServico lastService = new CategoriaServico();

        categorias = stringCategorias.split("\\,");
        descricoes = stringDescricoes.split("\\,");

        if (categorias != null) {
            CategoriaServico newCategoria = new CategoriaServico();
            newCategoria.setNome(categorias[0]);
            newCategoria.setDescricao(descricoes[0]);
            newCategoria.setCategoriaPai(lastService);
            String sqlState = categoriaServicoDAO.cadastraCategoria(newCategoria);
            if (sqlState == "0") {
                lastService = newCategoria;
                for (int i = 1; i < categorias.length; i++) {
                    CategoriaServico subCategoria = new CategoriaServico();
                    subCategoria.setNome(categorias[i]);
                    subCategoria.setDescricao(descricoes[i]);
                    subCategoria.setCategoriaPai(lastService);
                    sqlState = categoriaServicoDAO.cadastraCategoria(subCategoria);
                    if (sqlState == "0") {
                        lastService = subCategoria;
                        result = "{\"id\":\"0\",\"descricao\":\"Cadastrado com Sucesso!\"}";
                    } else {
                        result = "{\"id\":\"1\",\"descricao\":\"Categoria " + categorias[i] + " inválida\"}";
                    }
                }
            } else {
                result = "{\"id\":\"1\",\"descricao\":\"Categoria " + categorias[0] + " inválida\"}";
            }
        } else {
            result = "{\"id\":\"1\",\"descricao\":\"Categoria(s) inválida(s)\"}";
        }

        return result;
    }
}
