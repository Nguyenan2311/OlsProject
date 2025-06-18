package control;

import DAO.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@MultipartConfig
@WebServlet("/editmedia")
public class EditMedia extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mediaId = request.getParameter("mediaId");
        String description = request.getParameter("description");
        Part part = request.getPart("mediaFile");

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
        String relativePath = "uploads/" + fileName;
        String mediaType = fileName.endsWith(".mp4") ? "video" : "image";

        // Cập nhật DB
        DAO dao = new DAO();
        dao.editMedia(mediaId, relativePath, mediaType, description);

        response.sendRedirect("userprofile");
    }
}
