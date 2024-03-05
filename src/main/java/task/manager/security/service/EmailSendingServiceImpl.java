package task.manager.security.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    @Override
    public boolean sendEmail(String todo, String emailReceiver) { // todo
        String subject = "Subject\n\n";
        String from = System.getenv("TASKS_MAIL_FROM");
        String password = System.getenv("TASKS_MAIL_PWD");
        return Mailer.send(from, password, emailReceiver, subject, todo);
    }

    static class Mailer {
        private Mailer() {
        }

        public static boolean send(String from, String password, String to, String subject, String content) {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "mail29.mydevil.net");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(content);
                Transport.send(message);
                return true;
            } catch (MessagingException e) {
                System.out.println("There was a problem while sending email");
                System.out.println(e.getMessage());
                return false;
            }
        }
    }

}
