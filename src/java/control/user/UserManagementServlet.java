package control.user;

import DAO.UserDAO;
import model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RoleOption;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read filter/search/sort/page params
        String gender = request.getParameter("gender");
        String roleIdStr = request.getParameter("roleId");
        String statusStr = request.getParameter("status");
        String search = request.getParameter("search");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder = request.getParameter("sortOrder");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");
        Integer roleId = (roleIdStr != null && !roleIdStr.isEmpty()) ? Integer.parseInt(roleIdStr) : null;
        Integer status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : null;
        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (pageSizeStr != null && !pageSizeStr.isEmpty()) ? Integer.parseInt(pageSizeStr) : 10;
        int offset = (page - 1) * pageSize;
        // Get filtered, sorted, paginated users
        List<User> users = userDAO.getUsers(gender, roleId, status, search, sortColumn, sortOrder, pageSize, offset);
        // For dropdowns: fetch roles, statuses, genders
        List<String> genders = List.of("Male", "Female");
        List<Integer> statuses = List.of(0, 1); // 0: inactive, 1: active
        List<RoleOption> roles = UserDAO.getRoleOptions(); // static method to fetch roles from Setting
        // Count total users for pagination (reuse getUsers with no limit for count)
        int totalUsers = userDAO.getUsers(gender, roleId, status, search, sortColumn, sortOrder, Integer.MAX_VALUE, 0).size();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        // Set attributes
        request.setAttribute("users", users);
        request.setAttribute("genders", genders);
        request.setAttribute("statuses", statuses);
        request.setAttribute("roles", roles);
        request.setAttribute("selectedGender", gender);
        request.setAttribute("selectedRoleId", roleId);
        request.setAttribute("selectedStatus", status);
        request.setAttribute("search", search);
        request.setAttribute("sortColumn", sortColumn);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }
}
