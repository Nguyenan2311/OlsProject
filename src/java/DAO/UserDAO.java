package DAO;


import context.DBContext;
import model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO extends DBContext {
    
    public User findByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
             if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole_id(rs.getInt("role_id"));
            user.setDob(rs.getDate("dob"));
            user.setCreated_date(rs.getDate("created_date"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setGender(rs.getString("gender"));
            user.setPhone(rs.getString("phone"));
            user.setImage(rs.getString("image_url"));
            user.setAddress(rs.getString("address"));
            return user;
        }
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean update(User user) {
        String sql = "UPDATE [User] SET password = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getPassword());
            stmt.setInt(2, user.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public User findById(int id) {
    String sql = "SELECT * FROM [User] WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole_id(rs.getInt("role_id"));
            user.setDob(rs.getDate("dob"));
            user.setCreated_date(rs.getDate("created_date"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setGender(rs.getString("gender"));
            user.setPhone(rs.getString("phone"));
            user.setImage(rs.getString("image_url"));
            user.setAddress(rs.getString("address"));
            return user;
        }
    } catch (Exception ex) {
        Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error finding user by ID: " + id, ex);
    }
    return null;
}
}