package service;


import model.EmailUtil;
import model.PasswordResetToken;
import model.User;
import DAO.PasswordResetTokenDAO;
import java.time.*;
import java.util.Date;
import java.util.UUID;

public class PasswordResetService {
    private static final int EXPIRATION_MINUTES = 5;
    
    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public Date calculateExpiryDate() {
        // S? d?ng Java 8 Time API ?? x? l� timezone ch�nh x�c
        ZonedDateTime nowInVN = ZonedDateTime.now(ZoneId.of("Asia/Bangkok")); // UTC+7
        ZonedDateTime expiryInVN = nowInVN.plusMinutes(EXPIRATION_MINUTES);
        return Date.from(expiryInVN.toInstant());
    }

    public boolean isTokenExpired(Date expiryDate) {
        Instant nowUTC = Instant.now();
        Instant expiryUTC = expiryDate.toInstant();
        
        // Debug th?i gian
        System.out.println("[DEBUG] Current UTC Time: " + nowUTC);
        System.out.println("[DEBUG] Token UTC Expiry: " + expiryUTC);
        System.out.println("[DEBUG] Token Status: " + (nowUTC.isAfter(expiryUTC) ? "EXPIRED" : "VALID"));
        
        return nowUTC.isAfter(expiryUTC);
    }
    
    public PasswordResetToken createResetToken(User user) {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(generateToken());
        token.setUser(user);
        token.setExpiryDate(calculateExpiryDate());
        
        PasswordResetTokenDAO tokenDAO = new PasswordResetTokenDAO();
        if (tokenDAO.save(token)) {
            // Debug sau khi t?o token
            System.out.println("[DEBUG] Token created. Expires at: " + 
                token.getExpiryDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")));
            return token;
        }
        return null;
    }
    
    public boolean sendResetEmail(String email, String resetLink) {
        return EmailUtil.sendPasswordResetEmail(email, resetLink);
    }
    
    public User validateToken(String token) {
    PasswordResetTokenDAO tokenDAO = new PasswordResetTokenDAO();
    PasswordResetToken resetToken = tokenDAO.findByToken(token);
    
    if (resetToken == null) {
        System.out.println("[DEBUG] Token not found: " + token);
        return null;
    }
    
    if (isTokenExpired(resetToken.getExpiryDate())) {
        System.out.println("[DEBUG] Token expired at: " + 
            resetToken.getExpiryDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")));
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
} 