package service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService
{
    private String host;
    private String port;
    private String user;
    private String pass;
    private String debug;

    public EmailService(String host, String port, String user, String pass, String debug) {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.debug = debug;
    }

    public void sendMail(String text, List<String> recp) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.ssl.trust", "*");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };

        Session ses = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(ses);
        msg.setFrom(new InternetAddress("support@cmsweb.com", "CMSWeb Support"));
        msg.setSubject("CMSWeb Support");
        msg.setContent(text, "text/html");
        for (String abonat : recp){
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(abonat));
        }
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.debug));
        Transport.send(msg);
    }
}
