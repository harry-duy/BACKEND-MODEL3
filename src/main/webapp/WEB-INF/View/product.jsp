<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Product" %>
<jsp:useBean id="productDAO" class="DAO.ProductDAO" scope="application"/>

<%
    List<Product> products = productDAO.getAllProducts();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-5">
    <h2>Management Product</h2>

    <!-- Search Form -->
    <form action="product" method="get" class="row g-3">
        <div class="col-md-3">
            <input type="text" name="search" class="form-control" placeholder="Enter Product Name">
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <!-- Add Product Button -->
    <button class="btn btn-success mt-3" data-bs-toggle="modal" data-bs-target="#addProductModal">Add Product</button>

    <!-- Product Table -->
    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>STT</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Color</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% int count = 1; %>
        <% for (Product product : products) { %>
        <tr>
            <td><%= count++ %></td>
            <td><%= product.getName() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getQuantity() %></td>
            <td><%= product.getColor() %></td>
            <td><%= product.getCategoryName() %></td>
            <td>
                <button class="btn btn-warning btn-sm" data-bs-toggle="modal"
                        data-bs-target="#editProductModal<%= product.getId() %>">Edit</button>
                <button class="btn btn-danger btn-sm" data-bs-toggle="modal"
                        data-bs-target="#deleteProductModal<%= product.getId() %>">Delete</button>
            </td>
        </tr>

        <!-- Edit Product Modal -->
        <div class="modal fade" id="editProductModal<%= product.getId() %>" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="product" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Product</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="id" value="<%= product.getId() %>">
                            <label>Product Name:</label>
                            <input type="text" name="name" class="form-control" value="<%= product.getName() %>">
                            <label>Price:</label>
                            <input type="text" name="price" class="form-control" value="<%= product.getPrice() %>">
                            <label>Quantity:</label>
                            <input type="number" name="quantity" class="form-control" value="<%= product.getQuantity() %>">
                            <label>Color:</label>
                            <input type="text" name="color" class="form-control" value="<%= product.getColor() %>">
                            <label>Description:</label>
                            <textarea name="description" class="form-control"><%= product.getDescription() %></textarea>
                            <label>Category:</label>
                            <input type="number" name="category_id" class="form-control" value="<%= product.getCategoryId() %>">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Delete Product Modal -->
        <div class="modal fade" id="deleteProductModal<%= product.getId() %>" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete <strong><%= product.getName() %></strong>?
                    </div>
                    <div class="modal-footer">
                        <a href="product?action=delete&id=<%= product.getId() %>" class="btn btn-danger">Yes, Delete</a>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>

        <% } %>
        </tbody>
    </table>
</div>

<!-- Add Product Modal -->
<div class="modal fade" id="addProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="product" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <label>Product Name:</label>
                    <input type="text" name="name" class="form-control" required>
                    <label>Price:</label>
                    <input type="text" name="price" class="form-control" required>
                    <label>Quantity:</label>
                    <input type="number" name="quantity" class="form-control" required>
                    <label>Color:</label>
                    <input type="text" name="color" class="form-control" required>
                    <label>Description:</label>
                    <textarea name="description" class="form-control" required></textarea>
                    <label>Category ID:</label>
                    <input type="number" name="category_id" class="form-control" required>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Add Product</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
