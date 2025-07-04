<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><c:out value="${courseDetail.courseInfo.title}" default="Course Details"/> - EDEMY</title>
        <!-- Bootstrap CSS & Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            /* (Style của bạn đã được sửa đổi và tối ưu) */
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f8f9fa;
            }
            
            /* === Navbar Styling === */
            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 20px 50px;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .logo {
                height: 45px;
                display: flex;
                align-items: center;
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
            .right-section {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            /* SỬA: Chỉ áp dụng cho ô search trong navbar */
            .navbar .search-bar .form-control {
                width: 300px; /* Giảm chiều rộng cho hợp lý */
                height: 50px;
                padding: 8px 12px;
                font-size: 16px;
                border-radius: 20px;
                border: 1px solid #ccc;
            }

            .signup-btn {
                padding: 8px 20px;
                background-color: #4a00e0;
                color: white;
                border: none;
                font-weight: bold;
                cursor: pointer;
                width: 150px;
                height: 50px;
                border-radius: 10px;
            }

            /* === Main Content Styling === */
            .sidebar-section {
                margin-bottom: 1.5rem;
            }
            .category-link {
                text-decoration: none;
                display: block;
                padding: 0.25rem 0;
            }
            .main-content {
                background-color: white;
                padding: 2rem;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            }
            .course-thumbnail-main {
                width: 100%;
                max-width: 100%;
                height: auto;
                border-radius: 15px;
                margin-bottom: 1rem;
            }
            .course-title {
                font-size: 2.5rem;
                font-weight: 700;
            }
            .course-tagline {
                font-size: 1.8rem;
                color: #6c757d;
                font-style: italic;
            }
            .course-briefinfor {
                font-size: 1.5rem;
                color: #6c757d;
            }
            .price .price-original {
                text-decoration: line-through;
                color: #6c757d;
            }
            .price .price-sale {
                font-weight: 700;
                font-size: 1.75rem;
                color: #d9534f;
            }

            /* === Media Tabs Styling === */
            .media-tabs .nav-link {
                cursor: pointer;
            }
            .media-tabs .nav-link.active {
                background-color: #0d6efd !important;
                color: white !important;
            }
            .media-item {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 1rem;
                text-align: center;
            }
            .media-item .bi-play-circle {
                font-size: 3rem;
                color: #0d6efd;
            }
            .visual-description {
                margin-top: 1rem;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            /* === Modal Styling === */
            /* SỬA: Reset style cho các input bên trong modal để nó dùng style của Bootstrap */
            .modal-body .form-control,
            .modal-body .form-select {
                width: 100% !important; /* Luôn chiếm 100% chiều rộng của modal-body */
                height: auto !important; /* Để Bootstrap tự tính chiều cao */
                border-radius: 0.375rem !important; /* Dùng border-radius mặc định của Bootstrap */
                background-color: #fff; /* Đảm bảo nền trắng cho input thường */
            }

            /* Style riêng cho các input bị disabled để trông giống ảnh mẫu */
            .modal-body .form-control:disabled,
            .modal-body .form-control[readonly] {
                background-color: #e9ecef; /* Màu nền xám cho input bị khóa */
                opacity: 1; /* Hiển thị rõ ràng */
            }

            /* Style cho ảnh trong modal xem ảnh */
            #imageModal .modal-content img {
                max-width: 100%;
                height: auto;
            }
            .zoomable-image {
                cursor: pointer;
            }

            /* Style cho các lựa chọn gói giá trong modal */
            #registrationModal .form-check-label {
                cursor: pointer;
            }
            #registrationModal .form-check-label strong {
                font-size: 1.1rem;
            }
            /* CSS tùy chỉnh cho khối thông báo */
    .custom-alert-container {
        position: fixed; /* Giữ cố định trên màn hình */
        top: 20px;       /* Cách lề trên 20px */
        left: 50%;       /* Căn giữa theo chiều ngang */
        transform: translateX(-50%); /* Dịch chuyển về bên trái 50% chiều rộng của chính nó để căn giữa hoàn hảo */
        z-index: 2000;   /* Đảm bảo nó luôn nổi lên trên các thành phần khác */
        width: 200%;      /* Chiếm 90% chiều rộng màn hình */
        max-width: 600px;  /* Nhưng không rộng quá 600px trên màn hình lớn */
    }
        </style>
    </head>
    <body>
         
    <%-- =================================================================== --%>
    <%--        KHỐI MÃ ĐỂ HIỂN THỊ THÔNG BÁO THÀNH CÔNG/THẤT BẠI          --%>
    <%-- =================================================================== --%>
    <c:if test="${not empty sessionScope.registrationStatus}">
    <%-- Container để căn giữa và tạo khoảng đệm --%>
    <div class="container mt-3"> 
        <c:choose>
            <%-- TRƯỜNG HỢP THÀNH CÔNG --%>
            <c:when test="${sessionScope.registrationStatus == 'success'}">
                <%-- 
                    p-4: Tăng padding lớn hơn.
                    fs-4: Tăng cỡ chữ lớn hơn.
                    d-flex align-items-center: Căn icon và chữ theo chiều dọc.
                --%>
                <div class="alert alert-success alert-dismissible fade show p-4 d-flex align-items-center" role="alert">
                    <i class="bi bi-check-circle-fill fs-2 me-3"></i> <%-- Icon to hơn, có lề phải --%>
                    <div>
                        <h4 class="alert-heading">Success!</h4>
                        <p class="mb-0 fs-5">${sessionScope.registrationMessage}</p>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:when>

            <%-- TRƯỜDNG HỢP THẤT BẠI --%>
            <c:when test="${sessionScope.registrationStatus == 'error'}">
                <div class="alert alert-danger alert-dismissible fade show p-4 d-flex align-items-center" role="alert">
                    <i class="bi bi-exclamation-triangle-fill fs-2 me-3"></i>
                    <div>
                        <h4 class="alert-heading">Error!</h4>
                        <p class="mb-0 fs-5">${sessionScope.registrationMessage}</p>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:when>
        </c:choose>
    </div>

    <%-- Xóa các thuộc tính session sau khi đã hiển thị --%>
    <%
        session.removeAttribute("registrationStatus");
        session.removeAttribute("registrationMessage");
    %>
