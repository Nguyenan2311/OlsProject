<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${course.subtitle} - Course Lessons</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            :root {
                --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                --secondary-gradient: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                --accent-gradient: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
                --success-gradient: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
                --warning-gradient: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
                --primary-color: #667eea;
                --secondary-color: #764ba2;
                --accent-color: #4facfe;
                --light-color: #f8fafc;
                --dark-color: #1a202c;
                --success-color: #43e97b;
                --warning-color: #fa709a;
                --danger-color: #ef4444;
                --border-radius: 12px;
                --border-radius-small: 8px;
                --box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
                --box-shadow-hover: 0 15px 35px rgba(0, 0, 0, 0.15);
                --transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
                --transition-bounce: all 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
            }

            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
                margin: 0;
                padding: 0;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                color: var(--dark-color);
                line-height: 1.6;
            }

            .sidebar {
                width: 380px;
                background: rgba(255, 255, 255, 0.98);
                backdrop-filter: blur(20px);
                height: 100vh;
                overflow-y: auto;
                padding: 30px;
                box-shadow: var(--box-shadow);
                position: sticky;
                top: 0;
                transition: var(--transition);
                z-index: 100;
                border-right: 1px solid rgba(255, 255, 255, 0.2);
            }

            .sidebar::-webkit-scrollbar {
                width: 6px;
            }

            .sidebar::-webkit-scrollbar-track {
                background: rgba(0, 0, 0, 0.05);
                border-radius: 10px;
            }

            .sidebar::-webkit-scrollbar-thumb {
                background: var(--primary-gradient);
                border-radius: 10px;
            }

            .sidebar-header {
                padding-bottom: 25px;
                border-bottom: 2px solid rgba(102, 126, 234, 0.1);
                margin-bottom: 30px;
                text-align: center;
            }

            .sidebar-header h2 {
                background: var(--primary-gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-size: 1.6rem;
                font-weight: 700;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 10px;
            }

            .sidebar-header h2 i {
                margin-right: 12px;
                background: var(--accent-gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-size: 1.4rem;
            }

            .course-progress {
                background: rgba(102, 126, 234, 0.1);
                border-radius: var(--border-radius);
                padding: 12px;
                margin-top: 10px;
            }

            .course-progress-text {
                font-size: 0.85rem;
                color: var(--primary-color);
                font-weight: 500;
                margin-bottom: 8px;
            }

            .course-progress-bar {
                height: 6px;
                background: rgba(102, 126, 234, 0.2);
                border-radius: 10px;
                overflow: hidden;
            }

            .course-progress-fill {
                height: 100%;
                background: var(--primary-gradient);
                width: 65%;
                border-radius: 10px;
                transition: width 0.8s ease;
            }

            .module {
                margin-bottom: 20px;
                border-radius: var(--border-radius);
                overflow: hidden;
                background: white;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                transition: var(--transition);
                border: 1px solid rgba(255, 255, 255, 0.5);
            }

            .module:hover {
                transform: translateY(-2px);
                box-shadow: var(--box-shadow-hover);
            }

            .module-title {
                font-weight: 600;
                padding: 18px 20px;
                background: var(--primary-gradient);
                color: white;
                cursor: pointer;
                display: flex;
                justify-content: space-between;
                align-items: center;
                transition: var(--transition);
                position: relative;
                overflow: hidden;
            }

            .module-title::before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
                transition: left 0.5s ease;
            }

            .module-title:hover::before {
                left: 100%;
            }

            .module-title:hover {
                background: var(--secondary-gradient);
            }

            .module-title i {
                transition: var(--transition-bounce);
                font-size: 1.1rem;
            }

            .module-title.collapsed i {
                transform: rotate(-90deg);
            }

            .module-number {
                background: rgba(255, 255, 255, 0.25);
                padding: 4px 10px;
                border-radius: 20px;
                font-size: 0.8rem;
                font-weight: 600;
                margin-right: 12px;
            }

            .chapter {
                padding: 15px 20px;
                border-left: 4px solid rgba(102, 126, 234, 0.1);
                background: linear-gradient(135deg, rgba(102, 126, 234, 0.02) 0%, rgba(255, 255, 255, 0.8) 100%);
                transition: var(--transition);
            }

            .chapter-title {
                font-weight: 600;
                padding: 12px 0;
                color: var(--dark-color);
                display: flex;
                align-items: center;
                border-radius: var(--border-radius-small);
                margin-bottom: 10px;
                transition: var(--transition);
                font-size: 0.95rem;
            }

            .chapter-title i {
                margin-right: 10px;
                background: var(--accent-gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-size: 1rem;
            }

            .subchapter-title {
                font-weight: 500;
                padding: 10px 0 10px 20px;
                color: #64748b;
                display: flex;
                align-items: center;
                font-size: 0.9rem;
                margin: 8px 0;
                position: relative;
            }

            .subchapter-title::before {
                content: '';
                position: absolute;
                left: 0;
                top: 50%;
                width: 12px;
                height: 2px;
                background: var(--accent-color);
                border-radius: 1px;
            }

            .subchapter-title i {
                margin-right: 8px;
                color: var(--accent-color);
                font-size: 0.85rem;
            }

            .lessons {
                margin-left: 0;
                padding-left: 0;
            }

            .sub-lessons {
                margin-left: 20px;
                padding-left: 20px;
                border-left: 2px solid rgba(102, 126, 234, 0.1);
            }

            .lesson-item {
                padding: 12px 16px;
                margin-bottom: 6px;
                display: flex;
                align-items: center;
                text-decoration: none;
                color: #475569;
                border-radius: var(--border-radius-small);
                transition: var(--transition);
                font-size: 0.9rem;
                font-weight: 500;
                position: relative;
                background: white;
                border: 1px solid rgba(0, 0, 0, 0.05);
            }

            .lesson-item:hover {
                background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(255, 255, 255, 0.9) 100%);
                color: var(--primary-color);
                transform: translateX(8px);
                box-shadow: 0 4px 15px rgba(102, 126, 234, 0.15);
            }

            .lesson-item.active {
                background: var(--primary-gradient);
                color: white;
                font-weight: 600;
                box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
                transform: translateX(5px);
            }

            .lesson-item.active::before {
                content: '';
                position: absolute;
                left: 0;
                top: 0;
                bottom: 0;
                width: 4px;
                background: var(--success-gradient);
                border-radius: 4px 0 0 4px;
            }

            .lesson-item i {
                margin-right: 10px;
                font-size: 0.9rem;
                min-width: 16px;
                text-align: center;
            }

            .lesson-item.active i {
                animation: pulse 2s infinite;
            }

            @keyframes pulse {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.1);
                }
                100% {
                    transform: scale(1);
                }
            }

            .lesson-number {
                background: rgba(102, 126, 234, 0.1);
                color: var(--primary-color);
                padding: 2px 8px;
                border-radius: 12px;
                font-size: 0.75rem;
                font-weight: 600;
                margin-right: 8px;
                min-width: 24px;
                text-align: center;
            }

            .lesson-item.active .lesson-number {
                background: rgba(255, 255, 255, 0.25);
                color: white;
            }

            .content-area {
                flex: 1;
                padding: 40px;
                overflow-y: auto;
                height: 100vh;
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(20px);
            }

            .content-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 30px;
                padding: 25px 30px;
                background: white;
                border-radius: var(--border-radius);
                box-shadow: var(--box-shadow);
                border: 1px solid rgba(255, 255, 255, 0.5);
            }

            .content-header h1 {
                background: var(--primary-gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-size: 2rem;
                font-weight: 700;
                margin: 0;
            }

            .progress-container {
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .progress-text {
                font-size: 0.9rem;
                color: var(--primary-color);
                font-weight: 600;
            }

            .progress-bar-container {
                background: rgba(102, 126, 234, 0.1);
                border-radius: 20px;
                height: 8px;
                width: 200px;
                overflow: hidden;
            }

            .progress-bar {
                height: 100%;
                background: var(--success-gradient);
                transition: width 0.8s ease;
                border-radius: 20px;
            }

            .content-tabs {
                display: flex;
                margin-bottom: 30px;
                background: white;
                border-radius: var(--border-radius);
                padding: 8px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                border: 1px solid rgba(255, 255, 255, 0.5);
            }

            .tab {
                padding: 14px 25px;
                background: transparent;
                border-radius: var(--border-radius-small);
                text-decoration: none;
                color: #64748b;
                transition: var(--transition);
                font-weight: 500;
                position: relative;
                display: flex;
                align-items: center;
                gap: 8px;
                flex: 1;
                justify-content: center;
            }

            .tab:hover {
                color: var(--primary-color);
                background: rgba(102, 126, 234, 0.05);
            }

            .tab.active {
                color: white;
                background: var(--primary-gradient);
                box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
            }

            .content-display {
                min-height: 500px;
                padding: 35px;
                border-radius: var(--border-radius);
                background: white;
                box-shadow: var(--box-shadow);
                margin-bottom: 30px;
                border: 1px solid rgba(255, 255, 255, 0.5);
            }

            .video-container {
                position: relative;
                padding-bottom: 56.25%;
                height: 0;
                overflow: hidden;
                border-radius: var(--border-radius);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
                margin-bottom: 25px;
            }

            .video-container iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border: none;
                border-radius: var(--border-radius);
            }

            .video-description {
                margin-top: 20px;
                padding: 20px;
                background: linear-gradient(135deg, rgba(79, 172, 254, 0.1) 0%, rgba(0, 242, 254, 0.1) 100%);
                border-radius: var(--border-radius);
                border-left: 4px solid var(--accent-color);
            }

            .empty-state {
                text-align: center;
                padding: 80px 20px;
                color: #64748b;
            }

            .empty-state i {
                font-size: 4rem;
                background: var(--accent-gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                margin-bottom: 20px;
            }

            .empty-state h3 {
                font-weight: 600;
                margin-bottom: 15px;
                color: var(--dark-color);
                font-size: 1.5rem;
            }

            .empty-state p {
                font-size: 1.1rem;
                color: #64748b;
            }

            @media (max-width: 768px) {
                body {
                    flex-direction: column;
                }

                .sidebar {
                    width: 100%;
                    height: auto;
                    max-height: 400px;
                    position: relative;
                }

                .content-area {
                    padding: 20px;
                }

                .content-header {
                    flex-direction: column;
                    gap: 20px;
                    text-align: center;
                }

                .content-header h1 {
                    font-size: 1.5rem;
                }
            }

            /* Loading animation */
            @keyframes shimmer {
                0% {
                    transform: translateX(-100%);
                }
                100% {
                    transform: translateX(100%);
                }
            }

            .loading-shimmer {
                position: relative;
                overflow: hidden;
            }

            .loading-shimmer::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.4), transparent);
                animation: shimmer 1.5s infinite;
            }
        </style>
    </head>

    <body>
        <%@include file = "header.jsp" %>
        <div class="main-flex" style="display: flex;">
            <div class="sidebar">
                <div class="sidebar-header">
                    <h2><i class="fas fa-graduation-cap"></i> ${course.subtitle}</h2>

                </div>

                <c:forEach items="${listModule}" var="module">
                    <div class="module">
                        <div class="module-title" onclick="toggleModule(this)">
                            <div style="display: flex; align-items: center;">
                                <span class="module-number">${module.order}</span>
                                <span>${module.name}</span>
                            </div>
                            <i class="fas fa-chevron-down"></i>
                        </div>

                        <div class="chapter">
                            <c:forEach items="${listChapter}" var="chapter">
                                <c:if test="${chapter.moduleId == module.id}">
                                    <div class="chapter-title">
                                        <i class="fas fa-folder-open"></i>
                                        <span>Chapter ${chapter.order}: ${chapter.subtitle}</span>
                                    </div>

                                    <!-- Lessons trực tiếp trong Chapter -->
                                    <div class="lessons">
                                        <c:forEach items="${listLesson}" var="lesson">
                                            <c:if test="${lesson.chapterId == chapter.id && lesson.subChapterId==0}">
                                                <a href="?courseId=${param.courseId}&lessonId=${lesson.id}"
                                                   class="lesson-item ${param.lessonId == lesson.id ? 'active' : ''}">
                                                    <i class="fas fa-play-circle"></i>
                                                    <span class="lesson-number">${lesson.order}</span>
                                                    <span>${lesson.title}</span>
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <!-- SubChapters -->
                                    <c:forEach items="${listSubChapter}" var="sub">
                                        <c:if test="${sub.chapterId == chapter.id}">
                                            <div class="subchapter-title">
                                                <i class="fas fa-folder"></i>
                                                <span>SubChapter ${sub.order}: ${sub.title}</span>
                                            </div>

                                            <!-- Lessons trong SubChapter -->
                                            <div class="lessons sub-lessons">
                                                <c:forEach items="${listLesson}" var="lesson">
                                                    <c:if test="${lesson.subChapterId == sub.id}">
                                                        <a href="?courseId=${param.courseId}&lessonId=${lesson.id}"
                                                           class="lesson-item ${param.lessonId == lesson.id ? 'active' : ''}">
                                                            <i class="fas fa-play-circle"></i>
                                                            <span class="lesson-number">${lesson.order}</span>
                                                            <span>${lesson.title}</span>
                                                        </a>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="content-area">
                <div class="content-header">
                    <h1>Lesson Content</h1>
                </div>

                <c:if test="${not empty param.lessonId}">
                    <div class="content-tabs">
                        <a href="?courseId=${param.courseId}&lessonId=${param.lessonId}&contentType=text" 
                           class="tab ${param.contentType != 'video' ? 'active' : ''}">
                            <i class="fas fa-file-alt"></i> Text Content
                        </a>
                        <a href="?courseId=${param.courseId}&lessonId=${param.lessonId}&contentType=video" 
                           class="tab ${param.contentType == 'video' ? 'active' : ''}">
                            <i class="fas fa-video"></i> Video Content
                        </a>
                    </div>

                    <div class="content-display">
                        <c:forEach items="${listLessonContent}" var="lc">
                            <c:if test="${lc.lessonId == param.lessonId}">
                                <c:choose>
                                    <c:when test="${param.contentType == 'video' && not empty lc.videoId}">
                                        <div class="video-container">
                                            <iframe src="https://www.youtube.com/embed/${lc.videoId}" 
                                                    frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                                    allowfullscreen></iframe>
                                        </div>
                                        <c:if test="${not empty lc.videoDescription}">
                                            <div class="video-description">
                                                <p><i class="fas fa-info-circle"></i> ${lc.videoDescription}</p>
                                            </div>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-content">
                                            ${lc.textContent}
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${empty param.lessonId}">
                    <div class="empty-state">
                        <i class="fas fa-book-reader"></i>
                        <h3>Ready to Learn?</h3>
                        <p>Select a lesson from the sidebar to begin your learning journey</p>
                    </div>
                </c:if>
            </div>
        </div>

        <script>
            function toggleModule(element) {
                element.classList.toggle('collapsed');
                const chapter = element.nextElementSibling;

                if (chapter.style.display === 'none') {
                    chapter.style.display = 'block';
                    chapter.style.animation = 'slideDown 0.3s ease';
                } else {
                    chapter.style.animation = 'slideUp 0.3s ease';
                    setTimeout(() => {
                        chapter.style.display = 'none';
                    }, 300);
                }
            }

            // Add CSS for animations
            const style = document.createElement('style');
            style.textContent = `
                @keyframes slideDown {
                    from { opacity: 0; transform: translateY(-10px); }
                    to { opacity: 1; transform: translateY(0); }
                }
                
                @keyframes slideUp {
                    from { opacity: 1; transform: translateY(0); }
                    to { opacity: 0; transform: translateY(-10px); }
                }
            `;
            document.head.appendChild(style);

            // Initialize - collapse all modules by default
            document.addEventListener('DOMContentLoaded', function () {
                const modules = document.querySelectorAll('.module-title');
                modules.forEach(module => {
                    module.classList.add('collapsed');
                    const chapter = module.nextElementSibling;
                    chapter.style.display = 'none';
                });

                // Expand the module containing the active lesson
                const activeLesson = document.querySelector('.lesson-item.active');
                if (activeLesson) {
                    let module = activeLesson.closest('.module').querySelector('.module-title');
                    module.classList.remove('collapsed');
                    module.nextElementSibling.style.display = 'block';

                    // Scroll to active lesson with smooth animation
                    setTimeout(() => {
                        activeLesson.scrollIntoView({
                            behavior: 'smooth',
                            block: 'center',
                            inline: 'nearest'
                        });
                    }, 300);
                }

                // Add hover effects to lesson items
                const lessonItems = document.querySelectorAll('.lesson-item');
                lessonItems.forEach(item => {
                    item.addEventListener('mouseenter', function () {
                        this.style.transform = 'translateX(8px) scale(1.02)';
                    });

                    item.addEventListener('mouseleave', function () {
                        if (!this.classList.contains('active')) {
                            this.style.transform = 'translateX(0) scale(1)';
                        }
                    });
                });
            });
        </script>
    </body>
</html>