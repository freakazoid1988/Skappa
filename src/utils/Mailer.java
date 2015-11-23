package utils;

import event.Event;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by davem on 24/09/2015.
 */

public class Mailer extends SwingWorker<Void, String> {
	Session session;
	private Event ev;
	private String path, email, username, password, smtpServer, port;
	private char[] passwordArray;
	private JFrame frame;

	public Mailer(JFrame frame, Event ev, String path, String email, String username, char[] passwordArray, String smtpServer,
				  String port) {
		this.ev = ev;
		this.path = path;
		this.email = email;
		this.username = username;
		this.passwordArray = passwordArray;
		this.smtpServer = smtpServer;
		this.port = port;
		this.frame = frame;
	}

	private String transformPassword(char[] passwordArray) {
		return new String(passwordArray);
	}

	public boolean connect() {

		password = transformPassword(passwordArray);

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", port);

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return true;
	}

	public boolean createMessage(Person p, int key) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

		try {
			// String home = System.getProperty("user.home");
			File QRDir = new File(path + "/QRs/");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getEmail()));
			message.setSubject("Partecipazione evento");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Gentile " + p.getName() + ", La informiamo che si � prenotato all'evento " + "'"
					+ ev.getName() + "'" + " in luogo " + ev.getLocation() + " (" + ev.getProvince() + ")"
					+ ", il quale si terr� in data " + sdf.format(ev.getDate().getTime())
					+ ". In allegato trover� il codice QR che Le � stato assegnato. \n\n\n Cordiali saluti, SKappA.");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(QRDir + "/" + key + ".png");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(key + ".png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			return false;
		}
		return true;
	}

	@Override
	protected Void doInBackground() throws Exception {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int progress = 0;
		setProgress(progress);
		int totality = ev.getAttendee_number().size();
		float counter = 0;
		connect();
		for (Map.Entry<Integer, Person> entry : ev.getAttendeeNumber().entrySet()) {
			Person p = entry.getValue();
			if (!createMessage(p, entry.getKey())) {
				publish(p.getEmail());
			}
			float step = (++counter / totality);
			progress = (int) (step * 100);
			setProgress(progress);
		}
		frame.setCursor(null);
		return null;

	}
	
	/*@Override
	public void done(){
		JOptionPane.showMessageDialog(frame, "Fatto!");
	}*/

	@Override
	protected void process(List<String> chunks) {
		String message = "Impossibile inviare il messaggio al seguente destinatario: \n" + chunks.get(chunks.size() - 1);
		JOptionPane.showMessageDialog(frame, message, "", JOptionPane.WARNING_MESSAGE);
	}
}