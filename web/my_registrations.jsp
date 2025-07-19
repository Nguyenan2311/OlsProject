<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Course Registrations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #6366f1;
            --secondary-color: #f1f5f9;
            --accent-color: #06b6d4;
            --success-color: #10b981;
            --warning-color: #f59e0b;
            --danger-color: #ef4444;
            --dark-color: #1e293b;
            --light-color: #f8fafc;
            --border-color: #e2e8f0;
            --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
            --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
            --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: var(--dark-color);
            line-height: 1.6;
        }

        .main-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            box-shadow: var(--shadow-lg);
            margin: 20px;
            overflow: hidden;
            animation: fadeInUp 0.6s ease-out;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .sidebar {
            background: linear-gradient(180deg, #f8fafc 0%, #e2e8f0 100%);
            padding: 30px 25px;
            border-right: 1px solid var(--border-color);
            min-height: 100vh;
            position: relative;
        }
        
        .sidebar-section {
            margin-bottom: 30px;
        }
        
        .sidebar-section h5 {
            color: var(--dark-color);
            font-weight: 600;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .sidebar-section h5 i {
            color: var(--primary-color);
        }

        .search-box .input-group {
            position: relative;
            overflow: hidden;
            border-radius: 15px;
            box-shadow: var(--shadow-sm);
        }

        .search-box .form-control {
            border: 0;
            padding: 15px 20px;
            background: white;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        .search-box .form-control:focus {
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
            border: 0;
        }

        .search-box .btn {
            border: 0;
            background: var(--primary-color);
            padding: 15px 25px;
            transition: all 0.3s ease;
        }

        .search-box .btn:hover {
            background: #5855eb;
            transform: translateY(-1px);
        }

        .category-list .list-group {
            border-radius: 15px;
            overflow: hidden;
            box-shadow: var(--shadow-sm);
        }

        .category-list .list-group-item {
            border: 0;
            padding: 15px 20px;
            background: white;
            border-bottom: 1px solid var(--border-color);
            transition: all 0.3s ease;
        }
        
        .category-list .list-group-item.active {
            background: var(--primary-color);
            transform: translateX(5px);
        }

        .category-list .list-group-item.active a {
            color: white;
        }

        .category-list .list-group-item:last-child {
            border-bottom: 0;
        }

        .category-list .list-group-item:hover:not(.active) {
            background: var(--secondary-color);
            transform: translateX(5px);
        }

        .category-list a {
            text-decoration: none;
            color: var(--dark-color);
            font-weight: 500;
            display: block;
            transition: all 0.3s ease;
        }
        
        .main-content {
            padding: 40px 30px;
            background: white;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid var(--border-color);
        }

        .page-header h2 {
            color: var(--dark-color);
            font-weight: 700;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .page-header h2 i {
            color: var(--primary-color);
            font-size: 28px;
        }

        .stats-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            color: white;
            padding: 25px;
            border-radius: 15px;
            text-align: center;
            box-shadow: var(--shadow-md);
            transition: all 0.3s ease;
        }
        
        .stat-card:nth-child(1) { background: linear-gradient(135deg, #6366f1, #8b5cf6); }
        .stat-card:nth-child(2) { background: linear-gradient(135deg, #10b981, #14b8a6); }
        .stat-card:nth-child(3) { background: linear-gradient(135deg, #f97316, #f59e0b); }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-lg);
        }

        .stat-card h3 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 5px;
        }

        .stat-card p {
            font-size: 14px;
            opacity: 0.9;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .table-container {
            background: white;
            border-radius: 15px;
            box-shadow: var(--shadow-sm);
            overflow: hidden;
            margin-bottom: 30px;
        }

        .table {
            margin: 0;
        }

        .table thead {
            background: var(--light-color);
        }

        .table thead th {
            border: 0;
            padding: 20px 15px;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            letter-spacing: 0.5px;
            color: #64748b;
        }

        .table tbody tr {
            transition: all 0.2s ease-in-out;
        }

        .table tbody tr:hover {
            background: rgba(99, 102, 241, 0.05);
            transform: scale(1.01);
            box-shadow: var(--shadow-md);
            position: relative;
            z-index: 10;
        }

        .table tbody td {
            padding: 20px 15px;
            vertical-align: middle;
            border-color: var(--border-color);
        }

        .status-badge {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .status-badge.active { background: rgba(16, 185, 129, 0.1); color: var(--success-color); }
        .status-badge.submitted { background: rgba(245, 158, 11, 0.1); color: var(--warning-color); }
        .status-badge.cancelled { background: rgba(239, 68, 68, 0.1); color: var(--danger-color); }
        .status-badge.expired { background: rgba(100, 116, 139, 0.1); color: #64748b; }
        
        /* --- NEW/UPDATED --- */
        .btn-action {
            padding: 8px 16px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            border: 0;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }

        .btn-action:hover {
            transform: translateY(-2px);
            box-shadow: var(--shadow-md);
        }
        
        .btn-edit {
            background: var(--accent-color);
            color: white;
        }
        .btn-edit:hover {
            background: #0891b2; /* Darker accent */
            color: white;
        }
        
        .btn-cancel {
            background: var(--danger-color);
            color: white;
        }
        .btn-cancel:hover {
            background: #dc2626; /* Darker danger */
            color: white;
        }
        /* --- END NEW/UPDATED --- */

        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            padding-top: 20px;
        }

        .pagination {
            gap: 8px;
        }

        .page-link {
            border: 0;
            padding: 10px 16px;
            border-radius: 10px;
            background: white;
            color: var(--dark-color);
            font-weight: 500;
            transition: all 0.3s ease;
            box-shadow: var(--shadow-sm);
        }

        .page-link:hover {
            background: var(--primary-color);
            color: white;
            transform: translateY(-2px);
        }

        .page-item.active .page-link {
            background: var(--primary-color);
            color: white;
            box-shadow: var(--shadow-md);
        }
        
        .page-item.disabled .page-link {
            background-color: var(--secondary-color);
            color: #94a3b8;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #64748b;
        }

        .empty-state i {
            font-size: 64px;
            color: var(--border-color);
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="main-container">
        <div class="row g-0">
            <!-- Sidebar -->
            <div class="col-lg-3 col-md-4">
                <div class="sidebar">
                    <!-- Search Box -->
                    <div class="sidebar-section search-box">
                        <h5><i class="fas fa-search"></i> Search</h5>
                        <form method="get" action="user_registrations">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" placeholder="Search by subject..." value="${param.search}">
                                <input type="hidden" name="category" value="${param.category}">
                                <button class="btn" type="submit">
                                    <i class="fas fa-search text-white"></i>
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- Subject Categories -->
                    <div class="sidebar-section category-list">
                        <h5><i class="fas fa-folder-open"></i> Categories</h5>
                        <ul class="list-group">
                            <li class="list-group-item ${empty param.category ? 'active' : ''}">
                                <a href="user_registrations?search=${param.search}">All Categories</a>
                            </li>
                            <li class="list-group-item ${param.category == 'English' ? 'active' : ''}">
                                <a href="user_registrations?category=English&search=${param.search}">English</a>
                            </li>
                            <li class="list-group-item ${param.category == 'Japanese' ? 'active' : ''}">
                                <a href="user_registrations?category=Japanese&search=${param.search}">Japanese</a>
                            </li>
                             <li class="list-group-item ${param.category == 'Korean' ? 'active' : ''}">
                                <a href="user_registrations?category=Korean&search=${param.search}">Korean</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-lg-9 col-md-8">
                <div class="main-content">
                    <div class="page-header">
                        <h2><i class="fas fa-graduation-cap"></i> My Course Registrations</h2>
                    </div>
                    
                    <c:if test="${not empty message}">
                        <div class="alert alert-${messageType == 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Stats Cards -->
                    <div class="stats-cards">
                        <div class="stat-card">
                            <h3>${fn:length(registrationList)}</h3>
                            <p>Total Registrations</p>
                        </div>
                        <div class="stat-card">
                            <h3><c:out value="${activeCount}" default="0"/></h3>
                            <p>Active Courses</p>
                        </div>
                        <div class="stat-card">
                             <h3><c:out value="${submittedCount}" default="0"/></h3>
                            <p>Submitted</p>
                        </div>
                    </div>

                    <!-- Registration Table -->
                    <div class="table-container">
                        <c:choose>
                            <c:when test="${empty registrationList}">
                                <div class="empty-state">
                                    <i class="fas fa-folder-open"></i>
                                    <h3>No Registrations Found</h3>
                                    <p>Your search or filter returned no results. Try a different query!</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Course</th>
                                                <th>Package</th>
                                                <th>Cost</th>
                                                <th>Status</th>
                                                <th>Valid Period</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="reg" items="${registrationList}">
                                                <tr>
                                                    <td><strong>#${reg.id}</strong></td>
                                                    <td>
                                                        <strong>${reg.subject}</strong><br>
                                                        <small class="text-muted">${reg.category}</small>
                                                    </td>
                                                    <td>
                                                         <span class="badge bg-secondary fw-normal">${reg.packageName}</span>
                                                    </td>
                                                    <td>
                                                        <strong><fmt:formatNumber value="${reg.totalCost}" type="currency" currencySymbol="$"/></strong>
                                                    </td>
                                                    <td>
                                                        <span class="status-badge ${fn:toLowerCase(reg.status)}">${reg.status}</span>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${reg.validFrom}" pattern="MMM dd, yyyy"/><br>
                                                        <small class="text-muted">to</small><br>
                                                        <fmt:formatDate value="${reg.validTo}" pattern="MMM dd, yyyy"/>
                                                    </td>
                                                    <td>
                                                        <%-- === ACTION LOGIC UPDATED HERE === --%>
                                                        <c:choose>
                                                            <%-- Case 1: If status is 'Submitted', show Edit and Cancel buttons --%>
                                                            <c:when test="${reg.status == 'Submitted'}">
                                                                <a href="course-detail?id=${reg.courseId}&action=summit" class="btn-action btn-edit me-2">
                                                                    <i class="fas fa-edit"></i> Edit
                                                                </a>
                                                                <form method="post" action="user_registrations" class="d-inline">
                                                                    <input type="hidden" name="action" value="cancel">
                                                                    <input type="hidden" name="id" value="${reg.id}">
                                                                    <button type="submit" class="btn-action btn-cancel" onclick="return confirm('Are you sure you want to cancel this registration?');">
                                                                        <i class="fas fa-times"></i> Cancel
                                                                    </button>
                                                                </form>
                                                            </c:when>
                                                            <%-- Case 2: For any other status (Active, Expired, Cancelled), show no actions --%>
                                                            <c:otherwise>
                                                                <span class="text-muted fst-italic small">No actions</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                         <%-- === END OF UPDATED ACTION LOGIC === --%>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Pagination -->
                    <c:if test="${totalPages > 1}">
                        <div class="pagination-container">
                             <nav aria-label="Page navigation">
                                <ul class="pagination">
                                     <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="user_registrations?page=${page - 1}&search=${param.search}&category=${param.category}" aria-label="Previous">
                                            <span aria-hidden="true">«</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${page == i ? 'active' : ''}">
                                            <a class="page-link" href="user_registrations?page=${i}&search=${param.search}&category=${param.category}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="user_registrations?page=${page + 1}&search=${param.search}&category=${param.category}" aria-label="Next">
                                            <span aria-hidden="true">»</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Auto-hide alerts after 5 seconds
            const alert = document.querySelector('.alert');
            if(alert) {
                 setTimeout(() => {
                    new bootstrap.Alert(alert).close();
                }, 5000);
            }
        });
    </script>
</body>
</html>