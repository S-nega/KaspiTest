package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();
    void saveUser(User user);
    User findOneById(int id);
    void deleteUser(int id);
}
