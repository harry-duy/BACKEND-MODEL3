package repository.user;

import model.User;
import repository.connection.DBRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

    private static final String INSERT_USER_SQL =
            "INSERT INTO users (username, password, email, role_id, status) VALUES (?, ?, ?, ?, 1)";

    private static final String GET_USER_BY_USERNAME_PASSWORD =
            "SELECT * FROM users WHERE username = ? AND password = ? AND status = 1";

    private static final String GET_ALL_USERS =
            "SELECT id, username, email, role_id FROM users WHERE status = 1";

    private static final String UPDATE_USER_STATUS =
            "UPDATE users SET status = ? WHERE id = ?";

    private static final String GET_USER_BY_ID =
            "SELECT * FROM users WHERE id = ?";

    private static final String FIND_USERS_BY_NAME =
            "SELECT * FROM users WHERE username LIKE ? AND status = 1";

    private static final String UPDATE_USER =
            "UPDATE users SET username = ?, email = ?, role_id = ? WHERE id = ?";

    // ================= EXISTING METHODS =================

    public void saveUser(User user) {
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lưu user: ", e);
        }
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

    public User login(String username, String password) {
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_PASSWORD)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đăng nhập: ", e);
        }
        return null;
    }

    // ================= NEW METHODS =================

    public User findById(int id) {
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm user theo ID: ", e);
        }
        return null;
    }

    public List<User> findByName(String name) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_NAME)) {

            statement.setString(1, "%" + name + "%");
            ResultSet rs = statement.executeQuery();

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
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm user theo tên: ", e);
        }
        return users;
    }

    public void updateUser(int id, User user) {
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getRoleId());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật user: ", e);
        }
    }
}
