package control.course;



import DAO.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Course;
import model.User;

// Đảm bảo URL là /my-course
@WebServlet(name = "MyCourseServlet", urlPatterns = {"/my-course"})
public class MyCourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        CourseDAO courseDAO = new CourseDAO();
        // Gọi đúng phương thức DAO để lấy khóa học active
       // ĐÃ SỬA LẠI
// Khai báo biến để chứa đúng loại danh sách "CourseListItem" đầy đủ thông tin
List<CourseDAO.CourseListItem> activeCourses = courseDAO.getActiveCoursesByCustomerId(user.getId());

        // Đặt tên attribute là "courses" để JSP dễ hiểu
        request.setAttribute("courses", activeCourses);

        // Chuyển đến đúng file JSP
        request.getRequestDispatcher("my-course.jsp").forward(request, response);
    }
}