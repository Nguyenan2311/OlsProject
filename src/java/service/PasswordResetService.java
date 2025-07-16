package service;

import DAO.PasswordResetTokenDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import model.EmailUtil;
import model.PasswordResetToken;
import model.User;

public class PasswordResetService {
    private static final int EXPIRATION_MINUTES = 5;

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Date calculateExpiryDate() {
        ZonedDateTime nowInVN = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"));
        ZonedDateTime expiryInVN = nowInVN.plusMinutes(5L);
        return Date.from(expiryInVN.toInstant());
    }

    public boolean isTokenExpired(Date expiryDate) {
        Instant nowUTC = Instant.now();
        Instant expiryUTC = expiryDate.toInstant();
        System.out.println("[DEBUG] Current UTC Time: " + nowUTC);
        System.out.println("[DEBUG] Token UTC Expiry: " + expiryUTC);
        System.out.println("[DEBUG] Token Status: " + (nowUTC.isAfter(expiryUTC) ? "EXPIRED" : "VALID"));
        return nowUTC.isAfter(expiryUTC);
    }

    public PasswordResetToken createResetToken(User user) {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(this.generateToken());
        token.setUser(user);
        token.setExpiryDate(this.calculateExpiryDate());
        PasswordResetTokenDAO tokenDAO = new PasswordResetTokenDAO();
        if (tokenDAO.save(token)) {
            System.out.println("[DEBUG] Token created. Expires at: " + token.getExpiryDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")));
            return token;
        }
        return null;
    }

    public boolean sendResetEmail(String email, String resetLink) {
        return EmailUtil.sendPasswordResetEmail((String)email, (String)resetLink);
    }
     public boolean sendSetPasswordEmail(String email, String randomPassword) {
        return EmailUtil.sendRandomPasswordEmail(email, randomPassword);
    }

    public User validateToken(String token) {
        PasswordResetTokenDAO tokenDAO = new PasswordResetTokenDAO();
        PasswordResetToken resetToken = tokenDAO.findByToken(token);
        if (resetToken == null) {
            System.out.println("[DEBUG] Token not found: " + token);
            return null;
        }
        if (this.isTokenExpired(resetToken.getExpiryDate())) {
            System.out.println("[DEBUG] Token expired at: " + resetToken.getExpiryDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")));
            return null;
        }
        System.out.println("[DEBUG] Token valid: " + token);
        return resetToken.getUser();
    }

    public boolean markTokenAsUsed(String token) {
        PasswordResetTokenDAO tokenDAO = new PasswordResetTokenDAO();
        boolean result = tokenDAO.delete(token);
        System.out.println("[DEBUG] Token deleted: " + result);
        return result;
    }
    public boolean sendInitialSetPasswordEmail(User user, HttpServletRequest request) {
        if (user == null || user.getEmail() == null) {
            return false;
        }

        try {
            // 1. Tạo một token reset mật khẩu mới cho user
            PasswordResetToken token = createResetToken(user);
            if (token == null) {
                System.err.println("Failed to create a password reset token for user: " + user.getEmail());
                return false;
            }

            // 2. Xây dựng link reset mật khẩu
            // Ví dụ: http://localhost:8080/YourAppName/ResetPasswordServlet?token=xxxxx
            String resetLink = getBaseUrl(request) + "/ResetPasswordServlet?token=" + token.getToken();

            // 3. Soạn nội dung email
            String subject = "Welcome to EDEMY! Please set your password.";
            String body = "Hello " + user.getFullName() + ",\n\n"
                        + "Welcome to our platform! To get started, you need to set your password by clicking the link below:\n\n"
                        + resetLink + "\n\n"
                        + "This link will be valid for 1 hour. If you did not create an account, please ignore this email.\n\n"
                        + "Thank you,\nThe EDEMY Team";

            // 4. Gửi email (bạn cần có một lớp EmailService để làm việc này)
            EmailUtil.sendPasswordResetEmail(user.getEmail(), resetLink);
            // Giả sử bạn có một lớp tiện ích để gửi email
            // return EmailService.send(user.getEmail(), subject, body);
            
            // Tạm thời chỉ in ra console để debug
            System.out.println("---- SENDING EMAIL (SIMULATION) ----");
            System.out.println("To: " + user.getEmail());
            System.out.println("Subject: " + subject);
            System.out.println("Body: \n" + body);
            System.out.println("------------------------------------");
            
            return true; // Giả định là đã gửi thành công

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method để lấy URL gốc của ứng dụng
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // localhost
        int serverPort = request.getServerPort();        // 8080
        String contextPath = request.getContextPath();   // /YourAppName

        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }
}
   
