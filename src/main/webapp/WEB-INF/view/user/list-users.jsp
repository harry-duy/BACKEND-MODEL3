<%@ page import="model.User, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  List<User> users = (List<User>) request.getAttribute("users");
  String searchQuery = (String) request.getAttribute("searchQuery");
  String error = (String) request.getAttribute("error");
  String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Danh sách người dùng</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    .custom-header {
      background-color: white;
      padding: 10px 0;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .container1 {
      max-width: 1200px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
    }
    .header-logo {
      height: 60px;
    }
    .search-bar {
      display: flex;
      align-items: center;
      border: 2px solid #FFA500;
      border-radius: 5px;
      background-color: #fff;
      max-width: 500px;
      flex-grow: 1;
      margin: 0 20px;
    }
    .search-input {
      flex: 1;
      padding: 10px 15px;
      border: none;
      font-size: 14px;
    }
    .search-btn {
      background-color: #FFA500;
      border: none;
      padding: 10px 15px;
      color: white;
      cursor: pointer;
    }
    .search-btn i {
      font-size: 18px;
    }
    .header-right {
      display: flex;
      align-items: center;
      gap: 30px;
    }
    .header-account, .payment, .manage {
      text-decoration: none;
      font-size: 14px;
      font-weight: bold;
      color: #FFA500;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .header-account i, .payment i, .manage i {
      font-size: 24px;
      margin-bottom: 5px;
    }
    .header-account:hover, .payment:hover, .manage:hover {
      color: #FF8C00;
    }
    .dropdown-menu {
      display: none;
      position: absolute;
      background-color: #fff;
      min-width: 180px;
      box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
      border-radius: 5px;
      padding: 10px 0;
    }
    .dropdown-menu a {
      display: block;
      padding: 10px 15px;
      text-decoration: none;
      color: #333;
    }
    .dropdown-menu a:hover {
      background-color: #f1f1f1;
    }
    .show {
      display: block;
    }
    /* Add alert fade out effect */
    .alert-fade {
      animation: fadeOut 5s forwards;
    }
    @keyframes fadeOut {
      0% { opacity: 1; }
      70% { opacity: 1; }
      100% { opacity: 0; }
    }
  </style>
</head>
<body style="background-color: #f8f9fa;">

<!-- Header -->
<header class="custom-header">
  <div class="container1">
    <!-- Logo -->
    <div>
      <a href="/book">
        <img src="https://beedesign.com.vn/wp-content/uploads/2020/08/logo-nha-sach-Viet.jpg" alt="Logo" class="header-logo">
      </a>
    </div>

    <!-- Search form -->
    <form class="search-bar" action="users" method="get">
      <input class="search-input" type="text" name="search" placeholder="Nhập tên người dùng..." value="<%= searchQuery != null ? searchQuery : "" %>">
      <button class="search-btn" type="submit">
        <i class="fas fa-search"></i>
      </button>
    </form>

    <!-- Right-side buttons -->
    <div class="header-right">
      <c:if test="${empty sessionScope.user}">
        <a href="/login" class="header-account">
          <i class="bi bi-person-fill"></i>
          <span>Tài khoản</span>
        </a>
      </c:if>

      <c:if test="${not empty sessionScope.user}">
        <a href="/logout" class="header-account">
          <i class="bi bi-box-arrow-right"></i>
          <span>Đăng xuất</span>
        </a>
      </c:if>

      <c:if test="${sessionScope.roleId == 1}">
        <div class="dropdown">
          <a href="#" class="manage" onclick="toggleDropdown()">
            <i class="bi bi-person-lines-fill"></i>
            <span>Quản lý</span>
          </a>
          <div class="dropdown-menu">
            <a href="/users">Quản lý người dùng</a>
            <a href="/managementBook">Quản lý sách</a>
            <a href="/orderDetails">Quản lý đơn hàng</a>
          </div>
        </div>
      </c:if>

      <a href="/orderpage" class="payment">
        <i class="bi bi-cart-fill"></i>
        <span>Thanh toán</span>
      </a>
    </div>
  </div>
</header>

<!-- User Table -->
<div class="container mt-4">
  <h2 class="text-center">Danh sách Người Dùng</h2>

  <!-- Error Message Display -->
  <c:if test="${not empty param.error}">
    <div class="alert alert-danger alert-fade" id="errorAlert">
        ${param.error}
    </div>
  </c:if>

  <!-- Success Message Display -->
  <c:if test="${not empty param.success}">
    <div class="alert alert-success alert-fade" id="successAlert">
        ${param.success}
    </div>
  </c:if>

  <table class="table table-bordered">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Email</th>
      <th>Role ID</th>
      <th>Trạng thái</th>
      <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <% for (User u : users) { %>
    <tr>
      <td><%= u.getId() %></td>
      <td><%= u.getUsername() %></td>
      <td><%= u.getEmail() %></td>
      <td><%= u.getRoleId() %></td>
      <td>
        <% if (u.getStatus() == 1) { %>
        <span class="badge bg-success">Hoạt động</span>
        <% } else { %>
        <span class="badge bg-danger">Bị ẩn</span>
        <% } %>
      </td>
      <td>
        <button class="btn btn-warning btn-sm" onclick="openEditModal(<%= u.getId() %>, '<%= u.getUsername() %>', '<%= u.getEmail() %>', <%= u.getRoleId() %>)">Sửa</button>

        <% if (u.getStatus() == 1) { %>
        <form action="users" method="post" class="d-inline">
          <input type="hidden" name="id" value="<%= u.getId() %>">
          <input type="hidden" name="action" value="delete">
          <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn ẩn người dùng này?')">Ẩn</button>
        </form>
        <% } else { %>
        <form action="users" method="post" class="d-inline">
          <input type="hidden" name="id" value="<%= u.getId() %>">
          <input type="hidden" name="action" value="restore">
          <button type="submit" class="btn btn-success btn-sm" onclick="return confirm('Bạn có chắc muốn khôi phục người dùng này?')">Khôi phục</button>
        </form>
        <% } %>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>

<!-- Edit Modal -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="editForm" action="users" method="post">
        <div class="modal-header">
          <h5 class="modal-title" id="editModalLabel">Chỉnh sửa Người Dùng</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="editUserId">
          <input type="hidden" name="action" value="edit">
          <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" class="form-control" name="username" id="editUsername" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" name="email" id="editEmail" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Role ID</label>
            <select class="form-select" name="role_id" id="editRoleId" required>
              <option value="1">1 - Admin</option>
              <option value="2">2 - User</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
          <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Function to open the edit modal with user data
  function openEditModal(id, username, email, roleId) {
    document.getElementById("editUserId").value = id;
    document.getElementById("editUsername").value = username;
    document.getElementById("editEmail").value = email;
    document.getElementById("editRoleId").value = roleId;

    // Open the modal
    var editModal = new bootstrap.Modal(document.getElementById("editModal"));
    editModal.show();
  }

  // Function to toggle the dropdown menu
  function toggleDropdown() {
    document.querySelector(".dropdown-menu").classList.toggle("show");
  }

  // Close dropdown when clicking outside
  window.onclick = function(event) {
    if (!event.target.matches('.manage')) {
      var dropdowns = document.getElementsByClassName("dropdown-menu");
      for (var i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }

  // Auto hide alerts after 5 seconds
  document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
      var alerts = document.querySelectorAll('.alert-fade');
      alerts.forEach(function(alert) {
        if (alert) {
          alert.style.display = 'none';
        }
      });
    }, 5000);
  });
</script>

</body>
</html>