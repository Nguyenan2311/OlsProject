package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.Course;
import model.CourseDetail;
import model.CourseVisualContent;
import model.PricePackage;
import model.Tagline;

public class CourseDAO
extends DBContext {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<CourseListItem> getCourses(String search, String category, String sortBy, Integer taglineId, int limit, int offset) throws Exception {
        ArrayList<CourseListItem> courses = new ArrayList<CourseListItem>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT c.id, c.subtitle as title, c.created_date,        ct.thumbnail_url,        t.name as tagline,        s.value as category,        c.number_of_learner,        c.total_duration,        MIN(pp.sale_price) as min_price,        MIN(pp.price) as original_price FROM Course c LEFT JOIN Setting s ON c.category_id = s.id LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id LEFT JOIN Tagline t ON ctt.tagline_id = t.id LEFT JOIN Course_Thumbnails ct ON c.id = ct.course_id LEFT JOIN PricePackage pp ON c.id = pp.course_id WHERE c.status = 1 ");
        ArrayList<Object> params = new ArrayList<Object>();
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
        sql.append("ORDER BY ").append(this.getSortColumn(sortBy)).append(" ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(limit);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); ++i) {
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
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return courses;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getTotalCourses(String search, String category, Integer taglineId) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT c.id) FROM Course c LEFT JOIN Setting s ON c.category_id = s.id LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id WHERE c.status = 1 ");
        ArrayList<Object> params = new ArrayList<Object>();
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
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); ++i) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                int n = rs.getInt(1);
                this.closeResources(conn, stmt, rs);
                return n;
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Tagline> getAllTaglines() throws Exception {
        ArrayList<Tagline> taglines = new ArrayList<Tagline>();
        String sql = "SELECT id, name FROM [dbo].[Tagline]";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tagline tagline = new Tagline();
                tagline.setId(rs.getInt("id"));
                tagline.setName(rs.getString("name"));
                taglines.add(tagline);
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return taglines;
    }

     */
    public List<String> getCategories() throws Exception {
        ArrayList<String> categories = new ArrayList<String>();
        String sql = "SELECT DISTINCT s.value FROM Setting s INNER JOIN SettingType st ON s.setting_type_id = st.id WHERE st.name = 'Course Categories' AND s.status = 1 ORDER BY s.value";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String category = rs.getString("value");
                if (category == null || category.trim().isEmpty()) continue;
                categories.add(category);
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
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
    public CourseDetail getCourseDetailById(int courseId) throws Exception {
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setCourseInfo(this.getBasicCourseInfo(courseId));
        courseDetail.setFullDescription(this.getCourseFullDescription(courseId));
        courseDetail.setAllPricePackages(this.getAllPricePackagesForCourse(courseId));
        courseDetail.setLowestPricePackage(this.getLowestPricePackage(courseId));
        List<CourseVisualContent> allVisuals = this.getCourseVisuals(courseId);
        courseDetail.setImages(allVisuals.stream().filter(v -> v.getType() == 1).collect(Collectors.toList()));
        courseDetail.setVideos(allVisuals.stream().filter(v -> v.getType() == 2).collect(Collectors.toList()));
        return courseDetail;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CourseListItem getBasicCourseInfo(int courseId) throws Exception {
        String sql = "SELECT c.id, c.subtitle as title, c.created_date, c.description,        ct.thumbnail_url, t.name as tagline FROM Course c LEFT JOIN Course_Thumbnails ct ON c.id = ct.course_id LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id LEFT JOIN Tagline t ON ctt.tagline_id = t.id WHERE c.id = ? AND c.status = 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                CourseListItem course = new CourseListItem();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setThumbnailUrl(rs.getString("thumbnail_url"));
                course.setTagline(rs.getString("tagline"));
                course.setCreatedDate(rs.getDate("created_date"));
                CourseListItem courseListItem = course;
                this.closeResources(conn, stmt, rs);
                return courseListItem;
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getCourseFullDescription(int courseId) throws Exception {
        String sql = "SELECT description FROM ProductDescription WHERE course_id = ? AND status = 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String string = rs.getString("description");
                this.closeResources(conn, stmt, rs);
                return string;
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PricePackage getLowestPricePackage(int courseId) throws Exception {
        String sql = "SELECT TOP 1 * FROM PricePackage WHERE course_id = ? AND (end_date IS NULL OR end_date >= GETDATE()) ORDER BY sale_price ASC, price ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                PricePackage pp = new PricePackage();
                pp.setId(rs.getInt("id"));
                pp.setCourseId(rs.getInt("course_id"));
                pp.setTitle(rs.getString("title"));
                pp.setPrice(rs.getDouble("price"));
                pp.setSalePrice(rs.getDouble("sale_price"));
                PricePackage pricePackage = pp;
                this.closeResources(conn, stmt, rs);
                return pricePackage;
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<CourseVisualContent> getCourseVisuals(int courseId) throws Exception {
        ArrayList<CourseVisualContent> visuals = new ArrayList<CourseVisualContent>();
        String sql = "SELECT cvc.id, cvc.content, cvc.type, cvc.description FROM CourseVisualContent cvc JOIN CourseVisualContent_Course cvcc ON cvc.id = cvcc.course_visual_content_id WHERE cvcc.course_id = ? AND cvcc.status = 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                CourseVisualContent visual = new CourseVisualContent();
                visual.setId(rs.getInt("id"));
                visual.setContent(rs.getString("content"));
                visual.setType(rs.getInt("type"));
                visual.setDescription(rs.getString("description"));
                visuals.add(visual);
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return visuals;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CourseDetailItem getCourseDetail(int courseId) throws Exception {

        StringBuilder sql = new StringBuilder(
            "SELECT c.id, c.subtitle as title, c.description, c.total_duration, " +
            "       c.number_of_learner, ct.thumbnail_url, t.name as tagline, " +
            "       s.value as category, (u.first_name + ' ' + u.last_name) as expert_name " +
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
            conn = this.getConnection();
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
                CourseDetailItem courseDetailItem = courseDetail;
                this.closeResources(conn, stmt, rs);
                return courseDetailItem;
            }
            this.closeResources(conn, stmt, rs);
        }
        catch (Throwable throwable) {
            this.closeResources(conn, stmt, rs);
            throw throwable;
        }
        return null;
    }

    private String getSortColumn(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "c.subtitle ASC";
        }
        switch (sortBy.toLowerCase().trim()) {
            case "price": {
                return "MIN(pp.sale_price) ASC";
            }
            case "category": {
                return "s.value ASC";
            }
            case "learners": {
                return "c.number_of_learner DESC";
            }
            case "duration": {
                return "c.total_duration ASC";
            }
            case "newest": {
                return "c.created_date DESC";
            }
            case "oldest": {
                return "c.created_date ASC";
            }
        }
        return "c.subtitle ASC";
    }

    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
            }
        }
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }

    
    /**
     * Get courses for admin management with pagination, filtering and owner info
     */
    public List<CourseManagementItem> getCoursesForManagement(String search, String category, String status, int limit, int offset) throws Exception {
        List<CourseManagementItem> courses = new ArrayList<>();
        
        // Simplified query without complex joins first
        StringBuilder sql = new StringBuilder(
            "SELECT c.id, c.subtitle as name, " +
            "       ISNULL(s.value, 'Unknown') as category, " +
            "       0 as lesson_count, " +
            "       ISNULL(u.first_name + ' ' + u.last_name, 'Unknown') as owner_name, " +
            "       CASE WHEN c.status = 1 THEN 'Active' ELSE 'Inactive' END as status " +
            "FROM Course c " +
            "LEFT JOIN Setting s ON c.category_id = s.id " +
            "LEFT JOIN [User] u ON c.expert_id = u.id " +
            "WHERE 1=1 "
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
        
        if (status != null && !status.trim().isEmpty()) {
            if ("Active".equals(status)) {
                sql.append("AND c.status = 1 ");
            } else if ("Inactive".equals(status)) {
                sql.append("AND c.status = 0 ");
            }
        }
        
        sql.append("ORDER BY c.id DESC");
        
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
            
            int count = 0;
            int added = 0;
            while (rs.next()) {
                if (count >= offset && added < limit) {
                    CourseManagementItem item = new CourseManagementItem();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setCategory(rs.getString("category"));
                    item.setLessonCount(rs.getInt("lesson_count"));
                    item.setOwnerName(rs.getString("owner_name"));
                    item.setStatus(rs.getString("status"));
                    courses.add(item);
                    added++;
                }
                count++;
                if (added >= limit) break;
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return courses;
    }
    
    /**
     * Get total count of courses for management with filters
     */
    public int getTotalCoursesForManagement(String search, String category, String status) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(DISTINCT c.id) " +
            "FROM Course c " +
            "LEFT JOIN Setting s ON c.category_id = s.id " +
            "WHERE 1=1 "
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
        
        if (status != null && !status.trim().isEmpty()) {
            if ("Active".equals(status)) {
                sql.append("AND c.status = 1 ");
            } else if ("Inactive".equals(status)) {
                sql.append("AND c.status = 0 ");
            }
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
    
    /**
     * Update course status
     */
    public boolean updateCourseStatus(int courseId, int status) throws Exception {
        String sql = "UPDATE Course SET status = ?, updated_date = GETDATE() WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setInt(2, courseId);
            
            return stmt.executeUpdate() > 0;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    
    /**
     * Get course by ID for editing (including inactive courses)
     */
    public Course getCourseByIdForEdit(int courseId) throws Exception {
        String sql = "SELECT * FROM Course WHERE id = ?";
        
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
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return null;
    }
    /**
     * Insert new course
     */
    public boolean insertCourse(Course course) throws Exception {
        String sql = "INSERT INTO Course (subtitle, expert_id, total_duration, category_id, description, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getSubtitle());
            stmt.setInt(2, course.getExpert_id());
            stmt.setInt(3, course.getTotal_duration());
            stmt.setInt(4, course.getCategory_id());
            stmt.setString(5, course.getDescription());
            stmt.setInt(6, course.getStatus());
            
            return stmt.executeUpdate() > 0;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    
    /**
     * Update existing course
     */
    public boolean updateCourse(Course course) throws Exception {
        String sql = "UPDATE Course SET subtitle = ?, expert_id = ?, total_duration = ?, category_id = ?, " +
                    "description = ?, status = ?, updated_date = GETDATE() WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getSubtitle());
            stmt.setInt(2, course.getExpert_id());
            stmt.setInt(3, course.getTotal_duration());
            stmt.setInt(4, course.getCategory_id());
            stmt.setString(5, course.getDescription());
            stmt.setInt(6, course.getStatus());
            stmt.setInt(7, course.getId());
            
            return stmt.executeUpdate() > 0;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    
    /**
     * Get category ID by name
     */
    public int getCategoryIdByName(String categoryName) throws Exception {
        String sql = "SELECT s.id FROM Setting s " +
                    "INNER JOIN SettingType st ON s.setting_type_id = st.id " +
                    "WHERE st.name = 'Course Categories' AND s.value = ? AND s.status = 1";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoryName);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return 0;
    }
    
    /**
     * Get category name by ID
     */
    public String getCategoryNameById(int categoryId) throws Exception {
        String sql = "SELECT s.value FROM Setting s " +
                    "INNER JOIN SettingType st ON s.setting_type_id = st.id " +
                    "WHERE st.name = 'Course Categories' AND s.id = ? AND s.status = 1";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("value");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return null;
    }
    
    /**
     * Inner class for course management item
     */
    public static class CourseManagementItem {
        private int id;
        private String name;
        private String category;
        private int lessonCount;
        private String ownerName;
        private String status;
        
        // Constructors
        public CourseManagementItem() {}
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public int getLessonCount() { return lessonCount; }
        public void setLessonCount(int lessonCount) { this.lessonCount = lessonCount; }
        
        public String getOwnerName() { return ownerName; }
        public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
    
    
    /**
     * Inner class cho course list item


    public List<PricePackage> getAllPricePackagesForCourse(int courseId) throws Exception {
        ArrayList<PricePackage> packages = new ArrayList<PricePackage>();
        String sql = "SELECT * FROM PricePackage WHERE course_id = ? AND (end_date IS NULL OR end_date >= GETDATE()) ORDER BY price ASC";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    PricePackage pp = new PricePackage();
                    pp.setId(rs.getInt("id"));
                    pp.setCourseId(rs.getInt("course_id"));
                    pp.setTitle(rs.getString("title"));
                    pp.setPrice(rs.getDouble("price"));
                    pp.setSalePrice(rs.getDouble("sale_price"));
                    pp.setStartDate((Date)rs.getDate("start_date"));
                    pp.setEndDate((Date)rs.getDate("end_date"));
                    packages.add(pp);
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Error fetching all price packages for course ID " + courseId + ": " + e.getMessage());
            throw new Exception("Could not retrieve price packages.", e);
        }
        return packages;
    }

    public List<PricePackage> getPricePackages(int courseId) throws Exception {
        ArrayList<PricePackage> pricePackages = new ArrayList<PricePackage>();
        String sql = "SELECT id, course_id, title, price, sale_price, start_date, end_date FROM PricePackage WHERE course_id = ? AND sale_price > 0 AND (start_date IS NULL OR start_date <= ?) AND (end_date IS NULL OR end_date >= ?) ORDER BY sale_price ASC";
        LocalDateTime now = LocalDateTime.now();
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            Timestamp timestampNow = Timestamp.valueOf(now);
            stmt.setInt(1, courseId);
            stmt.setTimestamp(2, timestampNow);
            stmt.setTimestamp(3, timestampNow);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    PricePackage pricePackage = new PricePackage();
                    pricePackage.setId(rs.getInt("id"));
                    pricePackage.setCourseId(rs.getInt("course_id"));
                    pricePackage.setTitle(rs.getString("title"));
                    pricePackage.setPrice(rs.getDouble("price"));
                    pricePackage.setSalePrice(rs.getDouble("sale_price"));
                    pricePackage.setStartDate((Date)rs.getDate("start_date"));
                    pricePackage.setEndDate((Date)rs.getDate("end_date"));
                    pricePackages.add(pricePackage);
                }
            }
        }
        catch (Exception e) {
            throw new Exception("Error fetching price packages: " + e.getMessage(), e);
        }
        return pricePackages;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation

     */
    public PricePackage getPricePackageById(int pricePackageId) throws Exception {
        String sql = "SELECT * FROM PricePackage WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setInt(1, pricePackageId);
            try (ResultSet rs = stmt.executeQuery();){
                if (!rs.next()) return null;
                PricePackage pp = new PricePackage();
                pp.setId(rs.getInt("id"));
                pp.setCourseId(rs.getInt("course_id"));
                pp.setTitle(rs.getString("title"));
                pp.setPrice(rs.getDouble("price"));
                pp.setSalePrice(rs.getDouble("sale_price"));
                pp.setStartDate((Date)rs.getDate("start_date"));
                pp.setEndDate((Date)rs.getDate("end_date"));
                PricePackage pricePackage = pp;
                return pricePackage;
            }
        }
        catch (SQLException e) {
            System.err.println("Error fetching price package by ID " + pricePackageId + ": " + e.getMessage());
            throw new Exception("Could not retrieve price package by ID.", e);
        }
    }
 
 // Trong file CourseDAO.java

// Thay đổi kiểu trả về từ List<Course> thành List<CourseListItem>
public List<CourseListItem> getActiveCoursesByCustomerId(int customerId) {
    List<CourseListItem> list = new ArrayList<>();
    // Cập nhật câu lệnh SQL để JOIN các bảng cần thiết và lấy đủ thông tin
    String sql = "SELECT c.id, c.subtitle as title, c.description, t.name as tagline, ct.thumbnail_url " +
                 "FROM Course c " +
                 "JOIN PersonalCourse pc ON c.id = pc.course_id " +
                 "LEFT JOIN Course_Thumbnails ct ON c.id = ct.course_id " +
                 "LEFT JOIN Course_Tagline ctt ON c.id = ctt.course_id " +
                 "LEFT JOIN Tagline t ON ctt.tagline_id = t.id " +
                 "WHERE pc.customer_id = ? AND pc.status = 1";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Tạo đối tượng CourseListItem thay vì Course
                CourseListItem courseItem = new CourseListItem();
                courseItem.setId(rs.getInt("id"));
                courseItem.setTitle(rs.getString("title")); // JSP đang dùng subtitle, nên đổi tên hoặc dùng title
                courseItem.setDescription(rs.getString("description"));
                courseItem.setTagline(rs.getString("tagline"));
                courseItem.setThumbnailUrl(rs.getString("thumbnail_url"));
                
                list.add(courseItem);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    public static class CourseListItem {
        private int id;
        private String title;
        private String thumbnailUrl;
        private String tagline;
        private String category;
        private int numberOfLearner;
        private String description;
        private int totalDuration;
        private double salePrice;
        private double originalPrice;
        private Date createdDate;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnailUrl() {
            return this.thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getTagline() {
            return this.tagline;
        }

        public void setTagline(String tagline) {
            this.tagline = tagline;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getNumberOfLearner() {
            return this.numberOfLearner;
        }

        public void setNumberOfLearner(int numberOfLearner) {
            this.numberOfLearner = numberOfLearner;
        }

        public int getTotalDuration() {
            return this.totalDuration;
        }

        public void setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
        }

        public double getSalePrice() {
            return this.salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        public double getOriginalPrice() {
            return this.originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Date getCreatedDate() {
            return this.createdDate;
        }

        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }

        public String getFormattedDuration() {
            if (this.totalDuration < 60) {
                return this.totalDuration + " mins";
            }
            int hours = this.totalDuration / 60;
            int mins = this.totalDuration % 60;
            return hours + "h " + (String)(mins > 0 ? mins + "m" : "");
        }

        public boolean hasDiscount() {
            return this.originalPrice > 0.0 && this.originalPrice > this.salePrice;
        }

        public double getDiscountPercentage() {
            if (this.hasDiscount()) {
                return (this.originalPrice - this.salePrice) / this.originalPrice * 100.0;
            }
            return 0.0;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

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

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getTotalDuration() {
            return this.totalDuration;
        }

        public void setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
        }

        public int getNumberOfLearner() {
            return this.numberOfLearner;
        }

        public void setNumberOfLearner(int numberOfLearner) {
            this.numberOfLearner = numberOfLearner;
        }

        public String getThumbnailUrl() {
            return this.thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getTagline() {
            return this.tagline;
        }

        public void setTagline(String tagline) {
            this.tagline = tagline;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getExpertName() {
            return this.expertName;
        }

        public void setExpertName(String expertName) {
            this.expertName = expertName;
        }

        public String getFormattedDuration() {
            if (this.totalDuration < 60) {
                return this.totalDuration + " mins";
            }
            int hours = this.totalDuration / 60;
            int mins = this.totalDuration % 60;
            return hours + "h " + (String)(mins > 0 ? mins + "m" : "");
        }
    }
}