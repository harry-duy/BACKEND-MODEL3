package DAO;

import Model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductDAO {
    ProductDAO dao = new ProductDAO();
    Connection conn = dao.getConnection();

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/FinalTest";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "159357bapD";
    private static Connection connection;

    private static final String SELECT_ALL_PRODUCTS = "SELECT p.*, c.name as category_name FROM Product p JOIN Category c ON p.category_id = c.id WHERE p.status = 1";
    private static final String INSERT_PRODUCT = "INSERT INTO Product (name, price, quantity, color, description, category_id, status) VALUES (?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, quantity = ?, color = ?, description = ?, category_id = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "UPDATE Product SET status = 0 WHERE id = ?";
    private static final String SEARCH_PRODUCTS = "SELECT p.*, c.name as category_name FROM Product p JOIN Category c ON p.category_id = c.id WHERE p.status = 1 AND (p.name LIKE ? OR p.price LIKE ? OR c.name LIKE ?)";


    public ProductDAO() {} // Private constructor to prevent instantiation

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ProductDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategoryId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.setInt(7, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCTS)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            preparedStatement.setString(3, searchKeyword);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int status = rs.getInt("status");

                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        status // Truyền vào int thay vì boolean
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
