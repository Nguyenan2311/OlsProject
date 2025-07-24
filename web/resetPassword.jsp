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
         <%@include file = "header.jsp" %>
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
                <a href="login" class="login-link">Want to back? Login</a>
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