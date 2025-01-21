package com.ader.RestApi.service;

import com.ader.RestApi.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User createUser(User user);
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    // User saveUser(User user);
    void deleteUser(Long id);
}
