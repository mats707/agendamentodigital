/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("CadastrarCategoria/{categorias}")
    public String cadastrarCategoriaNome(@PathParam("categorias") String stringCategorias) throws SQLException, ClassNotFoundException {

        String result = "{}";

        //Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        ServicoDAO servicoDAO = new ServicoDAO();

        String[] categorias;
        Servico lastService = new Servico();

        categorias = stringCategorias.split("\\,");

        if (categorias != null) {
            Servico newCategoria = new Servico();
            newCategoria.setNome(categorias[0]);
            newCategoria.setServicoPai(lastService);
            String sqlState = servicoDAO.cadastraNovaCategoria(newCategoria);
            if (sqlState == "0") {
                lastService = newCategoria;
                for (int i = 1; i < categorias.length; i++) {
                    Servico subCategoria = new Servico();
                    subCategoria.setNome(categorias[i]);
                    subCategoria.setServicoPai(lastService);
                    sqlState = servicoDAO.cadastraNovoServico(subCategoria);
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
