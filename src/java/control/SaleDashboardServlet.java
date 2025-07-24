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

        // Lấy các tham số filter từ request
        String status = request.getParameter("status");
        String email = request.getParameter("email");
        String courseName = request.getParameter("courseName");
        String orderId = request.getParameter("orderId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        
        try {
            OrderDAO orderDAO = new OrderDAO();
            List<SaleDashboardOrderDTO> orderList = orderDAO.getFilteredOrdersForSaleDashboard(status, email, courseName, orderId, fromDate, toDate);
            
            request.setAttribute("orderList", orderList);
            // Giữ lại filter để hiển thị lại trên form
            request.setAttribute("status", status);
            request.setAttribute("email", email);
            request.setAttribute("courseName", courseName);
            request.setAttribute("orderId", orderId);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.getRequestDispatcher("/sale_dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Chuyển đến trang lỗi
            request.setAttribute("errorMessage", "Could not load the sale dashboard.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("updateStatus".equals(action)) {
            String orderIdStr = request.getParameter("orderId");
            String newStatus = request.getParameter("newStatus");
            HttpSession session = request.getSession();
            try {
                int orderId = Integer.parseInt(orderIdStr);
                DAO.OrderDAO orderDAO = new DAO.OrderDAO();
                boolean updated = orderDAO.updateOrderStatusById(orderId, newStatus);
                if (updated) {
                    // Nếu trạng thái mới là Active hoặc Completed thì ghi danh hoặc cập nhật PersonalCourse
                    if ("Active".equalsIgnoreCase(newStatus) || "Pending".equalsIgnoreCase(newStatus) || "Processing".equalsIgnoreCase(newStatus)) {
                        model.Order order = orderDAO.getOrderById(orderId);
                        model.OrderDetail detail = orderDAO.getOrderDetailByOrderId(orderId);
                        if (order != null && detail != null) {
                            DAO.PersonalCourseDAO pcDao = new DAO.PersonalCourseDAO();
                            int statusInt = 0;
                            if ("Active".equalsIgnoreCase(newStatus)) statusInt = 1;
                            else if ("Pending".equalsIgnoreCase(newStatus)) statusInt = 2;
                            else if ("Processing".equalsIgnoreCase(newStatus)) statusInt = 0;
                            if (!pcDao.exists(order.getUserId(), detail.getCourseId())) {
                                pcDao.enrollCourse(order.getUserId(), detail.getCourseId(), detail.getPricePackageId());
                                pcDao.updateStatus(order.getUserId(), detail.getCourseId(), statusInt);
                            } else {
                                pcDao.updateStatus(order.getUserId(), detail.getCourseId(), statusInt);
                            }
                        }
                    }
                    session.setAttribute("successMessage", "Order #" + orderId + " status updated to " + newStatus + ".");
                } else {
                    session.setAttribute("errorMessage", "Failed to update order status.");
                }
            } catch (Exception e) {
                session.setAttribute("errorMessage", "Error: " + e.getMessage());
            }
            response.sendRedirect("sale-dashboard");
            return;
        }
        if ("deleteOrder".equals(action)) {
            String orderIdStr = request.getParameter("orderId");
            HttpSession session = request.getSession();
            try {
                int orderId = Integer.parseInt(orderIdStr);
                DAO.OrderDAO orderDAO = new DAO.OrderDAO();
                boolean deleted = orderDAO.deleteOrder(orderId);
                if (deleted) {
                    session.setAttribute("successMessage", "Order deleted successfully.");
                } else {
                    session.setAttribute("errorMessage", "Failed to delete order.");
                }
            } catch (Exception e) {
                session.setAttribute("errorMessage", "Error: " + e.getMessage());
            }
            response.sendRedirect("sale-dashboard");
            return;
        }
        // ... (các xử lý POST khác nếu có)
    }
}