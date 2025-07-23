package DAO;

import context.DBContext;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RoleOption;

public class UserDAO extends DBContext {

    
   

    // Get all roles from Setting table (setting_type_id = 1)
    public static List<RoleOption> getRoleOptions() {
        List<RoleOption> roles = new ArrayList<>();
        String query = "SELECT id, value FROM Setting WHERE setting_type_id = 1 AND status = 1 ORDER BY id";
        try (Connection conn = new UserDAO().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(new RoleOption(rs.getInt("id"), rs.getString("value")));
            }
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return roles;
    }


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
    String sql = "UPDATE [User] SET password = ?, status = 1 WHERE id = ?";
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

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(id) FROM [User] WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public User createNewUser(String firstName, String lastName, String email, String phone, String gender, String hashedPassword) {
        String sql = "INSERT INTO [User] (first_name, last_name, email, phone, gender, password, role_id, created_date, status) " +
                     "OUTPUT INSERTED.id, INSERTED.email, INSERTED.first_name, INSERTED.last_name, INSERTED.phone, " +
                     "INSERTED.gender, INSERTED.role_id, INSERTED.created_date, INSERTED.status " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        int defaultRoleId = 2;
        int defaultStatus = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, hashedPassword);
            stmt.setInt(7, defaultRoleId);
            stmt.setInt(8, defaultStatus);

            try (ResultSet rs = stmt.executeQuery()) {
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
            }

        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateProfile(String firstName, String lastname, String gender, String phone, Date dob, String address, String uid) {
        String query = "UPDATE [User] SET first_name = ?, last_name = ?, gender = ?, phone = ?, dob = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, firstName);
            ps.setString(2, lastname);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setDate(5, new java.sql.Date(dob.getTime()));
            ps.setString(6, address);
            ps.setString(7, uid);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // New advanced user list method for admin management
    public List<User> getUsers(String gender, Integer roleId, Integer status, String search, String sortColumn, String sortOrder, int limit, int offset) {
        List<User> users = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT u.*, s.value AS role_name FROM [User] u ");
        query.append("LEFT JOIN Setting s ON u.role_id = s.id AND s.setting_type_id = 1 WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (gender != null && !gender.isEmpty()) {
            query.append("AND u.gender = ? ");
            params.add(gender);
        }
        if (roleId != null) {
            query.append("AND u.role_id = ? ");
            params.add(roleId);
        }
        if (status != null) {
            query.append("AND u.status = ? ");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            query.append("AND (");
            query.append("CONCAT(u.first_name, ' ', u.last_name) LIKE ? OR u.email LIKE ? OR u.phone LIKE ?");
            query.append(") ");
            String like = "%" + search + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        // Sorting
        String[] allowedSort = {"id", "first_name", "last_name", "gender", "email", "phone", "role_id", "status"};
        boolean validSort = false;
        for (String col : allowedSort) {
            if (col.equalsIgnoreCase(sortColumn)) {
                validSort = true;
                break;
            }
        }
        if (validSort) {
            query.append("ORDER BY u." + sortColumn + " ");
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.append("DESC ");
            } else {
                query.append("ASC ");
            }
        } else {
            query.append("ORDER BY u.id ASC ");
        }
        query.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(limit);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
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
                user.setStatus(rs.getInt("status"));
                user.setRoleName(rs.getString("role_name")); // add to User model
                users.add(user);
            }
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return users;
    }

    // Add new user (for AddUserServlet)
    public static boolean addUser(User user) {
        String sql = "INSERT INTO [User] (email, password, role_id, first_name, last_name, gender, phone, address, status, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = new UserDAO().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole_id());
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getLast_name());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getAddress());
            ps.setInt(9, user.getStatus());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    // Update only role and status (for EditUserServlet)
    public static boolean updateUserRoleAndStatus(int id, int roleId, int status) {
        String sql = "UPDATE [User] SET role_id = ?, status = ? WHERE id = ?";
        try (Connection conn = new UserDAO().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, status);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    // Get user by ID (for ViewUserServlet, EditUserServlet)
    public static User getUserById(int id) {
        String sql = "SELECT u.*, s.value AS role_name FROM [User] u LEFT JOIN Setting s ON u.role_id = s.id AND s.setting_type_id = 1 WHERE u.id = ?";
        try (Connection conn = new UserDAO().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
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
                user.setStatus(rs.getInt("status"));
                user.setRoleName(rs.getString("role_name"));
                return user;
            }
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return users;
    }
