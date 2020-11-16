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
public class CancelarBloqueioAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Inicialização dos objetos
        BloqueioAgenda bloqueio = new BloqueioAgenda();
        BloqueioAgendaDAO objDAO = new BloqueioAgendaDAO();
        Funcionario objFunc = new Funcionario();

        //Inicialização das variaveis
        String dataString = request.getParameter("dataBloqueio");
        String horaInicialString = request.getParameter("horaInicialBloqueio");
        String funcionarioString = request.getParameter("funcionarioBloqueio");
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";
        String sqlState = "";

        if (dataString != null && horaInicialString != null && funcionarioString != null) {

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
            //objFunc.setIdFuncionario(Integer.parseInt(funcionarioString));
            bloqueio.setFuncionario(new Funcionario(Integer.parseInt(funcionarioString)));
            funcaoMsg = "";
            funcaoStatus = "";
            sqlState = objDAO.deletar(bloqueio);
            //Verifica o retorno da DAO (banco de dados)
            if (sqlState == "0") {
                funcaoMsg = "Cancelamento feito com sucesso";
                funcaoStatus = "success";

            } else if (sqlState.equalsIgnoreCase("unqbloqueioagenda")) {
                // Independente do serviço o cliente não poderá agendar em um horário que ele já marcou/agendou
                funcaoMsg = "Infelizmente encontramos um erro\nContate um administrador";
                funcaoStatus = "error";
            }

        } else {
            funcaoMsg = "Dados não correspondentes!";
            funcaoStatus = "error";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("pagina", "/Administrador/BloqueioAgenda/Listar");

        return null;
    }

}
