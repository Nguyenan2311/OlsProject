<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <style>
        /* Add your CSS styles here */
    </style>
</head>
<body>
    <h1>User Management</h1>
    <form method="get" action="userManagement">
        Gender:
        <select name="gender">
            <option value="">All</option>
            <c:forEach var="g" items="${genders}">
                <option value="${g}" ${g == selectedGender ? 'selected' : ''}>${g}</option>
            </c:forEach>
        </select>
        Role:
        <select name="roleId">
            <option value="">All</option>
            <c:forEach var="role" items="${roles}">
                <option value="${role.id}" ${role.id == selectedRoleId ? 'selected' : ''}>${role.name}</option>
            </c:forEach>
        </select>
        Status:
        <select name="status">
            <option value="">All</option>
            <option value="1" ${selectedStatus == 1 ? 'selected' : ''}>Active</option>
            <option value="0" ${selectedStatus == 0 ? 'selected' : ''}>Inactive</option>
        </select>
        Search:
        <input type="text" name="search" value="${search}" placeholder="Full name, email, mobile" />
        <button type="submit">Filter/Search</button>
        <a href="userManagement">Reset</a>
        <a href="addUser">Add User</a>
    </form>
    <table border="1">
        <tr>
            <th><a href="?sortColumn=id&sortOrder=${sortOrder == 'asc' && sortColumn == 'id' ? 'desc' : 'asc'}">ID</a></th>
            <th><a href="?sortColumn=full_name&sortOrder=${sortOrder == 'asc' && sortColumn == 'full_name' ? 'desc' : 'asc'}">Full Name</a></th>
            <th><a href="?sortColumn=gender&sortOrder=${sortOrder == 'asc' && sortColumn == 'gender' ? 'desc' : 'asc'}">Gender</a></th>
            <th><a href="?sortColumn=email&sortOrder=${sortOrder == 'asc' && sortColumn == 'email' ? 'desc' : 'asc'}">Email</a></th>
            <th><a href="?sortColumn=phone&sortOrder=${sortOrder == 'asc' && sortColumn == 'phone' ? 'desc' : 'asc'}">Mobile</a></th>
            <th><a href="?sortColumn=role_id&sortOrder=${sortOrder == 'asc' && sortColumn == 'role_id' ? 'desc' : 'asc'}">Role</a></th>
            <th><a href="?sortColumn=status&sortOrder=${sortOrder == 'asc' && sortColumn == 'status' ? 'desc' : 'asc'}">Status</a></th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.first_name} ${user.last_name}</td>
                <td>${user.gender}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.roleName}</td>
                <td><c:choose><c:when test="${user.status == 1}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose></td>
                <td>
                    <a href="viewUser?id=${user.id}">View</a>
                    <a href="editUser?id=${user.id}">Edit</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div>
        <c:if test="${totalPages > 1}">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == page}">
                        <b>${i}</b>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>
