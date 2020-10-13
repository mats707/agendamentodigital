/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.ServicoDAO;
import dao.UsuarioDAO;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.PathParam;
import modelos.Cliente;
import modelos.Funcionario;
import modelos.Pessoa;
import modelos.Servico;
import modelos.Usuario;
import util.Email;

/**
 * REST Web Service
 *
 * @author Rafael Pereira
 */
@Path("/Email")
public class restEmail {

    Email utilEmail = new Email();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restEmail
    //http://localhost:8080/AgendamentoDigital/api/Email/Notificar/1/2/2/10-10-2020/12:00
     */
    public restEmail() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Notificar/{idCliente}/{idServico}/{idFuncionario}/{data}/{horaIni}")
    public String emailDispacherAgendar(@PathParam("idCliente") Integer idCliente,
            @PathParam("idServico") Integer idServico,
            @PathParam("idFuncionario") Integer idFuncionario,
            @PathParam("data") String data,
            @PathParam("horaIni") String horaIni) {

        String horaFim = "";

        //Construindo Objetos utilizados nesse metodo
        ClienteDAO objClienteDAO = new ClienteDAO();
        ServicoDAO objServicoDAO = new ServicoDAO();
        FuncionarioDAO objFuncionarioDAO = new FuncionarioDAO();
        PessoaDAO objPessoaDAO = new PessoaDAO();
        UsuarioDAO objUsuarioDAO = new UsuarioDAO();

        Cliente objCliente = new Cliente();
        Funcionario objFuncionario = new Funcionario();
        Servico objServico = new Servico();

        //Construção de horario e Data
        Date dataAgendamento = null;
        String dataAgendamentoString = null;
        try {
            dataAgendamento = new SimpleDateFormat("dd-MM-yyyy").parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(restEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dataAgendamentoString = new SimpleDateFormat("dd/MM/yyyy").format(dataAgendamento);
        } catch (Exception ex) {
            Logger.getLogger(restEmail.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> emailsDestinatarios = new ArrayList<>();

        //Construção de cliente
        objCliente.setIdCliente(idCliente);
        objClienteDAO.buscar(objCliente);
        objPessoaDAO.buscar(objCliente);
        objUsuarioDAO.buscarId(objCliente.getUsuario());
        //Recebe e-mail de cliente
        String emailCliente = objCliente.getUsuario().getEmail();
        emailsDestinatarios.add(emailCliente);

        // Construção de funcionario
        objFuncionario.setIdFuncionario(idFuncionario);
        objFuncionarioDAO.buscar(objFuncionario);
        objPessoaDAO.buscar(objFuncionario);
        objUsuarioDAO.buscarId(objFuncionario.getUsuario());
        //Recebe e-mail de funcionario
        String emailFuncionario = objFuncionario.getUsuario().getEmail();
        emailsDestinatarios.add(emailFuncionario);

        //Construção de Serviço
        objServico.setIdServico(idServico);
        objServicoDAO.buscar(objServico);

        //Define hora final
        Time horaInicial;
        Duration duracao;
        Long horaFinal = null;
        try {
            horaInicial = new java.sql.Time(new SimpleDateFormat("kk:mm").parse(horaIni).getTime());
            duracao = objServico.getDuracao();
            horaFinal = horaInicial.getTime() + duracao.toMillis();
        } catch (Exception ex) {
            return ex.getMessage();
        }
        horaFim = new SimpleDateFormat("kk:mm").format(horaFinal);

        //Construindo a String para enviar o e-mail
        String listaEmailDestinatarios = montarEmailsDestinatarios(emailsDestinatarios);

        utilEmail.setListEmailsDestinarios(listaEmailDestinatarios + ",math.tcl@gmail.com");
        utilEmail.setAssunto("Confirmação de agendamento - " + objServico.getNome());
        utilEmail.setCabecalho("Serviço: " + objServico.getNome());
        utilEmail.setCorpo("Olá,\n"
                + "Recebemos seu agendamento, e estamos lhe avisando que está confirmado "
                + "Sr(a) " + objCliente.getNome() + " fará o serviço com nosso funcionario(a) " + objFuncionario.getNome()
                + " no dia " + dataAgendamentoString + " no horario de " + horaIni + " a " + horaFim
                + "\nEsperamos você");
        utilEmail.setRodape("Email enviado por Mafera! Volte sempre.");

        return utilEmail.emailDispacher();

    }

    private String montarEmailsDestinatarios(ArrayList<String> emailsDestinatarios) {
        String emails = "";
        emails = emailsDestinatarios.toString().replace("[", "").replace("]", "");
        return emails;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TesteEmail")
    public String teste() throws ParseException {

        String emailCli = "rafael-ps12@hotmail.com";
        String emailFunc = "sjfelipe93@gmail.com";
        String emailTeste = "math.tcl@gmail.com";

        ArrayList<String> EmailAddressString = new ArrayList<>();
        EmailAddressString.add(emailCli);
        EmailAddressString.add(emailFunc);
        EmailAddressString.add(emailTeste);

        return EmailAddressString.toString();
    }

}
