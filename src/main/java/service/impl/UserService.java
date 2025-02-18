package service.impl;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.user.UserRepository;
import service.IService;


import java.util.List;

public class UserService implements IService {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public List<User> getAll() {
        return userRepository.getAllUsers(); // ✅ Gọi phương thức thông qua đối tượng
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(int id, Object o) {

    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public List findByName(String name) {
        return null;
    }


}
