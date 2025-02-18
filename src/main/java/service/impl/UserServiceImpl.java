package service.impl;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.user.UserRepository;
import service.UserService;
import dao.UserDAO;


import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();
    private final UserDAO userDAO = new UserDAO(); // Gọi DAO để làm việc với database

    @Override
    public void registerUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.saveUser(user);
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userDAO.getUserByUsername(username); // Lấy user từ database theo username
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user; // Nếu đúng mật khẩu thì trả về user
        }
        return null; // Nếu sai thì trả về null
    }


    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers(); // ✅ Gọi phương thức thông qua đối tượng
    }


}
