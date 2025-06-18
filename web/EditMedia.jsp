<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa Ảnh/Video</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .media-preview {
                max-width: 100%;
                max-height: 300px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4">Chỉnh sửa Ảnh/Video</h3>

            <form action="editmedia" method="post" enctype="multipart/form-data">
                <!-- ID giả định -->
                <input type="hidden" name="mediaId" value="${media.id}" />
                <!-- Media hiện tại -->
                <div class="mb-4">
                    <label class="form-label fw-bold">Ảnh hiện tại:</label><br>
                    <c:choose>
                        <c:when test="${media.media_type == 'image'}">
                            <img src="${media.media_path}" alt="Ảnh hiện tại" class="media-preview">
                        </c:when>
                        <c:when test="${media.media_type == 'video'}">
                            <video controls class="media-preview">
                                <source src="${media.media_path}" type="video/mp4">
                                Trình duyệt không hỗ trợ phát video.
                            </video>
                        </c:when>
                    </c:choose>

                    
                </div>

                <!-- Tải media mới -->
                <div class="mb-3">
                    <label class="form-label">Chọn ảnh/video mới (nếu muốn thay đổi):</label>
                    <input type="file" class="form-control" name="mediaFile" accept="image/*,video/*">
                </div>

                <!-- Mô tả -->
                <div class="mb-3">
                    <label class="form-label">Mô tả:</label>
                    <textarea class="form-control" name="description" rows="3">${media.description}</textarea>
                </div>

                <!-- Nút gửi -->
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                    <a href="userprofile" class="btn btn-secondary">Quay lại</a>
                </div>
            </form>
        </div>
    </body>
</html>
