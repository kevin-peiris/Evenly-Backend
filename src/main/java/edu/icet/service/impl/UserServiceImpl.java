package edu.icet.service.impl;

import edu.icet.dto.Group;
import edu.icet.dto.User;
import edu.icet.entity.GroupEntity;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public boolean registerUser(User user) {
        try {
            UserEntity entity = mapper.map(user, UserEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getUser() {
        List<UserEntity> entityList = repository.findAll();
        List<User> userList = new ArrayList<>();

        for (UserEntity entity : entityList) {
            userList.add(mapper.map(entity, User.class));
        }
        return userList;
    }

    @Override
    public List<User> findByName(String name) {
        List<UserEntity> entityList = repository.findAll();
        List<User> userList = new ArrayList<>();

        for (UserEntity entity : entityList) {
            userList.add(mapper.map(entity, User.class));
        }
        return userList;
    }

    @Override
    public boolean deleteUser(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            UserEntity entity = mapper.map(user, UserEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findById(Integer id) {
        try {
            Optional<UserEntity> entity = repository.findById(id);
            if (entity!=null) {
                User user = mapper.map(entity, User.class);
                return user;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            Optional<UserEntity> entity = repository.findByEmail(email);
            if (entity!=null) {
                User user = mapper.map(entity, User.class);
                return user;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateUserPassword(String email, String newPassword) {
        try {
            Optional<UserEntity> entity = repository.findByEmail(email);
            if (entity!=null) {
                User user = mapper.map(entity, User.class);
                user.setPassword(newPassword);
                repository.save(mapper.map(user, UserEntity.class));
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
