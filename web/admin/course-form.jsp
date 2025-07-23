<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${pageTitle} - Admin Panel</title>
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
            .form-control, .form-select {
                border-radius: 8px;
                border: 1px solid #d1d5db;
                padding: 0.75rem;
            }
            .btn-primary {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                border-radius: 8px;
                padding: 0.75rem 2rem;
                font-weight: 500;
            }
            .btn-secondary {
                border-radius: 8px;
                padding: 0.75rem 2rem;
            }
        </style>
    </head>
    <body>
        <!-- Admin Header -->
        <div class="admin-header">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-8">
                        <h1 class="mb-0"><i class="bi bi-mortarboard-fill me-2"></i>${pageTitle}</h1>
                        <p class="mb-0 opacity-75">
                            <c:choose>
                                <c:when test="${isEdit}">Update course information</c:when>
                                <c:otherwise>Create a new course in the system</c:otherwise>
                            </c:choose>
                        </p>
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
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0">${pageTitle}</h5>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${isEdit}">
                                    <form method="POST" action="${pageContext.request.contextPath}/admin/course/edit">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="courseId" value="${course.id}">
                                </c:when>
                                <c:otherwise>
                                    <form method="POST" action="${pageContext.request.contextPath}/admin/course/new">
                                        <input type="hidden" name="action" value="create">
                                </c:otherwise>
                            </c:choose>

                                    <div class="row">
                                        <div class="col-md-8">
                                            <div class="mb-3">
                                                <label for="courseName" class="form-label">Course Name <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" id="courseName" name="courseName" 
                                                       value="${course.subtitle}" required 
                                                       placeholder="Enter course name">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="category" class="form-label">Category <span class="text-danger">*</span></label>
                                                <select class="form-select" id="category" name="category" required>
                                                    <option value="">Select Category</option>
                                                    <c:forEach var="cat" items="${categories}">
                                                        <option value="${cat}" ${currentCategory == cat ? 'selected' : ''}>${cat}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="description" class="form-label">Description</label>
                                        <textarea class="form-control" id="description" name="description" rows="4" 
                                                  placeholder="Enter course description">${course.description}</textarea>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label for="duration" class="form-label">Total Duration (minutes)</label>
                                                <input type="number" class="form-control" id="duration" name="duration" 
                                                       value="${course.total_duration}" min="0" 
                                                       placeholder="Enter duration in minutes">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label for="status" class="form-label">Status</label>
                                                <select class="form-select" id="status" name="status">
                                                    <option value="1" ${course.status == 1 ? 'selected' : ''}>Active</option>
                                                    <option value="0" ${course.status == 0 ? 'selected' : ''}>Inactive</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="alert alert-info">
                                        <i class="bi bi-info-circle me-2"></i>
                                        <strong>Note:</strong> This is a simplified course form. In a complete implementation, 
                                        you would add fields for expert assignment, pricing, thumbnails, and lesson management.
                                    </div>

                                    <div class="d-flex justify-content-between">
                                        <a href="${pageContext.request.contextPath}/admin/course-management" class="btn btn-secondary">
                                            <i class="bi bi-x-circle me-2"></i>Cancel
                                        </a>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-circle me-2"></i>
                                            <c:choose>
                                                <c:when test="${isEdit}">Update Course</c:when>
                                                <c:otherwise>Create Course</c:otherwise>
                                            </c:choose>
                                        </button>
                                    </div>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
