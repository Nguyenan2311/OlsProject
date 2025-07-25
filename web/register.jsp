<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - EDEMY</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f4f6f9;
        }
        .register-container {
            max-width: 420px;
            margin: 120px auto 40px;
            padding: 2rem 2.5rem;
            background-color: #fff;
            border-radius: 16px;
            box-shadow: 0 0 24px rgba(79,70,229,0.08);
        }
        .register-container h2 {
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
        .btn-register {
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
        .btn-register:hover {
            background: linear-gradient(90deg, #60a5fa 0%, #6366f1 100%);
            box-shadow: 0 4px 16px rgba(79,70,229,0.12);
        }
        .alert {
            font-size: 1rem;
            text-align: center;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 1px solid #ced4da;
            padding: 0.75rem 1rem;
        }
        .form-control:focus, .form-select:focus {
            border-color: #4f46e5;
            box-shadow: 0 0 0 0.18rem rgba(79,70,229,0.12);
        }
        @media (max-width: 600px) {
            .register-container { margin: 40px 8px; padding: 1.2rem 0.5rem; }
        }
    </style>
</head>
<body>
    <%@include file = "header.jsp" %>
    <div class="container">
        <div class="register-container">
            <div class="text-center mb-4">
                <div class="mb-2">
                    <i class="bi bi-person-circle" style="font-size: 3rem; color: #4f46e5;"></i>
                </div>
                <h2 class="fw-bold mb-1">Create Account</h2>
                <p class="text-muted mb-0">Join EDEMY to access all courses and features!</p>
            </div>
            <c:if test="${not empty requestScope.message}">
                <div id="alertMsg" class="alert ${requestScope.status == 'success' ? 'alert-success' : 'alert-danger'}">
                    ${requestScope.message}
                </div>
            </c:if>
            <form action="register" method="POST" novalidate autocomplete="off">
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                        <input type="text" class="form-control" id="fullName" name="fullName" value="<c:out value='${param.fullName}'/>" required placeholder="Your full name">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="gender" class="form-label">Gender</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-gender-ambiguous"></i></span>
                        <select class="form-select" id="gender" name="gender" required>
                            <option value="" disabled ${empty param.gender ? 'selected' : ''}>Select Gender</option>
                            <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Phone Number</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-telephone"></i></span>
                        <input type="tel" class="form-control" id="phone" name="phone" value="<c:out value='${param.phone}'/>" required placeholder="e.g. 0987654321">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                        <input type="email" class="form-control" id="email" name="email" value="<c:out value='${param.email}'/>" required placeholder="you@example.com">
                    </div>
                </div>
                <div class="d-grid mt-4">
                    <button type="submit" class="btn btn-register btn-lg">Register</button>
                </div>
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
    </script>
</body>
</html>