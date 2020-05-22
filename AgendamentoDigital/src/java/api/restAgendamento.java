/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.FuncionarioDAO;
import dao.AgendamentoDAO;
import dao.ServicoDAO;
import java.sql.SQLException;
import java.sql.Time;
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
import modelos.Funcionario;
import modelos.Agendamento;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("/Agendamento")
public class restAgendamento {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restAgendamento
     */
    public restAgendamento() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/Listar")
    public String listar() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listar();

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.listarCompletoId(objAgendamento.getFuncionario());
            objServicoDao.buscar_dados_basicos(objAgendamento.getServico());
        }

        return objgson.toJson(arr);
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @Path("/HoraDisponivel/Funcionario/{id}")
//    public String validarDisponibilidadeFuncionario(@PathParam("id") Integer id) throws SQLException, ClassNotFoundException {
//
//        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
//
//        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
//        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
//
//        Funcionario objFuncionario = new Funcionario();
//        objFuncionario.setIdFuncionario(id);
//
//        Agendamento objAgendamento = new Agendamento();
//        objAgendamento.setFuncionario(objFuncionario);
//        
//        ArrayList<Time> horariosDisponiveis = new ArrayList<>();
//        
//        horariosDisponiveis = objAgendamentoDao.validarDisponibilidadeFuncionario(objAgendamento);
//
//        if (objAgendamento.getIdAgendamento() != null) {
//            for (Funcionario objFuncionario : objAgendamento.getFuncionarios()) {
//                objFuncionarioDao.listarCompletoId(objFuncionario);
//            }
//        }
//
//        return objgson.toJson(objAgendamento);
//    }
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("Listar/Categoria/{id}")
//    public String listarAgendamentoPorCategoria(@PathParam("id") Integer id) throws SQLException, ClassNotFoundException {
//
//        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
//
//        Agendamento servico = new Agendamento();
//        CategoriaAgendamento categoria = new CategoriaAgendamento();
//        categoria.setIdCategoriaAgendamento(id);
//        servico.setCategoria(categoria);
//
//        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
//        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
//
//        ArrayList<Agendamento> arr = objAgendamentoDao.listarPorCategoria(servico);
//
//        for (Agendamento objAgendamento : arr) {
//            for (Funcionario objFuncionario : objAgendamento.getFuncionarios()) {
//                objFuncionarioDao.listarCompletoId(objFuncionario);
//            }
//        }
//
//        return objgson.toJson(arr);
//    }
}
