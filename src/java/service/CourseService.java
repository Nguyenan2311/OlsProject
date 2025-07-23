package service;

import DAO.CourseDAO;
import java.util.List;
import java.util.logging.Logger;
import model.CourseDetail;
import model.Tagline;

public class CourseService {
    private static final Logger LOGGER = Logger.getLogger(CourseService.class.getName());
    private final CourseDAO courseDAO = new CourseDAO();

    public List<CourseDAO.CourseListItem> getCourseList(String search, String category, String sortBy, Integer taglineId, int rowsPerPage, int currentPage) throws Exception {
        int offset = (currentPage - 1) * rowsPerPage;
        if (offset < 0) {
            offset = 0;
        }
        List courses = this.courseDAO.getCourses(search, category, sortBy, taglineId, rowsPerPage, offset);
        LOGGER.info("Retrieved " + courses.size() + " courses");
        return courses;
    }

    public int getTotalCourses(String search, String category, Integer taglineId) throws Exception {
        return this.courseDAO.getTotalCourses(search, category, taglineId);
    }

    public List<String> getCategories() throws Exception {
        return this.courseDAO.getCategories();
    }
    
    public String getCategoryNameById(int categoryId) throws Exception {
        return courseDAO.getCategoryNameById(categoryId);
    }

    public CourseDetail getCourseDetail(int courseId) throws Exception {
        if (courseId < 1) {
            LOGGER.warning("Invalid courseId: " + courseId);
            return null;
        }
        return this.courseDAO.getCourseDetailById(courseId);
    }

    public int[] validatePaginationParams(String rowsPerPageStr, String currentPageStr) {
        int rowsPerPage = 2;
        int currentPage = 1;
        try {
            if (!(rowsPerPageStr == null || rowsPerPageStr.trim().isEmpty() || (rowsPerPage = Integer.parseInt(rowsPerPageStr.trim())) >= 1 && rowsPerPage <= 100)) {
                rowsPerPage = 2;
            }
        }
        catch (NumberFormatException e) {
            LOGGER.warning("Invalid rowsPerPage: " + rowsPerPageStr);
        }
        try {
            if (currentPageStr != null && !currentPageStr.trim().isEmpty() && (currentPage = Integer.parseInt(currentPageStr.trim())) < 1) {
                currentPage = 1;
            }
        }
        catch (NumberFormatException e) {
            LOGGER.warning("Invalid currentPage: " + currentPageStr);
        }
        return new int[]{rowsPerPage, currentPage};
    }

    public String validateDisplayOption(String param, String defaultValue) {
        return "on".equalsIgnoreCase(param) ? "on" : "off";
    }

    public int calculateTotalPages(int totalCourses, int rowsPerPage) {
        return (int)Math.ceil((double)totalCourses / (double)rowsPerPage);
    }

    public List<Tagline> getAllTaglines() throws Exception {
        return this.courseDAO.getAllTaglines();
    }

    public CourseDetail getCourseDetails(int courseId) throws Exception {
        if (courseId < 1) {
            LOGGER.warning("Invalid courseId for details: " + courseId);
            return null;
        }
        return this.courseDAO.getCourseDetailById(courseId);
    }
    
    // Course Management Methods
    public List<CourseDAO.CourseManagementItem> getCoursesForManagement(String search, String category, String status, int rowsPerPage, int currentPage) 
            throws Exception {
        int offset = (currentPage - 1) * rowsPerPage;
        if (offset < 0) offset = 0;
        
        List<CourseDAO.CourseManagementItem> courses = courseDAO.getCoursesForManagement(search, category, status, rowsPerPage, offset);
        LOGGER.info("Retrieved " + courses.size() + " courses for management");
        return courses;
    }
    
    public int getTotalCoursesForManagement(String search, String category, String status) throws Exception {
        return courseDAO.getTotalCoursesForManagement(search, category, status);
    }
    
    /**
     * Update course status
     */
    public boolean updateCourseStatus(int courseId, int status) throws Exception {
        return courseDAO.updateCourseStatus(courseId, status);
    }
    
    /**
     * Get course by ID for editing
     */
    public Course getCourseByIdForEdit(int courseId) throws Exception {
        return courseDAO.getCourseByIdForEdit(courseId);
    }
    
    /**
     * Insert new course
     */
    public boolean insertCourse(Course course) throws Exception {
        return courseDAO.insertCourse(course);
    }
    
    /**
     * Update existing course
     */
    public boolean updateCourse(Course course) throws Exception {
        return courseDAO.updateCourse(course);
    }
    
    /**
     * Get category ID by name
     */
    public int getCategoryIdByName(String categoryName) throws Exception {
        return courseDAO.getCategoryIdByName(categoryName);
    }
}