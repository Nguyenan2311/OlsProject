package control.course;

import DAO.CourseDAO;
import model.Course;
import service.CourseService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/admin/course/new", "/admin/course/edit", "/admin/course/details"})
public class CourseFormServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CourseFormServlet.class.getName());
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String servletPath = request.getServletPath();
            String courseIdStr = request.getParameter("id");
            
            // Load categories once for all forms
            List<String> categories = courseService.getCategories();

            if ("/admin/course/new".equals(servletPath)) {
                // New course form
                request.setAttribute("categories", categories);
                request.setAttribute("pageTitle", "Add New Course");
                request.setAttribute("isEdit", false);
                request.getRequestDispatcher("/admin/course-form.jsp").forward(request, response);
                
            } else if ("/admin/course/edit".equals(servletPath)) {
                // Edit course form
                if (courseIdStr != null) {
                    int courseId = Integer.parseInt(courseIdStr);
                    Course course = courseService.getCourseByIdForEdit(courseId);
                    
                    if (course != null) {
                        // Get category name for the course
                        String currentCategory = courseService.getCategoryNameById(course.getCategory_id());
                        
                        request.setAttribute("course", course);
                        request.setAttribute("currentCategory", currentCategory);
                        request.setAttribute("categories", categories);
                        request.setAttribute("pageTitle", "Edit Course");
                        request.setAttribute("isEdit", true);
                        request.getRequestDispatcher("/admin/course-form.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID is required");
                }
                
            } else if ("/admin/course/details".equals(servletPath)) {
                // Course details view
                if (courseIdStr != null) {
                    int courseId = Integer.parseInt(courseIdStr);
                    Course course = courseService.getCourseByIdForEdit(courseId);
                    
                    if (course != null) {
                        request.setAttribute("course", course);
                        request.setAttribute("pageTitle", "Course Details");
                        request.getRequestDispatcher("/admin/course-details.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID is required");
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in CourseFormServlet", e);
            request.setAttribute("errorMessage", "Unable to process request. Please try again later.");
            request.setAttribute("errorDetails", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("CourseFormServlet POST called with action: " + action);
        System.out.println("courseName: " + request.getParameter("courseName"));
        System.out.println("category: " + request.getParameter("category"));
        
        try {
            if ("create".equals(action)) {
                handleCreateCourse(request, response);
            } else if ("update".equals(action)) {
                handleUpdateCourse(request, response);
            } else {
                // Invalid action, redirect to course management
                response.sendRedirect(request.getContextPath() + "/admin/course-management");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CourseFormServlet POST Error: " + e.getMessage());
            System.out.println("Action parameter: " + request.getParameter("action"));
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/admin/course-form.jsp").forward(request, response);
        }
    }
    
    private void handleCreateCourse(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        // Get form parameters
        String name = request.getParameter("courseName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String durationStr = request.getParameter("duration");
        String statusStr = request.getParameter("status");
        
        // Validate required fields
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Course name is required");
        }
        
        if (category == null || category.trim().isEmpty()) {
            throw new Exception("Category is required");
        }
        
        // Parse numeric fields
        int duration = 0;
        if (durationStr != null && !durationStr.trim().isEmpty()) {
            try {
                duration = Integer.parseInt(durationStr.trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid duration format");
            }
        }
        
        int status = 1; // Default to active
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusStr.trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid status format");
            }
        }
        
        // Get category ID
        int categoryId = courseService.getCategoryIdByName(category);
        if (categoryId == 0) {
            throw new Exception("Invalid category selected");
        }
        
        // Create course object
        Course course = new Course();
        course.setSubtitle(name.trim());
        course.setCategory_id(categoryId);
        course.setDescription(description != null ? description.trim() : "");
        course.setTotal_duration(duration);
        course.setStatus(status);
        course.setExpert_id(1); // TODO: Get from session when user auth is implemented
        
        // Insert course
        boolean success = courseService.insertCourse(course);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/course-management?success=Course created successfully");
        } else {
            throw new Exception("Failed to create course");
        }
    }
    
    private void handleUpdateCourse(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        // Get course ID
        String idStr = request.getParameter("courseId");
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new Exception("Course ID is required");
        }
        
        int courseId;
        try {
            courseId = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid course ID format");
        }
        
        // Get existing course
        Course course = courseService.getCourseByIdForEdit(courseId);
        if (course == null) {
            throw new Exception("Course not found");
        }
        
        // Get form parameters
        String name = request.getParameter("courseName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String durationStr = request.getParameter("duration");
        String statusStr = request.getParameter("status");
        
        // Validate required fields
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Course name is required");
        }
        
        if (category == null || category.trim().isEmpty()) {
            throw new Exception("Category is required");
        }
        
        // Parse numeric fields
        int duration = course.getTotal_duration(); // Keep existing if not provided
        if (durationStr != null && !durationStr.trim().isEmpty()) {
            try {
                duration = Integer.parseInt(durationStr.trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid duration format");
            }
        }
        
        int status = course.getStatus(); // Keep existing if not provided
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusStr.trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid status format");
            }
        }
        
        // Get category ID
        int categoryId = courseService.getCategoryIdByName(category);
        if (categoryId == 0) {
            throw new Exception("Invalid category selected");
        }
        
        // Update course object
        course.setSubtitle(name.trim());
        course.setCategory_id(categoryId);
        course.setDescription(description != null ? description.trim() : "");
        course.setTotal_duration(duration);
        course.setStatus(status);
        
        // Update course
        boolean success = courseService.updateCourse(course);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/course-management?success=Course updated successfully");
        } else {
            throw new Exception("Failed to update course");
        }
    }
}
