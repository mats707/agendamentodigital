/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

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
import testes.ValidarCodigo;

/**
 *
 * @author alunocmc
 */
public class AgendarAction implements ICommand {

    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    restAgendamento objRestAgendamento = new restAgendamento();

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
                Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setDataAgendamento(dataAgendamento);

            //Parse horaAgendamento
            try {
                horaAgendamento = new java.sql.Time(formatter.parse(hora).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setHoraAgendamento(horaAgendamento);

            String validoParaAgendar = objRestAgendamento.verificarHorarioOcupado(objAgendamento);

            if (validoParaAgendar == "valido") {
                //Chamada da DAO para Cadastrar
                sqlState = agendamentoDAO.cadastrar(objAgendamento);
                //Verifica o retorno da DAO (banco de dados)
                if (sqlState == "0") {
                    funcaoMsg = "Agendado com sucesso!";
                    funcaoStatus = "success";
                } else if (sqlState.equalsIgnoreCase("unqagendamentocliente")) {
                    // Independente do serviço o cliente não poderá agendar em um horário que ele já marcou/agendou
                    funcaoMsg = "Você já possui um agendamento nesse horário!";
                    funcaoStatus = "error";
                } else if (sqlState.equalsIgnoreCase("unqAgendamentoFuncionario")) {
                    // Independente do serviço o funcionário não poderá ter mais de um agendamento no mesmo horário
                    funcaoMsg = "O funcionário escolhido já possui hora marcada! Escolha outro horário, por favor!";
                    funcaoStatus = "error";
                } else {
                    funcaoMsg = "Não foi possível realizar o agendamento, tente novamente mais tarde!";
                    funcaoStatus = "error";
                }
            } else if (validoParaAgendar == "funcionario_ocupado") {
                funcaoMsg = "O funcionário escolhido já possui um serviço agendando nesse horário! Escolha outro horário, por favor!";
                funcaoStatus = "error";
            } else if (validoParaAgendar == "cliente_ocupado") {
                funcaoMsg = "Você possui um agendamento durante o horário escolhido! Escolha outro horário ou cancele o agendamento em conflito...";
                funcaoStatus = "error";
            } else {
                funcaoMsg = "Erro de validação do horário, em caso de dúvidas contate o suporte!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Carregando...";
            funcaoStatus = "info";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("datahoje", datahoje);
        request.setAttribute("allowTimes", Arrays.toString(ArrayAllowTimes));
        request.setAttribute("maxTime", maxTime);
        request.setAttribute("minTime", minTime);
        request.setAttribute("maxMonth", maxMonth);
        return funcaoMsg;
    }

    private String validoParaAgendar(Agendamento objAgendamento) {

        String statusValidacao = "";

        //Retorna horário ocupados pelo funcionário no dia solicitado
        ArrayList<Map<String, String>> arrayHorariosOcupados = new ArrayList<>();
        arrayHorariosOcupados = agendamentoDAO.listarHorariosOcupados(objAgendamento);

        //Obtém os valores padrão de agendamento definido pela empresa
        Empresa objEmpresa = new Empresa();
        EmpresaDAO objEmpresaDAO = new EmpresaDAO();
        objEmpresaDAO.buscar(objEmpresa);
        Integer intervaloAgendamentoHoraMin = objEmpresa.getIntervaloAgendamentoGeralServico().getHours() * 60;
        Integer intervaloAgendamentoMin = objEmpresa.getIntervaloAgendamentoGeralServico().getMinutes();
        Integer intervaloAgendamento = intervaloAgendamentoHoraMin + intervaloAgendamentoMin;
        
        ServicoDAO objServicoDAO = new ServicoDAO();
        objServicoDAO.buscar(objAgendamento.getServico());

        Long duracaoServicoLong = objAgendamento.getServico().getDuracao().toMinutes();
        Integer duracaoServico = Integer.parseInt(duracaoServicoLong.toString());

        //Mantém o maior intervalo de agendamento como referência
        if (duracaoServico >= intervaloAgendamento) {
            intervaloAgendamento = duracaoServico;
        }

        //Calcula as horas ocupadas do funcionário e do cliente, respectivamente, atualizando conforme a duração de cada serviço já agendado
        ArrayList<Map<String, String>> arrayHorariosOcupados_new = calcularHorasOcupadas(arrayHorariosOcupados, intervaloAgendamento);

        //Verifica se o funcionário é válido, ou seja, se os horários que ele possui agendado tem conflito com o novo agendamento
        statusValidacao = validarHorasOcupadas(objAgendamento, arrayHorariosOcupados_new, "FUNCIONARIO_OCUPADO");

        return statusValidacao;

    }

    private ArrayList<Map<String, String>> calcularHorasOcupadas(ArrayList<Map<String, String>> arrayHorariosOcupados, Integer intervaloAgendamento) {

        ArrayList<Map<String, String>> arrayHorariosOcupados_new = new ArrayList<>();

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
                Integer horas_ocupado = j / 60;
                Integer minutos_ocupado = (j % 60);
                hashHorasOcupadas.put("horas", horas_ocupado.toString() + ":" + minutos_ocupado);
                arrayHorariosOcupados_new.add(hashHorasOcupadas);
            }
        }

        return arrayHorariosOcupados_new;
    }

    private String validarHorasOcupadas(Agendamento objAgendamento, ArrayList<Map<String, String>> arrayHorariosOcupados, String msgRetorno) {

        for (int i = 0; i < arrayHorariosOcupados.size(); i++) {
            Map<String, String> horaOcupada = arrayHorariosOcupados.get(i);
            Integer horaSelecionada = objAgendamento.getHoraAgendamento().getHours() * 60;
            Integer minutoSelecionado = objAgendamento.getHoraAgendamento().getMinutes();
            Integer minutoTotalSelecionado = horaSelecionada + minutoSelecionado;
            if (minutoTotalSelecionado == Integer.parseInt(horaOcupada.get("minutos"))) {
                return msgRetorno;
            }
        }
        
        return "VALIDO";
    }
}
