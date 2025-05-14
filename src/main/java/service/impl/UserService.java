package service.impl;

import dao.UserDAO;
import model.User;
import repository.user.UserRepository;
import service.IService;
import service.IUserService;

import java.util.List;

public class UserService implements IService<User>, IUserService {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public void add(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public void update(int id, User user) {
        userRepository.updateUser(id, user);
    }

    @Override
    public void update(User user) {
        userRepository.updateUser(user.getId(), user);
    }

    @Override
    public void remove(int userId) {
        // Thêm debug log
        System.out.println("Removing (hiding) user with ID: " + userId);
        UserDAO.updateStatus(userId, 0); // Ẩn
    }

    public void restore(int userId) {
        // Thêm debug log
        System.out.println("Restoring user with ID: " + userId);
        UserDAO.updateStatus(userId, 1); // Khôi phục
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.login(username, password);
    }

    public static List<User> getAllUsersWithStatus() {
        return UserDAO.getAllUsersWithStatus();
    }
}