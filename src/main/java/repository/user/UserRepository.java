package repository.user;


import model.Role;
import model.User;
import repository.DBRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String GET_USER_BY_USERNAME_PASSWORD =
            "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO user (username, password, email, roleId) VALUES (?, ?, ?, ?)";

    public void saveUser(User user) {
        try (Connection connection = DBRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword()); // Lưu ý: Cần mã hóa mật khẩu
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRoleId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, email, role_id FROM users"; // Không lấy password

        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null, // Không trả về mật khẩu
                        rs.getString("email"),
                        rs.getInt("role_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DBRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getInt("role_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}


