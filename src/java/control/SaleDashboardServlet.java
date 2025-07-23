package control;


import DAO.OrderDAO;
import model.SaleDashboardOrderDTO;
import model.User;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sale-dashboard")
public class SaleDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        
        
        try {
            OrderDAO orderDAO = new OrderDAO();
            List<SaleDashboardOrderDTO> orderList = orderDAO.getAllOrdersForSaleDashboard();
            
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("/sale_dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Chuy?n ??n trang l?i
            request.setAttribute("errorMessage", "Could not load the sale dashboard.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}