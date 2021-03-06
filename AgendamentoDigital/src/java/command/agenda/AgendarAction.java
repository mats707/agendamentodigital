/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import api.restEmail;
import command.servico.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AgendamentoDAO;
import dao.EmpresaDAO;
import dao.ServicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.objects.NativeString;
import modelos.Agendamento;
import modelos.CategoriaServico;
import modelos.Cliente;
import modelos.Empresa;
import modelos.Funcionario;
import modelos.PerfilDeAcesso;
import modelos.Servico;
import modelos.StatusAgendamento;
import modelos.Usuario;
import api.restAgendamento;
import java.sql.SQLException;

/**
 *
 * @author alunocmc
 */
public class AgendarAction implements ICommand {

    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private restAgendamento objRestAgendamento = new restAgendamento();
    private restEmail objRestEmail = new restEmail();

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {

        //Configurações para o datetimepicker
        int day = Calendar.DAY_OF_MONTH;
        int month = Calendar.MONTH;
        int year = Calendar.YEAR;
        String datahoje = year + "-" + month + "-" + day;
        String datamaxima = year + "-" + (month + 1) + "-" + day;
        String allowTimes = "'09:00','11:00','12:00','20:00'";
        String[] ArrayAllowTimes = allowTimes.split(",");
        String maxTime = ArrayAllowTimes[ArrayAllowTimes.length - 1].substring(0, 5) + "1'"; //Soma 1 minuto no ultimo tempo permitido
        String minTime = ArrayAllowTimes[0];
        String maxMonth = "3";
        //Pagina a ser exibida
        request.setAttribute("pagina", "pages/client/agendamento/agendar.jsp");

        //Obtendo os parametros do REQUEST
        String idServico = request.getParameter("listaServico");
        String idFuncionario = request.getParameter("listaFuncionarios");
        String idCliente = request.getParameter("listaClientes");
        if (idCliente == null) {
            idCliente = "0";
        }
        String data = request.getParameter("inputDate");
        String hora = request.getParameter("listaHorarios");

        DateFormat formatter = new SimpleDateFormat("kk:mm");

        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";
        String StatusEmail = null;

        if (idServico != null && idFuncionario != null && data != null && hora != null) {

            //Instanciando Funcionario
            Funcionario objFuncionario = new Funcionario();
            objFuncionario.setIdFuncionario(Integer.parseInt(idFuncionario));

            //Instanciando Servico
            Servico objServico = new Servico();
            objServico.setIdServico(Integer.parseInt(idServico));

            //Instanciando Cliente
            Cliente objCliente = new Cliente();

            //Verifica Usuario Cliente
            //cria uma sessao para resgatar o usuario
            HttpSession sessaoUsuario = request.getSession();
            Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
            if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
                objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
            } else {
                objCliente.setIdCliente(Integer.parseInt(idCliente));
            }

            //Instanciando StatusAgendamento
            StatusAgendamento objStatus = StatusAgendamento.AGUARDANDOATENDIMENTO;

            //Instanciando Agendamento
            Agendamento objAgendamento = new Agendamento();
            objAgendamento.setCliente(objCliente);
            objAgendamento.setFuncionario(objFuncionario);
            objAgendamento.setServico(objServico);
            objAgendamento.setStatus(objStatus);
            Date dataAgendamento = null;
            Time horaAgendamento = null;
            //Parse dataAgendamento
            try {
                dataAgendamento = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            } catch (ParseException ex) {
                Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setDataAgendamento(dataAgendamento);

            //Parse horaAgendamento
            try {
                horaAgendamento = new java.sql.Time(formatter.parse(hora).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setHoraAgendamento(horaAgendamento);

            ServicoDAO objServicoDAO = new ServicoDAO();
            objServicoDAO.buscar(objAgendamento.getServico());

            String validoParaAgendar = objRestAgendamento.validoParaAgendar(objAgendamento);

            if (validoParaAgendar == "valido") {
                //Chamada da DAO para Cadastrar
                sqlState = agendamentoDAO.cadastrar(objAgendamento);
                //Verifica o retorno da DAO (banco de dados)
                if (sqlState == "0") {
                    StatusEmail = notificarAgendamento(objAgendamento);
                    if (StatusEmail == "email_enviado") {
                        funcaoMsg = "Agendado com sucesso!\\nUm e-mail foi enviado como lembrete.";
                        funcaoStatus = "success";
                    } else {
                        funcaoMsg = "Agendamento Realizado!\\nInfelizmente houve uma falha ao enviar o e-mail de confirmação.";
                        funcaoStatus = "info";
                    }
                } else if (sqlState.equalsIgnoreCase("unqagendamentocliente")) {
                    // Independente do serviço o cliente não poderá agendar em um horário que ele já marcou/agendou
                    funcaoMsg = "Você já possui um agendamento nesse horário!";
                    funcaoStatus = "error";
                } else if (sqlState.equalsIgnoreCase("unqAgendamentoFuncionario")) {
                    // Independente do serviço o funcionário não poderá ter mais de um agendamento no mesmo horário
                    funcaoMsg = "O funcionário escolhido já possui hora marcada!\\nEscolha outro horário, por favor!";
                    funcaoStatus = "error";
                } else {
                    funcaoMsg = "Não foi possível realizar o agendamento, tente novamente mais tarde!";
                }
            } else if (validoParaAgendar == "funcionario_ocupado") {
                funcaoMsg = "O funcionário escolhido já possui um serviço agendando nesse horário!\\nEscolha outro horário, por favor!";
                funcaoStatus = "error";
            } else if (validoParaAgendar == "cliente_ocupado") {
                funcaoMsg = "Você possui um agendamento durante o horário escolhido!\\nEscolha outro horário ou cancele o agendamento em conflito...";
                funcaoStatus = "error";
            } else {
                funcaoMsg = "Erro de validação do horário,\\nem caso de dúvidas contate o suporte!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Carregando...\\nAguarde um momento!";
            funcaoStatus = "info";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("datahoje", datahoje);
        request.setAttribute("allowTimes", Arrays.toString(ArrayAllowTimes));
        request.setAttribute("maxTime", maxTime);
        request.setAttribute("minTime", minTime);
        request.setAttribute("maxMonth", maxMonth);
        return funcaoMsg;
    }

    private String notificarAgendamento(Agendamento objAgendamento) {
        String dataAgendamentoString = new SimpleDateFormat("dd-MM-yyyy").format(objAgendamento.getDataAgendamento());
        String horaInicialString = new SimpleDateFormat("kk:mm").format(objAgendamento.getHoraAgendamento());
        return objRestEmail.emailDispacherAgendar(objAgendamento.getCliente().getIdCliente(), objAgendamento.getServico().getIdServico(), objAgendamento.getFuncionario().getIdFuncionario(), dataAgendamentoString, horaInicialString);
    }
}
