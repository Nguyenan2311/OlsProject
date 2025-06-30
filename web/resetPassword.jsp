<%-- 
    Document   : ResetPassword
    Created on : May 26, 2025, 10:10:28 PM
    Author     : tuana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EDEMY - Reset Password</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Arial', sans-serif;
            }

            body {
                background-color: ffffff;
                color: #333;
            }

            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 20px 50px;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }



            .logo {
                height: 45px; /* Chiều cao cố định */
                display: flex;
                align-items: center;
            }
            .logo img {
                height: 200%; /* Chiếm toàn bộ chiều cao của container */
                width: auto; /* Giữ tỷ lệ gốc của ảnh */
                max-width: 200px; /* Giới hạn chiều rộng tối đa */
                object-fit: contain; /* Đảm bảo ảnh không bị biến dạng */
            }

            .nav-links {
                display: flex;
                gap: 30px;
                margin-left: 20px;
                margin-right: auto; /* Đẩy phần còn lại sang phải */
            }

            .nav-links a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
                font-size: 25px;
            }

            .right-section {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .right-section search-bar  {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .search-bar input {
                padding: 8px 15px;
                border: 1px solid #ddd;
                border-radius: 20px;
                outline: none;
                width: 200px;
            }
            input[type="text"] {
                width: 800px;       /* chiều rộng */
                height: 50px;       /* chiều cao */
                padding: 8px 12px;  /* khoảng cách bên trong */
                font-size: 16px;    /* cỡ chữ */
                border-radius: 20px; /* bo góc */
                border: 1px solid #ccc;
            }

            .signup-btn {
                padding: 8px 20px;
                background-color: #4a00e0;
                color: white;
                border: none;
                border-radius: 4px;
                font-weight: bold;
                cursor: pointer;
                width: 150px;       /* chiều rộng */
                height: 50px;
                border-radius:10px; /* bo góc */
            }

            .container {
                max-width: 800px;
                margin: 50px auto;
                padding: 40px;
                background-color: ffffff;



            }
            .container p {
                color: #666;
                margin-bottom: 2px;
                text-align: center;
            }

            h2 {

                text-align: center;
                margin-bottom: 30px;
                color: #333;
                font-size: 60px
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: 500;
            }

            input[type="email"] {
                width: 100%;
                padding: 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
            }


            .login-link {
                display: block;
                margin: 15px 0;
                color: #4a00e0;
                text-decoration: none;
                font-size: 14px;
                align-content: center;
                text-align: center;
            }

            .login-link:hover {
                color: #3a00c0;
                text-decoration: underline;
            }

            .submit-btn {
                width: 100%;
                padding: 12px;
                background-color: #4a00e0;
                color: white;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .submit-btn:hover {
                background-color: #3a00c0;
            }

            hr {
                border: none;
                border-top: 1px solid #eee;
                margin: 30px 0;
            }
        </style>
    </head>
    <body>
        <nav class="navbar">
            <div class="logo">
                <img src="img/logo.png" alt="alt"/>
            </div>
            <div class="nav-links">
                <a href="#">Home</a>
                <a href="#">Courses</a>
                <a href="#">Blog</a>
                <a href="#">About</a>
            </div>
            <div class="right-section">
                <div class="search-bar">
                    <input type="text" placeholder="Search courses...">
                </div>
                <button class="signup-btn">Sign Up</button>
            </div>
        </nav>
        <div class="container">
            <h2>RESET PASSWORD</h2>
            <p>Provide us with your registered email address</p>

            <%-- Hiển thị thông báo lỗi --%>
            <% if (request.getAttribute("error") != null) { %>
            <div style="color: red; text-align: center; margin-bottom: 20px;">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>

            <form id="resetForm" action="RequestResetPasswordServlet" method="GET" novalidate>
                <div class="form-group">
                    <input type="text" id="email" name="email" placeholder="✉ Email">
                </div>
                <button type="submit" class="submit-btn">Send Reset Link</button>
                <a href="login.jsp" class="login-link">Want to back? Login</a>
            </form>

            <script>
                document.getElementById("resetForm").addEventListener("submit", function (e) {
                    var email = document.getElementById("email").value.trim();
                    var emailPattern = /^[a-zA-Z0-9._]+@[a-zA-Z0-9._]+\.[a-zA-Z]{2,}$/;

                    if (email === "") {
                        alert("Please input Email");
                        e.preventDefault();
                    } else if (!emailPattern.test(email)) {
                        alert("Invalid email. Please enter correct format.");
                        e.preventDefault();
                    }
                });
            </script>


        </div>
    </body>
</html>