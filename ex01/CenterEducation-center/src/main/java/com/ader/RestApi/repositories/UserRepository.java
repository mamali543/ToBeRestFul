package com.ader.RestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ader.RestApi.pojo.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByUsername(String username);
}
