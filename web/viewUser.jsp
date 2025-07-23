<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Details</title>
    <style>
        .user-detail-container {
            max-width: 500px;
            margin: 30px auto;
            border: 1px solid #ccc;
            padding: 24px;
            border-radius: 8px;
            background: #fafbfc;
        }
        .avatar {
            width: 120px; height: 120px; border-radius: 50%; object-fit: cover; display: block; margin: 0 auto 20px auto;
        }
        .info-label { font-weight: bold; }
        .actions { margin-top: 24px; text-align: center; }
        .actions a, .actions button { margin: 0 8px; }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${not empty user}">
        <div class="user-detail-container">
            <h2>User Details</h2>
            <c:if test="${not empty user.image}">
                <img src="${user.image}" alt="Avatar" class="avatar" />
            </c:if>
            <c:if test="${empty user.image}">
                <img src="default-avatar.png" alt="Avatar" class="avatar" />
            </c:if>
            <div><span class="info-label">Full Name:</span> ${user.first_name} ${user.last_name}</div>
            <div><span class="info-label">Gender:</span> ${user.gender}</div>
            <div><span class="info-label">Email:</span> ${user.email}</div>
            <div><span class="info-label">Mobile:</span> ${user.phone}</div>
            <div><span class="info-label">Role:</span> ${user.roleName}</div>
            <div><span class="info-label">Address:</span> ${user.address}</div>
            <div><span class="info-label">Status:</span> <c:choose><c:when test="${user.status == 1}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose></div>
            <div class="actions">
                <a href="editUser.jsp?id=${user.id}">Edit</a>
                <a href="userManagement">Back to List</a>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="user-detail-container">
            <h2>User Not Found</h2>
            <div>User information could not be loaded.</div>
            <div class="actions">
                <a href="userManagement">Back to List</a>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
