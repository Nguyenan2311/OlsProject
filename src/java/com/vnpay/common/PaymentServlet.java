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
import java.util.regex.Pattern;
import model.CourseDetail;
import service.CourseService;

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
                Map<String, String> errors = new HashMap<>();

                // --- Bắt đầu Validation ---
                if (!isEmailValid(email)) {
                    errors.put("email", "Please enter a valid form email address ex: abc@gmail.com");
                } else if (userDAO.isEmailExists(email)) {
                    errors.put("email", "This email is already registered. Please <a href='login.jsp' class='alert-link'>login</a>.");
                }

                if (fullName == null || fullName.trim().isEmpty()) {
                    errors.put("fullName", "Full Name is required.");
                }
                // --- Kết thúc Validation ---
                if (mobile == null || mobile.trim().isEmpty()) {
                    errors.put("mobile", "Phone Number is required.");
                }
                if (!errors.isEmpty()) {
                    // Nếu có lỗi, forward lại trang và hiển thị lỗi
                    req.setAttribute("validationErrors", errors);
                    req.setAttribute("submittedFullName", fullName);
                    req.setAttribute("submittedEmail", email);
                    req.setAttribute("submittedMobile", mobile);
                    req.setAttribute("submittedGender", gender);
                    req.setAttribute("openModal", true); // Tín hiệu để tự động mở modal

                    // Load lại dữ liệu trang để hiển thị đúng
                    CourseService courseService = new CourseService();
                    CourseDetail courseDetail = courseService.getCourseDetails(courseId);
                    req.setAttribute("courseDetail", courseDetail);

                    // Load lại categories và taglines cho sidebar
                    List<String> categories = courseService.getCategories();
                    List<model.Tagline> taglines = courseService.getAllTaglines();
                    req.setAttribute("categories", categories);
                    req.setAttribute("taglines", taglines);

                    req.getRequestDispatcher("/courseDetail.jsp").forward(req, resp);
                    return;
                }

                // Nếu không có lỗi, tạo user mới
                String[] nameParts = NameUtils.splitFullName(fullName);
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                String randomPassword = UUID.randomUUID().toString().substring(0, 8);
                String hashedPassword = PasswordHasher.hash(randomPassword);
                PasswordResetService resetService = new PasswordResetService();

                User newUser = userDAO.createNewUser(firstName, lastName, email, mobile, gender, hashedPassword);
                if (newUser == null) {
                    throw new Exception("Failed to create a new user account.");
                }

                boolean emailSent = resetService.sendInitialSetPasswordEmail(newUser, req);
                user = newUser; // Gán user mới tạo để xử lý ở các bước tiếp theo
            }

            // --- Bước 2: Ghi danh khóa học với trạng thái "Pending" ---
            registrationDAO.enrollCourse(user.getId(), courseId, pricePackageId);
            System.out.println("DEBUG: User " + user.getId() + " has been enrolled in course " + courseId + " with 'Pending' status.");

            // --- Bước 3: Lấy thông tin giá và tạo/cập nhật đơn hàng trong CSDL ---
            PricePackage selectedPackage = courseDAO.getPricePackageById(pricePackageId);
            if (selectedPackage == null) {
                throw new Exception("Price package not found.");
            }
            double totalAmount = selectedPackage.getSalePrice();
            String orderInfo = courseId + "_Payment for " + selectedPackage.getTitle();

            Order existingOrder = orderDAO.findUnpaidOrderByUserAndCourse(user.getId(), courseId);
            Order orderToUse;
            if (existingOrder != null) {
                // Cập nhật order cũ
                orderDAO.updateOrder(existingOrder.getId(), totalAmount, orderInfo);
                orderDAO.updateOrderDetail(existingOrder.getId(), courseId, pricePackageId, selectedPackage.getPrice(), totalAmount);
                orderToUse = existingOrder;
            } else {
                // Tạo order mới
                orderToUse = orderDAO.createOrder(user.getId(), totalAmount, orderInfo);
                if (orderToUse == null) {
                    throw new Exception("Could not create the order.");
                }
                orderDAO.createOrderDetail(orderToUse.getId(), courseId, pricePackageId, selectedPackage.getPrice(), totalAmount);
            }

            // --- Bước 4: Chuẩn bị tham số và tạo URL thanh toán VNPAY ---
            String vnp_TxnRef = String.valueOf(orderToUse.getId());
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
     *
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

    // Hàm validate email
    private boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
