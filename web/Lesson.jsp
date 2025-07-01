<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${course.subtitle} - Course Lessons</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4361ee;
            --secondary-color: #3f37c9;
            --accent-color: #4cc9f0;
            --light-color: #f8f9fa;
            --dark-color: #212529;
            --success-color: #4bb543;
            --warning-color: #f8961e;
            --danger-color: #ef233c;
            --border-radius: 8px;
            --box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }
        
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        
        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            background-color: #f5f7fa;
            color: var(--dark-color);
            line-height: 1.6;
        }
        
        .sidebar {
            width: 320px;
            background-color: white;
            height: 100vh;
            overflow-y: auto;
            padding: 25px;
            box-shadow: var(--box-shadow);
            position: sticky;
            top: 0;
            transition: var(--transition);
            z-index: 10;
        }
        
        .sidebar-header {
            padding-bottom: 20px;
            border-bottom: 1px solid rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        .sidebar-header h2 {
            color: var(--primary-color);
            font-size: 1.5rem;
            font-weight: 600;
            display: flex;
            align-items: center;
        }
        
        .sidebar-header h2 i {
            margin-right: 10px;
            color: var(--accent-color);
        }
        
        .module {
            margin-bottom: 25px;
            border-radius: var(--border-radius);
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }
        
        .module-title {
            font-weight: 500;
            padding: 12px 15px;
            background-color: var(--primary-color);
            color: white;
            border-radius: var(--border-radius);
            cursor: pointer;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: var(--transition);
        }
        
        .module-title:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
        }
        
        .module-title i {
            transition: transform 0.3s ease;
        }
        
        .module-title.collapsed i {
            transform: rotate(-90deg);
        }
        
        .chapter {
            margin-top: 8px;
            padding-left: 10px;
            border-left: 2px solid #e9ecef;
            transition: var(--transition);
        }
        
        .chapter-title {
            font-weight: 500;
            padding: 8px 10px;
            color: var(--dark-color);
            display: flex;
            align-items: center;
            border-radius: 4px;
            margin: 5px 0;
            transition: var(--transition);
        }
        
        .chapter-title:hover {
            background-color: #f1f3f8;
        }
        
        .chapter-title i {
            margin-right: 8px;
            color: var(--accent-color);
            font-size: 0.9rem;
        }
        
        .lessons {
            margin-left: 15px;
        }
        
        .lesson-item {
            padding: 8px 15px;
            margin-bottom: 5px;
            display: block;
            text-decoration: none;
            color: #495057;
            border-radius: 4px;
            transition: var(--transition);
            font-size: 0.95rem;
            position: relative;
        }
        
        .lesson-item:hover {
            background-color: #edf2ff;
            color: var(--primary-color);
            transform: translateX(5px);
        }
        
        .lesson-item.active {
            background-color: var(--primary-color);
            color: white;
            font-weight: 500;
            box-shadow: 0 3px 8px rgba(67, 97, 238, 0.3);
        }
        
        .lesson-item.active:before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            bottom: 0;
            width: 3px;
            background-color: var(--accent-color);
            border-radius: 3px 0 0 3px;
        }
        
        .lesson-item i {
            margin-right: 8px;
            font-size: 0.8rem;
        }
        
        .content-area {
            flex: 1;
            padding: 30px;
            overflow-y: auto;
            height: 100vh;
            background-color: white;
        }
        
        .content-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 1px solid #e9ecef;
        }
        
        .content-header h1 {
            color: var(--dark-color);
            font-size: 1.8rem;
            font-weight: 600;
        }
        
        .progress-container {
            background-color: #e9ecef;
            border-radius: 20px;
            height: 10px;
            width: 200px;
            overflow: hidden;
        }
        
        .progress-bar {
            height: 100%;
            background-color: var(--success-color);
            transition: width 0.5s ease;
        }
        
        .content-tabs {
            display: flex;
            margin-bottom: 25px;
            border-bottom: 1px solid #e9ecef;
        }
        
        .tab {
            padding: 12px 25px;
            background-color: transparent;
            margin-right: 5px;
            border-radius: var(--border-radius) var(--border-radius) 0 0;
            text-decoration: none;
            color: #6c757d;
            transition: var(--transition);
            font-weight: 500;
            position: relative;
        }
        
        .tab:hover {
            color: var(--primary-color);
            background-color: #f8f9fa;
        }
        
        .tab.active {
            color: var(--primary-color);
            background-color: transparent;
        }
        
        .tab.active:after {
            content: '';
            position: absolute;
            bottom: -1px;
            left: 0;
            right: 0;
            height: 3px;
            background-color: var(--primary-color);
            border-radius: 3px 3px 0 0;
        }
        
        .content-display {
            min-height: 400px;
            padding: 25px;
            border-radius: var(--border-radius);
            background-color: white;
            box-shadow: var(--box-shadow);
            margin-bottom: 30px;
        }
        
        .video-container {
            position: relative;
            padding-bottom: 56.25%;
            height: 0;
            overflow: hidden;
            border-radius: var(--border-radius);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
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
            margin-top: 15px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: var(--border-radius);
            border-left: 4px solid var(--accent-color);
        }
        
        .text-content {
            font-size: 1rem;
            line-height: 1.8;
        }
        
        .text-content h2, .text-content h3 {
            color: var(--primary-color);
            margin: 20px 0 15px;
        }
        
        .text-content p {
            margin-bottom: 15px;
        }
        
        .text-content ul, .text-content ol {
            margin: 15px 0;
            padding-left: 25px;
        }
        
        .text-content li {
            margin-bottom: 8px;
        }
        
        .text-content pre {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: var(--border-radius);
            overflow-x: auto;
            margin: 15px 0;
            font-family: 'Courier New', monospace;
        }
        
        .text-content code {
            font-family: 'Courier New', monospace;
            background-color: #f8f9fa;
            padding: 2px 5px;
            border-radius: 3px;
            font-size: 0.9rem;
        }
        
        .empty-state {
            text-align: center;
            padding: 50px 20px;
            color: #6c757d;
        }
        
        .empty-state i {
            font-size: 3rem;
            color: #adb5bd;
            margin-bottom: 15px;
        }
        
        .empty-state h3 {
            font-weight: 500;
            margin-bottom: 10px;
            color: #495057;
        }
        
        .navigation-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }
        
        .btn {
            padding: 10px 20px;
            border-radius: var(--border-radius);
            border: none;
            font-weight: 500;
            cursor: pointer;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
        }
        
        .btn i {
            margin-right: 8px;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(67, 97, 238, 0.3);
        }
        
        .btn-outline {
            background-color: transparent;
            border: 1px solid var(--primary-color);
            color: var(--primary-color);
        }
        
        .btn-outline:hover {
            background-color: #edf2ff;
        }
        
        @media (max-width: 768px) {
            body {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
                height: auto;
                max-height: 300px;
                position: relative;
            }
            
            .content-area {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            <h2><i class="fas fa-book-open"></i> ${course.subtitle}</h2>
        </div>
        
        <c:forEach items="${listModule}" var="module">
            <div class="module">
                <div class="module-title" onclick="toggleModule(this)">
                    <span>Module ${module.order}: ${module.name}</span>
                    <i class="fas fa-chevron-down"></i>
                </div>
                
                <div class="chapter">
                    <c:forEach items="${listChapter}" var="chapter">
                        <c:if test="${chapter.moduleId == module.id}">
                            <div class="chapter-title">
                                <i class="fas fa-list-ul"></i>
                                <span>Chapter ${chapter.order}: ${chapter.subtitle}</span>
                            </div>
                            
                            <div class="lessons">
                                <c:forEach items="${listLesson}" var="lesson">
                                    <c:if test="${lesson.chapterId == chapter.id}">
                                        <a href="?courseId=${param.courseId}&lessonId=${lesson.id}" 
                                           class="lesson-item ${param.lessonId == lesson.id ? 'active' : ''}">
                                            <i class="fas fa-play-circle"></i>
                                            Lesson ${lesson.order}: ${lesson.title}
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="content-area">
        <div class="content-header">
            <h1>Lesson Content</h1>
            <div class="progress-container">
                <div class="progress-bar" style="width: 65%"></div>
            </div>
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
                <i class="far fa-folder-open"></i>
                <h3>No Lesson Selected</h3>
                <p>Please select a lesson from the sidebar to view its content</p>
            </div>
        </c:if>
    </div>
    
    <script>
        function toggleModule(element) {
            element.classList.toggle('collapsed');
            const chapter = element.nextElementSibling;
            chapter.style.display = chapter.style.display === 'none' ? 'block' : 'none';
        }
        
        // Initialize - collapse all modules by default
        document.addEventListener('DOMContentLoaded', function() {
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
                
                // Scroll to active lesson
                activeLesson.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        });
    </script>
</body>
</html>