package com.ader.RestApi.repositories;

import java.util.Optional;

import com.ader.RestApi.pojo.User;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String login);
}
