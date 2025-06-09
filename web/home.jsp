<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edemy - Home</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                overflow-x: hidden;
            }

            /* Banner (Slider) */
            .banner {
                width: 100%;
                height: 200px;
                position: relative;
                overflow: hidden;
            }

            .banner img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .banner-text a {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: block;
                text-indent: -9999px;
            }

            /* Layout */
            .content {
                display: flex;
                padding: 20px;
                gap: 20px;
                max-width: 100%;
                margin: 0 auto;
            }

            .main-content {
                flex: 3;
                min-width: 0;
            }

            .sidebar {
                flex: 1;
                min-width: 250px;
            }

            .hot-posts,
            .featured-subjects {
                margin-bottom: 40px;
            }

            .hot-posts h3,
            .featured-subjects h3 {
                margin-bottom: 10px;
            }

            /* Cards */
            .card {
                width: 200px;
                border: 1px solid #ccc;
                padding: 10px;
                border-radius: 8px;
                text-align: center;
                background-color: #f9f9f9;
                transition: transform 0.2s ease;
                flex-shrink: 0;
            }

            .card:hover {
                transform: scale(1.03);
            }

            .card img {
                max-width: 100%;
                height: auto;
                border-radius: 4px;
            }

            /* Sidebar */
            .sidebar-item {
                background-color: #f1f1f1;
                padding: 30px;
                border-radius: 8px;
                margin-bottom: 20px;
                word-wrap: break-word;
            }

            .sidebar-item h3 {
                margin-top: 0;
            }

            .sidebar-item.contact-links a {
                display: block;
                margin-top: 5px;
                text-decoration: none;
                color: #333;
            }

            .sidebar-item.contact-links a:hover {
                text-decoration: underline;
            }

            a {
                color: inherit;
                text-decoration: none;
                font-weight: bold;
            }

            .quick-link-item {
                display: flex;
                align-items: center;
                gap: 8px;
                margin-top: 5px;
            }

            /* Sliders */
            .slider-wrapper {
                display: flex;
                overflow-x: auto;
                scroll-snap-type: x mandatory;
                -webkit-overflow-scrolling: touch;
                gap: 16px;
                width: 100%;
            }

            .slider-wrapper .banner-container {
                flex: 0 0 100%;
                scroll-snap-align: start;
            }

            /* Scrollable sections */
            .scroll-container {
                width: 100%;
                overflow-x: auto;
                padding-bottom: 10px;
                -webkit-overflow-scrolling: touch;
            }

            .scroll-content {
                display: flex;
                gap: 16px;
                padding: 0 10px;
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .content {
                    flex-direction: column;
                    padding: 10px;
                }

                .sidebar {
                    width: 100%;
                    min-width: auto;
                }

                .card {
                    width: 180px;
                }
            }
            /* Latest Posts styling */
            .latest-posts {
                padding: 15px;
            }

            .post-item {
                margin-bottom: 20px; /* Kho?ng cách gi?a các post */
                padding-bottom: 15px;
                border-bottom: 1px solid #e0e0e0; /* ???ng g?ch ngang nh? phân cách */
            }

            .post-item:last-child {
                margin-bottom: 0;
                padding-bottom: 0;
                border-bottom: none;
            }

            .post-title {
                margin-bottom: 5px;
                line-height: 1.4;
            }

            .post-date {
                color: #666;
                font-size: 0.9em;
                margin-top: 0;
            }
        </style>
        <script src="https://kit.fontawesome.com/8807c30b90.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%@include file = "header.jsp" %>

        <div class="slider-wrapper">
            <c:forEach items="${listSlider}" var="slider">
                <div class="banner-container">
                    <div class="banner">
                        <img src="${slider.image_url}" alt="${slider.back_link_url}">
                        <div class="banner-text">
                            <a href="${slider.back_link_url}"></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="content">
            <!-- Main content -->
            <div class="main-content">
                <div class="hot-posts">
                    <h3>Hot post</h3>
                    <div class="scroll-container">
                        <div class="scroll-content">
                            <c:forEach items="${listPost}" var="o">
                                <div class="card">
                                    <a href="https://example.com/post1">
                                        <img src="${o.thumbnail_url}" alt="" width="200px" height="200px">
                                        <p>${o.title}</p>
                                    </a>
                                    <p>${o.created_date}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="featured-subjects">
                    <h3>Subjects</h3>
                    <div class="scroll-container">
                        <div class="scroll-content">
                            <c:forEach items="${listCourse}" var="o">
                                <div class="card">
                                    <a href="https://example.com/subject1">
                                        <img src="${o.thumbnailUrl}" alt="">
                                        <p>${o.title}</p>
                                    </a>
                                    <p>${o.tagline}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sidebar -->
            <div class="sidebar">
                <div class="sidebar-item latest-posts"> <!-- Thêm class latest-posts -->
                    <h3 style="padding-bottom: 20px">Latest Post</h3>
                    <c:forEach items="${listLastPost}" var="o">                             
                        <div class="post-item"> <!-- B?c m?i post trong m?t div riêng -->
                            <p class="post-title">
                                <a href="#">${o.title}</a>
                            </p>
                            <p class="post-date">
                                ${o.created_date}
                            </p>
                        </div>
                    </c:forEach>
                </div>
                <div class="sidebar-item contact-links">
                    <h3>Quick Link</h3>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-envelope"></i><a href="mailto:abcxyz@gmail.com">abcxyz@gmail.com</a>
                    </div>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-phone"></i><a href="tel:0123456789">0123456789</a>
                    </div>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-users"></i><a href="#">About us</a>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>