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

/**
 *
 * @author Rafael Pereira
 */
public class AtualizarEmpresaAction implements ICommand {

    DateFormat formatter = new SimpleDateFormat("kk:mm");
    Time horaAbertura;
    Time horaEncerramento;
    String funcaoMsg;
    String funcaoStatus;

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/admin/empresa/menuEmpresa.jsp");

        Empresa objEmpresa = new Empresa();

        EmpresaDAO empresaDAO = new EmpresaDAO();

        String nome = request.getParameter("nome");
        objEmpresa.setNome(nome);
        //Converte String do timepicker de Abertura para time
        String horaAberturaString = request.getParameter("timePickerAbertura");

        try {
            horaAbertura = new java.sql.Time(formatter.parse(horaAberturaString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        objEmpresa.setHoraInicialTrabalho(horaAbertura);

        //Converte String do timepicker de Encerramento para time
        String horaEncerramentoString = request.getParameter("timepickerEncerramento");
        try {
            horaEncerramento = new java.sql.Time(formatter.parse(horaEncerramentoString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        objEmpresa.setHoraFinalTrabalho(horaEncerramento);

        //Converte String do intervalo para o tipo Duration
        String intervaloString = request.getParameter("intervaloAgendamentoGeralServico");
        Duration tempo = Duration.ofHours(Integer.parseInt("00"));
        tempo = tempo.plusMinutes(Integer.parseInt(intervaloString));
        tempo = tempo.plusSeconds(Integer.parseInt("00"));
        objEmpresa.setIntervaloAgendamentoGeralServico(tempo);

        //Converte a String para array<Integer>
        ArrayList<Integer> arrDia = new ArrayList<Integer>();
        String[] diaSemanaString = request.getParameterValues("diasemana");

        for (int i = 0; i < diaSemanaString.length; i++) {
            arrDia.add(Integer.parseInt(diaSemanaString[i]));
        }
        objEmpresa.setDiaSemanaTrabalho(arrDia);

        //Converte a String para array<Integer>
        ArrayList<Long> arrTelefone = new ArrayList<Long>();
        String[] telefone = request.getParameterValues("telefone");

        for (int i = 0; i < telefone.length; i++) {
            arrTelefone.add(Long.parseLong(telefone[i]));
        }
        objEmpresa.setTelefone(arrTelefone);

        objEmpresa.setEmail(request.getParameter("email"));

        Empresa objEmpresaAntiga = new Empresa();

        empresaDAO.buscar(objEmpresaAntiga);

        objEmpresa.setIdEmpresa(objEmpresaAntiga.getIdEmpresa());

        if (empresaDAO.alterar(objEmpresa)) {
            funcaoMsg = "Alterado com sucesso";
            funcaoStatus = "success";
        } else {
            funcaoMsg="Falha na alteração";
            funcaoStatus="error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return null;
    }

}
