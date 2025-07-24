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

@WebServlet("/myCourses")
public class MyCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        PersonalCourseDAO pcDao = new PersonalCourseDAO();
        CourseDAO courseDao = new CourseDAO();
        List<PersonalCourse> personalCourses = pcDao.getPersonalCoursesByUser(user.getId());
        List<MyCourseItem> myCourses = new ArrayList<>();
        
        // Debug logs
        System.out.println("User ID: " + user.getId());
        System.out.println("personalCourses size: " + personalCourses.size());
        for (PersonalCourse pc : personalCourses) {
            System.out.println("PersonalCourse: " + pc);
        }
        
        CourseDTODAO courseDTODao = new CourseDTODAO();
        List<CourseDTO> allCourseDTOs = courseDTODao.getCourse();
        Map<Integer, CourseDTO> courseDTOMap = new HashMap<>();
        for (CourseDTO dto : allCourseDTOs) {
            courseDTOMap.put(dto.getId(), dto);
        }

        for (PersonalCourse pc : personalCourses) {
            try {
                CourseDTO dto = courseDTOMap.get(pc.getCourseId());
                if (dto != null) {
                    MyCourseItem item = new MyCourseItem();
                    item.setId(dto.getId());
                    item.setTitle(dto.getTitle());
                    item.setTagline(dto.getTagline());
                    item.setThumbnailUrl(dto.getThumbnailUrl() != null ? dto.getThumbnailUrl() : "");
                    item.setValidFrom(pc.getEnrollDate());
                    item.setValidTo(pc.getExpireDate());
                    item.setActive(pc.getExpireDate() != null && pc.getExpireDate().after(new java.util.Date()));
                    myCourses.add(item);
                }
            } catch (Exception e) {
                System.out.println("Exception in processing PersonalCourse: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Final myCourses size: " + myCourses.size());
        request.setAttribute("myCourses", myCourses);
        request.getRequestDispatcher("myCourses.jsp").forward(request, response);
    }

    // DTO for JSP
    public static class MyCourseItem {
        private int id;
        private String title;
        private String tagline;
        private String thumbnailUrl;
        private java.util.Date validFrom;
        private java.util.Date validTo;
        private boolean active;
        // getters and setters...
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getTagline() { return tagline; }
        public void setTagline(String tagline) { this.tagline = tagline; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
        public java.util.Date getValidFrom() { return validFrom; }
        public void setValidFrom(java.util.Date validFrom) { this.validFrom = validFrom; }
        public java.util.Date getValidTo() { return validTo; }
        public void setValidTo(java.util.Date validTo) { this.validTo = validTo; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }
    
}
