package DAO;


import context.DBContext;
import model.PasswordResetToken;
import model.User;
import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordResetTokenDAO extends DBContext {
    
    public boolean save(PasswordResetToken token) {
    String sql = "INSERT INTO PasswordResetToken (token, user_id, expiry_date) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, token.getToken());
        stmt.setInt(2, token.getUser().getId());
        stmt.setTimestamp(3, new Timestamp(token.getExpiryDate().getTime()));
        System.out.println("Connection: " + conn);
        return stmt.executeUpdate() > 0;
    } catch (Exception ex) {
        Logger.getLogger(PasswordResetTokenDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
    


}
    
   public PasswordResetToken findByToken(String token) {
    String sql = "SELECT token, user_id, expiry_date FROM PasswordResetToken WHERE token = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(rs.getString("token"));
            resetToken.setExpiryDate(new Date(rs.getTimestamp("expiry_date").getTime()));
            
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(rs.getInt("user_id")); // Use findById instead of findByEmail
            resetToken.setUser(user);
            
            return resetToken;
        }
    } catch (Exception ex) {
        Logger.getLogger(PasswordResetTokenDAO.class.getName()).log(Level.SEVERE, "Error finding token: " + token, ex);
    }
    return null;
}
    
   public boolean update(PasswordResetToken token) {
    String sql = "UPDATE PasswordResetToken SET is_used = 1 WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, token.getId());
        return stmt.executeUpdate() > 0;
    } catch (Exception ex) {
        Logger.getLogger(PasswordResetTokenDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}
   public boolean delete(String token) {
    String sql = "DELETE FROM PasswordResetToken WHERE token = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, token);
        return stmt.executeUpdate() > 0;
    } catch (Exception ex) {
        Logger.getLogger(PasswordResetTokenDAO.class.getName()).log(Level.SEVERE, "Error deleting token: " + token, ex);
        return false;
    }
}

   
}