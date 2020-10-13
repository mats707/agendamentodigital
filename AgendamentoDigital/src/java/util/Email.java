/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author matht
 */
public class Email {

    private static final String EMAIL = "contato.mafera@gmail.com";
    private static final String PASSWORD = "wjgezlbbjfnvsces";
    private static String listEmailsDestinarios;
    private static String assunto;
    private static String cabecalho;
    private static String corpo;
    private static String rodape;

    public static void setListEmailsDestinarios(String aListEmailsDestinarios) {
        listEmailsDestinarios = aListEmailsDestinarios;
    }

    public static void setAssunto(String aAssunto) {
        assunto = aAssunto;
    }

    public static void setCabecalho(String aCabecalho) {
        cabecalho = aCabecalho;
    }

    public static void setCorpo(String aCorpo) {
        corpo = aCorpo;
    }

    public static void setRodape(String aRodape) {
        rodape = aRodape;
    }

    public Email() {
    }

    public String emailDispacher() {
        Properties props = new Properties();

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
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            //Remetente
            message.setFrom(new InternetAddress(EMAIL));
            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(listEmailsDestinarios);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
            message.setSentDate(new Date());
            message.setHeader("X-Priority", "1");
            
            Multipart mainPart = new MimeMultipart();
            
            BodyPart html_cabecalho = new MimeBodyPart();
            html_cabecalho.setContent(cabecalho + "<br><br>", "text/html; charset=utf-8");
            mainPart.addBodyPart(html_cabecalho);
            
            BodyPart html_corpo = new MimeBodyPart();
            html_corpo.setContent(corpo + "<br><br><br>", "text/html; charset=utf-8");
            mainPart.addBodyPart(html_corpo);
            
            BodyPart html_rodape = new MimeBodyPart();
            html_rodape.setContent(rodape, "text/html; charset=utf-8");
            mainPart.addBodyPart(html_rodape);
            
            message.setContent(mainPart);
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

            return "email_enviado";

        } catch (MessagingException e) {
            return "email_erro: " + e;
        }
    }

}
