package control.course;

import DAO.CourseDAO;
import DAO.CourseDAO.CourseManagementItem;
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

@WebServlet("/admin/course-management")
public class CourseManagementServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CourseManagementServlet.class.getName());
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
            // Get parameters
            String searchKeyword = request.getParameter("search");
            String category = request.getParameter("category");
            String status = request.getParameter("status");
            String pageStr = request.getParameter("page");
            String pageSizeStr = request.getParameter("pageSize");
            String action = request.getParameter("action");

            // Handle status update action
            if ("updateStatus".equals(action)) {
                handleStatusUpdate(request, response);
                return;
            }

            // Pagination parameters
            int page = 1;
            int pageSize = 10;
            
            try {
                if (pageStr != null && !pageStr.trim().isEmpty()) {
                    page = Integer.parseInt(pageStr);
                    if (page < 1) page = 1;
                }
                if (pageSizeStr != null && !pageSizeStr.trim().isEmpty()) {
                    pageSize = Integer.parseInt(pageSizeStr);
                    if (pageSize < 5) pageSize = 5;
                    if (pageSize > 50) pageSize = 50;
                }
            } catch (NumberFormatException e) {
                // Use default values
            }

            int offset = (page - 1) * pageSize;

            // Get courses for management
            List<CourseManagementItem> courses = courseService.getCoursesForManagement(
                searchKeyword, category, status, pageSize, page);
            
            int totalCourses = courseService.getTotalCoursesForManagement(searchKeyword, category, status);
            int totalPages = (int) Math.ceil((double) totalCourses / pageSize);

            // Get categories for filter dropdown
            List<String> categories = courseService.getCategories();

            // Set attributes
            request.setAttribute("courses", courses);
            request.setAttribute("categories", categories);
            request.setAttribute("searchKeyword", searchKeyword != null ? searchKeyword : "");
            request.setAttribute("selectedCategory", category != null ? category : "");
            request.setAttribute("selectedStatus", status != null ? status : "");
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCourses", totalCourses);

            // Calculate pagination info
            int startItem = totalCourses > 0 ? offset + 1 : 0;
            int endItem = Math.min(offset + pageSize, totalCourses);
            request.setAttribute("startItem", startItem);
            request.setAttribute("endItem", endItem);

            request.getRequestDispatcher("/admin/course-management.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in CourseManagementServlet", e);
            request.setAttribute("errorMessage", "Unable to load course management. Please try again later.");
            request.setAttribute("errorDetails", e.getMessage());
            request.getRequestDispatcher("/admin/course-management.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleStatusUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String courseIdStr = request.getParameter("courseId");
            String newStatusStr = request.getParameter("newStatus");

            if (courseIdStr == null || newStatusStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
                return;
            }

            int courseId = Integer.parseInt(courseIdStr);
            int newStatus = Integer.parseInt(newStatusStr);

            boolean success = courseService.updateCourseStatus(courseId, newStatus);

            if (success) {
                // Redirect back to course management with success message
                String redirectUrl = request.getContextPath() + "/admin/course-management?statusUpdate=success";
                response.sendRedirect(redirectUrl);
            } else {
                // Redirect back with error message
                String redirectUrl = request.getContextPath() + "/admin/course-management?statusUpdate=error";
                response.sendRedirect(redirectUrl);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating course status", e);
            String redirectUrl = request.getContextPath() + "/admin/course-management?statusUpdate=error";
            response.sendRedirect(redirectUrl);
        }
    }
}
