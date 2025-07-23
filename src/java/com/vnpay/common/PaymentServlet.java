package com.vnpay.common;

import DAO.CourseDAO;
import DAO.OrderDAO;
import DAO.RegistrationDAO;
import DAO.UserDAO;
import model.NameUtils;
import model.Order;
import model.PasswordHasher;
import model.PricePackage;
import model.User;
import service.PasswordResetService;
import com.vnpay.common.Config; // Lớp Config chứa thông tin VNPAY

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/create-payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // Khởi tạo các DAO cần thiết
        UserDAO userDAO = new UserDAO();
        OrderDAO orderDAO = new OrderDAO();
        CourseDAO courseDAO = new CourseDAO();
        RegistrationDAO registrationDAO = new RegistrationDAO();

        String courseIdStr = req.getParameter("courseId");
        String redirectURL = "course-detail?id=" + courseIdStr;

        try {
            // --- Bước 1: Thu thập thông tin và xác định người dùng ---
            int courseId = Integer.parseInt(courseIdStr);
            int pricePackageId = Integer.parseInt(req.getParameter("pricePackageId"));
            User user = (User) session.getAttribute("user");

            // Nếu người dùng chưa đăng nhập, tiến hành tạo tài khoản mới
            if (user == null) {
                String fullName = req.getParameter("fullName");
                String email = req.getParameter("email");
                String mobile = req.getParameter("phone");
                String gender = req.getParameter("gender");

                // (Bạn có thể thêm logic validation ở đây và forward lại nếu có lỗi)
                if (userDAO.isEmailExists(email)) {
                    session.setAttribute("registrationStatus", "error");
                    session.setAttribute("registrationMessage", "This email is already registered. Please <a href='login' class='alert-link'>login</a> to continue.");
                    resp.sendRedirect(redirectURL);
                    return;
                }

                // Tạo tài khoản mới
                String[] nameParts = NameUtils.splitFullName(fullName);
                String temporaryPassword = UUID.randomUUID().toString();
                String hashedPassword = PasswordHasher.hash(temporaryPassword);

                User newUser = userDAO.createNewUser(nameParts[1], nameParts[0], email, mobile, gender, hashedPassword);
                if (newUser == null) {
                    throw new Exception("Could not create new user account.");
                }
                user = newUser; // Gán user mới tạo để sử dụng cho các bước tiếp theo

                // Gửi email chứa link thiết lập mật khẩu
                PasswordResetService emailService = new PasswordResetService();
                emailService.sendInitialSetPasswordEmail(newUser, req);
            }
            
            // --- Bước 2: Ghi danh khóa học với trạng thái "Pending" ---
            // Phương thức enrollCourse sẽ tự động xử lý việc ghi đè nếu cần
            registrationDAO.enrollCourse(user.getId(), courseId, pricePackageId);
            System.out.println("DEBUG: User " + user.getId() + " has been enrolled in course " + courseId + " with 'Pending' status.");

            // --- Bước 3: Lấy thông tin giá và tạo đơn hàng trong CSDL ---
            PricePackage selectedPackage = courseDAO.getPricePackageById(pricePackageId);
            if (selectedPackage == null) {
                throw new Exception("Price package not found.");
            }
            double totalAmount = selectedPackage.getSalePrice();
            String orderInfo = courseId + "_Payment for " + selectedPackage.getTitle();

            Order newOrder = orderDAO.createOrder(user.getId(), totalAmount, orderInfo);
            if (newOrder == null) {
                throw new Exception("Could not create the order.");
            }
            orderDAO.createOrderDetail(newOrder.getId(), courseId, pricePackageId, selectedPackage.getPrice(), totalAmount);

            // --- Bước 4: Chuẩn bị tham số và tạo URL thanh toán VNPAY ---
            String vnp_TxnRef = String.valueOf(newOrder.getId());
            long amount = (long) (totalAmount * 100000); // Nhân giá tiền với 100,000

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", orderInfo);
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", Config.getIpAddress(req));

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            String paymentUrl = buildVnpayUrl(vnp_Params);

            // --- Bước 5: Chuyển hướng người dùng đến VNPAY ---
            resp.sendRedirect(paymentUrl);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("registrationStatus", "error");
            session.setAttribute("registrationMessage", "An unexpected error occurred during the process. Please try again.");
            resp.sendRedirect(redirectURL);
        }
    }

    /**
     * Builds the VNPAY payment URL with a secure hash.
     * @param vnp_Params Map of parameters to be sent to VNPAY.
     * @return The final payment URL.
     * @throws IOException
     */
    private String buildVnpayUrl(Map<String, String> vnp_Params) throws IOException {
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return Config.vnp_PayUrl + "?" + queryUrl;
    }
}