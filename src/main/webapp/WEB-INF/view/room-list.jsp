<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý thuê phòng trọ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .table th {
            background-color: #343a40;
            color: white;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4 text-center">QUẢN LÝ THUÊ PHÒNG TRỌ</h2>

    <!-- Search Form -->
    <form action="rooms" method="get" class="mb-4">
        <input type="hidden" name="action" value="search">
        <div class="input-group">
            <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm theo mã phòng, tên người thuê hoặc số điện thoại..."
                   value="${param.keyword}">
            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            <a href="rooms" class="btn btn-outline-secondary">Xóa tìm kiếm</a>
        </div>
    </form>

    <!-- Create New Button -->
    <button id="showCreateForm" class="btn btn-success mb-3">Thêm mới</button>

    <!-- Create Form (initially hidden) -->
    <div id="createForm" class="form-container mb-4" style="display: none;">
        <h5>Thêm thông tin thuê trọ mới</h5>
        <form action="rooms" method="post" id="createRentalForm">
            <input type="hidden" name="action" value="create">

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Tên người thuê*</label>
                    <input type="text" name="tenantName" class="form-control" required
                           pattern="^[a-zA-ZÀ-ỹ\s]{5,50}$"
                           title="Tên phải từ 5-50 ký tự và không chứa số hoặc ký tự đặc biệt">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Số điện thoại*</label>
                    <input type="text" name="phoneNumber" class="form-control" required
                           pattern="^\d{10}$" title="Số điện thoại phải có 10 chữ số">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Ngày bắt đầu thuê*</label>
                    <input type="date" name="startDate" class="form-control" required
                           min="<fmt:formatDate value='<%= new java.util.Date() %>' pattern='yyyy-MM-dd' />">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Hình thức thanh toán*</label>
                    <select name="paymentType" class="form-select" required>
                        <option value="">-- Chọn --</option>
                        <c:forEach items="${paymentTypes}" var="pt">
                            <option value="${pt.id}">${pt.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Ghi chú</label>
                <textarea name="note" class="form-control" maxlength="200"></textarea>
                <small class="text-muted">Tối đa 200 ký tự</small>
            </div>

            <div class="d-flex justify-content-end">
                <button type="submit" class="btn btn-primary me-2">Lưu</button>
                <button type="button" id="cancelCreate" class="btn btn-secondary">Hủy</button>
            </div>
        </form>
    </div>

    <!-- Room List -->
    <form action="rooms" method="post" id="deleteForm">
        <input type="hidden" name="action" value="delete">
        <table class="table table-striped table-hover table-bordered">
            <thead class="table-dark">
            <tr>
                <th width="5%"><input type="checkbox" id="selectAll"></th>
                <th width="5%">STT</th>
                <th width="10%">Mã phòng</th>
                <th width="20%">Tên người thuê</th>
                <th width="12%">Số điện thoại</th>
                <th width="12%">Ngày thuê</th>
                <th width="15%">Hình thức thanh toán</th>
                <th width="15%">Ghi chú</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rooms}" var="room" varStatus="loop">
                <tr>
                    <td><input type="checkbox" name="selectedRooms" value="${room.roomCode}"></td>
                    <td>${loop.index + 1}</td>
                    <td>${room.roomCode}</td>
                    <td>${room.tenantName}</td>
                    <td>${room.phoneNumber}</td>
                    <td><fmt:formatDate value="${room.startDate}" pattern="dd-MM-yyyy" /></td>
                    <td>${room.paymentType.name}</td>
                    <td>${room.note}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty rooms}">
                <tr>
                    <td colspan="8" class="text-center">Không có dữ liệu</td>
                </tr>
            </c:if>
            </tbody>
        </table>

        <div class="d-flex justify-content-between align-items-center">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Xóa đã chọn</button>
            <span class="text-muted">Tổng: ${fn:length(rooms)} bản ghi</span>
        </div>
    </form>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc chắn muốn xóa các phòng đã chọn?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDelete">Xóa</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Toggle create form
    document.getElementById('showCreateForm').addEventListener('click', function() {
        document.getElementById('createForm').style.display = 'block';
    });

    document.getElementById('cancelCreate').addEventListener('click', function() {
        document.getElementById('createForm').style.display = 'none';
        document.getElementById('createRentalForm').reset();
    });

    // Select all checkboxes
    document.getElementById('selectAll').addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('input[name="selectedRooms"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = this.checked;
        });
    });

    // Delete confirmation
    document.getElementById('deleteBtn').addEventListener('click', function(e) {
        e.preventDefault();
        const selectedCount = document.querySelectorAll('input[name="selectedRooms"]:checked').length;

        if (selectedCount > 0) {
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            modal.show();

            document.getElementById('confirmDelete').addEventListener('click', function() {
                document.getElementById('deleteForm').submit();
            });
        } else {
            alert('Vui lòng chọn ít nhất một phòng để xóa');
        }
    });
</script>
</body>
</html>