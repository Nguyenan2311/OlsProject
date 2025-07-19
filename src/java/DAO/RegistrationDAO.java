package DAO;


import DAO.CourseDAO;
import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.PricePackage;


public class RegistrationDAO
extends DBContext {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public void enrollCourse(int customerId, int courseId, int pricePackageId) throws Exception {
    CourseDAO courseDAO = new CourseDAO();
    PricePackage selectedPackage = courseDAO.getPricePackageById(pricePackageId);
    if (selectedPackage == null) {
        throw new Exception("Price package not found.");
    }

    Date enrollDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(enrollDate);

    if (selectedPackage.getTitle() != null) {
        if (selectedPackage.getTitle().contains("1-Month")) {
            cal.add(Calendar.MONTH, 1);
        } else if (selectedPackage.getTitle().contains("6-Month")) {
            cal.add(Calendar.MONTH, 6);
        }
    }

    Date expireDate = cal.getTime();
    
    String sql = "INSERT INTO PersonalCourse (customer_id, course_id, enroll_date, expire_date, price_package_id, progress, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = this.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, customerId);
        stmt.setInt(2, courseId);
        stmt.setDate(3, new java.sql.Date(enrollDate.getTime()));
        stmt.setDate(4, new java.sql.Date(expireDate.getTime()));
        stmt.setInt(5, pricePackageId);
        stmt.setInt(6, 0); // progress
        stmt.setInt(7, 0); // status mặc định là 1
        stmt.executeUpdate();
    }
}

     public boolean isUserAlreadyEnrolled(int userId, int courseId) throws Exception {
        // Câu lệnh đếm số lượng bản ghi có cặp (customer_id, course_id) tương ứng
        String sql = "SELECT COUNT(id) FROM PersonalCourse WHERE customer_id = ? AND course_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, courseId);
            
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu số lượng đếm được > 0, nghĩa là đã tồn tại bản ghi.
                    return rs.getInt(1) > 0;
                }
            }
        }
        // Trả về false nếu có lỗi hoặc không tìm thấy
        return false;
    }

 
}