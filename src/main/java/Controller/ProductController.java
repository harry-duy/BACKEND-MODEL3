package Controller;

import DAO.ProductDAO;
import Model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            try {
                String name = request.getParameter("name");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String color = request.getParameter("color");
                String description = request.getParameter("description");
                int categoryId = Integer.parseInt(request.getParameter("category"));

                Product product = new Product(0, name, price, quantity, color, description, categoryId, "", 1);
                int newProductId = productDAO.addProduct(product);

                System.out.println("✅ Product added with ID: " + newProductId);

                response.setContentType("application/json");
                response.getWriter().write("{\"status\":\"success\"}");
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":\"error\"}");
            }
        }


        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(id);
            System.out.println("Đã xóa sản phẩm có ID: " + id);
        } else {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String color = request.getParameter("color");
            String description = request.getParameter("description");

            try {
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int categoryId = Integer.parseInt(request.getParameter("category_id"));

                if (id == null || id.isEmpty()) {
                    Product product = new Product(0, name, price, quantity, color, description, categoryId, "", 1);
                    int newProductId = productDAO.addProduct(product);
                    System.out.println("Sản phẩm mới đã được thêm với ID: " + newProductId);
                } else {
                    int productId = Integer.parseInt(id);
                    Product product = new Product(productId, name, price, quantity, color, description, categoryId, "", 1);
                    productDAO.updateProduct(product);
                    System.out.println("Đã cập nhật sản phẩm có ID: " + productId);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
                request.getRequestDispatcher("/WEB-INF/View/product.jsp").forward(request, response);
                return;
            }
        }
         if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String color = request.getParameter("color");
                String description = request.getParameter("description");
                int categoryId = Integer.parseInt(request.getParameter("category_id"));

                Product product = new Product(id, name, price, quantity, color, description, categoryId, "", 1);
                productDAO.updateProduct(product);
                System.out.println("Đã cập nhật sản phẩm có ID: " + id);

                response.sendRedirect("product");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ!");
                request.getRequestDispatcher("/WEB-INF/View/product.jsp").forward(request, response);
            }
        }


        response.sendRedirect("product");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");
        String color = request.getParameter("color");

        Double price = null;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá không hợp lệ!");
            }
        }

        List<Product> products = productDAO.getFilteredProducts(name, price, category, color);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/View/product.jsp").forward(request, response);
    }
}



