package com.ader.RestApi.repositories;

import com.ader.RestApi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM spring.users WHERE userId = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] {id}, new UserMapper()));
    }

    @Override
    public List<User> findAll(int page, int size) {
        String sql = "SELECT * FROM spring.users LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new UserMapper(), size, page * size);
    }

    @Override
    public User save(User entity) {
        String sql = "INSERT INTO users (firstName, lastName, role, login, password) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(), entity.getRole().toString(), entity.getLogin(), entity.getPassword());
        return entity;
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, role = ?, login = ?, password = ? WHERE userId = ?";
        jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(), entity.getRole().toString(), entity.getLogin(), entity.getPassword(), entity.getId());
        return  entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.users WHERE userId = ?";
        jdbcTemplate.update(sql, new Object[]{ id });
    }
}
