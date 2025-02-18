package repository.user;


import model.User;
import repository.DBRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
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

}

