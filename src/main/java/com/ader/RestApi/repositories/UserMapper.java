package com.ader.RestApi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ader.RestApi.pojo.Role;
import com.ader.RestApi.pojo.User;

public class UserMapper implements RowMapper<User> {

    @Override
    public User  mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(rs.getLong("userId"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("login"), rs.getString("password"), Role.values()[rs.getInt("role")]);
        return user;
    }
}
