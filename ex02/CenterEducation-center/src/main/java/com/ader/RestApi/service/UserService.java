package com.ader.RestApi.service;

public interface UserService {
    // Remove getAllUsers, createUser, getUserById, updateUser
    // Keep only the delete method which has custom logic
    void deleteUser(Long id);
}
