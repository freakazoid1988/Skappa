package utils;

/**
 * Created by davem on 24/09/2015.
 */
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import event.Event;

public class Mailer {

    private Event ev;

    public Mailer(Event ev){
        this.ev = ev;
    }

    public void send(){
        final String username = "gaetano.belcastro@gmail.com";
        final String password = "gaetano88";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy – HH:mm:ss");

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        for (Map.Entry<Integer, Person> entry : ev.getAttendeeNumber().entrySet()){
            Person p = entry.getValue();

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("gaetano.belcastro@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(p.getEmail()));
                message.setSubject("Partecipazione evento");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("Gentile " + p.getName()+", La informiamo che si è prenotato all'evento " +"'"+ev.getName() +"'"+ " in luogo "+ev.getLocation()+ " ("+ev.getProvince() +")" +", il quale si terrà in data "+sdf.format(ev.getDate().getTime())+". In allegato troverà il codice QR che Le è stato assegnato. \n\n\n Cordiali saluti, SKappA.");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource("C:\\QRs\\"+entry.getKey()+".png");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("C:\\QRs\\"+entry.getKey()+".png");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}