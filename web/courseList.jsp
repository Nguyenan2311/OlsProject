<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Course List - EDEMY</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <!-- Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
  
            /* Phần Featured */
            .sidebar-section h5 {
                font-size: 1.1rem;
                font-weight: 600;
                margin-bottom: 0.75rem;
                color: #333;
            }
            /* Liên kết Sort by Date */
            #dateSortLink {
                display: block;
                color: #0d6efd;
                text-decoration: none;
                font-size: 1rem;
                padding: 0.25rem 0;
                margin-bottom: 0.5rem;
                transition: color 0.2s ease;
            }
            #dateSortLink:hover {
                color: #0b5ed7;
                text-decoration: underline;
            }
            .sidebar-search-input {
                border-right: none;
                width: 100px;
            }
            .sidebar-search-btn {
                border-left: none;
                background: transparent;
            }
            .btn-search-main {
                background-color: #17a2b8;
                border-color: #17a2b8;
                width: 100%;
                font-weight: 500;
                padding: 0.5rem;
            }
            .category-link, .featured-link {
                display: block;
                color: #0d6efd;
                text-decoration: none;
                font-size: 1rem;
                padding: 0.25rem 0;
                transition: color 0.2s ease, font-weight 0.2s ease;
            }
            .category-link:hover, .featured-link:hover {
                color: #0b5ed7;
                text-decoration: underline;
            }
            .featured-link.fw-bold {
                font-weight: 600;
            }

            .contact-info p {
                margin-bottom: 0.25rem;
            }
            .contact-info a {
                margin-right: 0.5rem;
                text-decoration: none;
            }
            .contact-info a:hover {
                text-decoration: underline;
            }
            .display-controls {
                display: flex;
                align-items: center;
                margin-bottom: 1.5rem;
                flex-wrap: wrap;
            }
            .display-controls .form-check-inline {
                margin-right: 1.5rem;
                margin-bottom: 0.5rem;
            }
            .input-group .form-control {
                text-align: center;
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
                padding: 1rem;
            }
            .course-card .card-title {
                font-weight: 500;
                color: #0d6efd;
                font-size: 1.1rem;
                margin-bottom: 0.5rem;
            }
            .course-card .card-text {
                font-size: 0.9rem;
                color: #6c757d;
                margin-bottom: 0.75rem;
            }
            .course-card .public-date {
                font-size: 0.8rem;
                color: #6c757d;
                margin-bottom: 0.75rem;
            }
            .course-card .price .price-original {
                text-decoration: line-through;
                color: #6c757d;
                margin-right: 0.5rem;
                font-size: 0.9rem;
            }
            .course-card .price .price-sale {
                font-weight: 700;
                font-size: 1.25rem;
                color: #212529;
            }
            .btn-register {
                background-color: #28a745;
                border-color: #28a745;
                color: white;
                width: 100%;
                font-weight: 500;
            }
            .btn-register:hover {
                background-color: #218838;
                border-color: #1e7e34;
            }
            .pagination .page-link {
                color: #0d6efd;
            }
            .pagination .page-item.active .page-link {
                background-color: #0d6efd;
                border-color: #0d6efd;
            }
            .pagination .page-item.disabled .page-link {
                color: #6c757d;
            }
            /* Dropdown Date Options */
            .date-options {
                position: absolute;
                background-color: #fff;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 5px 0;
                z-index: 1000;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-top: 2px;
            }
            .date-option {
                display: block;
                padding: 5px 15px;
                text-decoration: none;
                color: #26231d;
                font-size: 0.95rem;
            }
            .date-option:hover {
                background-color: #f8f9fa;
                text-decoration: underline;
            }
        </style>
    </head>
      <%@include file = "header.jsp" %>
    <body>
      
        <div class="container mt-4">
            <h1 class="page-title">Course List</h1>
            <div class="row">
                <!-- ===== LEFT SIDEBAR ===== -->
                <div class="col-lg-3">
                    <form action="courses" method="GET" id="filterForm" novalidate=""></form>
                    <div class="sidebar-section">
                        <h5>Search course</h5>
                        <div class="input-group mb-2">
                            <input type="text" class="form-control sidebar-search-input" placeholder="Search..." 
                                   name="search" value="${searchKeyword}" form="filterForm">
                        </div>
                        <button type="submit" class="btn btn-search-main" form="filterForm">Search</button>
                    </div>

                    <!-- START: MODIFIED CATEGORY LINKS -->
                    <div class="sidebar-section">
                        <h5>Categories</h5>
                        <%-- Link for "All Categories" remains the same --%>
                        <c:url var="allCategoriesUrl" value="courses">
                            <c:param name="search" value="${searchKeyword}"/>
                            <c:param name="tag" value="${selectedTag}"/>
                            <c:param name="sortBy" value="${sortBy}"/>
                            <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                            <c:param name="showThumbnail" value="${showThumbnail}"/>
                            <c:param name="showTitle" value="${showTitle}"/>
                            <c:param name="showPrice" value="${showPrice}"/>
                            <c:param name="showTagline" value="${showTagline}"/>
                            <c:param name="showPublicDate" value="${showPublicDate}"/>
                        </c:url>
                        <a href="${allCategoriesUrl}" class="category-link ${empty selectedCategory ? 'fw-bold' : ''}">All Categories</a>

                        <%-- Loop through each category with new logic --%>
                        <c:forEach var="cat" items="${categories}">
                            <c:choose>
                                <%-- Case 1: The category is ALREADY selected. Create a link to DESELECT it. --%>
                                <c:when test="${cat eq selectedCategory}">
                                    <c:url var="categoryUrl" value="courses">
                                        <%-- Create URL without the 'category' param to clear the filter --%>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="tag" value="${selectedTag}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/>
                                    </c:url>
                                    <%-- The link is bold because it's the active filter --%>
                                    <a href="${categoryUrl}" class="category-link fw-bold">${cat}</a>
                                </c:when>
                                <%-- Case 2: The category is NOT selected. Create a link to SELECT it. --%>
                                <c:otherwise>
                                    <c:url var="categoryUrl" value="courses">
                                        <%-- Create URL with the 'category' param to apply the filter --%>
                                        <c:param name="category" value="${cat}"/>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="tag" value="${selectedTag}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/>
                                    </c:url>
                                    <%-- The link is not bold --%>
                                    <a href="${categoryUrl}" class="category-link">${cat}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <!-- START: MODIFIED FEATURED LINKS -->
                    <div class="sidebar-section">
                        <h5>Featured</h5>
                        <%-- Phần sắp xếp theo ngày giữ nguyên --%>
                        <div class="mb-2">
                            <a href="#" id="dateSortLink" class="category-link" onclick="toggleDateOptions(event)">Sort by Date</a>
                            <div id="dateOptions" class="date-options" style="display: none;">
                                <c:url var="newestUrl" value="courses">
                                    <c:param name="search" value="${searchKeyword}"/>
                                    <c:param name="category" value="${selectedCategory}"/>
                                    <c:param name="sortBy" value="newest"/>
                                    <c:param name="tag" value="${selectedTag}"/>
                                    <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                    <c:param name="showThumbnail" value="${showThumbnail}"/>
                                    <c:param name="showTitle" value="${showTitle}"/>
                                    <c:param name="showPrice" value="${showPrice}"/>
                                    <c:param name="showTagline" value="${showTagline}"/>
                                    <c:param name="showPublicDate" value="${showPublicDate}"/>
                                </c:url>
                                <a href="${newestUrl}" class="date-option ${sortBy == 'newest' ? 'fw-bold' : ''}" onclick="hideDateOptions()">Newest</a>

                                <c:url var="oldestUrl" value="courses">
                                    <c:param name="search" value="${searchKeyword}"/>
                                    <c:param name="category" value="${selectedCategory}"/>
                                    <c:param name="sortBy" value="oldest"/>
                                    <c:param name="tag" value="${selectedTag}"/>
                                    <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                    <c:param name="showThumbnail" value="${showThumbnail}"/>
                                    <c:param name="showTitle" value="${showTitle}"/>
                                    <c:param name="showPrice" value="${showPrice}"/>
                                    <c:param name="showTagline" value="${showTagline}"/>
                                    <c:param name="showPublicDate" value="${showPublicDate}"/>
                                </c:url>
                                <a href="${oldestUrl}" class="date-option ${sortBy == 'oldest' ? 'fw-bold' : ''}" onclick="hideDateOptions()">Oldest</a>
                            </div>
                        </div>

                        <%-- Vòng lặp qua các thẻ với logic toggle mới --%>
                        <c:forEach var="tagline" items="${taglines}">
                            <c:choose>
                                <%-- Trường hợp 1: Thẻ này ĐÃ được chọn. Tạo liên kết để BỎ CHỌN nó. --%>
                                <c:when test="${selectedTag == tagline.id}">
                                    <c:url var="tagUrl" value="courses">
                                        <%-- URL không có tham số 'tag' để xóa bộ lọc này --%>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="category" value="${selectedCategory}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/>
                                    </c:url>
                                    <%-- Liên kết được in đậm vì nó là bộ lọc đang hoạt động --%>
                                    <a href="${tagUrl}" class="featured-link fw-bold">${tagline.name}</a>
                                </c:when>

                                <%-- Trường hợp 2: Thẻ này CHƯA được chọn. Tạo liên kết để CHỌN nó. --%>
                                <c:otherwise>
                                    <c:url var="tagUrl" value="courses">
                                        <%-- URL với tham số 'tag' để áp dụng bộ lọc --%>
                                        <c:param name="tag" value="${tagline.id}"/>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="category" value="${selectedCategory}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/>
                                    </c:url>
                                    <%-- Liên kết không được in đậm --%>
                                    <a href="${tagUrl}" class="featured-link">${tagline.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <!-- END: MODIFIED FEATURED LINKS -->
                    <div class="sidebar-section contact-info">
                        <h5>Contact</h5>
                        <p>Email: abc@gmail.com</p>
                        <p>Phone: 012345678</p>
                        <div>
                            <a href="https://www.facebook.com/">Facebook</a>|
                            <a href="https://www.youtube.com/">Github</a>|
                            <a href="https://github.com/">Youtube</a>
                        </div>
                    </div>
                </div>
                <!-- ===== RIGHT CONTENT AREA ===== -->
                <div class="col-lg-9">
                    <div class="display-controls">
                        <div class="input-group" style="width: auto;">
                            <label for="rowsPerPage" class="input-group-text">Courses per page</label>
                            <input type="number" class="form-control" id="rowsPerPage" name="rowsPerPage" 
                                   value="${rowsPerPage}" min="1" max="100" form="filterForm">
                            <button class="btn btn-info text-white" type="submit" form="filterForm">Choose</button>
                        </div>
                        <div class="ms-auto">
                            <strong>Select display</strong>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input display-option" type="checkbox" name="showThumbnail" value="on" id="showThumbnail" ${showThumbnail == 'on' ? 'checked' : ''} form="filterForm" onchange="validateDisplayOptions()">
                                <label class="form-check-label" for="showThumbnail">Thumbnail</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input display-option" type="checkbox" name="showTitle" value="on" id="showTitle" ${showTitle == 'on' ? 'checked' : ''} form="filterForm" onchange="validateDisplayOptions()">
                                <label class="form-check-label" for="showTitle">Title</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input display-option" type="checkbox" name="showPrice" value="on" id="showPrice" ${showPrice == 'on' ? 'checked' : ''} form="filterForm" onchange="validateDisplayOptions()">
                                <label class="form-check-label" for="showPrice">Price</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input display-option" type="checkbox" name="showTagline" value="on" id="showTagline" ${showTagline == 'on' ? 'checked' : ''} form="filterForm" onchange="validateDisplayOptions()">
                                <label class="form-check-label" for="showTagline">Tagline</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input display-option" type="checkbox" name="showPublicDate" value="on" id="showPublicDate" ${showPublicDate == 'on' ? 'checked' : ''} form="filterForm" onchange="validateDisplayOptions()">
                                <label class="form-check-label" for="showPublicDate">Public Date</label>
                            </div>
                        </div>
                    </div>
                    <div class="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4">
                        <c:choose>
                            <c:when test="${hasResults}">
                                <c:forEach var="course" items="${courses}">
                                    <div class="col">
                                        <div class="card course-card">
                                            <c:if test="${showThumbnail == 'on'}">
                                                <img src="${not empty course.thumbnailUrl ? course.thumbnailUrl : 'https://via.placeholder.com/400x225.png?text=No+Image'}" class="card-img-top" alt="${course.title}">
                                            </c:if>
                                            <div class="card-body d-flex flex-column">
                                                <c:if test="${showTitle == 'on'}">
                                                    <a href="course-detail?id=${course.id}" class="text-decoration-none">
                                                        <h5 class="card-title">${course.title}</h5>
                                                    </a>
                                                </c:if>
                                                <c:if test="${showTagline == 'on'}">
                                                    <p class="card-text">${course.tagline}</p>
                                                </c:if>
                                                <c:if test="${showPublicDate == 'on'}">
                                                    <p class="public-date">
                                                        Public: <fmt:formatDate value="${course.createdDate}" pattern="yyyy/MM/dd"/>
                                                    </p>
                                                </c:if>
                                                <c:if test="${showPrice == 'on'}">
                                                    <div class="price mb-3">
                                                        <span class="price-original">
                                                            <fmt:formatNumber value="${course.originalPrice}" type="currency" currencySymbol="$"/>
                                                        </span>
                                                        <span class="price-sale">
                                                            <fmt:formatNumber value="${course.salePrice}" type="currency" currencySymbol="$"/>
                                                        </span>  
                                                    </div>
                                                </c:if>
                                                <div class="mt-auto">
                                                    <a href="course-detail?id=${course.id}" class="btn btn-register">Register Now</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12">
                                    <div class="alert alert-info">No courses found matching your criteria. Try different filters!</div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${totalPages > 1}">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <c:url var="pageUrl" value="courses">
                                        <c:param name="page" value="${currentPage - 1}"/>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="category" value="${selectedCategory}"/>
                                        <c:param name="tag" value="${selectedTag}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/> <!-- Thêm mới -->
                                    </c:url>
                                    <a class="page-link" href="${pageUrl}">« Prev</a>
                                </li>
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <c:url var="pageUrl" value="courses">
                                            <c:param name="page" value="${i}"/>
                                            <c:param name="search" value="${searchKeyword}"/>
                                            <c:param name="category" value="${selectedCategory}"/>
                                            <c:param name="tag" value="${selectedTag}"/>
                                            <c:param name="sortBy" value="${sortBy}"/>
                                            <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                            <c:param name="showThumbnail" value="${showThumbnail}"/>
                                            <c:param name="showTitle" value="${showTitle}"/>
                                            <c:param name="showPrice" value="${showPrice}"/>
                                            <c:param name="showTagline" value="${showTagline}"/>
                                            <c:param name="showPublicDate" value="${showPublicDate}"/> <!-- Thêm mới -->
                                        </c:url>
                                        <a class="page-link" href="${pageUrl}">${i}</a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <c:url var="pageUrl" value="courses">
                                        <c:param name="page" value="${currentPage + 1}"/>
                                        <c:param name="search" value="${searchKeyword}"/>
                                        <c:param name="category" value="${selectedCategory}"/>
                                        <c:param name="tag" value="${selectedTag}"/>
                                        <c:param name="sortBy" value="${sortBy}"/>
                                        <c:param name="rowsPerPage" value="${rowsPerPage}"/>
                                        <c:param name="showThumbnail" value="${showThumbnail}"/>
                                        <c:param name="showTitle" value="${showTitle}"/>
                                        <c:param name="showPrice" value="${showPrice}"/>
                                        <c:param name="showTagline" value="${showTagline}"/>
                                        <c:param name="showPublicDate" value="${showPublicDate}"/> <!-- Thêm mới -->
                                    </c:url>
                                    <a class="page-link" href="${pageUrl}">Next »</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const filterForm = document.getElementById('filterForm');
                const rowsPerPageInput = document.getElementById('rowsPerPage');
                const displayOptions = document.querySelectorAll('.display-option');
                rowsPerPageInput.addEventListener('change', function () {
                    let value = parseInt(this.value);
                    if (isNaN(value) || value < 1 || value > 100) {
                        value = 5;
                        this.value = value;
                    }
                });
                window.validateDisplayOptions = function () {
                    let atLeastOneChecked = false;
                    displayOptions.forEach(option => {
                        if (option.checked)
                            atLeastOneChecked = true;
                    });
                    if (!atLeastOneChecked) {
                        document.getElementById('showThumbnail').checked = true;
                    }
                    filterForm.submit();
                };
                window.toggleDateOptions = function (event) {
                    event.preventDefault();
                    const dateOptions = document.getElementById('dateOptions');
                    if (dateOptions.style.display === 'block') {
                        dateOptions.style.display = 'none';
                    } else {
                        dateOptions.style.display = 'block';
                    }
                };
                window.hideDateOptions = function () {
                    const dateOptions = document.getElementById('dateOptions');
                    dateOptions.style.display = 'none';
                };
                // Ẩn dropdown khi nhấp ra ngoài
                document.addEventListener('click', function (event) {
                    const dateLink = document.getElementById('dateSortLink');
                    const dateOptions = document.getElementById('dateOptions');
                    if (!dateLink.contains(event.target) && !dateOptions.contains(event.target)) {
                        dateOptions.style.display = 'none';
                    }
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>