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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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
import modelos.Agendamento;
import modelos.Cliente;
import modelos.Servico;
import modelos.StatusAgendamento;
import testes.ValidarCodigo;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/HorariosDisponiveis/Servico/{idServico}/Funcionario/{idFuncionario}/{dataEntrada}")
    public String listar(@PathParam("idServico") Integer idServico, @PathParam("idFuncionario") Integer idFuncionario, @PathParam("dataEntrada") String dataEntrada) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        Date dataAgendamento = null;
        //Parse dataAgendamento
        try {
            dataAgendamento = new SimpleDateFormat("yyyy-MM-dd").parse(dataEntrada);
        } catch (ParseException ex) {
            Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setFuncionario(new Funcionario(idFuncionario));
        agendamento.setServico(new Servico(idServico));
        agendamento.setDataAgendamento(dataAgendamento);

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();

        ArrayList<Map<String, String>> horariosOcupados = objAgendamentoDao.listarHorariosOcupados(agendamento);

        return objgson.toJson(horariosOcupados);
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Listar/Cliente/{idCliente}")
    public String listarCliente(@PathParam("idCliente") Integer idCliente) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listarCliente(new Agendamento(new Cliente(idCliente)));

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.buscar(objAgendamento.getFuncionario());
            objServicoDao.buscar_dados_basicos(objAgendamento.getServico());
        }

        return objgson.toJson(arr);
    }
}
