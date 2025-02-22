<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.OrderDetail" %>
<%@ page import="service.impl.order.OrderService" %>
<%@ page import="repository.DBRepository" %>
<%@ page import="service.impl.OrderDetail.OrderDetailService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  OrderDetailService orderDetailService = new OrderDetailService(DBRepository.getConnection());
  List<OrderDetail> orderDetails = orderDetailService.getAll();
  request.setAttribute("orderDetails", orderDetails);
%>


<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quản Lý Chi Tiết Đơn Hàng</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body { font-family: Arial, sans-serif; background-color: #f8f9fa; }
    .container { margin-top: 30px; }
    .table th, .table td { text-align: center; vertical-align: middle; }
    .status-processing { color: #ffc107; font-weight: bold; }
    .status-delivered { color: #28a745; font-weight: bold; }
    .status-cancelled { color: #dc3545; font-weight: bold; }
  </style>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<div class="container">
  <h2 class="text-center text-primary">Quản Lý Chi Tiết Đơn Hàng</h2>
  <div class="card shadow p-4">
    <div class="row mb-3">
      <div class="col-md-6">
        <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm theo tên khách hàng...">
      </div>
      <div class="col-md-3">
        <select id="statusFilter" class="form-select">
          <option value="">-- Lọc theo trạng thái --</option>
          <option value="Đang xử lý">Đang xử lý</option>
          <option value="Đã giao">Đã giao</option>
          <option value="Đã hủy">Đã hủy</option>
        </select>
      </div>
    </div>

    <table class="table table-striped table-bordered">
      <thead class="table-dark">
      <tr>
        <th>ID</th>
        <th>Order ID</th>
        <th>Book ID</th>
        <th>Số lượng</th>
        <th>Khách Hàng</th>
        <th>Số Điện Thoại</th>
        <th>Địa Chỉ</th>
        <th>Ghi chú</th>
        <th>Phương Thức Thanh Toán</th>
        <th>Trạng Thái</th>
        <th>Hành Động</th>
      </tr>
      </thead>
      <tbody id="orderTable">
      <c:forEach var="orderDetail" items="${orderDetails}">
        <tr>
          <td>${orderDetail.id}</td>
          <td>${orderDetail.orderId}</td>
          <td>${orderDetail.bookId}</td>
          <td>${orderDetail.quantity}</td>
          <td>${orderDetail.fullName}</td>
          <td>${orderDetail.phoneNumber}</td>
          <td>
              ${orderDetail.street}, ${orderDetail.ward}, ${orderDetail.district}, ${orderDetail.provinceCity}
          </td>
          <td>${orderDetail.noteOrder}</td>
          <td>${orderDetail.paymentMethod}</td>
          <td>
            <span class="${orderDetail.status eq 'Đang xử lý' ? 'status-processing'
                          : (orderDetail.status eq 'Đã giao' ? 'status-delivered'
                          : 'status-cancelled')}">
                ${orderDetail.status}
            </span>
          </td>
          <td>
            <button class="btn btn-warning btn-sm editBtn"
                    data-id="${orderDetail.id}"
                    data-name="${orderDetail.fullName}"
                    data-phone="${orderDetail.phoneNumber}"
                    data-address="${orderDetail.street}, ${orderDetail.ward}, ${orderDetail.district}, ${orderDetail.provinceCity}"
                    data-status="${orderDetail.status}">
              Sửa
            </button>

            <a href="ordersList?action=delete&id=${orderDetail.id}" class="btn btn-danger btn-sm"
               onclick="return confirm('Bạn có chắc chắn muốn xóa chi tiết đơn hàng này?')">
              Xóa
            </a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Modal Chỉnh Sửa Đơn Hàng -->
<div class="modal fade" id="editOrderModal" tabindex="-1" aria-labelledby="editOrderLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editOrderLabel">Chỉnh Sửa Chi Tiết Đơn Hàng</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form action="ordersList" method="post">
      <input type="hidden" name="id" id="orderId">
      <div class="mb-3">
        <label class="form-label">Tên Khách Hàng</label>
        <input type="text" name="fullName" id="orderName" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Số Điện Thoại</label>
        <input type="text" name="phoneNumber" id="orderPhone" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Địa Chỉ</label>
        <input type="text" name="address" id="orderAddress" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Trạng Thái</label>
        <select name="status" id="orderStatus" class="form-select">
          <option value="Đang xử lý">Đang xử lý</option>
          <option value="Đã giao">Đã giao</option>
          <option value="Đã hủy">Đã hủy</option>
        </select>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">Cập Nhật</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
      </div>
    </form>

    </div>
  </div>
</div>

<script>
  document.querySelectorAll('.editBtn').forEach(button => {
    button.addEventListener('click', function() {
      document.getElementById('orderId').value = this.dataset.id;
      document.getElementById('orderName').value = this.dataset.name;
      document.getElementById('orderPhone').value = this.dataset.phone;
      document.getElementById('orderAddress').value = this.dataset.address;
      document.getElementById('orderStatus').value = this.dataset.status;

      new bootstrap.Modal(document.getElementById('editOrderModal')).show();
    });
  });
</script>
<script>
  document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchInput");
    const statusFilter = document.getElementById("statusFilter");
    const rows = document.querySelectorAll("#orderTable tr");

    function filterTable() {
      let searchValue = searchInput.value.toLowerCase();
      let selectedStatus = statusFilter.value.toLowerCase();

      rows.forEach(row => {
        let customerName = row.cells[4].textContent.toLowerCase(); // Tên khách hàng
        let statusText = row.cells[9].textContent.toLowerCase(); // Trạng thái đơn hàng

        let matchesSearch = customerName.includes(searchValue);
        let matchesStatus = selectedStatus === "" || statusText.includes(selectedStatus);

        row.style.display = matchesSearch && matchesStatus ? "" : "none";
      });
    }

    // Bắt sự kiện khi nhập vào ô tìm kiếm
    searchInput.addEventListener("keyup", filterTable);

    // Bắt sự kiện khi chọn trạng thái
    statusFilter.addEventListener("change", filterTable);
  });
</script>


</body>
</html>
