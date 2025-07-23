package control.user;

import DAO.UserDAO;
import model.RoleOption;
import model.User;
import model.EmailUtil;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RoleOption;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RoleOption> roles = UserDAO.getRoleOptions();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("addUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int roleId = Integer.parseInt(request.getParameter("role_id"));
        String address = request.getParameter("address");
        int status = Integer.parseInt(request.getParameter("status"));
        // Generate random password
        String password = generatePassword();
        User user = new User();
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole_id(roleId);
        user.setAddress(address);
        user.setStatus(status);
        user.setPassword(password);
        boolean success = UserDAO.addUser(user);
        if (success) {
            // Send email
            String msg = "Your account has been created. Your login password is: " + password;
            EmailUtil.sendRandomPasswordEmail(email, msg);
            request.setAttribute("message", "User added and password sent to email.");
        } else {
            request.setAttribute("error", "Failed to add user. Email may already exist.");
        }
        List<RoleOption> roles = UserDAO.getRoleOptions();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("addUser.jsp").forward(request, response);
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
