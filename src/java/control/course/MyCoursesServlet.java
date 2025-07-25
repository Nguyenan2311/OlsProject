package control.course;

import DAO.PersonalCourseDAO;
import DAO.CourseDAO;
import DAO.CourseDTODAO;
import model.PersonalCourse;
import model.User;
import model.Course;
import model.CourseDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.MyCourse;

@WebServlet("/myCourses")
public class MyCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        
        int id = user.getId();
        CourseDTODAO dao = new CourseDTODAO();
        List<MyCourse> allCourseDTOs = dao.getMyCourse(id);
        
        
        request.setAttribute("myCourses", allCourseDTOs);
        request.getRequestDispatcher("myCourses.jsp").forward(request, response);
    }

    // DTO for JSP
    
    
}
