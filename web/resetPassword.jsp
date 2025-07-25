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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f4f6f9;
            }
            .reset-container {
                max-width: 420px;
                margin: 120px auto 40px;
                padding: 2rem 2.5rem;
                background-color: #fff;
                border-radius: 16px;
                box-shadow: 0 0 24px rgba(79,70,229,0.08);
            }
            .reset-container h2 {
                font-weight: 700;
                color: #4f46e5;
                margin-bottom: 10px;
            }
            .form-label {
                font-weight: 500;
            }
            .input-group-text {
                background: #f4f6f9;
                border: none;
                color: #4f46e5;
            }
            .btn-reset {
                background: linear-gradient(90deg, #6366f1 0%, #60a5fa 100%);
                border: none;
                color: white;
                font-weight: 600;
                padding: 0.7rem;
                border-radius: 8px;
                width: 100%;
                transition: all 0.3s;
                font-size: 1.1rem;
            }
            .btn-reset:hover {
                background: linear-gradient(90deg, #60a5fa 0%, #6366f1 100%);
                box-shadow: 0 4px 16px rgba(79,70,229,0.12);
            }
            .alert, .error-msg {
                font-size: 1rem;
                text-align: center;
                color: red;
                margin-bottom: 10px;
            }
            .form-control {
                border-radius: 8px;
                border: 1px solid #ced4da;
                padding: 0.75rem 1rem;
            }
            .form-control:focus {
                border-color: #4f46e5;
                box-shadow: 0 0 0 0.18rem rgba(79,70,229,0.12);
            }
            .login-link {
                display: block;
                margin: 15px 0 0 0;
                color: #4f46e5;
                text-decoration: none;
                font-size: 0.95rem;
                text-align: center;
            }
            .login-link:hover {
                color: #4338ca;
                text-decoration: underline;
            }
            @media (max-width: 600px) {
                .reset-container { margin: 40px 8px; padding: 1.2rem 0.5rem; }
            }
        </style>
    </head>
    <body>
         <%@include file = "header.jsp" %>
        <div class="container">
            <div class="reset-container">
                <div class="text-center mb-4">
                    <i class="bi bi-lock-fill" style="font-size: 2.5rem; color: #4f46e5;"></i>
                    <h2 class="fw-bold mb-1">Reset Password</h2>
                    <p class="text-muted mb-0">Provide us with your registered email address</p>
                </div>
                <% if (request.getAttribute("error") != null) { %>
                    <div id="alertMsg" class="error-msg">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                <form id="resetForm" action="RequestResetPasswordServlet" method="GET" novalidate autocomplete="off">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                            <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" required>
                        </div>
                    </div>
                    <div class="d-grid mt-3">
                        <button type="submit" class="btn btn-reset btn-lg">Send Reset Link</button>
                    </div>
                    <a href="login" class="login-link">Want to back? Login</a>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Tự động ẩn alert sau 3s
            setTimeout(function() {
                var alertMsg = document.getElementById('alertMsg');
                if(alertMsg) alertMsg.style.display = 'none';
            }, 3000);
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
    </body>
</html>