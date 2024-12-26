package com.ader.RestApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ader.RestApi.pojo.User;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM spring.users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM spring.users WHERE login = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), login);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll(int page, int size) {
        String sql = "SELECT * FROM spring.users ORDER BY user_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new UserMapper(), size, page * size);
    }

    @Override
    public User save(User entity) {
        System.out.println(entity.toString());
        String sql = "INSERT INTO spring.users (first_name, last_name, login, password, role) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(),  entity.getLogin(), entity.getPassword(), entity.getRole().ordinal());
        return entity;
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE spring.users SET first_name = ?, last_name = ?, login = ?, password = ?, role = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(), entity.getLogin(), entity.getPassword(), entity.getRole().ordinal(), entity.getId());
        return  entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.users WHERE user_id = ?";
        jdbcTemplate.update(sql, new Object[]{ id });
    }


}
