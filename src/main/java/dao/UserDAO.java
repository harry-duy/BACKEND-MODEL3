package dao;

import model.User;
import repository.connection.DBRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    private static final String GET_USER_BY_USERNAME =
            "SELECT id, username, password, email, role_id, status FROM users WHERE username = ?";

    private static final String GET_ALL_USERS =
            "SELECT id, username, email, role_id FROM users WHERE status = 1";

    // Xóa mềm user (Cập nhật status = 0) hoặc khôi phục (status = 1)
    public static void updateStatus(int userId, int status) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thêm debug log
            System.out.println("Executing SQL: UPDATE users SET status = " + status + " WHERE id = " + userId);

            stmt.setInt(1, status);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();

            // Thêm debug log
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected == 0) {
                LOGGER.warning("User with ID " + userId + " not found or status not updated");
            } else {
                LOGGER.info("User with ID " + userId + " status updated to " + status);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user status: ", e);
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        User user = null;

        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_USERNAME)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getInt("role_id"),
                            rs.getInt("status")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy user theo username: ", e);
        }
        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null,
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        1
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách user: ", e);
        }
        return users;
    }

    private static final String GET_ALL_USERS_WITH_STATUS =
            "SELECT id, username, email, role_id, status FROM users";

    public static List<User> getAllUsersWithStatus() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_USERS_WITH_STATUS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null,
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy tất cả user: ", e);
        }
        return users;
    }
}