</c:if>
        <nav class="navbar">
            <div class="logo">
                <img src="img/logo.png" alt="alt"/>
            </div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/home" class="nav-link active">Home</a>
                <a href="${pageContext.request.contextPath}/courses" class="nav-link active">Courses</a>
                <a href="#">Blog</a>
                <a href="#">About</a>
            </div>
            <div class="right-section">
                <div class="search-bar">
                    <input type="text" class="form-control" placeholder="Search courses..." 
                           name="search" value="${searchKeyword}" form="filterForm" 
                           onkeypress="if (event.key === 'Enter')
                                       filterForm.submit();">
                </div>
                <button class="signup-btn">
                <c:if test="${not empty sessionScope.user}">
                <a href="logout" class="signup-btn">Log out</a>
            </c:if>
                 <c:if test="${empty sessionScope.user}">
                <a href="login" class="signup-btn">Log in</a>
            </c:if>
                </button>
            </div>
        </nav>

        <div class="container mt-5">
            <h1 class="mb-4 text-center" style="font-weight: 700;">Course Details</h1>
            <div class="row">
                <!-- ===== LEFT SIDEBAR ===== -->
                <div class="col-lg-3">
                    <div class="sidebar-section">
                        <h5>Search course</h5>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search...">
                            <button class="btn btn-primary" type="button"><i class="bi bi-search"></i></button>
                        </div>
                    </div>
                    <div class="sidebar-section">
                        <h5>Categories</h5>
                        <c:forEach var="cat" items="${categories}">
                            <a href="courses?category=${cat}" class="category-link">${cat}</a>
                        </c:forEach>
                    </div>
                    <div class="sidebar-section">
                        <h5>Featured</h5>
                        <c:forEach var="tag" items="${taglines}">
                            <a href="courses?tag=${tag.id}" class="category-link">${tag.name}</a>
                        </c:forEach>
                    </div>
                    <div class="sidebar-section">
                        <h5>Contact</h5>
                        <p class="mb-1">Email: abc@gmail.com</p>
                        <p class="mb-1">Phone: 012345678</p>
                        <div><a href="#">Facebook</a> | <a href="#">Github</a> | <a href="#">Youtube</a></div>
                    </div>
                </div>

                <!-- ===== RIGHT CONTENT AREA ===== -->
                <div class="col-lg-9">
                    <div class="main-content">
                        <c:if test="${not empty courseDetail and not empty courseDetail.courseInfo}">
                            <div class="mb-3">
                                <img src="${not empty courseDetail.courseInfo.thumbnailUrl ? courseDetail.courseInfo.thumbnailUrl : 'https://via.placeholder.com/800x450'}"
                                     alt="<c:out value='${courseDetail.courseInfo.title}'/>" class="course-thumbnail-main">
                            </div>
                            <h2 class="course-title mb-2"><c:out value="${courseDetail.courseInfo.title}"/></h2>
                            <p class="course-tagline mb-3"><c:out value="${courseDetail.courseInfo.tagline}"/></p>

                            <%-- SỬA: Hiển thị mô tả đầy đủ từ courseDetail.fullDescription --%>
                            <p class="course-briefinfor mb-4"><c:out value="${courseDetail.courseInfo.description}"/></p>

                            <c:if test="${not empty courseDetail.lowestPricePackage}">
                                <div class="price my-3">
                                    <h5 class="mb-1">Price:</h5>
                                    <span class="price-original fs-5"><fmt:formatNumber value="${courseDetail.lowestPricePackage.price}" type="currency" currencySymbol="$"/></span>
                                    <span class="price-sale ms-2"><fmt:formatNumber value="${courseDetail.lowestPricePackage.salePrice}" type="currency" currencySymbol="$"/></span>
                                </div>
                            </c:if>

                            <div class="mt-4">
                                <h5>Course Media:</h5>
                                <ul class="nav nav-tabs media-tabs" id="mediaTab" role="tablist">
                                    <li class="nav-item" role="presentation"><button class="nav-link active" id="images-tab" data-bs-toggle="tab" data-bs-target="#images-content" type="button">Images</button></li>
                                    <li class="nav-item" role="presentation"><button class="nav-link" id="videos-tab" data-bs-toggle="tab" data-bs-target="#videos-content" type="button">Video</button></li>
                                </ul>
                                <div class="tab-content pt-3">
                                    <div class="tab-pane fade show active" id="images-content" role="tabpanel">
                                        <div class="row g-3">
                                            <c:choose>
                                                <c:when test="${not empty courseDetail.images}">
                                                    <c:forEach var="img" items="${courseDetail.images}">
                                                        <div class="col-md-4">
                                                            <div class="media-item">
                                                                <%-- Sửa: không cần data-image, sẽ lấy src trực tiếp --%>
                                                                <img src="${img.content}" class="img-fluid rounded zoomable-image" alt="Course Image">
                                                                <p class="visual-description"><c:out value="${img.description}" default="No description"/></p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise><p class="text-muted">No additional images available.</p></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="videos-content" role="tabpanel">
                                        <div class="row g-3">
                                            <c:choose>
                                                <c:when test="${not empty courseDetail.videos}">
                                                    <c:forEach var="video" items="${courseDetail.videos}">
                                                        <div class="col-md-6">
                                                            <div class="media-item p-0">
                                                                <div class="ratio ratio-16x9">
                                                                    <iframe src="${video.content}" title="<c:out value='${video.description}'/>" allowfullscreen></iframe>
                                                                </div>
                                                                <p class="visual-description p-3"><c:out value="${video.description}" default="No description"/></p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise><p class="text-muted">No videos available.</p></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <%-- SỬA: Thay thế thẻ <a> bằng thẻ <button> để mở modal --%>
                            <div class="text-center mt-5">
                                <button type="button" class="btn btn-success btn-lg px-5 py-3" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#registrationModal">
                                    Register Now
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!-- =================================================================== -->
        <!--                      THÊM: CÁC MODAL (POP-UP)                       -->
        <!-- =================================================================== -->

        <!-- 1. MODAL ĐỂ XEM ẢNH PHÓNG TO -->
        <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header border-0"><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                    <div class="modal-body text-center p-0"><img src="" id="modalImage" class="img-fluid"></div>
                </div>
            </div>
        </div>

        <!-- 2. MODAL ĐỂ ĐĂNG KÝ KHÓA HỌC -->
        <div class="modal fade" id="registrationModal" tabindex="-1" aria-labelledby="registrationModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registrationModalLabel">Register for <c:out value="${courseDetail.courseInfo.title}"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="register-course" method="POST" id="registrationForm" novalidate="">
                            <input type="hidden" name="courseId" value="${courseDetail.courseInfo.id}">
                            <h6>Select Price Package</h6>
                            <c:choose>
                                <c:when test="${not empty courseDetail.allPricePackages}">
                                    <c:forEach var="pkg" items="${courseDetail.allPricePackages}" varStatus="loop">
                                        <div class="form-check border rounded p-3 mb-2">
                                            <input class="form-check-input" type="radio" name="pricePackageId" id="pkg-${pkg.id}" value="${pkg.id}" ${loop.first ? 'checked' : ''} required>
                                            <label class="form-check-label w-100" for="pkg-${pkg.id}">
                                                <strong><c:out value="${pkg.title}"/></strong>
                                                <div class="d-flex justify-content-between">
                                                    <span>Original: <span class="text-decoration-line-through"><fmt:formatNumber value="${pkg.price}" type="currency" currencySymbol="$"/></span></span>
                                                    <span class="text-danger fw-bold">Sale: <fmt:formatNumber value="${pkg.salePrice}" type="currency" currencySymbol="$"/></span>
                                                </div>
                                            </label>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><div class="alert alert-warning">No price packages available.</div></c:otherwise>
                            </c:choose>
                            <hr class="my-4">
                            <h6>Your Information</h6>
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <div class="mb-3"><label class="form-label">Full Name</label><input type="text" name="fullName"  class="form-control" id="fullNameInput" value="<c:out value='${sessionScope.user.fullName}'/>" disabled novalidate></div>
                                    <div class="mb-3"><label class="form-label">Email</label><input type="email" name="email" class="form-control"  id="emailInput" value="<c:out value='${sessionScope.user.email}'/>" disabled novalidate></div>
                                    <div class="mb-3"><label class="form-label">Mobile</label><input type="tel" name="mobile" class="form-control"  id="mobileInput" value="<c:out value='${sessionScope.user.phone}'/>" disabled novalidate></div>
                                    <div class="mb-3"><label class="form-label">Gender</label><input type="text" name="gender" class="form-control"  id="genderInput" value="<c:out value='${sessionScope.user.gender}'/>" disabled novalidate></div>
        
 
                                    </c:when>
                                    <%-- Trong file courseDetail.jsp, bên trong Modal, thay thế khối <c:otherwise> --%>
                                    <c:otherwise>
                                        <%-- Ô Full Name --%>
                                    <div class="mb-3">
                                        <label for="fullNameInput" class="form-label">Full Name</label>
                                        <%-- Điền lại giá trị đã nhập, thêm class is-invalid nếu có lỗi --%>
                                        <input type="text" class="form-control ${not empty validationErrors.fullName ? 'is-invalid' : ''}" 
                                               id="fullNameInput" name="fullName" value="<c:out value='${submittedFullName}'/>" required>
                                        <%-- Hiển thị thông báo lỗi ngay bên dưới --%>
                                        <c:if test="${not empty validationErrors.fullName}">
                                            <div class="invalid-feedback">
                                                ${validationErrors.fullName}
                                            </div>
                                        </c:if>
                                    </div>

                                    <%-- Ô Email --%>
                                    <div class="mb-3">
                                        <label for="emailInput" class="form-label">Email</label>
                                        <input type="email" class="form-control ${not empty validationErrors.email ? 'is-invalid' : ''}" 
                                               id="emailInput" name="email" value="<c:out value='${submittedEmail}'/>" required>
                                        <c:if test="${not empty validationErrors.email}">
                                            <div class="invalid-feedback">
                                                ${validationErrors.email}
                                            </div>
                                        </c:if>
                                    </div>

                                    <%-- Ô Mobile --%>
                                    <div class="mb-3">
                                        <label for="mobileInput" class="form-label">Mobile</label>
                                        <input type="tel" class="form-control ${not empty validationErrors.mobile ? 'is-invalid' : ''}" 
                                               id="mobileInput" name="mobile" value="<c:out value='${submittedMobile}'/>" required>
                                        <c:if test="${not empty validationErrors.mobile}">
                                            <div class="invalid-feedback">
                                                ${validationErrors.mobile}
                                            </div>
                                        </c:if>
                                    </div>

                                    <%-- Ô Gender --%>
                                    <div class="mb-3">
                                        <label for="genderInput" class="form-label">Gender</label>
                                        <select class="form-select ${not empty validationErrors.gender ? 'is-invalid' : ''}" 
                                                id="genderInput" name="gender" required>
                                            <option value="" selected disabled>Select Gender</option>
                                            <%-- Giữ lại lựa chọn cũ của người dùng --%>
                                            <option value="Male" ${submittedGender == 'Male' ? 'selected' : ''}>Male</option>
                                            <option value="Female" ${submittedGender == 'Female' ? 'selected' : ''}>Female</option>
                                            <option value="Other" ${submittedGender == 'Other' ? 'selected' : ''}>Other</option>
                                        </select>
                                        <c:if test="${not empty validationErrors.gender}">
                                            <div class="invalid-feedback">
                                                ${validationErrors.gender}
                                            </div>
                                        </c:if>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" form="registrationForm">Submit Registration</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <!-- SỬA: Mã JavaScript được đặt ở cuối, sau các modal -->
        <script>
    document.addEventListener('DOMContentLoaded', function () {
        // ... (phần code xử lý image modal giữ nguyên) ...
        const imageModalInstance = new bootstrap.Modal(document.getElementById('imageModal'));
        const modalImageElement = document.getElementById('modalImage');

        document.querySelectorAll('.zoomable-image').forEach(image => {
            image.addEventListener('click', function () {
                modalImageElement.src = this.src;
                imageModalInstance.show();
            });
        });
    });

    // ====================================================================
    //        THÊM ĐOẠN MÃ NÀY ĐỂ TỰ ĐỘNG MỞ POP-UP KHI CÓ LỖI
    // ====================================================================
    (function () {
        // Sử dụng JSTL để kiểm tra xem có tín hiệu 'openModal' từ servlet không
        <c:if test="${not empty openModal && openModal == true}">
            // Nếu có, lấy đối tượng Modal đăng ký
            var registrationModalElement = document.getElementById('registrationModal');
            if (registrationModalElement) {
                // Tạo một instance của Bootstrap Modal
                var registrationModal = new bootstrap.Modal(registrationModalElement);
                // Và cho nó hiển thị
                registrationModal.show();
            }
        </c:if>
    })();
</script>
    </body>
</html>