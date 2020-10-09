/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import dao.FuncionarioDAO;
import dao.EmpresaDAO;
import dao.ServicoDAO;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelos.Funcionario;
import modelos.Empresa;
import modelos.Cliente;
import modelos.Servico;
import util.TimeDeserializer;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Empresa")
public class restEmpresa {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restEmpresa
     */
    public restEmpresa() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String listar() throws SQLException, ClassNotFoundException {

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson objgson = gsonBuilder.setPrettyPrinting().create();

            Empresa objEmpresa = new Empresa();
            EmpresaDAO objEmpresaDAO = new EmpresaDAO();

            objEmpresaDAO.buscar(objEmpresa);

            String json = objgson.toJson(objEmpresa);
            return json;
        } catch (JsonParseException ex) {
            return ex.getMessage();
        }
    }

}
