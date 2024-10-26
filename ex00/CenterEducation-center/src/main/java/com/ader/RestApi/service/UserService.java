package com.ader.RestApi.service;

import com.ader.RestApi.pojo.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers(int page, int size);
    User createUser(User user);
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    User saveUser(User user);
    void deleteUser(Long id);
}
