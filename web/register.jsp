<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - EDEMY</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', sans-serif; background-color: #ffffff; }
        .register-container {
            max-width: 500px;
            margin: 5rem auto;
            padding: 2rem;
        }
        .form-label {
            font-size: 0.8rem;
            color: #6c757d;
            font-weight: 500;
            text-transform: uppercase;
        }
        .form-control {
            border-radius: 8px;
            border: 1px solid #ced4da;
            padding: 0.75rem 1rem;
        }
        .form-control:focus {
            border-color: #0DDE94;
            box-shadow: 0 0 0 0.25rem rgba(13, 222, 148, 0.25);
        }
        .btn-verify {
            background-color: #0DDE94;
            border-color: #0DDE94;
            color: white;
            padding: 0.75rem;
            border-radius: 8px;
            font-weight: 600;
            width: 100%;
        }
        .btn-verify:hover {
            background-color: #0bb57a;
            border-color: #0bb57a;
        }
    </style>
</head>
<body>
    <%-- Có thể include header ở đây --%>
    <%-- <jsp:include page="/common/header.jsp" /> --%>

    <div class="register-container">
        <div class="text-center mb-4">
            <h1 class="h2 fw-bold">Register</h1>
            <p class="text-muted">Already Registered? <a href="login">Login</a></p>
        </div>

        <%-- Hiển thị thông báo thành công hoặc thất bại --%>
        <c:if test="${not empty requestScope.message}">
            <div class="alert ${requestScope.status == 'success' ? 'alert-success' : 'alert-danger'}">
                ${requestScope.message}
            </div>
        </c:if>

        <form action="register" method="POST">
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="fullName" name="fullName" value="<c:out value='${param.fullName}'/>" required>
            </div>
            <div class="mb-3">
                <label for="gender" class="form-label">Gender</label>
                <select class="form-select" id="gender" name="gender" required>
                    <option value="" disabled selected>Select Gender</option>
                    <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="tel" class="form-control" id="phone" name="phone" value="<c:out value='${param.phone}'/>" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="<c:out value='${param.email}'/>" required>
            </div>

            <div class="d-grid mt-4">
                <button type="submit" class="btn btn-verify">Verify</button>
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>