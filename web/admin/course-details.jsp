<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Details - Admin Panel</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
        .info-item {
            padding: 1rem;
            border-bottom: 1px solid #e5e7eb;
        }
        .info-item:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: 600;
            color: #374151;
            margin-bottom: 0.5rem;
        }
        .info-value {
            color: #6b7280;
        }
        .badge {
            padding: 0.5rem 0.75rem;
            border-radius: 6px;
            font-weight: 500;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <!-- Admin Header -->
    <div class="admin-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h1 class="mb-0"><i class="bi bi-mortarboard-fill me-2"></i>Course Details</h1>
                    <p class="mb-0 opacity-75">View detailed information about the course</p>
                </div>
                <div class="col-md-4 text-md-end">
                    <a href="${pageContext.request.contextPath}/admin/course-management" class="btn btn-light">
                        <i class="bi bi-arrow-left me-2"></i>Back to Course Management
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Course Information</h5>
                    </div>
                    <div class="card-body p-0">
                        <div class="info-item">
                            <div class="info-label">Course ID</div>
                            <div class="info-value">#${course.id}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Course Name</div>
                            <div class="info-value">${course.subtitle}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Description</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty course.description}">
                                        ${course.description}
                                    </c:when>
                                    <c:otherwise>
                                        <em class="text-muted">No description provided</em>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Total Duration</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${course.total_duration > 0}">
                                        <c:choose>
                                            <c:when test="${course.total_duration < 60}">
                                                ${course.total_duration} minutes
                                            </c:when>
                                            <c:otherwise>
                                                ${course.total_duration / 60} hours ${course.total_duration % 60} minutes
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <em class="text-muted">Not specified</em>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Number of Learners</div>
                            <div class="info-value">${course.number_of_learner} enrolled</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Status</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${course.status == 1}">
                                        <span class="badge bg-success">
                                            <i class="bi bi-check-circle me-1"></i>Active
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">
                                            <i class="bi bi-x-circle me-1"></i>Inactive
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Created Date</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty course.created_date}">
                                        <fmt:formatDate value="${course.created_date}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                    </c:when>
                                    <c:otherwise>
                                        <em class="text-muted">Not available</em>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Last Updated</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty course.updated_date}">
                                        <fmt:formatDate value="${course.updated_date}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                    </c:when>
                                    <c:otherwise>
                                        <em class="text-muted">Not available</em>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Actions</h5>
                    </div>
                    <div class="card-body">
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/admin/course/edit?id=${course.id}" 
                               class="btn btn-primary">
                                <i class="bi bi-pencil me-2"></i>Edit Course
                            </a>
                            <button type="button" class="btn btn-outline-secondary" 
                                    onclick="toggleStatus(${course.id}, ${course.status == 1 ? 0 : 1})">
                                <c:choose>
                                    <c:when test="${course.status == 1}">
                                        <i class="bi bi-pause-circle me-2"></i>Deactivate Course
                                    </c:when>
                                    <c:otherwise>
                                        <i class="bi bi-play-circle me-2"></i>Activate Course
                                    </c:otherwise>
                                </c:choose>
                            </button>
                            <a href="${pageContext.request.contextPath}/courses?id=${course.id}" 
                               class="btn btn-outline-info" target="_blank">
                                <i class="bi bi-eye me-2"></i>View Public Page
                            </a>
                        </div>
                    </div>
                </div>

                <div class="card mt-3">
                    <div class="card-header">
                        <h5 class="mb-0">Quick Stats</h5>
                    </div>
                    <div class="card-body">
                        <div class="row text-center">
                            <div class="col-6">
                                <h4 class="text-primary mb-1">${course.number_of_learner}</h4>
                                <small class="text-muted">Learners</small>
                            </div>
                            <div class="col-6">
                                <h4 class="text-success mb-1">
                                    <c:choose>
                                        <c:when test="${course.total_duration > 0}">
                                            ${course.total_duration / 60}h
                                        </c:when>
                                        <c:otherwise>0h</c:otherwise>
                                    </c:choose>
                                </h4>
                                <small class="text-muted">Duration</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        function toggleStatus(courseId, newStatus) {
            if (confirm('Are you sure you want to change the status of this course?')) {
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
                courseIdInput.value = courseId;
                
                const statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'newStatus';
                statusInput.value = newStatus;
                
                form.appendChild(actionInput);
                form.appendChild(courseIdInput);
                form.appendChild(statusInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>
