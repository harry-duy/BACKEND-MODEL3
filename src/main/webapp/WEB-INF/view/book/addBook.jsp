<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Thêm Sách</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center">Thêm Sách Mới</h2>
  <form action="managementBook?action=add" method="post">
  <div class="mb-3">
      <label class="form-label">Tên sách:</label>
      <input type="text" class="form-control" name="title" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Tác giả:</label>
      <input type="text" class="form-control" name="author" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Giá (VNĐ):</label>
      <input type="number" step="0.01" class="form-control" name="price" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Số lượng:</label>
      <input type="number" class="form-control" name="stockQuantity" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Mô tả:</label>
      <textarea class="form-control" name="bookDescription" rows="3"></textarea>
    </div>
    <div class="mb-3">
      <label class="form-label">URL Hình ảnh:</label>
      <input type="text" class="form-control" name="imageURL" required>
    </div>
    <button type="submit" class="btn btn-success">Thêm Sách</button>
    <a href="managementBook" class="btn btn-secondary">Quay lại</a>
  </form>
</div>
</body>
</html>
