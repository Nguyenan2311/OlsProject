<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- ... giữ nguyên phần style như response trước ... -->
    <style>
        body { min-height: 100vh; background: linear-gradient(135deg, #70e1f5 0%, #ffd194 100%);
            display: flex; align-items: center; justify-content: center; font-family: 'Montserrat', sans-serif; }
        .changepass-card { background: #fff; border-radius: 2rem; box-shadow: 0 8px 40px rgba(22,90,143,0.18), 0 2.5px 10px 0 rgba(120,119,198,0.16); padding: 2.5rem 2.4rem 2rem 2.4rem; width: 390px; position: relative; animation: popin 0.7s cubic-bezier(.36,.07,.19,.97) both; }
        @keyframes popin { 0% { transform: scale(0.9) translateY(30px); opacity: 0; } 100% { transform: scale(1) translateY(0); opacity: 1; } }
        .changepass-card .circle-icon { width: 67px; height: 67px; background: linear-gradient(135deg, #3d8ce7 30%, #00ffcb 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; position: absolute; top: -35px; left: 50%; transform: translateX(-50%); box-shadow: 0 4px 24px #1ca7ec50; }
        .changepass-card h3 { margin-top: 50px; font-weight: bold; color: #228ba1; letter-spacing: 1px; }
        .changepass-card p { color: #757575; font-weight: 500; font-size: 1.02em; margin-bottom: 1.6rem; }
        .form-group label { color: #38475a; font-weight: 500; }
        .form-control { background: rgba(248,249,250, 0.94); border: 1px solid #a6d2ed; border-radius: 0.7rem; box-shadow: 0 1px 2px rgba(35,175,250,0.01); padding: 1rem; font-size: 1.01em; }
        .form-control:focus { border-color: #34ce57; box-shadow: 0 1px 6px #3d8ce745; }
        .btn-change { background: linear-gradient(87deg, #5ee7df 0%, #b490ca 100%); color: #fff; letter-spacing: 0.04rem; padding: 0.75rem 2.4rem; border: none; border-radius: 1.5rem; font-size: 1.14em; font-weight: 700; margin-top: 1rem; box-shadow: 0 2.5px 10px 0 #b490ca23; transition: background 300ms, box-shadow 200ms, transform 150ms; }
        .btn-change:hover, .btn-change:focus { background: linear-gradient(87deg, #9470e1 0%, #5ee7df 100%); color: #fff; transform: translateY(-2px) scale(1.03); box-shadow: 0 8px 20px -6px #9470e170; }
        .changepass-footer { text-align: center; color: #95a1b1; font-size: 0.94em; margin-top: 1.2rem;}
        .alert-danger { border-radius: 1.4rem; font-weight: 600; font-size:1em; }
        @media (max-width: 500px) {
            .changepass-card { padding: 1.4rem 0.8rem 1.2rem 0.8rem; width: 100%; min-width: 0; border-radius: 1.2rem;}
        }
    </style>
</head>
<body>
    <div class="changepass-card">
        <div class="circle-icon">
            <!-- SVG lock icon như bên trên -->
            <svg fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M12 17v1m-6 3h12a2 2 0 002-2v-7a2 2 0 00-2-2h-1V7a5 5 0 00-10 0v4H6a2 2 0 00-2 2v7a2 2 0 002 2zm6-8v4" />
            </svg>
        </div>
        <h3>Change Password</h3>
        <p>Protect your account by using a strong, unique password.</p>

        <%-- HIỂN THỊ LỖI (nếu có message lỗi từ backend truyền về) --%>
        <c:if test="${not empty eror}">
            <div class="alert alert-danger text-center mt-3 mb-3">${eror}</div>
        </c:if>
        <c:if test="${not empty eror2}">
            <div class="alert alert-danger text-center mt-3 mb-3">${eror2}</div>
        </c:if>
        <c:if test="${not empty mess}">
            <div class="alert alert-success text-center mt-3 mb-3">${mess}</div>
        </c:if>

        <form method="post" action="changepassword">
            <div class="form-group">
                <label for="current-password">Current Password</label>
                <input type="password" class="form-control" id="current-password" name="oldPassword"
                       placeholder="Enter current password" required>
            </div>
            <div class="form-group">
                <label for="new-password">New Password</label>
                <input type="password" class="form-control" id="new-password" name="newPassword"
                        placeholder="Enter new password" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">Confirm New Password</label>
                <input type="password" class="form-control" id="confirm-password" name="rePassword"
                        placeholder="Confirm new password" required>
            </div>
            <button type="submit" class="btn btn-change btn-block">Change Password</button>
        </form>
        <div class="changepass-footer">
            <span>Forgot your password? <a href="#" style="color:#6e88a2;text-decoration:underline;">Reset it</a></span>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>