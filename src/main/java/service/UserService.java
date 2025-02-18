package service;

import model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    User authenticate(String username, String password);  // ✅ Phải có dòng này
    List<User> getAllUsers();
}
