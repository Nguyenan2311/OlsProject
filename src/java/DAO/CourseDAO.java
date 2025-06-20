package DAO;
import context.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import java.util.Date; // Thêm import này
import model.Tagline;
public class CourseDAO extends DBContext {
    
    /**
     * Lấy danh sách courses với filtering và pagination
     */
    public List<CourseListItem> getCourses(String search, String category, String sortBy, Integer taglineId, int limit, int offset) 
            throws Exception {
        List<CourseListItem> courses = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT c.id, c.subtitle as title, c.created_date, " +
            "       ct.thumbnail_url, " +
            "       t.name as tagline, " +
            "       s.value as category, " +
            "       c.number_of_learner, " +
            "       c.total_duration, " +
            "       MIN(pp.sale_price) as min_price, " +
            "       MIN(pp.price) as original_price " +
            "FROM Course c " +
            "LEFT JOIN Setting s ON c.category_id = s.id " +
            "LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id " +
            "LEFT JOIN Tagline t ON ctt.tagline_id = t.id " +
            "LEFT JOIN Course_Thumbnails ct ON c.id = ct.course_id " +
            "LEFT JOIN PricePackage pp ON c.id = pp.course_id " +
            "WHERE c.status = 1 "
        );
        
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND c.subtitle LIKE ? ");
            params.add("%" + search.trim() + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND s.value = ? ");
            params.add(category.trim());
        }
        if (taglineId != null && taglineId > 0) {
            sql.append("AND ctt.tagline_id = ? ");
            params.add(taglineId);
        }
        
        sql.append("GROUP BY c.id, c.subtitle, c.created_date, ct.thumbnail_url, t.name, s.value, c.number_of_learner, c.total_duration ");
        sql.append("ORDER BY ").append(getSortColumn(sortBy)).append(" ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        
        params.add(offset);
        params.add(limit);
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
                CourseListItem course = new CourseListItem();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setThumbnailUrl(rs.getString("thumbnail_url"));
                course.setTagline(rs.getString("tagline"));
                course.setCategory(rs.getString("category"));
                course.setNumberOfLearner(rs.getInt("number_of_learner"));
                course.setTotalDuration(rs.getInt("total_duration"));
                course.setCreatedDate(rs.getDate("created_date"));
                
                double salePrice = rs.getDouble("min_price");
                double originalPrice = rs.getDouble("original_price");
                course.setSalePrice(rs.wasNull() ? 0.0 : salePrice);
                course.setOriginalPrice(rs.wasNull() ? 0.0 : originalPrice);
                
                courses.add(course);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return courses;
    }
    
