package model;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

    private static final Logger LOGGER = Logger.getLogger(EmailUtil.class.getName());
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String EMAIL = "tuananh2004ab@gmail.com";

   
    private static final String PASSWORD = "wowl axzr waik jmsx";
    private static final int SMTP_TIMEOUT = 5000;


    public static boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        if (!EmailUtil.isValidEmail(toEmail)) {
            LOGGER.log(Level.WARNING, "Invalid email address: {0}", toEmail);
            return false;
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", (Object) 587);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.timeout", (Object) 5000);
        props.put("mail.smtp.connectiontimeout", (Object) 5000);
        props.put("mail.smtp.writetimeout", (Object) 5000);
        Session session = Session.getInstance((Properties) props, (Authenticator) new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailUtil.EMAIL, EmailUtil.PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom((Address) new InternetAddress(EMAIL, "EDEMY System"));
            message.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse((String) toEmail));
            message.setSubject("EDEMY - Password Reset Request");
            message.setContent((Object) EmailUtil.buildResetEmailContent(resetLink), "text/html; charset=utf-8");
            Transport.send((Message) message);
            LOGGER.log(Level.INFO, "Password reset email sent to {0}", toEmail);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send email to " + toEmail, e);
            return false;
        }
    }

    public static boolean sendRandomPasswordEmail(String toEmail, String randomPass) {
        if (!EmailUtil.isValidEmail(toEmail)) {
            LOGGER.log(Level.WARNING, "Invalid email address: {0}", toEmail);
            return false;
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", (Object) 587);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.timeout", (Object) 5000);
        props.put("mail.smtp.connectiontimeout", (Object) 5000);
        props.put("mail.smtp.writetimeout", (Object) 5000);
        Session session = Session.getInstance((Properties) props, (Authenticator) new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailUtil.EMAIL, EmailUtil.PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom((Address) new InternetAddress(EMAIL, "EDEMY System"));
            message.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse((String) toEmail));
            message.setSubject("EDEMY - Password Reset Request");
            message.setContent((Object) EmailUtil.buildSetPasswordContent(randomPass), "text/html; charset=utf-8");
            Transport.send((Message) message);
            LOGGER.log(Level.INFO, "Password reset email sent to {0}", toEmail);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send email to " + toEmail, e);
            return false;
        }
    }

    private static String buildResetEmailContent(String resetLink) {
        return "<!DOCTYPE html><html style='font-family: Arial, sans-serif;'><head><meta charset='UTF-8'></head><body style='margin: 0; padding: 20px; color: #333;'><div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 5px; padding: 20px;'><h2 style='color: #2c3e50;'>Password Reset Request</h2><p>You requested to reset your EDEMY account password. Click the button below to proceed:</p><a href='" + resetLink + "' style='display: inline-block; background-color: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; margin: 15px 0;'>Reset Password</a><p>This link will expire in 24 hours. If you didn't request this, please ignore this email.</p><hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'><p style='font-size: 12px; color: #777;'>EDEMY Learning Platform</p></div></body></html>";
    }

    private static String buildSetPasswordContent(String newPass) {
        return "<!DOCTYPE html><html style='font-family: Arial, sans-serif;'><head><meta charset='UTF-8'></head><body style='margin: 0; padding: 20px; color: #333;'><div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 5px; padding: 20px;'>"
                + "<h2 style='color: #2c3e50;'>Your Password</h2>"
                +" <h2 style='color: #2c3e50;'>" + newPass+ "</h2>"
                + "<p>This link will expire in 24 hours. If you didn't request this, please ignore this email.</p><hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'><p style='font-size: 12px; color: #777;'>EDEMY Learning Platform</p></div></body></html>";
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }
}
