package DAO;


import context.DBContext;
import model.Order; // B?n c?n t?o l?p model n�y
import model.OrderDetail; // B?n c?n t?o l?p model n�y

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.SaleDashboardOrderDTO;

public class OrderDAO extends DBContext {

    /**
     * Creates a new order in the database with a 'Pending' status.
     * This is the first step before redirecting to the payment gateway.
     *
     * @param userId The ID of the user creating the order.
     * @param totalAmount The total amount for this order.
     * @param orderInfo A brief description of the order.
     * @return The newly created Order object, including its generated ID.
     * @throws Exception if the database operation fails.
     */
    public Order createOrder(int userId, double totalAmount, String orderInfo) throws Exception {
        String sql = "INSERT INTO Orders (user_id, total_amount, order_info, payment_status, payment_gateway) " +
                     "OUTPUT INSERTED.id, INSERTED.user_id, INSERTED.total_amount, INSERTED.order_info, " +
                     "INSERTED.payment_status, INSERTED.created_date " +
                     "VALUES (?, ?, ?, 'Pending', 'VNPAY')";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setDouble(2, totalAmount);
            stmt.setString(3, orderInfo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order newOrder = new Order();
                    newOrder.setId(rs.getInt("id"));
                    newOrder.setUserId(rs.getInt("user_id"));
                    newOrder.setTotalAmount(rs.getDouble("total_amount"));
                    newOrder.setOrderInfo(rs.getString("order_info"));
                    newOrder.setPaymentStatus(rs.getString("payment_status"));
                    newOrder.setCreatedDate(rs.getTimestamp("created_date"));
                    return newOrder;
                }
            }
        }
        return null; // Return null if order creation fails
    }

    /**
     * Creates a new order detail record, linking a product (course) to an order.
     *
     * @param orderId The ID of the parent order.
     * @param courseId The ID of the course being purchased.
     * @param pricePackageId The ID of the selected price package.
     * @param price The original price at the time of purchase.
     * @param salePrice The sale price at the time of purchase.
     * @return The newly created OrderDetail object.
     * @throws Exception if the database operation fails.
     */
    public OrderDetail createOrderDetail(int orderId, int courseId, int pricePackageId, double price, double salePrice) throws Exception {
        String sql = "INSERT INTO OrderDetail (order_id, course_id, price_package_id, price, sale_price) " +
                     "OUTPUT INSERTED.id VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, courseId);
            stmt.setInt(3, pricePackageId);
            stmt.setDouble(4, price);
            stmt.setDouble(5, salePrice);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setId(rs.getInt(1));
                    detail.setOrderId(orderId);
                    detail.setCourseId(courseId);
                   detail.setPricePackageId(pricePackageId);
                   detail.setPrice(price);
                   detail.setSalePrice(salePrice);
                    return detail;
                }
            }
        }
        return null;
    }

    /**
     * Updates the status of an order after receiving a notification from the payment gateway (IPN).
     *
     * @param orderId The ID of the order to update.
     * @param newStatus The new payment status (e.g., "Completed", "Failed").
     * @param transactionNo The transaction number from the payment gateway.
     * @return true if the update was successful, false otherwise.
     * @throws Exception if the database operation fails.
     */
    public boolean updateOrderStatus(int orderId, String newStatus, String transactionNo) throws Exception {
        String sql = "UPDATE Orders SET payment_status = ?, vnp_TransactionNo = ?, updated_date = GETDATE() WHERE id = ? AND payment_status = 'Pending'";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, transactionNo);
            stmt.setInt(3, orderId);

            int affectedRows = stmt.executeUpdate();
            // We check for "AND payment_status = 'Pending'" to prevent updating an already completed order.
            return affectedRows > 0;
        }
    }
    
    /**
     * Retrieves an order by its ID, typically used by the IPN servlet to find the order to update.
     *
     * @param orderId The ID of the order.
     * @return The Order object if found, otherwise null.
     * @throws Exception
     */
    public Order getOrderById(int orderId) throws Exception {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    // ... set other fields
                    return order;
                }
            }
        }
        return null;
    }

    /**
     * [MỚI] Lấy danh sách tất cả các đơn hàng để hiển thị trên dashboard của Sale.
     * @return Danh sách các DTO chứa thông tin tổng hợp của đơn hàng.
     * @throws Exception
     */
    public List<SaleDashboardOrderDTO> getAllOrdersForSaleDashboard() throws Exception {
        List<SaleDashboardOrderDTO> orderList = new ArrayList<>();
        String sql = "SELECT o.id AS orderId, o.created_date AS orderDate, u.email AS customerEmail, " +
                     "c.subtitle AS courseName, pp.title AS packageName, o.total_amount AS totalAmount, " +
                     "o.payment_status AS paymentStatus " +
                     "FROM Orders o " +
                     "JOIN [User] u ON o.user_id = u.id " +
                     "JOIN OrderDetail od ON o.id = od.order_id " +
                     "JOIN Course c ON od.course_id = c.id " +
                     "JOIN PricePackage pp ON od.price_package_id = pp.id " +
                     "ORDER BY o.created_date DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                SaleDashboardOrderDTO dto = new SaleDashboardOrderDTO();
                dto.setOrderId(rs.getInt("orderId"));
                dto.setOrderDate(rs.getTimestamp("orderDate"));
                dto.setCustomerEmail(rs.getString("customerEmail"));
                dto.setCourseName(rs.getString("courseName"));
                dto.setPackageName(rs.getString("packageName"));
                dto.setTotalAmount(rs.getDouble("totalAmount"));
                dto.setPaymentStatus(rs.getString("paymentStatus"));
                orderList.add(dto);
            }
        }
        return orderList;
    }

    /**
     * Lấy danh sách đơn hàng có filter cho Sale Dashboard
     */
    public List<SaleDashboardOrderDTO> getFilteredOrdersForSaleDashboard(String status, String email, String courseName, String orderId, String fromDate, String toDate) throws Exception {
        List<SaleDashboardOrderDTO> orderList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT o.id AS orderId, o.created_date AS orderDate, u.email AS customerEmail, " +
                "c.subtitle AS courseName, pp.title AS packageName, o.total_amount AS totalAmount, " +
                "o.payment_status AS paymentStatus " +
                "FROM Orders o " +
                "JOIN [User] u ON o.user_id = u.id " +
                "JOIN OrderDetail od ON o.id = od.order_id " +
                "JOIN Course c ON od.course_id = c.id " +
                "JOIN PricePackage pp ON od.price_package_id = pp.id WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.payment_status = ?");
            params.add(status);
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + email + "%");
        }
        if (courseName != null && !courseName.isEmpty()) {
            sql.append(" AND c.subtitle LIKE ?");
            params.add("%" + courseName + "%");
        }
        if (orderId != null && !orderId.isEmpty()) {
            sql.append(" AND o.id = ?");
            params.add(orderId);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND o.created_date >= ?");
            params.add(java.sql.Date.valueOf(fromDate));
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND o.created_date <= ?");
            params.add(java.sql.Date.valueOf(toDate));
        }
        sql.append(" ORDER BY o.created_date DESC");
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SaleDashboardOrderDTO dto = new SaleDashboardOrderDTO();
                    dto.setOrderId(rs.getInt("orderId"));
                    dto.setOrderDate(rs.getTimestamp("orderDate"));
                    dto.setCustomerEmail(rs.getString("customerEmail"));
                    dto.setCourseName(rs.getString("courseName"));
                    dto.setPackageName(rs.getString("packageName"));
                    dto.setTotalAmount(rs.getDouble("totalAmount"));
                    dto.setPaymentStatus(rs.getString("paymentStatus"));
                    orderList.add(dto);
                }
            }
        }
        return orderList;
    }

    /**
     * [MỚI] Lấy thông tin chi tiết của một đơn hàng để kích hoạt khóa học.
     * @param orderId ID của đơn hàng.
     * @return Đối tượng OrderDetail nếu tìm thấy.
     * @throws Exception
     */
    public OrderDetail getOrderDetailByOrderId(int orderId) throws Exception {
        String sql = "SELECT * FROM OrderDetail WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setId(rs.getInt("id"));
                    detail.setOrderId(rs.getInt("order_id"));
                    detail.setCourseId(rs.getInt("course_id"));
                    detail.setPricePackageId(rs.getInt("price_package_id"));
                    return detail;
                }
            }
        }
        return null;
    }

    /**
     * Cập nhật trạng thái đơn hàng theo orderId
     */
    public boolean updateOrderStatusById(int orderId, String newStatus) throws Exception {
        String sql = "UPDATE Orders SET payment_status = ?, updated_date = GETDATE() WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa đơn hàng và các chi tiết liên quan
    public boolean deleteOrder(int orderId) throws Exception {
        String sqlDetail = "DELETE FROM OrderDetail WHERE order_id = ?";
        String sqlOrder = "DELETE FROM Orders WHERE id = ?";
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement stmtOrder = conn.prepareStatement(sqlOrder)) {
                stmtDetail.setInt(1, orderId);
                stmtDetail.executeUpdate();
                stmtOrder.setInt(1, orderId);
                int affected = stmtOrder.executeUpdate();
                conn.commit();
                return affected > 0;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Tìm Order chưa thanh toán cho user và course (Pending hoặc Processing)
    public Order findUnpaidOrderByUserAndCourse(int userId, int courseId) throws Exception {
        String sql = "SELECT o.* FROM Orders o JOIN OrderDetail od ON o.id = od.order_id WHERE o.user_id = ? AND od.course_id = ? AND (o.payment_status = 'Pending' OR o.payment_status = 'Processing')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderInfo(rs.getString("order_info"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setCreatedDate(rs.getTimestamp("created_date"));
                    // ... set other fields nếu cần
                    return order;
                }
            }
        }
        return null;
    }

    // Cập nhật lại order cũ
    public void updateOrder(int orderId, double totalAmount, String orderInfo) throws Exception {
        String sql = "UPDATE Orders SET total_amount = ?, order_info = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, totalAmount);
            stmt.setString(2, orderInfo);
            stmt.setInt(3, orderId);
            stmt.executeUpdate();
        }
    }

    // Cập nhật lại order detail cũ
    public void updateOrderDetail(int orderId, int courseId, int pricePackageId, double price, double salePrice) throws Exception {
        String sql = "UPDATE OrderDetail SET price_package_id = ?, price = ?, sale_price = ? WHERE order_id = ? AND course_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pricePackageId);
            stmt.setDouble(2, price);
            stmt.setDouble(3, salePrice);
            stmt.setInt(4, orderId);
            stmt.setInt(5, courseId);
            stmt.executeUpdate();
        }
    }
}
