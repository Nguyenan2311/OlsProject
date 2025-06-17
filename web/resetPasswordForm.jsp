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
                background-color: #ffffff;
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
                font-size: 18px;
                text-transform: uppercase;
            }

            input[type="password"],
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
                border-radius: 7px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s;
                text-transform: none;
            }

            .submit-btn:hover {
                background-color: #3a00c0;
            }

            hr {
                border: none;
                border-top: 1px solid #eee;
                margin: 30px 0;
            }
            
            .error-message {
                color: red;
                font-size: 14px;
                margin-top: 5px;
                display: none;
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

            <%-- Hiển thị thông báo lỗi --%>
            <% if (request.getAttribute("error") != null) { %>
                <div style="color: red; text-align: center; margin-bottom: 20px;">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="ResetPasswordServlet" method="POST" id="passwordForm">
                <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
                
                <div class="form-group">
                    <label for="newPassword">New password</label>
                    <input type="password" id="newPassword" name="newPassword" required 
                           oninput="validatePassword(this)">
                    <div id="passwordError" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm new password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required
                           oninput="checkPasswordMatch()">
                    <div id="confirmError" class="error-message"></div>
                </div>

                <button type="submit" class="submit-btn">Change Password</button>
            </form>
        </div>

        <script>
            function validatePassword(input) {
                const passwordError = document.getElementById("passwordError");
                const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
                
                if (input.value.length === 0) {
                    passwordError.style.display = "none";
                    input.setCustomValidity("");
                    return;
                }
                
                if (!regex.test(input.value)) {
                    passwordError.textContent = "Password must be at least 8 characters long and include uppercase, lowercase letters, and a number!";
                    passwordError.style.display = "block";
                    input.setCustomValidity("Invalid password");
                } else {
                    passwordError.style.display = "none";
                    input.setCustomValidity("");
                }
                
                checkPasswordMatch();
            }
            
            function checkPasswordMatch() {
                const password = document.getElementById("newPassword").value;
                const confirmPassword = document.getElementById("confirmPassword").value;
                const confirmError = document.getElementById("confirmError");
                
                if (confirmPassword.length === 0) {
                    confirmError.style.display = "none";
                    document.getElementById("confirmPassword").setCustomValidity("");
                    return;
                }
                
                if (password !== confirmPassword) {
                    confirmError.textContent = "Passwords do not match!";
                    confirmError.style.display = "block";
                    document.getElementById("confirmPassword").setCustomValidity("Passwords do not match");
                } else {
                    confirmError.style.display = "none";
                    document.getElementById("confirmPassword").setCustomValidity("");
                }
            }
            
            document.getElementById("passwordForm").addEventListener("submit", function(e) {
                const password = document.getElementById("newPassword").value;
                const confirmPassword = document.getElementById("confirmPassword").value;
                const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
                
                if (!regex.test(password)) {
                    e.preventDefault();
                    document.getElementById("passwordError").textContent = "Password must be at least 8 characters long and include uppercase, lowercase letters, and a number!";
                    document.getElementById("passwordError").style.display = "block";
                    document.getElementById("newPassword").focus();
                } else if (password !== confirmPassword) {
                    e.preventDefault();
                    document.getElementById("confirmError").textContent = "Passwords do not match!";
                    document.getElementById("confirmError").style.display = "block";
                    document.getElementById("confirmPassword").focus();
                }
            });
        </script>
    </body>
</html>