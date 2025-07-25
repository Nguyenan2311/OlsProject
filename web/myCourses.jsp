<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Courses</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        body {
            background: #f5f7fa;
            font-family: 'Roboto', Arial, sans-serif;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            background: #fff;
            padding: 32px 24px 32px 24px;
            border-radius: 16px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.07);
        }
        h2 {
            font-size: 2.2rem;
            font-weight: 700;
            margin-bottom: 32px;
            color: #2d3a4b;
            letter-spacing: 1px;
        }
        .courses-flex {
            display: flex;
            flex-wrap: wrap;
            gap: 32px;
            justify-content: flex-start;
        }
        .course-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.06);
            width: 320px;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            transition: transform 0.18s, box-shadow 0.18s;
            border: 1px solid #e6e8ec;
        }
        .course-card:hover {
            transform: translateY(-4px) scale(1.025);
            box-shadow: 0 8px 32px rgba(60,100,180,0.08);
            border-color: #b5c6e0;
        }
        .card-thumb {
            width: 100%;
            height: 160px;
            object-fit: cover;
            background: #f0f1f6;
        }
        .card-body {
            padding: 20px 20px 16px 20px;
            flex: 1;
            display: flex;
            flex-direction: column;
        }
        .card-title {
            font-size: 1.25rem;
            font-weight: 700;
            color: #2d3a4b;
            margin-bottom: 8px;
            min-height: 48px;
        }
        .card-tagline {
            color: #6c7a89;
            font-size: 1rem;
            margin-bottom: 16px;
            min-height: 32px;
        }
        .card-meta {
            font-size: 0.98rem;
            color: #8b97a8;
            margin-bottom: 10px;
        }
        .card-status {
            font-weight: 700;
            margin-bottom: 10px;
        }
        .status-active {
            color: #27ae60;
        }
        .status-expired {
            color: #c0392b;
        }
        .card-action {
            margin-top: auto;
            display: flex;
            justify-content: flex-end;
        }
        .btn-view {
            background: linear-gradient(90deg, #4e9af1 0%, #2563eb 100%);
            color: #fff;
            border: none;
            border-radius: 6px;
            padding: 10px 22px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.18s;
            box-shadow: 0 2px 8px rgba(60,100,180,0.08);
            text-decoration: none;
        }
        .btn-view:hover {
            background: linear-gradient(90deg, #2563eb 0%, #4e9af1 100%);
        }
        @media (max-width: 900px) {
            .courses-flex { gap: 20px; }
            .course-card { width: 100%; max-width: 420px; }
        }
        @media (max-width: 600px) {
            .container { padding: 8px; }
            .courses-flex { flex-direction: column; gap: 16px; }
            .course-card { width: 100%; max-width: 100%; }
        }
    </style>
</head>
<body>
<div class="container">
      <%@include file = "header.jsp" %>
    <h2>My Courses</h2>
    <c:if test="${empty myCourses}">
        <div style="font-size:1.15rem;color:#c0392b;text-align:center;margin:32px 0;">You have no access-allowed courses.</div>
    </c:if>
    <c:if test="${not empty myCourses}">
        <div class="courses-flex">
            <c:forEach var="c" items="${myCourses}">
                <div class="course-card">
                    <img src="${c.thumbnailUrl != null && c.thumbnailUrl != '' ? c.thumbnailUrl : 'https://placehold.co/320x160?text=No+Image'}" alt="Course Thumbnail" class="card-thumb"/>
                    <div class="card-body">
                        <div class="card-title">${c.title}</div>
                        <div class="card-tagline">${c.tagline}</div>
                        <div class="card-meta">
                            <span>Valid: </span>
                            <span style="color:#2563eb">${c.validFrom}</span>
                            <span> - </span>
                            <span style="color:#2563eb">${c.validTo}</span>
                        </div>
                        <div class="card-status">
                            <c:choose>
                                <c:when test="${c.active==1}"><span class="status-active">Active</span></c:when>
                                <c:otherwise><span class="status-expired">Expired</span></c:otherwise>
                            </c:choose>
                        </div>
                        <div class="card-action">
                            <a href="lesson?courseId=${c.id}" class="btn-view">View Course</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
</body>
</html>
