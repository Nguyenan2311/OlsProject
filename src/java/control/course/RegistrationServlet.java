package control.course;

import DAO.RegistrationDAO;
import DAO.UserDAO;
import model.CourseDetail;
import model.User;
import model.NameUtils;
import model.PasswordHasher;
import service.CourseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.PasswordResetService;

@WebServlet("/register-course")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        RegistrationDAO registrationDAO = new RegistrationDAO();
        
        // Luôn xóa các thông báo cũ để tránh hiển thị lại
        session.removeAttribute("registrationStatus");
        session.removeAttribute("registrationMessage");

        String courseIdStr = request.getParameter("courseId");
        String pricePackageIdStr = request.getParameter("pricePackageId");
        String redirectURL = "course-detail?id=" + courseIdStr;

        try {
            User user = (User) session.getAttribute("user");
            
            // Ép kiểu ID một lần ở đầu
            int courseId = Integer.parseInt(courseIdStr);
            int pricePackageId = Integer.parseInt(pricePackageIdStr);

            if (user != null) {
                // ==========================================================
                //       TRƯỜNG HỢP 1: NGƯỜI DÙNG ĐÃ ĐĂNG NHẬP
                // ==========================================================
                
                // Không cần validate, chỉ cần ghi danh khóa học
                User getUser = userDAO.findByEmail(user.getEmail());
                 
            if (registrationDAO.isUserAlreadyEnrolled(user.getId(), courseId)) {
                session.setAttribute("registrationStatus", "error");
                session.setAttribute("registrationMessage", "You have already registered for this course. You can find it in 'My Courses'.");
                response.sendRedirect(redirectURL);
                return; // Dừng lại
            }   
                registrationDAO.enrollCourse(getUser.getId(), courseId, pricePackageId);
                
                session.setAttribute("registrationStatus", "success");
                session.setAttribute("registrationMessage", "Congratulations! You have successfully registered for the course.");
                response.sendRedirect(redirectURL);

            } else {
                // ==========================================================
                //       TRƯỜNG HỢP 2: NGƯỜI DÙNG CHƯA ĐĂNG NHẬP (KHÁCH)
                // ==========================================================
                
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String mobile = request.getParameter("mobile");
                String gender = request.getParameter("gender");

                Map<String, String> errors = new HashMap<>();

                // --- Bắt đầu Validation ---
                if (!isEmailValid(email)) {
                    errors.put("email", "Please enter a valid form email address ex: abc@gmail.com");
                } else if (userDAO.isEmailExists(email)) {
                    errors.put("email", "This email is already registered. Please <a href='login.jsp' class='alert-link'>login</a>.");
                }
                if (fullName == null || fullName.trim().isEmpty()) {
                    errors.put("fullName", "Full Name is required.");
                }
                // --- Kết thúc Validation ---

                if (!errors.isEmpty()) {
                    // Nếu có lỗi, forward lại trang và hiển thị lỗi
                    request.setAttribute("validationErrors", errors);
                    request.setAttribute("submittedFullName", fullName);
                    request.setAttribute("submittedEmail", email);
                    request.setAttribute("submittedMobile", mobile);
                    request.setAttribute("submittedGender", gender);
                    request.setAttribute("openModal", true); // Tín hiệu để tự động mở modal

                    // Load lại dữ liệu trang để hiển thị đúng
                    CourseService courseService = new CourseService();
                    CourseDetail courseDetail = courseService.getCourseDetails(courseId);
                    request.setAttribute("courseDetail", courseDetail);

                    request.getRequestDispatcher("/courseDetail.jsp").forward(request, response);
                    return;
                }

                // Nếu không có lỗi, tạo user mới
                String[] nameParts = NameUtils.splitFullName(fullName);
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                String randomPassword = UUID.randomUUID().toString().substring(0, 8);
                String hashedPassword = PasswordHasher.hash(randomPassword);
                PasswordResetService resetService = new PasswordResetService();
            

                User newUser = userDAO.createNewUser(firstName, lastName, email, mobile, gender, hashedPassword);
                if (newUser == null) {
                    throw new Exception("Failed to create a new user account.");
                } 
                
                boolean emailSent = resetService.sendInitialSetPasswordEmail(newUser, request);
                // Ghi danh khóa học cho người dùng mới
                registrationDAO.enrollCourse(newUser.getId(), courseId, pricePackageId);
                
                // Tự động đăng nhập và gửi thông báo thành công
                session.setAttribute("account", newUser);
                session.setAttribute("registrationStatus", "success");
                session.setAttribute("registrationMessage", "Your account has been created and you are now registered for the course. Your password has been sent to your email.");
                response.sendRedirect(redirectURL);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("registrationStatus", "error");
            session.setAttribute("registrationMessage", "An unexpected error occurred during the process. Please try again.");
            response.sendRedirect(redirectURL);
        }
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,}$";
        
        
        
        
        
        
        
        
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    
}