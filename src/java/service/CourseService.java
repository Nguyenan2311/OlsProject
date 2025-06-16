package service;

import DAO.CourseDAO;
import DAO.CourseDAO.CourseListItem;
import model.Course;
import java.util.List;
import java.util.logging.Logger;

public class CourseService {
    private static final Logger LOGGER = Logger.getLogger(CourseService.class.getName());
    private final CourseDAO courseDAO;

    public CourseService() {
        this.courseDAO = new CourseDAO();
    }

    // Updated method to include taglineId
    public List<CourseListItem> getCourseList(String search, String category, String sortBy, Integer taglineId, int rowsPerPage, int currentPage) 
            throws Exception {
        int offset = (currentPage - 1) * rowsPerPage;
        if (offset < 0) offset = 0;

        List<CourseListItem> courses = courseDAO.getCourses(search, category, sortBy, taglineId, rowsPerPage, offset);
        LOGGER.info("Retrieved " + courses.size() + " courses");
        return courses;
    }

    // Updated method to include taglineId
    public int getTotalCourses(String search, String category, Integer taglineId) throws Exception {
        return courseDAO.getTotalCourses(search, category, taglineId);
    }

    public List<String> getCategories() throws Exception {
        return courseDAO.getCategories();
    }

    public Course getCourseDetail(int courseId) throws Exception {
        if (courseId < 1) {
            LOGGER.warning("Invalid courseId: " + courseId);
            return null;
        }
        return courseDAO.getCourseById(courseId);
    }

    public int[] validatePaginationParams(String rowsPerPageStr, String currentPageStr) {
        int rowsPerPage = 2;
        int currentPage = 1;

        try {
            if (rowsPerPageStr != null && !rowsPerPageStr.trim().isEmpty()) {
                rowsPerPage = Integer.parseInt(rowsPerPageStr.trim());
                if (rowsPerPage < 1 ||rowsPerPage > 100 ) {
                    rowsPerPage = 2;
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid rowsPerPage: " + rowsPerPageStr);
        }

        try {
            if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
                currentPage = Integer.parseInt(currentPageStr.trim());
                if (currentPage < 1) {
                    currentPage = 1;
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid currentPage: " + currentPageStr);
        }

        return new int[]{rowsPerPage, currentPage};
    }

    public String validateDisplayOption(String param, String defaultValue) {
        return "on".equalsIgnoreCase(param) ? "on" : "off";
    }

    public int calculateTotalPages(int totalCourses, int rowsPerPage) {
        return (int) Math.ceil((double) totalCourses / rowsPerPage);
    }

    // Add method to get all taglines
    public List<model.Tagline> getAllTaglines() throws Exception {
        return courseDAO.getAllTaglines();
    }
}