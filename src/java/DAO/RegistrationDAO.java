package DAO;
import DAO.CourseDAO;
import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.PricePackage;

public class RegistrationDAO extends DBContext {
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
        
        // First, delete existing enrollment if it exists
        deleteExistingEnrollment(customerId, courseId);
        
        // Then insert new enrollment
        String sql = "INSERT INTO PersonalCourse (customer_id, course_id, enroll_date, expire_date, price_package_id, progress, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, courseId);
            stmt.setDate(3, new java.sql.Date(enrollDate.getTime()));
            stmt.setDate(4, new java.sql.Date(expireDate.getTime()));
            stmt.setInt(5, pricePackageId);
            stmt.setInt(6, 0); // progress
            stmt.setInt(7, 0); // status
            stmt.executeUpdate();
        }
    }
    
    // New method to delete existing enrollment
    private void deleteExistingEnrollment(int customerId, int courseId) throws Exception {
        String sql = "DELETE FROM PersonalCourse WHERE customer_id = ? AND course_id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, courseId);
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
     /**
     * Activates a user's access to a course by creating a record in PersonalCourse.
     * This method is called AFTER a successful payment.
     *
     * @param customerId The ID of the user.
     * @param courseId The ID of the course.
     * @param pricePackageId The ID of the price package (used to calculate expiry date).
     * @param orderDetailId The ID of the order detail record that triggered this enrollment.
     * @throws Exception if the database operation fails.
     */
    public void activateCourseEnrollment(int customerId, int courseId, int pricePackageId, int orderDetailId) throws Exception {
        // Kiểm tra đã có bản ghi status=0 chưa
        String checkSql = "SELECT COUNT(*) FROM PersonalCourse WHERE customer_id = ? AND course_id = ? AND status = 0";
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, customerId);
            checkStmt.setInt(2, courseId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Nếu đã có bản ghi status=0, chỉ update status=2
                    String updateSql = "UPDATE PersonalCourse SET status = 2 WHERE customer_id = ? AND course_id = ? AND status = 0";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, customerId);
                        updateStmt.setInt(2, courseId);
                        updateStmt.executeUpdate();
                    }
                    return;
                }
            }
        }
        // Nếu chưa có, insert mới như cũ
        
        // Lấy thông tin gói giá để tính ngày hết hạn
        CourseDAO courseDAO = new CourseDAO(); // Giả sử CourseDAO có thể lấy price package
        PricePackage selectedPackage = courseDAO.getPricePackageById(pricePackageId);
        
        if (selectedPackage == null) {
            throw new Exception("Price package (ID: " + pricePackageId + ") not found during course activation.");
        }
        
        // Sử dụng LocalDate cho logic ngày tháng an toàn và hiện đại
        LocalDate enrollDate = LocalDate.now();
        LocalDate expireDate = enrollDate; 

        if (selectedPackage.getTitle() != null) {
            if (selectedPackage.getTitle().contains("1-Month")) {
                expireDate = enrollDate.plusMonths(1);
            } else if (selectedPackage.getTitle().contains("6-Month")) {
                expireDate = enrollDate.plusMonths(6);
            } else if (selectedPackage.getTitle().contains("1-Year")) {
                expireDate = enrollDate.plusYears(1);
            }
        }
        
        // Xóa bản ghi cũ nếu có
        deleteExistingEnrollment(customerId, courseId);

        // Câu lệnh INSERT vào PersonalCourse
        String sql = "INSERT INTO PersonalCourse (customer_id, course_id, enroll_date, expire_date, progress, order_detail_id, status, price_package_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, courseId);
            stmt.setDate(3, java.sql.Date.valueOf(enrollDate)); // Sửa lỗi: chuyển LocalDate sang java.sql.Date
            stmt.setDate(4, java.sql.Date.valueOf(expireDate)); // Sửa lỗi: chuyển LocalDate sang java.sql.Date
            stmt.setInt(5, 0); // Progress ban đầu
         
            stmt.setInt(6, orderDetailId); // Liên kết với chi tiết đơn hàng
            stmt.setInt(7, 2); // Trạng thái đã kích hoạt (pending)
            stmt.setInt(8, pricePackageId); // Trạng thái đã kích hoạt

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Activating course enrollment failed, no rows affected.");
            }
        }
    }
}
