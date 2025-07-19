package control.course;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CourseRegistration;
import model.User;
import service.CourseRegistrationService;

@WebServlet(name = "CourseRegistrationServlet", urlPatterns = {"/my-registrations"})
public class CourseRegistrationServlet extends HttpServlet {
    private CourseRegistrationService registrationService;

    @Override
    public void init() throws ServletException {
        registrationService = new CourseRegistrationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login"); // Redirect to login if not authenticated
            return;
        }

        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String pageStr = request.getParameter("page");

        int page = 1;
        int pageSize = 10;

        try {
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {
            // Get filtered registrations
            List<CourseRegistration> allRegistrations = registrationService.getAllRegistrations(search, category, user.getId());

            // Calculate pagination
            int totalRegistrations = allRegistrations.size();
            int totalPages = (int) Math.ceil((double) totalRegistrations / pageSize);

            // Ensure page is within valid range
            page = Math.max(1, Math.min(page, totalPages));

            // Get registrations for current page
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRegistrations);
            List<CourseRegistration> pagedRegistrations = allRegistrations.subList(
                    Math.min(start, totalRegistrations),
                    Math.min(end, totalRegistrations)
            );

            // Calculate stats
            long activeCount = registrationService.getActiveCount(user.getId());
            long submittedCount = registrationService.getSubmittedCount(user.getId());

            // Set request attributes
            request.setAttribute("registrationList", pagedRegistrations);
            request.setAttribute("activeCount", activeCount);
            request.setAttribute("submittedCount", submittedCount);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);

            // Forward to JSP
            request.getRequestDispatcher("/my_registrations.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        } catch (Exception ex) {
            Logger.getLogger(CourseRegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("cancel".equals(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                try {
                    registrationService.cancelRegistration(Integer.parseInt(id), customerId);
                    request.setAttribute("message", "Registration cancelled successfully");
                    request.setAttribute("messageType", "success");
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid registration ID");
                    request.setAttribute("messageType", "danger");
                } catch (SQLException e) {
                    request.setAttribute("message", "Database error: " + e.getMessage());
                    request.setAttribute("messageType", "danger");
                } catch (Exception ex) {
                    Logger.getLogger(CourseRegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        doGet(request, response);
    }
}
