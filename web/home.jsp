<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edemy - Home</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #ffffff 0%, #ffffff 100%);
                min-height: 100vh;
                overflow-x: hidden;
            }

            /* Banner (Slider) */
            .banner {
                width: 100%;
                height: 400px;
                position: relative;
                overflow: hidden;
                border-radius: 20px;
                margin: 20px;
                box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            }

            .banner::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: linear-gradient(45deg, rgba(102,126,234,0.8), rgba(118,75,162,0.8));
                z-index: 1;
            }

            .banner img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .banner:hover img {
                transform: scale(1.05);
            }

            .banner-text a {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: block;
                text-indent: -9999px;
                z-index: 2;
            }

            /* Layout */
            .content {
                display: flex;
                padding: 40px 20px;
                gap: 40px;
                max-width: 1400px;
                margin: 0 auto;
            }

            .main-content {
                flex: 3;
                min-width: 0;
            }

            .sidebar {
                flex: 1;
                min-width: 300px;
            }

            .hot-posts,
            .featured-subjects {
                margin-bottom: 60px;
                animation: fadeInUp 0.8s ease-out;
            }

            .hot-posts h3,
            .featured-subjects h3 {
                font-size: 2.5rem;
                margin-bottom: 30px;
                background: linear-gradient(45deg, #667eea, #764ba2);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-weight: 700;
                text-align: center;
                position: relative;
            }

            .hot-posts h3::after,
            .featured-subjects h3::after {
                content: '';
                position: absolute;
                bottom: -10px;
                left: 50%;
                transform: translateX(-50%);
                width: 80px;
                height: 4px;
                background: linear-gradient(45deg, #667eea, #764ba2);
                border-radius: 2px;
            }

            /* Cards */
            .card {
                width: 280px;
                background: rgba(255,255,255,0.95);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255,255,255,0.2);
                padding: 20px;
                border-radius: 20px;
                text-align: center;
                transition: all 0.3s ease;
                flex-shrink: 0;
                position: relative;
                overflow: hidden;
                box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            }

            .card::before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
                transition: left 0.5s ease;
            }

            .card:hover::before {
                left: 100%;
            }

            .card:hover {
                transform: translateY(-10px) scale(1.02);
                box-shadow: 0 20px 40px rgba(0,0,0,0.2);
            }

            .card img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                border-radius: 15px;
                margin-bottom: 15px;
                transition: transform 0.3s ease;
            }

            .card:hover img {
                transform: scale(1.1);
            }

            .card p {
                font-weight: 600;
                color: #333;
                margin-bottom: 10px;
                font-size: 1.1rem;
            }

            .card a {
                text-decoration: none;
                color: inherit;
            }

            /* Sidebar */
            .sidebar-item {
                background: rgba(255,255,255,0.95);
                backdrop-filter: blur(10px);
                padding: 30px;
                border-radius: 20px;
                margin-bottom: 30px;
                box-shadow: 0 8px 32px rgba(0,0,0,0.1);
                border: 1px solid rgba(255,255,255,0.2);
                transition: all 0.3s ease;
                animation: slideInRight 0.8s ease-out;
            }

            .sidebar-item:hover {
                transform: translateX(-5px);
                box-shadow: 0 12px 40px rgba(0,0,0,0.15);
            }

            .sidebar-item h3 {
                margin-top: 0;
                font-size: 1.8rem;
                background: linear-gradient(45deg, #667eea, #764ba2);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-weight: 700;
                margin-bottom: 20px;
            }

            .sidebar-item.contact-links a {
                display: block;
                margin-top: 15px;
                text-decoration: none;
                color: #333;
                transition: all 0.3s ease;
                padding: 10px 0;
                border-bottom: 1px solid rgba(0,0,0,0.1);
            }

            .sidebar-item.contact-links a:hover {
                color: #667eea;
                transform: translateX(5px);
            }

            .quick-link-item {
                display: flex;
                align-items: center;
                gap: 15px;
                margin-top: 15px;
                padding: 15px;
                background: rgba(102,126,234,0.1);
                border-radius: 10px;
                transition: all 0.3s ease;
            }

            .quick-link-item:hover {
                background: rgba(102,126,234,0.2);
                transform: translateX(5px);
            }

            .quick-link-item i {
                color: #667eea;
                font-size: 1.2rem;
            }

            /* Sliders */
            .slider-wrapper {
                display: flex;
                overflow-x: auto;
                scroll-snap-type: x mandatory;
                -webkit-overflow-scrolling: touch;
                gap: 20px;
                padding: 0 20px;
                scrollbar-width: none;
                -ms-overflow-style: none;
            }

            .slider-wrapper::-webkit-scrollbar {
                display: none;
            }

            .slider-wrapper .banner-container {
                flex: 0 0 calc(100% - 40px);
                scroll-snap-align: start;
            }

            /* Scrollable sections */
            .scroll-container {
                width: 100%;
                overflow-x: auto;
                padding-bottom: 20px;
                -webkit-overflow-scrolling: touch;
                scrollbar-width: thin;
                scrollbar-color: #667eea rgba(0,0,0,0.1);
            }

            .scroll-container::-webkit-scrollbar {
                height: 8px;
            }

            .scroll-container::-webkit-scrollbar-track {
                background: rgba(0,0,0,0.1);
                border-radius: 10px;
            }

            .scroll-container::-webkit-scrollbar-thumb {
                background: linear-gradient(45deg, #667eea, #764ba2);
                border-radius: 10px;
            }

            .scroll-content {
                display: flex;
                gap: 20px;
                padding: 20px;
            }

            /* Latest Posts styling */
            .latest-posts {
                padding: 25px;
            }

            .post-item {
                margin-bottom: 25px;
                padding: 20px;
                background: rgba(102,126,234,0.05);
                border-radius: 15px;
                border-left: 4px solid #667eea;
                transition: all 0.3s ease;
            }

            .post-item:hover {
                background: rgba(102,126,234,0.1);
                transform: translateX(5px);
            }

            .post-item:last-child {
                margin-bottom: 0;
            }

            .post-title {
                margin-bottom: 10px;
                line-height: 1.5;
                font-weight: 600;
            }

            .post-title a {
                color: #333;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .post-title a:hover {
                color: #667eea;
            }

            .post-date {
                color: #666;
                font-size: 0.9em;
                margin-top: 5px;
                font-style: italic;
            }

            /* Animations */
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

            @keyframes slideInRight {
                from {
                    opacity: 0;
                    transform: translateX(30px);
                }
                to {
                    opacity: 1;
                    transform: translateX(0);
                }
            }

            /* Responsive adjustments */
            @media (max-width: 1024px) {
                .content {
                    flex-direction: column;
                    padding: 20px;
                    gap: 30px;
                }

                .sidebar {
                    width: 100%;
                    min-width: auto;
                }

                .banner {
                    height: 300px;
                    margin: 10px;
                }

                .hot-posts h3,
                .featured-subjects h3 {
                    font-size: 2rem;
                }
            }

            @media (max-width: 768px) {
                .card {
                    width: 250px;
                }

                .banner {
                    height: 250px;
                }

                .hot-posts h3,
                .featured-subjects h3 {
                    font-size: 1.8rem;
                }

                .sidebar-item {
                    padding: 20px;
                }
            }

            @media (max-width: 480px) {
                .card {
                    width: 220px;
                }

                .content {
                    padding: 10px;
                }

                .banner {
                    margin: 5px;
                    height: 200px;
                }

                .hot-posts h3,
                .featured-subjects h3 {
                    font-size: 1.5rem;
                }
            }

            /* Floating elements */
            .floating-shapes {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                pointer-events: none;
                z-index: -1;
            }

            .shape {
                position: absolute;
                background: rgba(255,255,255,0.1);
                border-radius: 50%;
                animation: float 6s ease-in-out infinite;
            }

            .shape:nth-child(1) {
                width: 80px;
                height: 80px;
                top: 10%;
                left: 10%;
                animation-delay: 0s;
            }

            .shape:nth-child(2) {
                width: 120px;
                height: 120px;
                top: 20%;
                right: 10%;
                animation-delay: 2s;
            }

            .shape:nth-child(3) {
                width: 60px;
                height: 60px;
                bottom: 10%;
                left: 20%;
                animation-delay: 4s;
            }

            @keyframes float {
                0%, 100% { transform: translateY(0px); }
                50% { transform: translateY(-20px); }
            }
        </style>
        <script src="https://kit.fontawesome.com/8807c30b90.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- Floating background shapes -->
        <div class="floating-shapes">
            <div class="shape"></div>
            <div class="shape"></div>
            <div class="shape"></div>
        </div>

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
                    <h3>? Hot Posts</h3>
                    <div class="scroll-container">
                        <div class="scroll-content">
                            <c:forEach items="${listPost}" var="o">
                                <div class="card">
                                    <a href="blogDetail?bid=${o.id}">
                                        <img src="${o.thumbnail_url}" alt="${o.title}">
                                        <p>${o.title}</p>
                                    </a>
                                    <p class="post-date">${o.created_date}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="featured-subjects">
                    <h3>? Featured Subjects</h3>
                    <div class="scroll-container">
                        <div class="scroll-content">
                            <c:forEach items="${listCourse}" var="o">
                                <div class="card">
                                    <a href="lesson?courseId=${o.id}">
                                        <img src="${o.thumbnailUrl}" alt="${o.title}">
                                        <p>${o.title}</p>
                                    </a>
                                    <p class="post-date">${o.tagline}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sidebar -->
            <div class="sidebar">
                <div class="sidebar-item latest-posts">
                    <h3>? Latest Posts</h3>
                    <c:forEach items="${listLastPost}" var="o">                             
                        <div class="post-item">
                            <p class="post-title">
                                <a href="blogDetail?bid=${o.id}">${o.title}</a>
                            </p>
                            <p class="post-date">
                                ? ${o.created_date}
                            </p>
                        </div>
                    </c:forEach>
                </div>
                
                <div class="sidebar-item contact-links">
                    <h3>? Quick Links</h3>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-envelope"></i>
                        <a href="mailto:abcxyz@gmail.com">abcxyz@gmail.com</a>
                    </div>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-phone"></i>
                        <a href="tel:0123456789">0123456789</a>
                    </div>
                    <div class="quick-link-item">
                        <i class="fa-solid fa-users"></i>
                        <a href="#">About Us</a>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>