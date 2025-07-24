
package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CourseDetail;
import service.CourseService;
import DAO.PersonalCourseDAO;
import model.PersonalCourse;
import model.User;

@WebServlet(value={"/course-detail"})
public class CourseDetailServlet
extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseDetailServlet.class.getName());
    private CourseService courseService;

    public void init() throws ServletException {
        super.init();
        this.courseService = new CourseService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String courseIdStr = request.getParameter("id");
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                response.sendRedirect("courses");
                return;
            }
            int courseId = Integer.parseInt(courseIdStr);
            CourseDetail courseDetail = this.courseService.getCourseDetails(courseId);
            if (courseDetail == null || courseDetail.getCourseInfo() == null) {
                request.setAttribute("errorMessage", (Object)"Course not found.");
                request.getRequestDispatcher("/courseList.jsp").forward((ServletRequest)request, (ServletResponse)response);
                return;
            }
            List categories = this.courseService.getCategories();
            List taglines = this.courseService.getAllTaglines();
            // Lấy danh sách khoá học user đã đăng ký ở trạng thái active hoặc pending
            List<Integer> userRegisteredActiveOrPendingCourseIds = null;
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                PersonalCourseDAO pcDao = new PersonalCourseDAO();
                userRegisteredActiveOrPendingCourseIds = new java.util.ArrayList<>();
                for (PersonalCourse pc : pcDao.getPersonalCoursesByUser(user.getId())) {
                    if (pc.getStatus() == 1 || pc.getStatus() == 2) {
                        userRegisteredActiveOrPendingCourseIds.add(pc.getCourseId());
                    }
                }
            }
            request.setAttribute("userRegisteredActiveOrPendingCourseIds", userRegisteredActiveOrPendingCourseIds);
            request.setAttribute("courseDetail", (Object)courseDetail);
            request.setAttribute("categories", (Object)categories);
            request.setAttribute("taglines", (Object)taglines);
            request.getRequestDispatcher("/courseDetail.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid course ID format", e);
            response.sendRedirect("courses");
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in CourseDetailServlet", e);
            request.setAttribute("errorMessage", (Object)"Unable to load course details. Please try again later.");
            request.getRequestDispatcher("/courseList.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}