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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
            font-family: 'Roboto', sans-serif;
            background-color: #f8f9fa;
        }

        /* === Navbar Styling === */
        
        /* === Sidebar Styling giống courseDetail === */
        .sidebar-section {
            margin-bottom: 1.5rem;
        }
        
        .sidebar-section h5 {
            font-weight: 600;
            margin-bottom: 15px;
        }
        
        .category-link {
            text-decoration: none;
            display: block;
            padding: 0.25rem 0;
            color: #333;
        }
        
        .category-link:hover {
            color: #6366f1;
        }

        .main-content {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
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
            background: #0891b2;
            color: white;
        }
        
        .btn-cancel {
            background: var(--danger-color);
            color: white;
        }
        .btn-cancel:hover {
            background: #dc2626;
            color: white;
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
    <%@include file = "header.jsp" %>
    <div class="container mt-5">
        <h1 class="mb-4 text-center" style="font-weight: 700;">My Course Registrations</h1>
        <div class="row">
            <!-- ===== LEFT SIDEBAR (Updated to match courseDetail) ===== -->
            <div class="col-lg-3">
                <div class="sidebar-section">
                    <h5>Search course</h5>
                    <form method="get" action="my-registrations">
                        <div class="input-group">
                            <input type="text" name="search" class="form-control" placeholder="Search..." value="${param.search}">
                            <input type="hidden" name="category" value="${param.category}">
                            <button class="btn btn-primary" type="submit"><i class="bi bi-search"></i></button>
                        </div>
                    </form>
                </div>
                
                <div class="sidebar-section">
                    <h5>Categories</h5>
                   
                </div> 
                    <a href="courses?search=&tag=&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off" class="category-link">All Categories</a>
                    <a href="courses?category=English&search=&tag=&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off" class="category-link">English</a>
                    <a href="courses?category=Japanese&search=&tag=&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off" class="category-link">Japanese</a>
                    <a href="courses?category=Korean&search=&tag=&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off"class="category-link">Korean</a>
                
                <div class="sidebar-section">
                    <h5>Featured</h5>
                   <a href="courses?search=&category=&sortBy=title&tag=1&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off" class="category-link">Master English Basics</a>
                  <a href="courses?tag=2&search=&category=&sortBy=title&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off"class="category-link">Start Your Japanese Journey</a>
                   <a href="courses?tag=3&search=&category=&sortBy=title&rowsPerPage=2&showThumbnail=on&showTitle=off&showPrice=off&showTagline=off&showPublicDate=off"class="category-link">Prepare for TOPIK I</a>
                </div>
                
                <div class="sidebar-section">
                    <h5>Contact</h5>
                    <p class="mb-1">Email: abc@gmail.com</p>
                    <p class="mb-1">Phone: 012345678</p>
                    <div><a href="#">Facebook</a> | <a href="#">Github</a> | <a href="#">Youtube</a></div>
                </div>
            </div>

            <!-- ===== RIGHT CONTENT AREA ===== -->
            <div class="col-lg-9">
                <div class="main-content">
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
                                                        <c:choose>
                                                            <c:when test="${reg.status == 'Submitted'}">
                                                                <a href="course-detail?id=${reg.courseId}&action=summit" class="btn-action btn-edit me-2">
                                                                    <i class="fas fa-edit"></i> Edit
                                                                </a>
                                                                <form method="post" action="my-registrations" class="d-inline">
                                                                    <input type="hidden" name="action" value="cancel">
                                                                    <input type="hidden" name="id" value="${reg.id}">
                                                                    <button type="submit" class="btn-action btn-cancel" onclick="return confirm('Are you sure you want to cancel this registration?');">
                                                                        <i class="fas fa-times"></i> Cancel
                                                                    </button>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted fst-italic small">No actions</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
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