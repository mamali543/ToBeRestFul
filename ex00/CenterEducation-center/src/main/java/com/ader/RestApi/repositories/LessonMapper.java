package com.ader.RestApi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.Role;
import com.ader.RestApi.pojo.User;


public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson  mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setLessonId(rs.getLong("lesson_id"));
        lesson.setStartTime(rs.getTime("start_time").toLocalTime());
        lesson.setEndTime(rs.getTime("end_time").toLocalTime());
        lesson.setDayOfWeek(rs.getString("day_of_week"));

        User user = new User();
        user.setId(rs.getLong("teacher_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.values()[rs.getInt("role")]);
        lesson.setTeacher(user);
        return lesson;
    }
}
