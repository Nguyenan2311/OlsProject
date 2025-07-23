<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    
    <!-- Custom CSS -->
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f4f6f9;
        }

        .login-container {
            max-width: 420px;
            margin: 120px auto 40px;
            padding: 2rem 2.5rem;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
        }

        .login-container h2 {
            font-weight: 600;
            margin-bottom: 10px;
        }

        .form-label {
            font-weight: 500;
        }

        .login-button {
            background-color: #4f46e5;
            border: none;
            color: white;
            font-weight: 500;
            padding: 0.6rem;
            border-radius: 8px;
            width: 100%;
            transition: all 0.3s ease;
        }

        .login-button:hover {
            background-color: #4338ca;
        }

        .links a {
            text-decoration: none;
            font-size: 0.9rem;
            color: #4f46e5;
        }

        .links a:hover {
            text-decoration: underline;
        }

        .error-msg {
            color: red;
            font-size: 0.9rem;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <%@include file="header.jsp" %>

    <div class="container">
        <div class="login-container">
            <h2 class="text-center mb-2">Đăng nhập</h2>
            <p class="text-center text-muted mb-4">Vui lòng nhập thông tin để tiếp tục</p>

            <c:if test="${not empty errorMessage}">
                <div class="error-msg">${errorMessage}</div>
            </c:if>

            <form action="login" method="POST" novalidate>
                <!-- Email -->
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="text" name="email" class="form-control" placeholder="Nhập email..." required>
                </div>

                <!-- Password -->
                <div class="mb-3">
                    <label class="form-label">Mật khẩu</label>
                    <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu..." required>
                </div>

                <!-- Submit Button -->
                <div class="d-grid mb-3">
                    <button type="submit" class="login-button">Đăng nhập</button>
                </div>

                <!-- Links -->
                <div class="d-flex justify-content-between links">
                    <a href="register">Bạn chưa có tài khoản?</a>
                    <a href="RequestResetPasswordServlet">Quên mật khẩu?</a>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