    public int getTotalCourses(String search, String category, Integer taglineId) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(DISTINCT c.id) FROM Course c " +
            "LEFT JOIN Setting s ON c.category_id = s.id " +
            "LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id " +
            "WHERE c.status = 1 "
        );
        
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND c.subtitle LIKE ? ");
            params.add("%" + search.trim() + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND s.value = ? ");
            params.add(category.trim());
        }
        if (taglineId != null && taglineId > 0) {
            sql.append("AND ctt.tagline_id = ? ");
            params.add(taglineId);
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
            if (rs.next()) {
                return rs.getInt(1);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return 0;
    }
    
    public List<Tagline> getAllTaglines() throws Exception {
        List<Tagline> taglines = new ArrayList<>();
        String sql = "SELECT id, name FROM [dbo].[Tagline]";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tagline tagline = new Tagline();
                tagline.setId(rs.getInt("id"));
                tagline.setName(rs.getString("name"));
                taglines.add(tagline);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return taglines;
    }
    /**
     * L?y danh s�ch categories
     */
    public List<String> getCategories() throws Exception {
        List<String> categories = new ArrayList<>();
        
        String sql = "SELECT DISTINCT s.value FROM Setting s " +
                    "INNER JOIN SettingType st ON s.setting_type_id = st.id " +
                    "WHERE st.name = 'Course Categories' AND s.status = 1 " +
                    "ORDER BY s.value";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String category = rs.getString("value");
                if (category != null && !category.trim().isEmpty()) {
                    categories.add(category);
                }
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return categories;
    }
    
    /**
     * L?y course theo ID
     */
    public Course getCourseById(int courseId) throws Exception {
        String sql = "SELECT * FROM Course WHERE id = ? AND status = 1";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseId);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setSubtitle(rs.getString("subtitle"));
                course.setExpert_id(rs.getInt("expert_id"));
                course.setTotal_duration(rs.getInt("total_duration"));
                course.setCategory_id(rs.getInt("category_id"));
                course.setDescription(rs.getString("description"));
                course.setStatus(rs.getInt("status"));
                course.setUpdated_date(rs.getDate("updated_date"));
                course.setCreated_date(rs.getDate("created_date"));
                course.setNumber_of_learner(rs.getInt("number_of_learner"));
                return course;
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return null;
    }
    
    /**
     * L?y course details v?i ??y ?? th�ng tin
     */
    public CourseDetailItem getCourseDetail(int courseId) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT c.id, c.subtitle as title, c.description, c.total_duration, " +
            "       c.number_of_learner, ct.thumbnail_url, t.name as tagline, " +
            "       s.value as category, u.full_name as expert_name " +
            "FROM Course c " +
            "LEFT JOIN Setting s ON c.category_id = s.id " +
            "LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id " +
            "LEFT JOIN Tagline t ON ctt.tagline_id = t.id " +
            "LEFT JOIN Course_Thumbnails ct ON c.id = ct.course_id " +
            "LEFT JOIN [User] u ON c.expert_id = u.id " +
            "WHERE c.id = ? AND c.status = 1"
        );
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, courseId);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                CourseDetailItem courseDetail = new CourseDetailItem();
                courseDetail.setId(rs.getInt("id"));
                courseDetail.setTitle(rs.getString("title"));
                courseDetail.setDescription(rs.getString("description"));
                courseDetail.setTotalDuration(rs.getInt("total_duration"));
                courseDetail.setNumberOfLearner(rs.getInt("number_of_learner"));
                courseDetail.setThumbnailUrl(rs.getString("thumbnail_url"));
                courseDetail.setTagline(rs.getString("tagline"));
                courseDetail.setCategory(rs.getString("category"));
                courseDetail.setExpertName(rs.getString("expert_name"));
                return courseDetail;
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return null;
    }
    
    /**
     * Helper method ?? get sort column
     */
    private String getSortColumn(String sortBy) {
    if (sortBy == null || sortBy.trim().isEmpty()) {
        return "c.subtitle ASC";
    }

    switch (sortBy.toLowerCase().trim()) {
        case "price": return "MIN(pp.sale_price) ASC";
        case "category": return "s.value ASC";
        case "learners": return "c.number_of_learner DESC";
        case "duration": return "c.total_duration ASC";
        case "newest": return "c.created_date DESC";
        case "oldest": return "c.created_date ASC";
        case "title":
        default: return "c.subtitle ASC";
    }
}

    /**
     * Helper method ?? ?�ng resources
     */
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
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
    
    
    /**
     * Inner class cho course list item
     */
    public static class CourseListItem {
        private int id;
        private String title;
        private String thumbnailUrl;
        private String tagline;
        private String category;
        private int numberOfLearner;
        private int totalDuration;
        private double salePrice;
        private double originalPrice;
        private Date createdDate; // <-- THÊM TRƯỜNG MỚI (đổi tên cho đúng chuẩn Java)
        // Constructors
        public CourseListItem() {}
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getThumbnailUrl() { return thumbnailUrl; }
        public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
        
        public String getTagline() { return tagline; }
        public void setTagline(String tagline) { this.tagline = tagline; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public int getNumberOfLearner() { return numberOfLearner; }
        public void setNumberOfLearner(int numberOfLearner) { this.numberOfLearner = numberOfLearner; }
        
        public int getTotalDuration() { return totalDuration; }
        public void setTotalDuration(int totalDuration) { this.totalDuration = totalDuration; }
        
        public double getSalePrice() { return salePrice; }
        public void setSalePrice(double salePrice) { this.salePrice = salePrice; }
        
        public double getOriginalPrice() { return originalPrice; }
        public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }
        // CẬP NHẬT: Thêm Getter/Setter cho trường mới
        public Date getCreatedDate() { return createdDate; }
        public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
        // ... các phương thức helper khác giữ nguyên ...
        public String getFormattedDuration() {
            if (totalDuration < 60) {
                return totalDuration + " mins";
            } else {
                int hours = totalDuration / 60;
                int mins = totalDuration % 60;
                return hours + "h " + (mins > 0 ? mins + "m" : "");
            }
        }
        
        public boolean hasDiscount() {
            return originalPrice > 0 && originalPrice > salePrice;
        }
        
        public double getDiscountPercentage() {
            if (hasDiscount()) {
                return ((originalPrice - salePrice) / originalPrice) * 100;
            }
            return 0;
        }
    }
    /**
     * Inner class cho course detail
     */
    public static class CourseDetailItem {
        private int id;
        private String title;
        private String description;
        private int totalDuration;
        private int numberOfLearner;
        private String thumbnailUrl;
        private String tagline;
        private String category;
        private String expertName;
        
        // Constructors
        public CourseDetailItem() {}
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public int getTotalDuration() { return totalDuration; }
        public void setTotalDuration(int totalDuration) { this.totalDuration = totalDuration; }
        
        public int getNumberOfLearner() { return numberOfLearner; }
        public void setNumberOfLearner(int numberOfLearner) { this.numberOfLearner = numberOfLearner; }
        
        public String getThumbnailUrl() { return thumbnailUrl; }
        public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
        
        public String getTagline() { return tagline; }
        public void setTagline(String tagline) { this.tagline = tagline; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String getExpertName() { return expertName; }
        public void setExpertName(String expertName) { this.expertName = expertName; }
        
        public String getFormattedDuration() {
            if (totalDuration < 60) {
                return totalDuration + " mins";
            } else {
                int hours = totalDuration / 60;
                int mins = totalDuration % 60;
                return hours + "h " + (mins > 0 ? mins + "m" : "");
            }
        }
    }
}