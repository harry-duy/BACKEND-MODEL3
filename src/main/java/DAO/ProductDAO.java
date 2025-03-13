package DAO;

import Model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/FinalTest";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "159357bapD";

    private static final String SELECT_ALL_PRODUCTS = "SELECT p.*, c.name as category_name FROM Product p JOIN Category c ON p.category_id = c.id WHERE p.status = 1";
    private static final String INSERT_PRODUCT = "INSERT INTO Product (name, price, quantity, color, description, category_id, status) VALUES (?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, quantity = ?, color = ?, description = ?, category_id = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "UPDATE Product SET status = 0 WHERE id = ?";

    public ProductDAO() {}

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
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
            System.out.println("Sản phẩm có ID " + id + " đã được ẩn (status = 0).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getFilteredProducts(String name, Double price, String category, String color) {
        List<Product> productList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.*, c.name as category_name FROM Product p JOIN Category c ON p.category_id = c.id WHERE p.status = 1");

        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND p.name LIKE ?");
            params.add("%" + name + "%");
        }
        if (price != null) {
            sql.append(" AND p.price <= ?");
            params.add(price);
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND p.category_id = ?");
            params.add(Integer.parseInt(category));
        }
        if (color != null && !color.isEmpty()) {
            sql.append(" AND p.color LIKE ?");
            params.add("%" + color + "%");
        }

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(new Product(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }


    public int addProduct(Product product) {
        int generatedId = -1;
        String sql = "INSERT INTO Product (name, price, quantity, color, description, category_id, status) VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategoryId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }



}
