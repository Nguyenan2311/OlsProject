/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import DAO.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author An_PC
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/changepassword"})
public class ChangePassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        DAO dao = new DAO();

        int userId = user.getId();

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String rePass = request.getParameter("rePassword");

        if (!user.getPassword().equals(oldPass)) {
            request.setAttribute("error", "Mật khẩu cũ không chính xác");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        if (!newPass.equals(rePass)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận không khớp");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        boolean update = dao.changePassword(userId, newPass);

        if (update) {
            // Cập nhật mật khẩu trong session (nếu bạn muốn)
            user.setPassword(newPass);
            session.setAttribute("user", user);

            request.setAttribute("message", "Đổi mật khẩu thành công");
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại");
        }
        request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
