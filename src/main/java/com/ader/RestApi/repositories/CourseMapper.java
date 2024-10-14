package com.ader.RestApi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ader.RestApi.pojo.Course;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course(rs.getLong("courseId") , rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getString("name"), null, null, rs.getString("description"), null);
        return course;
    }
}
