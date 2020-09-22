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
import dao.EmpresaDAO;
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
import java.util.HashMap;
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
import modelos.Empresa;
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
    @Path("/Testes")
    public String testes() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listar();

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.listarCompletoId(objAgendamento.getFuncionario());
            objServicoDao.buscar_dados_basicos(objAgendamento.getServico());
        }

        Agendamento objAgendamento = new Agendamento();
        objAgendamento.setCliente(new Cliente(1));
        objAgendamento.setFuncionario(new Funcionario(2));
        objAgendamento.setServico(new Servico(1));
        objAgendamento.setStatus(StatusAgendamento.AGUARDANDOATENDIMENTO);
        Date dataAgendamento = null;
        Time horaAgendamento = null;
        //Parse dataAgendamento
        try {
            dataAgendamento = new SimpleDateFormat("dd/MM/yyyy").parse("22/09/2020");
        } catch (ParseException ex) {
            Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        objAgendamento.setDataAgendamento(dataAgendamento);

        DateFormat formatter = new SimpleDateFormat("kk:mm");
        //Parse horaAgendamento
        try {
            horaAgendamento = new java.sql.Time(formatter.parse("10:30").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        objAgendamento.setHoraAgendamento(horaAgendamento);

        //Retorna horário ocupados pelo funcionário no dia solicitado
        ArrayList<Map<String, String>> arrayHorariosOcupados = new ArrayList<>();
        arrayHorariosOcupados = objAgendamentoDao.listarHorariosOcupados(objAgendamento);

        Empresa objEmpresa = new Empresa();
        EmpresaDAO objEmpresaDAO = new EmpresaDAO();
        objEmpresaDAO.buscar(objEmpresa);

//        Integer horaInicialAgendamento = objEmpresa.getHoraInicialTrabalho().getHours() * 60;
//        Integer horaFinalAgendamento = objEmpresa.getHoraFinalTrabalho().getHours() * 60;
        Integer intervaloAgendamentoHoraMin = objEmpresa.getIntervaloAgendamentoGeralServico().getHours() * 60;
        Integer intervaloAgendamentoMin = objEmpresa.getIntervaloAgendamentoGeralServico().getMinutes();
        Integer intervaloAgendamento = intervaloAgendamentoHoraMin + intervaloAgendamentoMin;

        ServicoDAO objServicoDAO = new ServicoDAO();
        objServicoDAO.buscar(objAgendamento.getServico());

        Long duracaoServicoLong = objAgendamento.getServico().getDuracao().toMinutes();
        Integer duracaoServico = Integer.parseInt(duracaoServicoLong.toString());

        if (duracaoServico >= intervaloAgendamento) {
            intervaloAgendamento = duracaoServico;
        }

        ArrayList<Map<String, String>> arrHorasOcupadas = new ArrayList<>();

        //Remove do array montado acima os horarios ocupados por esse funcionario e as horas entre o intervalo de duração
        for (int i = 0; i < arrayHorariosOcupados.size(); i++) {
            Map<String, String> mapHorarioOcupado = arrayHorariosOcupados.get(i);
            String horaOcupada = mapHorarioOcupado.get("hora");
            String duracaoServicoOcupado = mapHorarioOcupado.get("duracaoServico");

            //Remove do array montado acima os horarios que estão entre o horario ocupado e sua duracao
            Integer horas = Integer.parseInt(horaOcupada.split(":")[0]);
            Integer minutos = Integer.parseInt(horaOcupada.split(":")[1]);
            Integer horaOcupadaEmMinutos = (horas * 60) + minutos;
            Integer duracaoHoraOcupadaEmMinutos = Integer.parseInt(duracaoServicoOcupado) + horaOcupadaEmMinutos;

            for (Integer j = horaOcupadaEmMinutos; j < duracaoHoraOcupadaEmMinutos; j = j + intervaloAgendamento) {
                Map<String, String> hashHorasOcupadas = new HashMap<String, String>();
                hashHorasOcupadas.put("minutos", j.toString());
                arrHorasOcupadas.add(hashHorasOcupadas);
            }

        }

        Boolean validoParaAgendar = true;

        for (int i = 0; i < arrHorasOcupadas.size(); i++) {
            Map<String, String> horaOcupada = arrHorasOcupadas.get(i);
            Integer horaSelecionada = objAgendamento.getHoraAgendamento().getHours() * 60;
            Integer minutoSelecionado = objAgendamento.getHoraAgendamento().getMinutes();
            Integer minutoTotalSelecionado = horaSelecionada + minutoSelecionado;
            if (minutoTotalSelecionado == Integer.parseInt(horaOcupada.get("minutos"))) {
                validoParaAgendar = false;
            }
        }

        return objgson.toJson(validoParaAgendar);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/HorariosDisponiveis/Funcionario/{idFuncionario}/{dataEntrada}")
    public String listar(@PathParam("idFuncionario") Integer idFuncionario, @PathParam("dataEntrada") String dataEntrada) throws SQLException, ClassNotFoundException {

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
