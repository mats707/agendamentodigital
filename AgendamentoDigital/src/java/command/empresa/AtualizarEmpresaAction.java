/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.empresa;

import dao.EmpresaDAO;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Empresa;
import util.Util;

/**
 *
 * @author Rafael Pereira
 */
public class AtualizarEmpresaAction implements ICommand {

    DateFormat formatter = new SimpleDateFormat("kk:mm");
    String funcaoMsg;
    String funcaoStatus;

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/admin/empresa/menuEmpresa.jsp");

        //Obtendo os parametros do REQUEST
        String nome = request.getParameter("nome");
        String horaAberturaString = request.getParameter("timePickerAbertura");
        String horaEncerramentoString = request.getParameter("timepickerEncerramento");
        String intervaloAgendamentoGeralServicoString = request.getParameter("intervaloAgendamentoGeralServico");
        String periodoMinimoCancelamentoString = request.getParameter("periodoMinimoCancelamento");
        String[] diaSemanaString = request.getParameterValues("diasemana");
        String[] telefone = request.getParameterValues("telefone");
        String email = request.getParameter("email");

        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        if (nome != null && horaAberturaString != null && horaEncerramentoString != null
                && intervaloAgendamentoGeralServicoString != null
                && periodoMinimoCancelamentoString != null
                && diaSemanaString != null && telefone != null && email != null) {

            Empresa objEmpresa = new Empresa();
            objEmpresa.setNome(nome);

            Time horaAbertura = null;
            Time horaEncerramento = null;

            //Converte String do timepicker de Abertura para time
            try {
                horaAbertura = new java.sql.Time(formatter.parse(horaAberturaString).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
            }
            objEmpresa.setHoraInicialTrabalho(horaAbertura);
            //Converte String do timepicker de Encerramento para time
            try {
                horaEncerramento = new java.sql.Time(formatter.parse(horaEncerramentoString).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
            }
            objEmpresa.setHoraFinalTrabalho(horaEncerramento);

            //Converte String do intervalo para o tipo Duration
            Duration intervaloAgendamentoGeralServicoDuration = Duration.ofHours(Integer.parseInt("00"));
            intervaloAgendamentoGeralServicoDuration = intervaloAgendamentoGeralServicoDuration.plusMinutes(Integer.parseInt("00"));
            intervaloAgendamentoGeralServicoDuration = intervaloAgendamentoGeralServicoDuration.plusSeconds(Integer.parseInt(intervaloAgendamentoGeralServicoString));
            objEmpresa.setIntervaloAgendamentoGeralServico(intervaloAgendamentoGeralServicoDuration);

            //Converte String do intervalo para o tipo Duration
            Duration periodoMinimoCancelamentoDuration = Duration.ofHours(Integer.parseInt("00"));
            periodoMinimoCancelamentoDuration = periodoMinimoCancelamentoDuration.plusMinutes(Integer.parseInt("00"));
            periodoMinimoCancelamentoDuration = periodoMinimoCancelamentoDuration.plusSeconds(Integer.parseInt(periodoMinimoCancelamentoString));
            objEmpresa.setPeriodoMinimoCancelamento(periodoMinimoCancelamentoDuration);

            //Converte a String para array<Integer>
            ArrayList<Integer> arrDia = new ArrayList<Integer>();
            for (int i = 0; i < diaSemanaString.length; i++) {
                arrDia.add(Integer.parseInt(diaSemanaString[i]));
            }
            objEmpresa.setDiaSemanaTrabalho(arrDia);

            //Converte a String para array<Integer>
            ArrayList<Long> arrTelefone = new ArrayList<Long>();
            for (int i = 0; i < telefone.length; i++) {
                String telefoneString = telefone[i];
                if (Util.isInteger(telefoneString));
                {
                    telefoneString = telefoneString.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
                    arrTelefone.add(Long.parseLong(telefoneString));
                }
            }
            objEmpresa.setTelefone(arrTelefone);
            objEmpresa.setEmail(email);

            EmpresaDAO empresaDAO = new EmpresaDAO();

            Empresa objEmpresaAntiga = new Empresa();
            empresaDAO.buscar(objEmpresaAntiga);

            objEmpresa.setIdEmpresa(objEmpresaAntiga.getIdEmpresa());
            sqlState = empresaDAO.alterar(objEmpresa);

            if (sqlState == "0") {
                funcaoMsg = "Alterado com sucesso";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Infelizmente não foi possível alterar os dados de empresa\\nContate o suporte administrador";
                funcaoStatus = "error";
            }
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return null;
    }

}
