package edu.icet.service;

import edu.icet.dto.Group;
import edu.icet.dto.User;

import java.util.List;

public interface UserService {
    boolean registerUser(User user);

    List<User> getUser();

    List<User> findByName(String name);

    boolean deleteUser(Integer id);

    boolean updateUser(User user);

    User findById(Integer id);

    User findByEmail(String email);

    boolean updateUserPassword(String email, String newPassword);
}
