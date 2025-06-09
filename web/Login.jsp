<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
     <%@include file="header.jsp" %>
    <div class="form">
        <h2>Login</h2>
        <p>Login to continue</p>
        <c:if test="${not empty errorMessage}">
                    <p style="color:red;" class="text-center">${errorMessage}</p>
        </c:if>
        <!-- Form với action="login" và method="POST" -->
        <form action="login" method="POST">
            <!-- Email Input -->
            <div class="input-group">
                <label>EMAIL</label>
                <input type="email" name="email" class="input-field" placeholder="Email" required oninvalid="this.setCustomValidity('Vui lòng nhập đúng định dạng email')">
            </div>
            
            <!-- Password Input -->
            <div class="input-group">
                <label>Password</label>
                <input type="password" name="password" class="input-field" placeholder="Password" required>
            </div>
            
            <!-- Links -->
            <div class="links">
                <a href="#" class="link">You don’t have account? sign up</a>
                <a href="RequestResetPasswordServlet" class="link">Foget password</a>
            </div>
            
            <!-- Login Button -->
            <button type="submit" class="login-button">Login</button>
        </form>
    </div>
</body>
</html>