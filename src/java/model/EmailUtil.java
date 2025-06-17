package model;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailUtil {
    private static final Logger LOGGER = Logger.getLogger(EmailUtil.class.getName());
    
    // set SMTP
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String EMAIL = "tuananh2004ab@gmail.com";
    private static final String PASSWORD = "efwu onlh lrzf zkej"; 
    
    // Timeout settings
    private static final int SMTP_TIMEOUT = 5000; // 5 seconds
    
    public static boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        // Validate email 
        if (!isValidEmail(toEmail)) {
            LOGGER.log(Level.WARNING, "Invalid email address: {0}", toEmail);
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        // set timeout
        props.put("mail.smtp.timeout", SMTP_TIMEOUT);
        props.put("mail.smtp.connectiontimeout", SMTP_TIMEOUT);
        props.put("mail.smtp.writetimeout", SMTP_TIMEOUT);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL, "EDEMY System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("EDEMY - Password Reset Request");
            message.setContent(buildResetEmailContent(resetLink), "text/html; charset=utf-8");

            Transport.send(message);
            LOGGER.log(Level.INFO, "Password reset email sent to {0}", toEmail);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send email to " + toEmail, e);
            return false;
        }
    }

    private static String buildResetEmailContent(String resetLink) {
        return "<!DOCTYPE html>" +
               "<html style='font-family: Arial, sans-serif;'>" +
               "<head><meta charset='UTF-8'></head>" +
               "<body style='margin: 0; padding: 20px; color: #333;'>" +
               "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 5px; padding: 20px;'>" +
               "<h2 style='color: #2c3e50;'>Password Reset Request</h2>" +
               "<p>You requested to reset your EDEMY account password. Click the button below to proceed:</p>" +
               "<a href='" + resetLink + "' style='display: inline-block; background-color: #3498db; color: white; " +
               "padding: 10px 20px; text-decoration: none; border-radius: 4px; margin: 15px 0;'>Reset Password</a>" +
               "<p>This link will expire in 24 hours. If you didn't request this, please ignore this email.</p>" +
               "<hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>" +
               "<p style='font-size: 12px; color: #777;'>EDEMY Learning Platform</p>" +
               "</div></body></html>";
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }
}