package DAO;

import context.DBContext;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
        
    }
    return null;
}

    public boolean updateProfile(String firstName, String lastname, String gender, String phone, Date dob, String address, String uid) {
        String query = "update [User] set first_name = ? , last_name = ?, gender = ?,phone = ?, dob = ?, address = ? where id =?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastname);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setDate(5, dob);
            ps.setString(6, address);
            ps.setString(7, uid);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            
            return false;
        }
    }

    public User getUserById(String uid) {
        String query = "SELECT * FROM [User] WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uid);
            ResultSet rs = ps.executeQuery();
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
        } catch (Exception e) {
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM [User]";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
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
                users.add(user);
            }
        } catch (Exception e) {
        }
        return users;
    }
}