package control.user;

import DAO.UserDAO;
import model.RoleOption;
import model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RoleOption;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = UserDAO.getUserById(id);
        List<RoleOption> roles = UserDAO.getRoleOptions();
        request.setAttribute("user", user);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("editUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int roleId = Integer.parseInt(request.getParameter("role_id"));
        int status = Integer.parseInt(request.getParameter("status"));
        boolean success = UserDAO.updateUserRoleAndStatus(id, roleId, status);
        if (success) {
            request.setAttribute("message", "User updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update user.");
        }
        User user = UserDAO.getUserById(id);
        List<RoleOption> roles = UserDAO.getRoleOptions();
        request.setAttribute("user", user);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("editUser.jsp").forward(request, response);
    }
}
