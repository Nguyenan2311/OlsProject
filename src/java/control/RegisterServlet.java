package control;


import DAO.UserDAO;
import model.User;
import model.NameUtils;
import model.PasswordHasher;
import service.PasswordResetService;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hi?n th? trang ??ng k�
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // L?y th�ng tin t? form
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        UserDAO userDAO = new UserDAO();
        
        // --- VALIDATION ---
        if (fullName.trim().isEmpty() || gender.isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            // Ki?m tra email ?� t?n t?i ch?a
            if (userDAO.isEmailExists(email)) {
                request.setAttribute("message", "This email is already associated with an account. Please <a href='login'>login</a>.");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // T�ch t�n
            String[] nameParts = NameUtils.splitFullName(fullName);
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            // T?o m?t kh?u t?m th?i v� hash n�
            String temporaryPassword = UUID.randomUUID().toString();
            String hashedPassword = PasswordHasher.hash(temporaryPassword);

            // T?o ng??i d�ng m?i trong CSDL
            User newUser = userDAO.createNewUser(firstName, lastName, email, phone, gender, hashedPassword);
            if (newUser == null) {
                throw new Exception("Failed to create the user account in the database.");
            }

            // G?I EMAIL CH?A LINK THI?T L?P M?T KH?U
            PasswordResetService resetService = new PasswordResetService();
            boolean emailSent = resetService.sendInitialSetPasswordEmail(newUser, request);

            if (emailSent) {
                // Hi?n th? th�ng b�o th�nh c�ng
                request.setAttribute("message", "Registration successful! Please check your email (" + email + ") to set your password and complete the process.");
                request.setAttribute("status", "success");
            } else {
                // Hi?n th? l?i n?u kh�ng g?i ???c email
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
}