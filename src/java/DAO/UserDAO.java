package DAO;

import context.DBContext;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO extends DBContext {

    public User findByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(User user) {
        String sql = "UPDATE [User] SET password = ? WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setInt(2, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public User findById(int id) {
        String sql = "SELECT * FROM [User] WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(id) FROM [User] WHERE email = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateProfile(String firstName, String lastName, String gender, String phone, Date dob, String address, String uid) {
        String query = "UPDATE [User] SET first_name = ?, last_name = ?, gender = ?, phone = ?, dob = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setDate(5, new java.sql.Date(dob.getTime()));
            ps.setString(6, address);
            ps.setString(7, uid);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return users;
    }

    public User createNewUser(String firstName, String lastName, String email, String phone, String gender, String hashedPassword) {
        String sql = "INSERT INTO [User] (first_name, last_name, email, phone, gender, password, role_id, created_date, status) " +
                "OUTPUT INSERTED.id, INSERTED.email, INSERTED.first_name, INSERTED.last_name, INSERTED.phone, " +
                "INSERTED.gender, INSERTED.role_id, INSERTED.created_date, INSERTED.status " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        int defaultRoleId = 2;
        int defaultStatus = 1;
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, hashedPassword);
            stmt.setInt(7, defaultRoleId);
            stmt.setInt(8, defaultStatus);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User newUser = new User();
                newUser.setId(rs.getInt("id"));
                newUser.setEmail(rs.getString("email"));
                newUser.setFirst_name(rs.getString("first_name"));
                newUser.setLast_name(rs.getString("last_name"));
                newUser.setPhone(rs.getString("phone"));
                newUser.setGender(rs.getString("gender"));
                newUser.setRole_id(rs.getInt("role_id"));
                newUser.setCreated_date(rs.getDate("created_date"));
                newUser.setStatus(rs.getInt("status"));
                return newUser;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
