<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh Sách Sản Phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .product-card {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;
            text-align: center;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            background-color: white;
            transition: transform 0.3s ease-in-out;
            position: relative;
        }
        .product-card:hover {
            transform: scale(1.05);
        }
        .product-card img {
            width: 100%;
            max-height: 180px;
            object-fit: contain;
        }
        .product-card h5 {
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
        }
        .price {
            color: red;
            font-size: 20px;
            font-weight: bold;
            margin: 10px 0;
        }
        .buy-button {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            width: 100%;
        }
        .buy-button:hover {
            background-color: #e68900;
        }
    </style>
</head>
<body style="background-color: #f8f9fa;">

<div class="container mt-4">
    <h2 class="text-center mb-4">Sản Phẩm</h2>

    <div class="row">
        <c:forEach var="book" items="${books}">
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="product-card">
                    <c:choose>
                        <c:when test="${not empty book.imageURL}">
                            <img src="${book.imageURL}" alt="Ảnh sản phẩm">
                        </c:when>
                        <c:otherwise>
                            <img src="default-image.jpg" alt="Không có ảnh">
                        </c:otherwise>
                    </c:choose>
                    <h5>${book.title}</h5>
                    <p class="price"><fmt:formatNumber value="${book.price}" type="number" pattern="#,##0.000" />VND</p>
                    <button class="buy-button">Mua ngay</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>