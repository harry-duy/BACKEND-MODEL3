package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBRepository {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/bookstoredb";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "159357bapD";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy kết nối", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi đóng kết nối", e);
            }
        }
    }
}
