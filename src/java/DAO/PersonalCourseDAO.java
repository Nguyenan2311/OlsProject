package DAO;

import context.DBContext;
import model.PersonalCourse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalCourseDAO extends DBContext {
    public List<PersonalCourse> getPersonalCoursesByUser(int userId) {
        List<PersonalCourse> list = new ArrayList<>();
        String sql = "SELECT * FROM PersonalCourse WHERE customer_id = ? AND expire_date >= GETDATE()";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCourse pc = new PersonalCourse();
                pc.setId(rs.getInt("id"));
                pc.setCustomerId(rs.getInt("customer_id"));
                pc.setCourseId(rs.getInt("course_id"));
                pc.setEnrollDate(rs.getDate("enroll_date"));
                pc.setExpireDate(rs.getDate("expire_date"));
                pc.setProgress(rs.getInt("progress"));
                pc.setPricePackageId(rs.getInt("price_package_id"));
                pc.setSaleNoteId(rs.getInt("sale_note_id"));
                pc.setStatus(rs.getInt("status"));
                list.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Kiểm tra đã ghi danh chưa
    public boolean exists(int userId, int courseId) throws Exception {
        String sql = "SELECT COUNT(*) FROM PersonalCourse WHERE customer_id = ? AND course_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Ghi danh mới
    public void enrollCourse(int userId, int courseId, int pricePackageId) throws Exception {
        String sql = "INSERT INTO PersonalCourse (customer_id, course_id, enroll_date, expire_date, price_package_id, progress, status) VALUES (?, ?, GETDATE(), DATEADD(MONTH, 1, GETDATE()), ?, 0, 1)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setInt(3, pricePackageId);
            ps.executeUpdate();
        }
    }

    // Cập nhật trạng thái ghi danh
    public void updateStatus(int userId, int courseId, int status) throws Exception {
        String sql = "UPDATE PersonalCourse SET status = ? WHERE customer_id = ? AND course_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, userId);
            ps.setInt(3, courseId);
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) {
        PersonalCourseDAO dao = new PersonalCourseDAO();
        List<PersonalCourse> list = dao.getPersonalCoursesByUser(3);
        for (PersonalCourse personalCourse : list) {
            System.out.println(list);
            
        }
    }
    
}
