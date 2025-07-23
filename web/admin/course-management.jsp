<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Management - Admin Panel</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8f9fa;
        }
        .admin-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #fff;
            border-bottom: 1px solid #e5e7eb;
            border-radius: 12px 12px 0 0 !important;
            padding: 1.5rem;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            padding: 0.5rem 1.5rem;
            font-weight: 500;
        }
        .btn-outline-primary {
            border-color: #667eea;
            color: #667eea;
            border-radius: 8px;
            font-weight: 500;
        }
        .btn-outline-primary:hover {
            background-color: #667eea;
            border-color: #667eea;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 1px solid #d1d5db;
            padding: 0.75rem;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .table {
            border-radius: 8px;
            overflow: hidden;
        }
        .table thead th {
            background-color: #f9fafb;
            border-bottom: 2px solid #e5e7eb;
            font-weight: 600;
            color: #374151;
            padding: 1rem;
        }
        .table tbody td {
            padding: 1rem;
            vertical-align: middle;
        }
        .badge {
            padding: 0.5rem 0.75rem;
            border-radius: 6px;
            font-weight: 500;
            font-size: 0.75rem;
        }
        .badge.bg-success {
            background-color: #10b981 !important;
        }
        .badge.bg-danger {
            background-color: #ef4444 !important;
        }
        .pagination .page-link {
            border-radius: 6px;
            margin: 0 2px;
            border: 1px solid #d1d5db;
            color: #374151;
        }
        .pagination .page-item.active .page-link {
            background-color: #667eea;
            border-color: #667eea;
        }
        .search-filters {
            background-color: #fff;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
        }
        .status-toggle {
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .status-toggle:hover {
            transform: scale(1.05);
        }
        .course-actions {
            display: flex;
            gap: 0.5rem;
        }
        .btn-sm {
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
            border-radius: 6px;
        }
        .alert {
            border-radius: 8px;
            border: none;
        }
        .stats-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <!-- Admin Header -->
    <div class="admin-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <h1 class="mb-0"><i class="bi bi-mortarboard-fill me-2"></i>Course Management</h1>
                    <p class="mb-0 opacity-75">Manage all courses in the system</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <a href="${pageContext.request.contextPath}/admin/course/new" class="btn btn-light">
                        <i class="bi bi-plus-circle me-2"></i>Add New Course
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <!-- Status Messages -->
        <c:if test="${param.statusUpdate == 'success'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle me-2"></i>Course status updated successfully!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${param.statusUpdate == 'error'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle me-2"></i>Failed to update course status. Please try again.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Statistics Card -->
        <div class="stats-card">
            <div class="row text-center">
                <div class="col-md-4">
                    <h3 class="mb-1">${totalCourses}</h3>
                    <p class="mb-0">Total Courses</p>
                </div>
                <div class="col-md-4">
                    <h3 class="mb-1">${startItem}-${endItem}</h3>
                    <p class="mb-0">Showing Results</p>
                </div>
                <div class="col-md-4">
                    <h3 class="mb-1">${totalPages}</h3>
                    <p class="mb-0">Total Pages</p>
                </div>
            </div>
        </div>

        <!-- Search and Filters -->
        <div class="search-filters">
            <form method="GET" action="${pageContext.request.contextPath}/admin/course-management">
                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="search" class="form-label">Search Course</label>
                        <input type="text" class="form-control" id="search" name="search" 
                               value="${searchKeyword}" placeholder="Enter course name...">
                    </div>
                    <div class="col-md-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-select" id="category" name="category">
                            <option value="">All Categories</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat}" ${selectedCategory == cat ? 'selected' : ''}>${cat}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="status" class="form-label">Status</label>
                        <select class="form-select" id="status" name="status">
                            <option value="">All Status</option>
                            <option value="Active" ${selectedStatus == 'Active' ? 'selected' : ''}>Active</option>
                            <option value="Inactive" ${selectedStatus == 'Inactive' ? 'selected' : ''}>Inactive</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label for="pageSize" class="form-label">Per Page</label>
                        <select class="form-select" id="pageSize" name="pageSize">
                            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                            <option value="25" ${pageSize == 25 ? 'selected' : ''}>25</option>
                            <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                        </select>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="bi bi-search me-2"></i>Search
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/course-management" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-clockwise me-2"></i>Reset
                        </a>
                    </div>
                </div>
            </form>
        </div>

        <!-- Course Table -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Course List</h5>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${not empty courses}">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Course Name</th>
                                        <th>Category</th>
                                        <th>Lessons</th>
                                        <th>Owner</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="course" items="${courses}">
                                        <tr>
                                            <td><strong>#${course.id}</strong></td>
                                            <td>
                                                <div class="fw-medium">${course.name}</div>
                                            </td>
                                            <td>
                                                <span class="badge bg-light text-dark">${course.category}</span>
                                            </td>
                                            <td>
                                                <span class="badge bg-info">${course.lessonCount} lessons</span>
                                            </td>
                                            <td>${course.ownerName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${course.status == 'Active'}">
                                                        <span class="badge bg-success status-toggle" 
                                                              onclick="toggleStatus('${course.id}', 0)">
                                                            <i class="bi bi-check-circle me-1"></i>Active
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger status-toggle" 
                                                              onclick="toggleStatus('${course.id}', 1)">
                                                            <i class="bi bi-x-circle me-1"></i>Inactive
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="course-actions">
                                                    <a href="${pageContext.request.contextPath}/admin/course/edit?id=${course.id}" 
                                                       class="btn btn-outline-primary btn-sm" title="Edit Course">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/course/details?id=${course.id}" 
                                                       class="btn btn-outline-info btn-sm" title="View Details">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-5">
                            <i class="bi bi-inbox display-1 text-muted"></i>
                            <h4 class="mt-3 text-muted">No courses found</h4>
                            <p class="text-muted">Try adjusting your search criteria or add a new course.</p>
                            <a href="${pageContext.request.contextPath}/admin/course/new" class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Add New Course
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Course pagination" class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Previous Page -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <c:url var="prevUrl" value="/admin/course-management">
                            <c:param name="page" value="${currentPage - 1}"/>
                            <c:param name="pageSize" value="${pageSize}"/>
                            <c:param name="search" value="${searchKeyword}"/>
                            <c:param name="category" value="${selectedCategory}"/>
                            <c:param name="status" value="${selectedStatus}"/>
                        </c:url>
                        <a class="page-link" href="${prevUrl}">
                            <i class="bi bi-chevron-left"></i>
                        </a>
                    </li>

                    <!-- Page Numbers -->
                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                        <c:if test="${pageNum <= 5 || pageNum > totalPages - 5 || (pageNum >= currentPage - 2 && pageNum <= currentPage + 2)}">
                            <li class="page-item ${currentPage == pageNum ? 'active' : ''}">
                                <c:url var="pageUrl" value="/admin/course-management">
                                    <c:param name="page" value="${pageNum}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="search" value="${searchKeyword}"/>
                                    <c:param name="category" value="${selectedCategory}"/>
                                    <c:param name="status" value="${selectedStatus}"/>
                                </c:url>
                                <a class="page-link" href="${pageUrl}">${pageNum}</a>
                            </li>
                        </c:if>
                    </c:forEach>

                    <!-- Next Page -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <c:url var="nextUrl" value="/admin/course-management">
                            <c:param name="page" value="${currentPage + 1}"/>
                            <c:param name="pageSize" value="${pageSize}"/>
                            <c:param name="search" value="${searchKeyword}"/>
                            <c:param name="category" value="${selectedCategory}"/>
                            <c:param name="status" value="${selectedStatus}"/>
                        </c:url>
                        <a class="page-link" href="${nextUrl}">
                            <i class="bi bi-chevron-right"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>

    <!-- Status Update Modal -->
    <div class="modal fade" id="statusModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirm Status Change</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to change the status of this course?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="confirmStatusChange">Confirm</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        let pendingStatusChange = null;

        function toggleStatus(courseId, newStatus) {
            pendingStatusChange = { courseId: courseId, newStatus: newStatus };
            const modal = new bootstrap.Modal(document.getElementById('statusModal'));
            modal.show();
        }

        document.getElementById('confirmStatusChange').addEventListener('click', function() {
            if (pendingStatusChange) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/admin/course-management';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'updateStatus';
                
                const courseIdInput = document.createElement('input');
                courseIdInput.type = 'hidden';
                courseIdInput.name = 'courseId';
                courseIdInput.value = pendingStatusChange.courseId;
                
                const statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'newStatus';
                statusInput.value = pendingStatusChange.newStatus;
                
                form.appendChild(actionInput);
                form.appendChild(courseIdInput);
                form.appendChild(statusInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        });

        // Auto-submit form on filter changes
        document.getElementById('pageSize').addEventListener('change', function() {
            this.form.submit();
        });
    </script>
</body>
</html>
