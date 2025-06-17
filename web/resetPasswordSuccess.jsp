<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EDEMY - Password Reset Success</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Arial', sans-serif;
            }

            body {
                background-color: #ffffff;
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
                margin-right: auto;
            }

            .nav-links a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
                font-size: 18px;
            }

            .right-section {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .search-bar input {
                padding: 8px 15px;
                border: 1px solid #ddd;
                border-radius: 20px;
                outline: none;
                width: 300px;
                height: 40px;
                font-size: 16px;
            }

            .signup-btn {
                padding: 8px 20px;
                background-color: #4a00e0;
                color: white;
                border: none;
                border-radius: 10px;
                font-weight: bold;
                cursor: pointer;
                width: 120px;
                height: 40px;
                font-size: 16px;
            }

            .container {
                max-width: 3000px;
                margin: 50px auto;
                padding: 50px;
                background-color: #ffffff;
                
                text-align: center;
            }

            .success-message {
                margin-bottom: 30px;
                margin-top: 50px;
                line-height: 1.6;
            }

            .success-message h2 {
                margin-bottom: 10px;
                color: #333;
                font-size: 50px;
            }

            .login-btn {
                display: inline-block;
                padding: 12px 30px;
                background-color: #4a00e0;
                color: white;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                text-decoration: none;
                transition: background-color 0.3s;
                margin-top: 20px;
            }

            .login-btn:hover {
                background-color: #3a00c0;
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
            <div class="success-message">
                <h2>Reset password successfully, please come to login</h2>
                
                <p2>Please login before go to course</p2>
            </div>

            <a href="login.html" class="login-btn">Login</a>
        </div>
    </body>
</html>