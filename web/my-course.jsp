<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Registrations - EDEMY</title>
        <!-- CSS và Fonts... -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            /* Copy style từ các trang khác để đảm bảo nhất quán */
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f8f9fa;
            }
            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 20px 50px;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .logo img {
                height: 200%;
                width: auto;
                max-width: 200px;
                object-fit: contain;
            }
            .nav-links {
                display: flex;
                gap: 30px;
                margin-left: 20px;
                margin-right: auto;
            }
            .nav-links a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
                font-size: 25px;
            }
            .nav-links a.active {
                color: #0d6efd;
                font-weight: 700;
            }
            .signup-btn {
                padding: 8px 20px;
                background-color: #4a00e0;
                color: white;
                border: none;
                border-radius: 10px;
                font-weight: bold;
                cursor: pointer;
                width: 150px;
                height: 50px;
            }
            .signup-btn a {
                color: white;
                text-decoration: none;
                display: block;
                line-height: 34px;
                text-align: center;
            }
            .page-title {
                font-size: 3rem;
                font-weight: 700;
                text-align: center;
                margin: 2rem 0;
            }
            .course-card {
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                transition: box-shadow 0.3s ease, transform 0.3s ease;
                height: 100%;
                background-color: #fff;
                overflow: hidden;
            }
            .course-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            }
            .course-card img {
                aspect-ratio: 16 / 9;
                object-fit: cover;
            }
            .card-body {
                padding: 1.25rem;
            }
            .course-card .card-title {
                font-weight: 500;
                color: #0d6efd;
                font-size: 1.1rem;
                margin-bottom: 0.5rem;
            }
            .btn-learn {
                background-color: #198754;
                border-color: #198754;
                color: white;
                width: 100%;
                font-weight: 500;
            }
            .empty-state {
                text-align: center;
                padding: 4rem 2rem;
                background-color: #fff;
                border-radius: 8px;
                border: 2px dashed #dee2e6;
            }
        </style>
    </head>
    <body>


        <!-- Navbar -->
        <nav class="navbar">
            <div class="logo"> <img src="img/logo.png" alt="EDEMY Logo"/> </div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/courses">Courses</a>
                <a href="#">Blog</a>
                <a href="#">About</a>
                <c:if test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/my-registrations" class="active">My Registrations</a>
                </c:if>
            </div>
            <div class="right-section">
                <button class="signup-btn">
                    <c:if test="${not empty sessionScope.user}"><a href="logout">Log out</a></c:if>
                    <c:if test="${empty sessionScope.user}"><a href="login">Log in</a></c:if>
                    </button>
                </div>
            </nav>

            <!-- Main Content -->
            <div class="container mt-5 mb-5">
                <h1 class="page-title">My Courses</h1>
            <c:choose>
                <%-- SỬA Ở ĐÂY: DÙNG BIẾN 'courses' (SỐ NHIỀU) ĐỂ KIỂM TRA --%>
                <c:when test="${not empty courses}">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4">
                        <%-- SỬA Ở ĐÂY: DÙNG BIẾN 'courses' (SỐ NHIỀU) ĐỂ LẶP --%>
                        <c:forEach var="course" items="${courses}">
                            <div class="col">
                                <div class="card course-card">
                                    <a href="course-detail?id=${course.id}"> <img src="${not empty course.thumbnailUrl ? course.thumbnailUrl : 'https://via.placeholder.com/400x225.png?text=No+Image'}" class="card-img-top" alt="${course.title}"> </a>
                                    <div class="card-body d-flex flex-column">
                                        <a href="course-detail?id=${course.id}" class="text-decoration-none"> <h5 class="card-title">${course.title}</h5> </a>
                                        <p class="card-text flex-grow-1">${course.tagline}</p>
                                        <div class="mt-auto">
                                            <a href="lesson?courseId=${course.id}" class="btn btn-learn">
                                                <i class="bi bi-play-circle-fill"></i> Start Learning
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <%-- Khối này bây giờ sẽ hoạt động đúng --%>
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="empty-state">
                                <i class="bi bi-journal-x" style="font-size: 4rem; color: #6c757d;"></i>
                                <h3 class="mt-3">You Haven't Registered for Any Courses Yet</h3>
                                <p class="text-muted">Your personal course library is waiting. Explore our catalog to find a course that sparks your interest!</p>
                                <a href="${pageContext.request.contextPath}/courses" class="btn btn-primary btn-lg mt-3">Explore Courses</a>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>