<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add New User</title>
    <style>
        .form-container { max-width: 500px; margin: 30px auto; border: 1px solid #ccc; padding: 24px; border-radius: 8px; background: #fafbfc; }
        .form-group { margin-bottom: 16px; }
        label { display: block; font-weight: bold; margin-bottom: 4px; }
        input, select { width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #bbb; }
        .actions { text-align: center; margin-top: 20px; }
        .actions button, .actions a { margin: 0 8px; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Add New User</h2>
    <form method="post" action="addUser">
        <div class="form-group">
            <label>First Name</label>
            <input type="text" name="first_name" required />
        </div>
        <div class="form-group">
            <label>Last Name</label>
            <input type="text" name="last_name" required />
        </div>
        <div class="form-group">
            <label>Gender</label>
            <select name="gender" required>
                <option value="">Select...</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required />
        </div>
        <div class="form-group">
            <label>Mobile</label>
            <input type="text" name="phone" required />
        </div>
        <div class="form-group">
            <label>Role</label>
            <select name="role_id" required>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Address</label>
            <input type="text" name="address" />
        </div>
        <div class="form-group">
            <label>Status</label>
            <select name="status" required>
                <option value="1">Active</option>
                <option value="0">Inactive</option>
            </select>
        </div>
        <div class="actions">
            <button type="submit">Add User</button>
            <a href="userManagement">Cancel</a>
        </div>
    </form>
    <c:if test="${not empty message}">
        <div class="success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
</div>
</body>
</html>
