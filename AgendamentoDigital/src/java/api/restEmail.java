/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.UsuarioDAO;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
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
import modelos.Usuario;
import testes.ValidarCodigo;

/**
 * REST Web Service
 *
 * @author Rafael Pereira
 */
@Path("/Email")
public class restEmail {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of restEmail
     */
    public restEmail() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Enviar")
    public String emailDispacher() {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.mime.charset", "ISO-8859-1");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "contato.mafera@gmail.com",
                        "wjgezlbbjfnvsces");
            }
        });
        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("contato.mafera@gmail.com"));

            //Remetente
            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse("rafael-ps12@hotmail.com, sjfelipe93@gmail.com, math.tcl@gmail.com");

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email com JavaMail");//Assunto
            message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Notificar/{idCliente}/{idFuncionario}/{data}/{horaIni}/{horaFim}")
    public String emailDispacherAgendar(@PathParam("idCliente") Integer idCliente,
            @PathParam("idFuncionario") Integer idFuncionario,
            @PathParam("data") String data,
            @PathParam("horaIni") String horaIni,
            @PathParam("horaFim") String horaFim) {

        //Construindo Objetos utilizados nesse metodo
        Properties props = new Properties();
        ClienteDAO objClienteDAO = new ClienteDAO();
        FuncionarioDAO objFuncionarioDAO = new FuncionarioDAO();
        PessoaDAO objPessoaDAO = new PessoaDAO();
        UsuarioDAO objUsuarioDAO = new UsuarioDAO();

        Cliente objCliente = new Cliente();
        Funcionario objFuncionario = new Funcionario();

        //Construção de horario e Data
        Date dataAgendamento = null;
        DateFormat formatter = new SimpleDateFormat("kk:mm");
        Time horaDeInicio = null;
        Time horaDeTermino = null;
        String dataAgendamentoString = null;
        try {
            dataAgendamento = new SimpleDateFormat("dd-MM-yyyy").parse(data);

        } catch (ParseException ex) {
            Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dataAgendamentoString = new SimpleDateFormat("dd/MM/yyyy").format(dataAgendamento);

        } catch (Exception ex) {
            Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Parâmetros de conexão com servidor Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.mime.charset", "ISO-8859-1");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("contato.mafera@gmail.com", "wjgezlbbjfnvsces");
            }
        });

        //Ativa Debug para sessão
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("contato.mafera@gmail.com"));

            //Construção de cliente
            objCliente.setIdCliente(idCliente);
            objClienteDAO.buscar(objCliente);

            objPessoaDAO.buscar(objCliente);

            objUsuarioDAO.buscarId(objCliente.getUsuario());

            String emailCliente = objCliente.getUsuario().getEmail();

            // Construção de funcionario
            objFuncionario.setIdFuncionario(idFuncionario);
            objFuncionarioDAO.buscar(objFuncionario);

            objPessoaDAO.buscar(objFuncionario);

            objUsuarioDAO.buscarId(objFuncionario.getUsuario());

            String emailFuncionario = objFuncionario.getUsuario().getEmail();

            //Construindo a String para enviar o e-mail
            String emailAEnviar = emailCliente + "," + emailFuncionario + ",rafael-ps12@hotmail.com";
            //Remetente
            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(emailAEnviar);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Confirmação de agendamento");//Assunto
            message.setText("Olá,\n"
                    + "Recebemos seu agendamento, e estamos lhe avisando que está confirmado "
                    + "Sr(a) " + objCliente.getNome() + " fará o serviço com nosso funcionario(a) " + objFuncionario.getNome()
                    + " no dia " + dataAgendamentoString + " no horario de " + horaIni + " a " + horaFim
                    + "\nEsperamos você");
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);
            return "email_enviado";

        } catch (MessagingException e) {
            return "email_erro: " + e;
            //throw new RuntimeException(e);

        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Teste")
    public String teste() throws ParseException {
        
        DateFormat formatter = new SimpleDateFormat("kk:mm");
        Time horaInicial = new java.sql.Time(formatter.parse("15:25").getTime());
        Duration duracao = Duration.ofMinutes(75);
        Long horaFinal = null;
        horaFinal = horaInicial.getTime()+ duracao.toMillis();

        String horaFinalString = new SimpleDateFormat("kk:mm").format(horaFinal);

        return horaFinalString;
    }

}
