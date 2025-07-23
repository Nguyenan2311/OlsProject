<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit User</title>
    <style>
        .form-container { max-width: 500px; margin: 30px auto; border: 1px solid #ccc; padding: 24px; border-radius: 8px; background: #fafbfc; }
        .form-group { margin-bottom: 16px; }
        label { display: block; font-weight: bold; margin-bottom: 4px; }
        input, select { width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #bbb; }
        .readonly { background: #f2f2f2; }
        .actions { text-align: center; margin-top: 20px; }
        .actions button, .actions a { margin: 0 8px; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Edit User</h2>
    <form method="post" action="editUser">
        <input type="hidden" name="id" value="${user.id}" />
        <div class="form-group">
            <label>Full Name</label>
            <input type="text" value="${user.first_name} ${user.last_name}" readonly class="readonly" />
        </div>
        <div class="form-group">
            <label>Gender</label>
            <input type="text" value="${user.gender}" readonly class="readonly" />
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="text" value="${user.email}" readonly class="readonly" />
        </div>
        <div class="form-group">
            <label>Mobile</label>
            <input type="text" value="${user.phone}" readonly class="readonly" />
        </div>
        <div class="form-group">
            <label>Role</label>
            <select name="role_id" required>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}" ${role.id == user.role_id ? 'selected' : ''}>${role.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Address</label>
            <input type="text" value="${user.address}" readonly class="readonly" />
        </div>
        <div class="form-group">
            <label>Status</label>
            <select name="status" required>
                <option value="1" ${user.status == 1 ? 'selected' : ''}>Active</option>
                <option value="0" ${user.status == 0 ? 'selected' : ''}>Inactive</option>
            </select>
        </div>
        <div class="actions">
            <button type="submit">Update</button>
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
