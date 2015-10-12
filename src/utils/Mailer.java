package utils;

/**
 * Created by davem on 24/09/2015.
 */

import event.Event;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

public class Mailer {

    private Event ev;
    private String path, email, username, password, smtpServer, port;
    private char[] passwordArray;
    //private int port;

    public Mailer(Event ev, String path, String email, String username, char[] passwordArray, String smtpServer, String port) {
        this.ev = ev;
        this.path = path;
        this.email = email;
        this.username = username;
        this.passwordArray = passwordArray;
        this.smtpServer = smtpServer;
        this.port = port;
    }

    private String transformPassword(char[] passwordArray) {
        return new String(passwordArray);
    }

    public boolean send() {

        password = transformPassword(passwordArray);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        for (Map.Entry<Integer, Person> entry : ev.getAttendeeNumber().entrySet()){
            Person p = entry.getValue();

            try {
                //String home = System.getProperty("user.home");
                File QRDir = new File(path + "/QRs/");

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(p.getEmail()));
                message.setSubject("Partecipazione evento");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("Gentile " + p.getName() + ", La informiamo che si è prenotato all'evento " + "'" + ev.getName() + "'" + " in luogo " + ev.getLocation() + " (" + ev.getProvince() + ")" + ", il quale si terrà  in data " + sdf.format(ev.getDate().getTime()) + ". In allegato troverà  il codice QR che Le è stato assegnato. \n\n\n Cordiali saluti, SKappA.");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(QRDir+"/"+entry.getKey()+".png");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(entry.getKey()+".png");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                return false;
            }
        }
        return true;
    }
}