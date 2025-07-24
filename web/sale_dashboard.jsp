<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sale Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(120deg, #f8fafc 0%, #e0e7ff 100%);
            min-height: 100vh;
        }
        .dashboard-card {
            border-radius: 1rem;
            box-shadow: 0 4px 24px rgba(0,0,0,0.07);
            padding: 1.5rem 1rem;
            margin-bottom: 1rem;
            transition: box-shadow 0.2s;
        }
        .dashboard-card .icon {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }
        .dashboard-card .card-title {
            font-size: 1.1rem;
            font-weight: 600;
        }
        .dashboard-card .card-text {
            font-size: 2rem;
        }
        .table {
            border-radius: 1rem;
            overflow: hidden;
            box-shadow: 0 2px 12px rgba(0,0,0,0.06);
        }
        .table-hover tbody tr:hover {
            background: #f1f5f9;
        }
        .form-filter .input-group-text {
            background: #e0e7ff;
            border: none;
        }
        .form-filter .form-control, .form-filter .form-select {
            border-radius: 0.5rem;
        }
        .form-filter .btn-primary {
            background: linear-gradient(90deg, #6366f1 0%, #60a5fa 100%);
            border: none;
            border-radius: 0.5rem;
        }
        .form-filter .btn-secondary {
            border-radius: 0.5rem;
        }
        @media (max-width: 768px) {
            .dashboard-card .card-text { font-size: 1.2rem; }
            .dashboard-card .icon { font-size: 1.5rem; }
            .table th, .table td { font-size: 0.9rem; }
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Sale Dashboard</h1>
            <span>Welcome, <c:out value="${sessionScope.user.first_name}"/>! <a href="logout">Logout</a></span>
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

        <!-- Box thống kê tổng quan -->
        <div class="row mb-4 g-3">
            <div class="col">
                <div class="dashboard-card text-bg-primary text-center">
                    <div class="icon"><i class="bi bi-collection"></i></div>
                    <div class="card-title">Total Orders</div>
                    <div class="card-text">
                        <c:out value="${fn:length(orderList)}"/>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="dashboard-card text-bg-warning text-center">
                    <div class="icon"><i class="bi bi-hourglass-split"></i></div>
                    <div class="card-title">Pending</div>
                    <div class="card-text">
                        <c:set var="pendingCount" value="0"/>
                        <c:forEach var="order" items="${orderList}">
                            <c:if test="${order.paymentStatus == 'Pending'}">
                                <c:set var="pendingCount" value="${pendingCount + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:out value="${pendingCount}"/>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="dashboard-card text-bg-success text-center">
                    <div class="icon"><i class="bi bi-check-circle-fill"></i></div>
                    <div class="card-title">Processing</div>
                    <div class="card-text">
                        <c:set var="completedCount" value="0"/>
                        <c:forEach var="order" items="${orderList}">
                            <c:if test="${order.paymentStatus == 'Processing'}">
                                <c:set var="completedCount" value="${completedCount + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:out value="${completedCount}"/>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="dashboard-card text-bg-danger text-center">
                    <div class="icon"><i class="bi bi-x-circle-fill"></i></div>
                    <div class="card-title">Processing</div>
                    <div class="card-text">
                        <c:set var="failedCount" value="0"/>
                        <c:forEach var="order" items="${orderList}">
                            <c:if test="${order.paymentStatus == 'Processing'}">
                                <c:set var="failedCount" value="${failedCount + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:out value="${failedCount}"/>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="dashboard-card text-bg-info text-center">
                    <div class="icon"><i class="bi bi-lightning-fill"></i></div>
                    <div class="card-title">Active</div>
                    <div class="card-text">
                        <c:set var="activeCount" value="0"/>
                        <c:forEach var="order" items="${orderList}">
                            <c:if test="${order.paymentStatus == 'Active'}">
                                <c:set var="activeCount" value="${activeCount + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:out value="${activeCount}"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- Biểu đồ trạng thái đơn hàng -->
        <div class="row mb-4">
            <div class="col-12 col-md-6 mx-auto">
                <canvas id="orderStatusChart"></canvas>
            </div>
        </div>
        <!-- Kết thúc box thống kê -->

        <h2>Orders Management</h2>
        <!-- Bộ lọc tìm kiếm -->
        <form class="row g-3 mb-4 form-filter" method="get" action="sale-dashboard">
            <div class="col-md-2">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-hash"></i></span>
                    <input type="text" class="form-control" name="orderId" placeholder="Order ID" value="${orderId != null ? orderId : ''}">
                </div>
            </div>
            <div class="col-md-2">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                    <input type="text" class="form-control" name="email" placeholder="Customer Email" value="${email != null ? email : ''}">
                </div>
            </div>
            <div class="col-md-2">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-book"></i></span>
                    <input type="text" class="form-control" name="courseName" placeholder="Course Name" value="${courseName != null ? courseName : ''}">
                </div>
            </div>
            <div class="col-md-2">
                <select class="form-select" name="status">
                    <option value="">All Status</option>
                    <option value="Pending" ${status == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Active" ${status == 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Processing" ${status == 'Processing' ? 'selected' : ''}>Processing</option>
                </select>
            </div>
            <div class="col-md-2">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                    <input type="date" class="form-control" name="fromDate" value="${fromDate != null ? fromDate : ''}">
                </div>
            </div>
            <div class="col-md-2">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-calendar2"></i></span>
                    <input type="date" class="form-control" name="toDate" value="${toDate != null ? toDate : ''}">
                </div>
            </div>
            <div class="col-12 d-flex justify-content-end">
                <button type="submit" class="btn btn-primary px-4"> <i class="bi bi-search"></i> Filter</button>
                <a href="sale-dashboard" class="btn btn-secondary ms-2 px-4">Reset</a>
            </div>
        </form>
        <!-- Kết thúc bộ lọc -->
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
                            <span class="badge d-inline-flex align-items-center ${order.paymentStatus == 'Processing' ? 'bg-success' : (order.paymentStatus == 'Pending' ? 'bg-warning text-dark' : (order.paymentStatus == 'Active' ? 'bg-info text-dark' : 'bg-danger'))}">
                                <c:choose>
                                    <c:when test="${order.paymentStatus == 'Processing'}">
                                        <i class="bi bi-check-circle-fill me-1"></i>
                                    </c:when>
                                    <c:when test="${order.paymentStatus == 'Pending'}">
                                        <i class="bi bi-hourglass-split me-1"></i>
                                    </c:when>
                                    <c:when test="${order.paymentStatus == 'Active'}">
                                        <i class="bi bi-lightning-fill me-1"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="bi bi-x-circle-fill me-1"></i>
                                    </c:otherwise>
                                </c:choose>
                                ${order.paymentStatus}
                            </span>
                        </td>
                        <td>
                            <form action="sale-dashboard" method="post" class="d-flex align-items-center gap-2">
                                <input type="hidden" name="action" value="updateStatus" />
                                <input type="hidden" name="orderId" value="${order.orderId}" />
                                <select name="newStatus" class="form-select form-select-sm status-select" style="width: 120px;">
                                    <option value="Pending" ${order.paymentStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="Active" ${order.paymentStatus == 'Active' ? 'selected' : ''}>Active</option>
                                    <option value="Processing" ${order.paymentStatus == 'Processing' ? 'selected' : ''}>Processing</option>
                                </select>
                                <button type="submit" class="btn btn-sm btn-outline-primary update-btn" ${order.paymentStatus == order.paymentStatus ? 'disabled' : ''}>
                                    <i class="bi bi-arrow-repeat"></i> Update
                                </button>
                            </form>
                            <form action="sale-dashboard" method="post" style="display:inline-block;" onsubmit="return confirm('Are you sure you want to delete this order?');">
                                <input type="hidden" name="action" value="deleteOrder" />
                                <input type="hidden" name="orderId" value="${order.orderId}" />
                                <button type="submit" class="btn btn-sm btn-outline-danger ms-1"><i class="bi bi-trash"></i> Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Tự động ẩn alert sau 4 giây
        window.addEventListener('DOMContentLoaded', function() {
            setTimeout(function() {
                document.querySelectorAll('.alert').forEach(function(alert) {
                    alert.classList.add('fade');
                    setTimeout(function() { alert.style.display = 'none'; }, 500);
                });
            }, 4000);

            // Enable/disable nút Update theo trạng thái dropdown
            document.querySelectorAll('tr').forEach(function(row) {
                var select = row.querySelector('.status-select');
                var btn = row.querySelector('.update-btn');
                if (select && btn) {
                    var current = select.value;
                    select.addEventListener('change', function() {
                        btn.disabled = (select.value === current);
                    });
                    // Khởi tạo trạng thái ban đầu
                    btn.disabled = true;
                }
            });

            // Biểu đồ trạng thái đơn hàng
            var ctx = document.getElementById('orderStatusChart').getContext('2d');
            var chart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ['Pending', 'Active', 'Processing'],
                    datasets: [{
                        data: [
                            Number(document.querySelector('.dashboard-card.text-bg-warning .card-text').textContent),
                            Number(document.querySelector('.dashboard-card.text-bg-info .card-text').textContent),
                            Number(document.querySelector('.dashboard-card.text-bg-success .card-text').textContent),
                            Number(document.querySelector('.dashboard-card.text-bg-danger .card-text').textContent)
                        ],
                        backgroundColor: [
                            '#facc15', // Pending
                            '#38bdf8', // Active
                            '#22c55e', // Processing
                            '#ef4444'  // Processing
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                            labels: { font: { size: 14 } }
                        }
                    },
                    cutout: '70%',
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        });
    </script>
</body>
</html>