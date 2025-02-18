<%@ page import="model.User, service.UserService, java.util.List" %>
<%@ page import="service.impl.UserServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || user.getRoleId() != 1) {
    response.sendRedirect("login.jsp");
    return;
  }

  UserService userService = new UserServiceImpl();
  List<User> users = ((UserServiceImpl) userService).getAllUsers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Danh sách người dùng</title>
</head>
<body>
<h2>Danh sách người dùng</h2>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>Email</th>
  </tr>
  <% for (User u : users) { %>
  <tr>
    <td><%= u.getId() %></td>
    <td><%= u.getUsername() %></td>
    <td><%= u.getEmail() %></td>
  </tr>
  <% } %>
</table>
</body>
</html>
