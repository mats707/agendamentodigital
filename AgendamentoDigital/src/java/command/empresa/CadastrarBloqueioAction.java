/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.empresa;

import dao.BloqueioAgendaDAO;
import dao.FuncionarioDAO;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Agendamento;
import modelos.BloqueioAgenda;
import modelos.Funcionario;

/**
 *
 * @author Rafael Pereira
 */
public class CadastrarBloqueioAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Inicialização dos objetos
        BloqueioAgenda bloqueio = new BloqueioAgenda();
        BloqueioAgendaDAO objDAO = new BloqueioAgendaDAO();
        FuncionarioDAO objFuncDAO = new FuncionarioDAO();

        //Inicialização das variaveis
        String dataString = request.getParameter("dataBloqueio");
        String duracaoString = request.getParameter("duracaoBloqueio");
        String horaInicialString = request.getParameter("horaBloqueio");
        String[] funcionarioString = request.getParameterValues("listaFuncionarios");
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";
        String sqlState = "";
        int countok = 0;
        int counterro = 0;

        if (dataString != null && duracaoString != null && horaInicialString != null && funcionarioString != null) {
            //Converte String do intervalo para o tipo Duration
            Duration tempo = Duration.ofHours(Integer.parseInt("00"));
            tempo = tempo.plusMinutes(Integer.parseInt(duracaoString));
            tempo = tempo.plusSeconds(Integer.parseInt("00"));
            bloqueio.setDuracaoBloqueio(tempo);

            //Converte String para data
            Date dataBloqueio = null;
            try {
                dataBloqueio = new SimpleDateFormat("dd/MM/yyyy").parse(dataString);
            } catch (ParseException ex) {
                Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            bloqueio.setDataBloqueio(dataBloqueio);

            //Converte String para hora
            Time horaInicial = null;
            DateFormat formatter = new SimpleDateFormat("kk:mm");
            try {
                horaInicial = new java.sql.Time(formatter.parse(horaInicialString).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            bloqueio.setHoraInicial(horaInicial);
            funcaoMsg = "";
            funcaoStatus = "";
            for (int i = 0; i < funcionarioString.length; i++) {
                Funcionario objFunc = new Funcionario();
                objFunc.setIdFuncionario(Integer.parseInt(funcionarioString[i]));
                bloqueio.setFuncionario(objFunc);
                objFuncDAO.buscar(objFunc);
                sqlState = objDAO.cadastrar(bloqueio);
                //Verifica o retorno da DAO (banco de dados)
                if (sqlState == "0") {
                    funcaoMsg += objFunc.getNome() + " foi cadastrado com sucesso! ";
                    if (funcaoStatus != "error") {
                        funcaoStatus = "success";
                    }

                } else if (sqlState.equalsIgnoreCase("unqbloqueioagenda")) {
                    // Independente do serviço o cliente não poderá agendar em um horário que ele já marcou/agendou
                    funcaoMsg += objFunc.getNome() + " já possui bloqueio nesta data e horario! ";
                    funcaoStatus = "error";
                }

            }

        } else {
            funcaoMsg = "Dados não correspondentes!";
            funcaoStatus = "error";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("pagina", "MenuBloqueio");

        return null;
    }

}
