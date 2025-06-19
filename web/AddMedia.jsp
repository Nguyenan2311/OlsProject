<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Ảnh/Video</title>
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
            <h3 class="mb-4">Thêm Ảnh/Video</h3>
             <c:if test="${not empty error}" >
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="addmedia" method="post" enctype="multipart/form-data">
                <!-- Tải media mới -->
                <input type="hidden" name="userId" value="${sessionScope.user.id}" />
                <div class="mb-3">
                    <label class="form-label">Chọn ảnh/video mới (nếu muốn thay đổi):</label>
                    <input type="file" class="form-control" name="mediaFile" accept="image/*,video/*">
                </div>

                <!-- Mô tả -->
                <div class="mb-3">
                    <label class="form-label">Mô tả:</label>
                    <textarea class="form-control" name="description" rows="3"></textarea>
                </div>

                <!-- Nút gửi -->
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">ADD</button>
                    <a href="userprofile" class="btn btn-secondary">Quay lại</a>
                </div>
            </form>
        </div>
    </body>
</html>
