/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import api.restAgendamento;
import api.restEmail;
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
import modelos.Funcionario;
import modelos.PerfilDeAcesso;
import modelos.Servico;
import modelos.StatusAgendamento;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class CancelarAction implements ICommand {

    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private restAgendamento objRestAgendamento = new restAgendamento();
    private restEmail objRestEmail = new restEmail();

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/client/home.jsp");

        //Obtendo os parametros do REQUEST
        String idServico = request.getParameter("servico");
        String idFuncionario = request.getParameter("funcionario");
        String dataAgendamentoString = request.getParameter("dataAgendamento");
        String horaAgendamentoString = request.getParameter("horaAgendamento");
        String idCliente = request.getParameter("listaClientes");
        if (idCliente == null) {
            idCliente = "0";
        }

        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";
        String StatusEmail = null;

        if (idServico!= null && idFuncionario!= null && dataAgendamentoString != null && horaAgendamentoString != null && idCliente != null) {
            
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
            StatusAgendamento objStatus = StatusAgendamento.CANCELADO;
            
            
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
                dataAgendamento = new SimpleDateFormat("dd/MM/yyyy").parse(dataAgendamentoString);
            } catch (ParseException ex2) {
                Logger.getLogger(restAgendamento.class.getName()).log(Level.SEVERE, null, ex2);
            }
            objAgendamento.setDataAgendamento(dataAgendamento);
            //Parse horaAgendamento
            try {
                horaAgendamento = new java.sql.Time(new SimpleDateFormat("kk:mm").parse(horaAgendamentoString).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(restAgendamento.class.getName()).log(Level.SEVERE, null, ex);
            }

            objAgendamento.setHoraAgendamento(horaAgendamento);

            //Chamada da DAO para Cadastrar
            sqlState = agendamentoDAO.alterarStatus(objAgendamento);

            //Verifica o retorno da DAO (banco de dados)
            if (sqlState == "0") {
                StatusEmail = notificarAgendamento(objAgendamento);
                if (StatusEmail == "email_enviado") {
                    funcaoMsg = "Cancelado com sucesso!\\nUm e-mail foi enviado como lembrete.";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Cancelamento Realizado!\\nInfelizmente houve uma falha ao enviar o e-mail de cancelamento.";
                    funcaoStatus = "info";
                }
            } else if (sqlState.equalsIgnoreCase("unqagendamento")) {
                // Independente do serviço o cliente não poderá agendar em um horário que ele já marcou/agendou
                funcaoMsg = "Tivemos problemas para cancelar seu agendamento\\nPor favor entre em contato com o atendimento!";
                funcaoStatus = "warning";
            } else {
                funcaoMsg = "Não foi possível realizar o cancelamento.\\nTente novamente mais tarde ou entre em contato com nossa equipe!";
            }

        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return null;
    }

    private String notificarAgendamento(Agendamento objAgendamento) {
        String dataAgendamentoString = new SimpleDateFormat("dd-MM-yyyy").format(objAgendamento.getDataAgendamento());
        String horaInicialString = new SimpleDateFormat("kk:mm").format(objAgendamento.getHoraAgendamento());
        return objRestEmail.emailDispacherCancelar(objAgendamento.getCliente().getIdCliente(), objAgendamento.getServico().getIdServico(), objAgendamento.getFuncionario().getIdFuncionario(), dataAgendamentoString, horaInicialString);
    }

}
