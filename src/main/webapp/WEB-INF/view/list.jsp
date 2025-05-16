<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý thuê phòng trọ</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <style>
        .form-container {
            display: ${showAddForm ? 'block' : 'none'};
        }
        .modal {
            display: ${confirmMessage != null ? 'block' : 'none'};
            background-color: rgba(0,0,0,0.4);
        }
    </style>
</head>
<body>

<div class="container mt-4">
    <h1 class="text-center mb-4">Quản Lý Thuê Phòng Trọ</h1>

    <!-- Alerts for Success and Error Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search Form -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Tìm kiếm thông tin</h5>
        </div>
        <div class="card-body">
            <form action="?action=search" method="post" class="row g-3">
                <div class="col-md-10">
                    <input type="text" name="searchTerm" class="form-control" placeholder="Nhập mã phòng trọ, tên người thuê trọ hoặc số điện thoại" value="${searchTerm}">
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Buttons for Add and Delete -->
    <div class="mb-3">
        <button class="btn btn-success" id="addRoomBtn" onclick="toggleAddForm()">Tạo mới</button>
        <button class="btn btn-danger" onclick="deleteMultiple()">Xóa</button>
    </div>

    <!-- Add Room Form -->
    <div class="card mb-4 form-container" id="addRoomForm">
        <div class="card-header">
            <h5>Thêm phòng trọ mới</h5>
        </div>
        <div class="card-body">
            <form action="?action=add" method="post" class="row g-3">
                <div class="col-md-6">
                    <label for="tenantName" class="form-label">Tên người thuê trọ *</label>
                    <input type="text" class="form-control ${not empty tenantNameError ? 'is-invalid' : ''}" id="tenantName" name="tenantName" value="${tenantName}" required>
                    <div class="invalid-feedback">${tenantNameError}</div>
                </div>

                <div class="col-md-6">
                    <label for="phoneNumber" class="form-label">Số điện thoại *</label>
                    <input type="text" class="form-control ${not empty phoneNumberError ? 'is-invalid' : ''}" id="phoneNumber" name="phoneNumber" value="${phoneNumber}" required>
                    <div class="invalid-feedback">${phoneNumberError}</div>
                </div>

                <div class="col-md-6">
                    <label for="startDate" class="form-label">Ngày bắt đầu thuê *</label>
                    <input type="text" class="form-control ${not empty startDateError ? 'is-invalid' : ''}" id="startDate" name="startDate" placeholder="dd-MM-yyyy" value="${startDate}" required>
                    <div class="invalid-feedback">${startDateError}</div>
                </div>

                <div class="col-md-6">
                    <label for="paymentTypeId" class="form-label">Hình thức thanh toán *</label>
                    <select class="form-select ${not empty paymentTypeError ? 'is-invalid' : ''}" id="paymentTypeId" name="paymentTypeId" required>
                        <option value="" selected disabled>Chọn hình thức thanh toán</option>
                        <c:forEach var="paymentType" items="${paymentTypes}">
                            <option value="${paymentType.id}" ${paymentTypeId == paymentType.id ? 'selected' : ''}>${paymentType.name}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">${paymentTypeError}</div>
                </div>

                <div class="col-12">
                    <label for="note" class="form-label">Ghi chú</label>
                    <textarea class="form-control ${not empty noteError ? 'is-invalid' : ''}" id="note" name="note" rows="3">${note}</textarea>
                    <div class="invalid-feedback">${noteError}</div>
                </div>

                <div class="col-12 mt-3">
                    <button type="submit" class="btn btn-primary">Tạo mới</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleAddForm()">Hủy</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Room List Table -->
    <div class="card">
        <div class="card-header">
            <h5>Danh sách phòng trọ</h5>
        </div>
        <div class="card-body">
            <form id="multipleDeleteForm" action="?action=deleteMultiple" method="get">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" id="selectAll" onclick="toggleSelectAll()">
                            </th>
                            <th>Mã phòng trọ</th>
                            <th>Tên người thuê trọ</th>
                            <th>Số điện thoại</th>
                            <th>Ngày bắt đầu thuê</th>
                            <th>Hình thức thanh toán</th>
                            <th>Ghi chú</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="room" items="${rentalRooms}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="selectedRooms" value="${room.roomCode}" class="roomCheckbox">
                                </td>
                                <td>${room.roomCode}</td>
                                <td>${room.tenantName}</td>
                                <td>${room.phoneNumber}</td>
                                <td>
                                    <fmt:formatDate value="${room.startDate}" pattern="dd-MM-yyyy" />
                                </td>
                                <td>${room.paymentTypeName}</td>
                                <td>${room.note}</td>
                                <td>
                                    <a href="?action=delete&roomCode=${room.roomCode}" class="btn btn-sm btn-danger"><i class="bi bi-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" onclick="closeModal()"></button>
            </div>
            <div class="modal-body">
                <p>${confirmMessage}</p>
                <form id="confirmDeleteForm" action="?action=${confirmAction}" method="post">
                    <c:forEach var="roomCode" items="${roomsToDelete}">
                        <input type="hidden" name="roomsToDelete" value="${roomCode}">
                    </c:forEach>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeModal()">Không</button>
                <button type="button" class="btn btn-primary" onclick="confirmDelete()">Có</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Toggle add form visibility
    function toggleAddForm() {
        var form = document.getElementById('addRoomForm');
        if (form.style.display === 'none' || form.style.display === '') {
            form.style.display = 'block';
        } else {
            form.style.display = 'none';
        }
    }

    // Toggle select all checkboxes
    function toggleSelectAll() {
        var checkboxes = document.getElementsByClassName('roomCheckbox');
        var selectAllCheckbox = document.getElementById('selectAll');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = selectAllCheckbox.checked;
        }
    }

    // Delete multiple rooms
    function deleteMultiple() {
        var checkboxes = document.getElementsByClassName('roomCheckbox');
        var selected = false;

        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selected = true;
                break;
            }
        }

        if (selected) {
            document.getElementById('multipleDeleteForm').submit();
        } else {
            alert('Vui lòng chọn ít nhất một phòng trọ để xóa.');
        }
    }

    // Close modal
    function closeModal() {
        document.getElementById('deleteModal').style.display = 'none';
    }

    // Confirm delete
    function confirmDelete() {
        document.getElementById('confirmDeleteForm').submit();
    }

    // Auto-close alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.getElementsByClassName('alert');
        for (var i = 0; i < alerts.length; i++) {
            alerts[i].style.display = 'none';
        }
    }, 5000);

    // Initialize modal if confirmMessage exists
    window.onload = function() {
        var confirmMessage = "${confirmMessage}";
        if (confirmMessage) {
            document.getElementById('deleteModal').style.display = 'block';
        }
    };
</script>

</body>
</html>