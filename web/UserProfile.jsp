<%-- 
    Document   : edit-profile.jsp
    Created on : Jun 4, 2025, 2:06:11 PM
    Author     : An_PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <!-- Thêm vào <head> -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@600&display=swap" rel="stylesheet">

        <!-- All your head content remains the same -->
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- DESCRIPTION -->
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="EduChamp : Education HTML Template" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="assets/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>EduChamp : Edit Profile</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">
        <style>
            .square-image {
                width: 100%;
                aspect-ratio: 1 / 1; /* Tạo hình vuông */
                object-fit: cover;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .image-caption {
                margin-top: 8px;
                font-size: 14px;
                color: #333;
                word-wrap: break-word;     /* Cho phép xuống dòng khi từ dài */
                word-break: break-word;    /* Ngắt từ khi quá dài */
                overflow-wrap: break-word; /* Đảm bảo tương thích nhiều trình duyệt */
                max-width: 100%;           /* Không vượt quá khung ảnh */
            }
            h3 {
                font-family: 'Poppins', sans-serif;
                font-weight: 600;
            }

            .image-card {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            /* Gallery Container */
            .gallery-container {
                margin-top: 2rem;
            }

            /* Gallery Items */
            .gallery-item {
                position: relative;
                aspect-ratio: 1;
                border: 2px dashed #dee2e6;
                border-radius: 15px;
                overflow: hidden;
                transition: all 0.3s ease;
                cursor: pointer;
                background: #f8f9fa;
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 200px;
            }

            .gallery-item:hover {
                border-color: #667eea;
                background: #f0f2ff;
                transform: translateY(-2px);
            }

            .gallery-item.has-image {
                border-style: solid;
                border-color: #667eea;
            }

            .gallery-item img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .gallery-item:hover img {
                transform: scale(1.05);
            }

            /* Gallery Placeholder */
            .gallery-placeholder {
                text-align: center;
                color: #6c757d;
                padding: 20px;
            }

            .gallery-placeholder i {
                font-size: 2rem;
                margin-bottom: 10px;
                display: block;
            }

            /* Gallery Overlay */
            .gallery-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0,0,0,0.7);
                display: flex;
                align-items: center;
                justify-content: center;
                opacity: 0;
                transition: opacity 0.3s ease;
                gap: 10px;
            }

            .gallery-item:hover .gallery-overlay {
                opacity: 1;
            }

            /* Gallery Buttons */
            .gallery-btn {
                background: rgba(255,255,255,0.9);
                border: none;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: all 0.3s ease;
            }

            .gallery-btn:hover {
                background: white;
                transform: scale(1.1);
            }

            /* Gallery Loading */
            .gallery-loading {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                z-index: 10;
            }
            .square-media {
                position: relative;
                width: 100%;
                padding-top: 100%; /* Tạo khung vuông */
                overflow: hidden;
                border-radius: 10px;
            }

            .square-media img,
            .square-media video {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
        </style>

    </head>
     <%@include file = "header.jsp" %>
    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>
            <!-- Header Top ==== -->
            
            <!-- header END ==== -->
            <!-- Content -->
            <div class="page-content bg-white">
                <!-- inner page banner END -->
                <div class="content-block">
                    <!-- About Us -->
                    <div class="section-area section-sp1">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-3 col-md-4 col-sm-12 m-b30">
                                    <div class="profile-bx text-center">

                                        <div class="profile-info">
                                            <h4>${sessionScope.user.last_name} ${sessionScope.user.first_name}</h4>
                                            <span>${sessionScope.user.email}</span>
                                        </div>

                                        <div class="profile-tabnav">
                                            <ul class="nav nav-tabs">
                                                <li class="nav-item">
                                                    <a class="nav-link active" href="userprofile"><i class="ti-pencil-alt"></i>Edit Profile</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="changepassword"><i class="ti-lock"></i>Change Password</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-9 col-md-8 col-sm-12 m-b30">
                                    <div class="profile-content-bx">
                                        <div class="profile-head">
                                            <h3>Edit Profile</h3>
                                        </div>
                                        <form action="updateprofile?uid=${sessionScope.user.id}" method="post" class="edit-profile">

                                            <div class="">
                                                <div class="form-group row">
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-10 ml-auto">

                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <c:if test="${not empty message}">
                                                        <p style="color:green;" class="text-center">${message}</p>
                                                    </c:if>
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Fist Name</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <input class="form-control" type="text" value="${sessionScope.user.first_name}" name="fname">
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Last Name</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <input class="form-control" type="text" value="${sessionScope.user.last_name}" name="lname">
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Gender</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7 d-flex align-items-center gap-3">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="gender" value="Male"
                                                                   <c:if test="${sessionScope.user.gender eq 'Male'}">checked</c:if> />
                                                                   <label class="form-check-label">Male</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="gender" value="Female"
                                                                <c:if test="${sessionScope.user.gender eq 'Female'}">checked</c:if> />
                                                                <label class="form-check-label">Female</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="gender" value="Other"
                                                                <c:if test="${sessionScope.user.gender eq 'Other'}">checked</c:if> />
                                                                <label class="form-check-label">Other</label>
                                                            </div>
                                                        </div>
                                                    </div>


                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Email</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" value="${sessionScope.user.email}" readonly="">

                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Phone</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <input class="form-control" type="text" value="${sessionScope.user.phone}" name="phone">
                                                        <c:if test="${not empty error}">
                                                            <div class="alert alert-danger">${error}</div>
                                                        </c:if>
                                                    </div>
                                                </div>


                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Date of birth</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <input class="form-control" type="date" value="${sessionScope.user.dob}" name="dob">
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Address</label>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <input class="form-control" type="text" value="${sessionScope.user.address}" name="address">
                                                    </div>
                                                </div>



                                                <div class="seperator"></div>


                                            </div>
                                            <div class="">
                                                <div class="">
                                                    <div class="row">
                                                        <div class="col-12 col-sm-3 col-md-3 col-lg-2">
                                                        </div>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <button type="submit" class="btn">Save changes</button>
                                                            <button type="reset"  class="btn-secondry">Reset</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                        <!-- Bắt đầu phần hiển thị media -->
                                        <h3 class="mt-4 mb-3">Ảnh/Video của bạn</h3>

                                        <div class="row">


                                            <div class="col-12 text-center my-4">
                                                <a href="addmedia" class="btn btn-primary">
                                                    <i class="fas fa-plus"></i> Thêm ảnh hoặc video
                                                </a>
                                            </div>


                                            <c:forEach items="${listM}" var="o">
                                                <div class="col-12 col-md-6 mb-4">
                                                    <div class="card h-100">
                                                        <div class="square-media">
                                                            <c:choose>
                                                                <c:when test="${o.media_type eq 'image'}">
                                                                    <img src="${o.media_path}" alt="Ảnh">
                                                                </c:when>
                                                                <c:when test="${o.media_type eq 'video'}">
                                                                    <video controls>
                                                                        <source src="${o.media_path}" type="video/mp4">
                                                                    </video>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                        <div class="card-body p-2">
                                                            <p class="card-text text-truncate">${o.description}</p>
                                                            <a href="mediadetail?mid=${o.id}" class="btn btn-sm btn-outline-primary">
                                                                <i class="fas fa-edit"></i> Edit
                                                            </a>
                                                            <a href="deletemedia?mid=${o.id}" class="btn btn-sm btn-outline-primary" style="color: red">
                                                                <i class="fas fa-cut"></i> Delete
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>


                                        </div>


                                        <!-- Kết thúc phần hiển thị media -->

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- contact area END -->
            </div>
            <!-- Content END-->

            <!-- Footer remains the same as in the original -->
            <footer>
                <!-- Your footer content -->
            </footer>
            <!-- Footer END ==== -->
            <button class="back-to-top fa fa-chevron-up" ></button>
        </div>
        <!-- External JavaScripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="assets/vendors/counter/waypoints-min.js"></script>
        <script src="assets/vendors/counter/counterup.min.js"></script>
        <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="assets/vendors/masonry/masonry.js"></script>
        <script src="assets/vendors/masonry/filter.js"></script>
        <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/js/contact.js"></script>
        <script src='assets/vendors/switcher/switcher.js'></script>

    </body>
</html>