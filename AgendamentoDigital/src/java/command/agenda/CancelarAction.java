/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import api.restAgendamento;
import dao.AgendamentoDAO;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Agendamento;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.StatusAgendamento;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class CancelarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/client/home.jsp");
        String idCliente = request.getParameter("listaClientes");
        if (idCliente == null) {
            idCliente = "0";
        }

        Agendamento objAgendamento = new Agendamento();
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        Cliente objCliente = new Cliente();

        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
        } else {
            objCliente.setIdCliente(Integer.parseInt(idCliente));
        }

        AgendamentoDAO objDAO = new AgendamentoDAO();
        String dataAgendamentoString =request.getParameter("dataAgendamento");
        Date dataAgendamento = null;
        try {
            dataAgendamento = new SimpleDateFormat("dd/MM/yyyy").parse(dataAgendamentoString);
        } catch (ParseException ex2) {
            Logger.getLogger(restAgendamento.class.getName()).log(Level.SEVERE, null, ex2);
        }
        Time horaAgendamento = null;
        String horaAgendamentoString = request.getParameter("horaAgendamento");
        DateFormat formatter = new SimpleDateFormat("kk:mm");
        //Parse horaAgendamento
        try {
            horaAgendamento = new java.sql.Time(formatter.parse(horaAgendamentoString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(restAgendamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        objAgendamento.setHoraAgendamento(horaAgendamento);
        objAgendamento.setDataAgendamento(dataAgendamento);
        objAgendamento.setCliente(objCliente);
        
        //objDAO.buscarCancelar(objAgendamento);

        //Instanciando StatusAgendamento
        StatusAgendamento objStatus = StatusAgendamento.CANCELADO;
        objAgendamento.setStatus(objStatus);

        objDAO.alterarStatus(objAgendamento);
        return null;
    }

}
