<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sale Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Sale Dashboard</h1>
            <span>Welcome, <c:out value="${sessionScope.account.fullName}"/>! <a href="logout">Logout</a></span>
        </div>

        <%-- Hiển thị thông báo --%>
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success">${sessionScope.successMessage}</div>
            <% session.removeAttribute("successMessage"); %>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger">${sessionScope.errorMessage}</div>
            <% session.removeAttribute("errorMessage"); %>
        </c:if>

        <h2>Orders Management</h2>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Order Date</th>
                    <th>Customer Email</th>
                    <th>Course</th>
                    <th>Package</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>#${order.orderId}</td>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td>${order.customerEmail}</td>
                        <td>${order.courseName}</td>
                        <td>${order.packageName}</td>
                        <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="$"/></td>
                        <td>
                            <span class="badge ${order.paymentStatus == 'Completed' ? 'bg-success' : (order.paymentStatus == 'Pending' ? 'bg-warning' : 'bg-danger')}">
                                ${order.paymentStatus}
                            </span>
                        </td>
                        <td>
                            <%-- Chỉ hiển thị nút Confirm cho các đơn hàng Pending --%>
                            <c:if test="${order.paymentStatus == 'Pending'}">
                                <form action="confirm-payment" method="POST" onsubmit="return confirm('Are you sure you want to confirm payment for this order?');">
                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                    <button type="submit" class="btn btn-sm btn-primary">Confirm Payment</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>