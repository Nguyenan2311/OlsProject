package control;



import service.EmailService;
import service.PasswordResetService;
import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.PasswordResetToken;
import java.io.IOException;


@WebServlet(name = "RequestResetPasswordServlet", urlPatterns = {"/RequestResetPasswordServlet"})
public class RequestResetPasswordServlet extends HttpServlet {

    private final PasswordResetService resetService = new PasswordResetService();
    private final EmailService emailService = new EmailService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    // 1. Get email from request
    String email = request.getParameter("email");
    
    // 2. Validate email input
    if (email == null || email.trim().isEmpty()) {
        request.setAttribute("error", "Email address is required");
        request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        return;
    }

    // 3. Find user by email
    UserDAO userDAO = new UserDAO();
    User user = userDAO.findByEmail(email);
    
    if (user == null) {
        request.setAttribute("error", "No account found with that email address");
        request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        return;
    }
    
    // 4. Create and save reset token
    PasswordResetService resetService = new PasswordResetService();
    PasswordResetToken token = resetService.createResetToken(user);
    
    if (token == null) {
        request.setAttribute("error", "Failed to generate reset token. Please try again.");
        request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        return;
    }
    
    // 5. Build reset link
    String appUrl = request.getScheme() + "://" + request.getServerName();
    
    // Include port if it's not default
    int serverPort = request.getServerPort();
    if (serverPort != 80 && serverPort != 443) {
        appUrl += ":" + serverPort;
    }
    
    appUrl += request.getContextPath();
    String resetLink = appUrl + "/resetPasswordForm.jsp?token=" + token.getToken();
    
    // 6. Send reset email
    boolean emailSent = resetService.sendResetEmail(user.getEmail(), resetLink);
    
    if (!emailSent) {
        request.setAttribute("error", "Failed to send reset email. Please try again.");
        request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        return;
    }
    
    // 7. Redirect to confirmation page
    request.setAttribute("email", user.getEmail());
    request.getRequestDispatcher("/resetPasswordSent.jsp").forward(request, response);
}
}