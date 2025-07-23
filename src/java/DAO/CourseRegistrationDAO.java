
package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.CourseRegistration;
import model.User;

public class CourseRegistrationDAO extends DBContext {
    public List<CourseRegistration> getAllRegistrations(String search, String category, int customerId) throws SQLException, Exception {
        List<CourseRegistration> registrations = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT pc.id, c.subtitle, s.value AS category, pc.enroll_date, pp.title, pp.sale_price, " +
            "pc.progress, pc.enroll_date AS valid_from, pc.expire_date, pc.customer_id, pc.course_id , pc.status " +
            "FROM PersonalCourse pc " +
            "JOIN Course c ON pc.course_id = c.id " +
            "JOIN PricePackage pp ON pc.price_package_id = pp.id " +
            "JOIN Setting s ON c.category_id = s.id " +
            "WHERE pc.customer_id = ?"
        );

        List<Object> params = new ArrayList<>();
        params.add(customerId);
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND c.subtitle LIKE ?");
            params.add("%" + search.trim() + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND s.value = ?");
            params.add(category.trim());
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                String status;
                if (rs.getInt("status") == 0) {
                    status = "Submitted"; // or "Submitted"
                } else {
                    status = rs.getInt("progress") == 100 ? "Completed" :
                             rs.getDate("expire_date").before(new Date()) ? "Expired" : "Active";
                }
                registrations.add(new CourseRegistration(
                    rs.getInt("id"),
                    rs.getString("subtitle"),
                    rs.getString("category"),
                    rs.getDate("enroll_date"),
                    rs.getString("title"),
                    rs.getDouble("sale_price"),
                    status,
                    rs.getDate("valid_from"),
                    rs.getDate("expire_date"),
                    rs.getInt("customer_id"),
                        rs.getInt("course_id")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving registrations: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return registrations;
    }


    public void cancelRegistration(int id, User user) throws SQLException, Exception {
    // Kiểm tra xem registration có tồn tại và thuộc về user không
    String checkSql = "SELECT id FROM PersonalCourse WHERE id = ? AND customer_id = ?";
    String deleteSql = "DELETE FROM PersonalCourse WHERE id = ? AND customer_id = ?";
    
    Connection conn = null;
    PreparedStatement checkStmt = null;
    PreparedStatement deleteStmt = null;
    ResultSet rs = null;
    
    try {
        conn = getConnection();
        
        // Kiểm tra trước khi xóa
        checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, id);
        checkStmt.setInt(2, user.getId());
        rs = checkStmt.executeQuery();
        
        if (!rs.next()) {
            throw new Exception("Registration not found or user not authorized");
        }
        
        // Thực hiện xóa
        deleteStmt = conn.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        deleteStmt.setInt(2, user.getId());
        
        int rowsAffected = deleteStmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new Exception("Unable to delete registration");
        }
        
        System.out.println("Successfully deleted registration with ID: " + id + " for user: " + user.getId());
        
    } catch (SQLException e) {
        throw new SQLException("Error deleting registration: " + e.getMessage(), e);
    } finally {
        // Đóng resources theo thứ tự đúng
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { System.err.println("Error closing ResultSet: " + e.getMessage()); }
        }
        if (checkStmt != null) {
            try { checkStmt.close(); } catch (SQLException e) { System.err.println("Error closing checkStmt: " + e.getMessage()); }
        }
        if (deleteStmt != null) {
            try { deleteStmt.close(); } catch (SQLException e) { System.err.println("Error closing deleteStmt: " + e.getMessage()); }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { System.err.println("Error closing Connection: " + e.getMessage()); }
        }
    }
}

    public long getActiveCount(int customerId) throws SQLException, Exception {
        String sql = "SELECT COUNT(*) FROM PersonalCourse pc " +
                    "JOIN Course c ON pc.course_id = c.id " +
                    "WHERE pc.customer_id = ? AND pc.progress >= 0 AND pc.expire_date >= GETDATE()";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving active count: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    public long getSubmittedCount(int customerId) throws SQLException, Exception {
        String sql = "SELECT COUNT(*) FROM PersonalCourse pc " +
                    "JOIN Course c ON pc.course_id = c.id " +
                    "WHERE pc.customer_id = ? AND pc.progress = 0";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving submitted count: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }
}
