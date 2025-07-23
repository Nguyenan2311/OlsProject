<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="com.vnpay.common.Config"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>KẾT QUẢ THANH TOÁN</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            :root {
                --primary-color: #00B14F;
                --secondary-color: #2D4271;
                --success-color: #28a745;
                --danger-color: #dc3545;
                --warning-color: #ffc107;
                --light-bg: #f8f9fa;
                --border-color: #dee2e6;
            }
            
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f5f7fa;
                color: #333;
                line-height: 1.6;
            }
            
            .payment-container {
                max-width: 800px;
                margin: 40px auto;
                background: white;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }
            
            .payment-header {
                background: linear-gradient(135deg, var(--secondary-color), var(--primary-color));
                color: white;
                padding: 20px;
                text-align: center;
                position: relative;
            }
            
            .payment-header h3 {
                margin: 0;
                font-weight: 500;
            }
            
            .payment-body {
                padding: 30px;
            }
            
            .payment-detail {
                display: flex;
                justify-content: space-between;
                padding: 12px 0;
                border-bottom: 1px solid var(--border-color);
            }
            
            .payment-detail:last-child {
                border-bottom: none;
            }
            
            .detail-label {
                font-weight: 500;
                color: var(--secondary-color);
            }
            
            .detail-value {
                text-align: right;
                font-weight: 400;
            }
            
            .status-success {
                color: var(--success-color);
                font-weight: 500;
            }
            
            .status-failure {
                color: var(--danger-color);
                font-weight: 500;
            }
            
            .status-invalid {
                color: var(--warning-color);
                font-weight: 500;
            }
            
            .payment-footer {
                text-align: center;
                padding: 20px;
                background-color: var(--light-bg);
                color: #6c757d;
                font-size: 0.9rem;
            }
            
            .amount-value {
                font-weight: 600;
                color: var(--primary-color);
            }
            
            .transaction-id {
                font-family: monospace;
                background-color: var(--light-bg);
                padding: 2px 5px;
                border-radius: 3px;
            }
            
            .action-buttons {
                display: flex;
                justify-content: center;
                gap: 15px;
                margin-top: 30px;
                flex-wrap: wrap;
            }
            
            .btn-back-to-cart {
                background-color: var(--secondary-color);
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                font-weight: 500;
                transition: all 0.3s;
            }
            
            .btn-back-to-cart:hover {
                background-color: #1a2c50;
                color: white;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            
            .btn-home {
                background-color: var(--primary-color);
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                font-weight: 500;
                transition: all 0.3s;
            }
            
            .btn-home:hover {
                background-color: #008a3e;
                color: white;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            
            @media (max-width: 576px) {
                .payment-container {
                    margin: 20px 10px;
                }
                
                .payment-body {
                    padding: 20px 15px;
                }
                
                .payment-detail {
                    flex-direction: column;
                }
                
                .detail-value {
                    text-align: left;
                    margin-top: 5px;
                }
            }
        </style>
    </head>
    <body>
        <%
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            
            // Format amount
            String amountStr = request.getParameter("vnp_Amount");
            long amount = amountStr != null ? Long.parseLong(amountStr) : 0;
            String formattedAmount = String.format("%,d VND", amount/100);
            
            // Format date
            String payDate = request.getParameter("vnp_PayDate");
            String formattedDate = "";
            if (payDate != null && payDate.length() >= 14) {
                formattedDate = payDate.substring(6, 8) + "/" + 
                               payDate.substring(4, 6) + "/" + 
                               payDate.substring(0, 4) + " " + 
                               payDate.substring(8, 10) + ":" + 
                               payDate.substring(10, 12) + ":" + 
                               payDate.substring(12, 14);
            }
            
            // Determine transaction status
            String transactionStatus = "";
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                    transactionStatus = "success";
                } else {
                    transactionStatus = "failure";
                }
            } else {
                transactionStatus = "invalid";
            }
        %>

        <div class="payment-container">
            <div class="payment-header">
                <h3>KẾT QUẢ THANH TOÁN</h3>
            </div>
            
            <div class="payment-body">
                <div class="payment-detail">
                    <span class="detail-label">Mã giao dịch thanh toán:</span>
                    <span class="detail-value transaction-id"><%=request.getParameter("vnp_TxnRef")%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Số tiền:</span>
                    <span class="detail-value amount-value"><%=formattedAmount%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Mô tả giao dịch:</span>
                    <span class="detail-value"><%=request.getParameter("vnp_OrderInfo")%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Mã lỗi thanh toán:</span>
                    <span class="detail-value"><%=request.getParameter("vnp_ResponseCode")%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Mã giao dịch tại CTT VNPAY-QR:</span>
                    <span class="detail-value transaction-id"><%=request.getParameter("vnp_TransactionNo")%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Mã ngân hàng thanh toán:</span>
                    <span class="detail-value"><%=request.getParameter("vnp_BankCode")%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Thời gian thanh toán:</span>
                    <span class="detail-value"><%=formattedDate%></span>
                </div>
                
                <div class="payment-detail">
                    <span class="detail-label">Tình trạng giao dịch:</span>
                    <span class="detail-value">
                        <% if (transactionStatus.equals("success")) { %>
                            <span class="status-success">Thành công</span>
                        <% } else if (transactionStatus.equals("failure")) { %>
                            <span class="status-failure">Không thành công</span>
                        <% } else { %>
                            <span class="status-invalid">Chữ ký không hợp lệ</span>
                        <% } %>
                    </span>
                </div>
                
                <div class="action-buttons">
                    <% if (transactionStatus.equals("failure")) { %>
                        <a href="course-detail?id=${reg.courseId}&action=summit" class="btn-back-to-cart">
                            <i class="fas fa-shopping-cart"></i> Quay lại đăng kí
                        </a>
                    <% } %>
                    <a href="home" class="btn-home">
                        <i class="fas fa-home"></i> Trang chủ
                    </a>
                </div>
            </div>
            
            <div class="payment-footer">
                <p>&copy; VNPAY <%= java.time.Year.now().getValue() %></p>
            </div>
        </div>
        
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>