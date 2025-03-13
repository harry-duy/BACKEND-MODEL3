<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Product" %>
<jsp:useBean id="productDAO" class="DAO.ProductDAO" scope="application"/>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null) {
        products = productDAO.getAllProducts();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div class="container mt-4">
    <h3>📦 Product Management</h3>

    <!-- Search Form -->
    <form action="product" method="get" class="row g-2">
        <div class="col-md-3"><input type="text" name="name" class="form-control" placeholder="Product Name"></div>
        <div class="col-md-3"><input type="text" name="price" class="form-control" placeholder="Price"></div>
        <div class="col-md-3"><input type="text" name="category" class="form-control" placeholder="Category"></div>
        <div class="col-md-3"><input type="text" name="color" class="form-control" placeholder="Color"></div>
        <div class="col-md-12 mt-2">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <!-- Add Product Button -->
    <button class="btn btn-success mt-3" data-bs-toggle="modal" data-bs-target="#addProductModal">
        Add Product
    </button>

    <!-- Add Product Modal -->
    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addProductForm">
                        <div class="mb-3">
                            <label for="productName" class="form-label">Product Name</label>
                            <input type="text" class="form-control" id="productName" required>
                        </div>
                        <div class="mb-3">
                            <label for="productPrice" class="form-label">Price</label>
                            <input type="number" class="form-control" id="productPrice" required>
                        </div>
                        <div class="mb-3">
                            <label for="productQuantity" class="form-label">Quantity</label>
                            <input type="number" class="form-control" id="productQuantity" required>
                        </div>
                        <div class="mb-3">
                            <label for="productColor" class="form-label">Color</label>
                            <input type="text" class="form-control" id="productColor">
                        </div>
                        <div class="mb-3">
                            <label for="productDescription" class="form-label">Description</label>
                            <textarea class="form-control" id="productDescription"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productCategory" class="form-label">Category</label>
                            <select class="form-control" id="productCategory">
                                <option value="1">Category 1</option>
                                <option value="2">Category 2</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Product Table -->
    <div class="table-responsive mt-3">
        <table class="table table-bordered">
            <thead class="table-primary">
            <tr>
                <th>#</th><th>Name</th><th>Price</th><th>Quantity</th><th>Color</th><th>Category</th><th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% int count = 1; for (Product product : products) { %>
            <tr>
                <td><%= count++ %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getQuantity() %></td>
                <td><%= product.getColor() %></td>
                <td><%= product.getCategoryName() %></td>
                <td>
                    <button class="btn btn-warning edit-product" data-id="<%= product.getId() %>">Edit</button>
                    <button class="btn btn-danger delete-product" data-id="<%= product.getId() %>">Delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<!-- Edit Product Modal -->
<div class="modal fade" id="editProductModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="product" method="post">
                <div class="modal-header">
                    <h5>Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="id" id="edit-id">
                    <label>Name:</label><input type="text" name="name" id="edit-name" class="form-control">
                    <label>Price:</label><input type="number" name="price" id="edit-price" class="form-control">
                    <label>Quantity:</label><input type="number" name="quantity" id="edit-quantity" class="form-control">
                    <label>Color:</label><input type="text" name="color" id="edit-color" class="form-control">
                    <label>Description:</label><textarea name="description" id="edit-description" class="form-control"></textarea>
                    <label>Category:</label><input type="text" name="category_id" id="edit-category" class="form-control">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(".edit-product").click(function () {
        let id = $(this).data("id");
        $.get("product?id=" + id, function (data) {
            $("#edit-id").val(data.id);
            $("#edit-name").val(data.name);
            $("#edit-price").val(data.price);
            $("#edit-quantity").val(data.quantity);
            $("#edit-color").val(data.color);
            $("#edit-description").val(data.description);
            $("#edit-category").val(data.category_id);
            $("#editProductModal").modal("show");
        });
    });

    $(".delete-product").click(function () {
        let id = $(this).data("id");
        $.post("product", { action: "delete", id: id }, function () {
            location.reload();
        });
    });


    document.getElementById("addProductForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const productData = {
            name: document.getElementById("productName").value,
            price: document.getElementById("productPrice").value,
            quantity: document.getElementById("productQuantity").value,
            color: document.getElementById("productColor").value,
            description: document.getElementById("productDescription").value,
            category: document.getElementById("productCategory").value
        };

        fetch("product", {  // "product" chính là đường dẫn Servlet bạn đang dùng
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(productData)
        })
            .then(response => response.json())
            .then(data => {
                alert("Product added successfully!");
                location.reload(); // Refresh page
            })
            .catch(error => console.error("Error:", error));
    });

</script>


</body>
</html>
