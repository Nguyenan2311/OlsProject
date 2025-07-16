/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.media;

import DAO.UserMediaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author An_PC
 */
@MultipartConfig
@WebServlet(name = "AddMedia", urlPatterns = {"/addmedia"})
public class AddMedia extends HttpServlet {

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
        request.getRequestDispatcher("AddMedia.jsp").forward(request, response);
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
        String userId = request.getParameter("userId");
        String description = request.getParameter("description");
        Part part = request.getPart("mediaFile");
        if (part == null || part.getSize() == 0) {
            request.setAttribute("error", "Vui lòng chọn ảnh hoặc video để cập nhật.");
            request.setAttribute("userId", userId); // nếu bạn dùng lại trong JSP
            request.getRequestDispatcher("AddMedia.jsp").forward(request, response);
            return;
        }

        // Lấy tên file gốc
        String fileName = new File(part.getSubmittedFileName()).getName();

        String uploadPath = "D:\\Fu-Learning\\Summer25\\SWP391\\OlsProject\\web\\uploads";

        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Ghi file vào web/uploads
        String filePath = uploadPath + File.separator + fileName;
        part.write(filePath);

        // Đường dẫn tương đối để hiển thị (VD: <img src="uploads/abc.jpg">)
        String mediaPath = "uploads/" + fileName;
        String mediaType = fileName.endsWith(".mp4") ? "video" : "image";
        UserMediaDAO userMediaDAO = new UserMediaDAO();
        userMediaDAO.addMedia(userId, mediaPath, mediaType, description);
        response.sendRedirect("userprofile");
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
