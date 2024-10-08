package com.ader.RestApi.service;


import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers(int page, int size) {
        return userRepository.findAll(page, size);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
