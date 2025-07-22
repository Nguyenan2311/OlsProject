package control;

import DAO.UserDAO;
import model.User;
import model.NameUtils;
import model.PasswordHasher;
import service.PasswordResetService;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    // Email validation pattern
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
"(?!.*\\.\\.)[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$"
;
    
    private static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang đăng ký
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        UserDAO userDAO = new UserDAO();
        
        // Validate gender
        String genderError = validateGender(gender);
        if (genderError != null) {
            request.setAttribute("message", genderError);
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        // --- VALIDATION ---
        if (fullName.trim().isEmpty() || gender == null || gender.isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        

        // Validate phone number
        String phoneError = validatePhone(phone);
        if (phoneError != null) {
            request.setAttribute("message", phoneError);
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Validate email format
        String emailError = validateEmail(email);
        if (emailError != null) {
            request.setAttribute("message", emailError);
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        

        try {
            // Kiểm tra email đã tồn tại chưa
            if (userDAO.isEmailExists(email)) {
                request.setAttribute("message", "This email is already associated with an account. Please <a href='login'>login</a>.");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // Tách tên
            String[] nameParts = NameUtils.splitFullName(fullName);
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            // Tạo mật khẩu tạm thời và hash nó
            String temporaryPassword = UUID.randomUUID().toString();
            

            // Tạo người dùng mới trong CSDL
            User newUser = userDAO.createNewUser(firstName, lastName, email, phone, gender, temporaryPassword);
            if (newUser == null) {
                throw new Exception("Failed to create the user account in the database.");
            }

            // GỬI EMAIL CHỨA LINK THIẾT LẬP MẬT KHẨU
            PasswordResetService resetService = new PasswordResetService();
            boolean emailSent = resetService.sendInitialSetPasswordEmail(newUser, request);

            if (emailSent) {
                // Hiển thị thông báo thành công
                request.getRequestDispatcher("/resetPasswordSent.jsp").forward(request, response);
                request.setAttribute("status", "success");
            } else {
                // Hiển thị lỗi nếu không gửi được email
                request.setAttribute("message", "Account created, but we failed to send the password setup email. Please contact support.");
                request.setAttribute("status", "error");
            }
            
            request.getRequestDispatcher("/register.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An unexpected error occurred. Please try again later.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    /**
     * Validates email format and returns specific error message
     * @param email the email to validate
     * @return error message if invalid, null if valid
     */
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required.";
        }
        
        email = email.trim();
        
        // Check if email contains @
        if (!email.contains("@")) {
            return "Email must contain @ symbol.";
        }
        
        // Check if email has content before @
        if (email.startsWith("@")) {
            return "Email must have content before @ symbol.";
        }
        
        // Check if email has content after @
        if (email.endsWith("@")) {
            return "Email must have content after @ symbol.";
        }
        
        // Check for multiple @ symbols
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return "Email can only contain one @ symbol.";
        }
        
        // Split email by @
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return "Email format is invalid.";
        }
        
        String localPart = parts[0];
        String domainPart = parts[1];
        
        // Check local part
        if (localPart.isEmpty()) {
            return "Email must have content before @ symbol.";
        }
        
        // Check domain part
        if (domainPart.isEmpty()) {
            return "Email must have content after @ symbol.";
        }
        
        // Check if domain contains dot
        if (!domainPart.contains(".")) {
            return "Email domain must contain a dot (.) symbol.";
        }
        
        // Check if domain starts or ends with dot
        if (domainPart.startsWith(".") || domainPart.endsWith(".")) {
            return "Email domain cannot start or end with a dot.";
        }
        
        // Check if domain has content before and after dot
        String[] domainParts = domainPart.split("\\.");
        for (String part : domainParts) {
            if (part.isEmpty()) {
                return "Email domain must have content between dots.";
            }
        }
        
        // Check if last part (TLD) is valid
        String tld = domainParts[domainParts.length - 1];
        if (tld.length() < 2 || tld.length() > 7) {
            return "Email domain extension must be 2-7 characters long.";
        }
        
        // Check if TLD contains only letters
        if (!tld.matches("[a-zA-Z]+")) {
            return "Email domain extension must contain only letters.";
        }
        
        // Final regex validation
        if (!EMAIL_REGEX.matcher(email).matches()) {
            return "Email format is invalid.";
        }
        
        return null; // Valid email
    }

    /**
     * Validates phone number format
     * @param phone the phone number to validate
     * @return error message if invalid, null if valid
     */
    private String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Phone number is required.";
        }
        
        phone = phone.trim();
        
        // Check if phone contains only digits
        if (!phone.matches("\\d+")) {
            return "Phone number must contain only numbers.";
        }
        return null; // Valid phone
    }

    /**
     * Validates gender selection
     * @param gender the gender to validate
     * @return error message if invalid, null if valid
     */
    private String validateGender(String gender) {
        if (gender == null) {
            return "Gender is required.";
        }
        
        gender = gender.trim();
        
        if (gender.isEmpty()) {
            return "Gender is required.";
        }
        
     
        return null; // Valid gender
    }
}