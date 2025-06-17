package control;



import service.PasswordResetService;
import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PasswordHasher;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;


@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    String token = request.getParameter("token");
    System.out.println("[DEBUG] Received token: " + token);
    
    PasswordResetService resetService = new PasswordResetService();
    User user = resetService.validateToken(token);
    
    if (user == null) {
        System.out.println("[DEBUG] Token invalid or expired, redirecting to resetPasswordExpired.jsp");
        request.getRequestDispatcher("/resetPasswordExpired.jsp").forward(request, response);
        return;
    }
    
    System.out.println("[DEBUG] Token valid, user: " + user.getEmail());
    request.setAttribute("token", token);
    request.getRequestDispatcher("/resetPasswordForm.jsp").forward(request, response);
}
    
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        
        PasswordResetService resetService = new PasswordResetService();
        User user = resetService.validateToken(token);
        
        if (user == null) {
            request.getRequestDispatcher("/resetPasswordExpired.jsp").forward(request, response);
            return;
        }
        
        try {
            // C?p nh?t password v� ?�nh d?u token ?� s? d?ng
            user.setPassword(PasswordHasher.hash(newPassword));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserDAO userDAO = new UserDAO();
        if (userDAO.update(user)) {
            resetService.markTokenAsUsed(token);
            request.getRequestDispatcher("/resetPasswordSuccess.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to update password");
            request.getRequestDispatcher("/resetPasswordForm.jsp").forward(request, response);
        }
    }
}