package control;


import DAO.OrderDAO;
import DAO.RegistrationDAO;
import model.Order;
import model.OrderDetail;
import model.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/confirm-payment")
public class ConfirmPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
       
        
        String orderIdStr = request.getParameter("orderId");
        
        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDAO orderDAO = new OrderDAO();
            RegistrationDAO regDAO = new RegistrationDAO();

            // L?y th�ng tin ??n h�ng ?? bi?t user_id
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) throw new Exception("Order not found.");

            // L?y chi ti?t ??n h�ng ?? bi?t course_id v� package_id
            OrderDetail detail = orderDAO.getOrderDetailByOrderId(orderId);
            if (detail == null) throw new Exception("Order detail not found.");

            // 1. C?p nh?t tr?ng th�i ??n h�ng th�nh "Completed"
            // Gi? s? giao d?ch n�y l� thanh to�n th? c�ng, kh�ng c� m� VNPAY
            boolean orderUpdated = orderDAO.updateOrderStatus(orderId, "Active", "Manual Confirmation");

            if (orderUpdated) {
                // 2. K�ch ho?t kh�a h?c cho ng??i d�ng
                // Ki?m tra xem h? ?� ??ng k� ch?a ?? tr�nh l?i
                
                    regDAO.activateCourseEnrollment(order.getUserId(), detail.getCourseId(), detail.getPricePackageId(), detail.getId());
                
                session.setAttribute("successMessage", "Order #" + orderId + " has been confirmed and the course is now active for the user.");
            } else {
                session.setAttribute("errorMessage", "Failed to confirm order #" + orderId + ". It might have been already processed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }
        
        response.sendRedirect("sale-dashboard");
    }
}