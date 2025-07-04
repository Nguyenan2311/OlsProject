package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class UserDAO
extends DBContext {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole_id(rs.getInt("role_id"));
            user.setDob((Date)rs.getDate("dob"));
            user.setCreated_date((Date)rs.getDate("created_date"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setGender(rs.getString("gender"));
            user.setPhone(rs.getString("phone"));
            user.setImage(rs.getString("image_url"));
            user.setAddress(rs.getString("address"));
            User user2 = user;
            return user2;
        }
        catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public boolean update(User user) {
        String sql = "UPDATE [User] SET password = ? WHERE id = ?";
        try (Connection conn = this.getConnection();){
            boolean bl;
            block14: {
                PreparedStatement stmt = conn.prepareStatement(sql);
                try {
                    stmt.setString(1, user.getPassword());
                    stmt.setInt(2, user.getId());
                    boolean bl2 = bl = stmt.executeUpdate() > 0;
                    if (stmt == null) break block14;
                }
                catch (Throwable throwable) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                stmt.close();
            }
            return bl;
        }
        catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public User findById(int id) {
        String sql = "SELECT * FROM [User] WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole_id(rs.getInt("role_id"));
            user.setDob((Date)rs.getDate("dob"));
            user.setCreated_date((Date)rs.getDate("created_date"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setGender(rs.getString("gender"));
            user.setPhone(rs.getString("phone"));
            user.setImage(rs.getString("image_url"));
            user.setAddress(rs.getString("address"));
            User user2 = user;
            return user2;
        }
        catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error finding user by ID: " + id, ex);
        }
        return null;
    }

    public boolean isEmailExists(String email) throws Exception {
        String sql = "SELECT COUNT(id) FROM [User] WHERE email = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    boolean bl = rs.getInt(1) > 0;
                    return bl;
                }
            }
        }
        return false;
    }

    public User createNewUser(String firstName, String lastName, String email, String phone, String gender, String hashedPassword) throws Exception {
        String sql = "INSERT INTO [User] (first_name, last_name, email, phone, gender, password, role_id, created_date, status) OUTPUT INSERTED.id, INSERTED.email, INSERTED.first_name, INSERTED.last_name, INSERTED.phone, INSERTED.gender, INSERTED.role_id, INSERTED.created_date, INSERTED.status VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        int defaultRoleId = 2;
        int defaultStatus = 1;
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, hashedPassword);
            stmt.setInt(7, defaultRoleId);
            stmt.setInt(8, defaultStatus);
            try (ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    User newUser = new User();
                    newUser.setId(rs.getInt("id"));
                    newUser.setEmail(rs.getString("email"));
                    newUser.setFirst_name(rs.getString("first_name"));
                    newUser.setLast_name(rs.getString("last_name"));
                    newUser.setPhone(rs.getString("phone"));
                    newUser.setGender(rs.getString("gender"));
                    newUser.setRole_id(rs.getInt("role_id"));
                    newUser.setCreated_date((Date)rs.getDate("created_date"));
                    newUser.setStatus(rs.getInt("status"));
                    User user = newUser;
                    return user;
                }
            }
        }
        return null;
    }
}