package com.ader.RestApi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ader.RestApi.pojo.Course;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course(rs.getLong("course_id") , rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate(), rs.getString("name"), null, null, rs.getString("description"), null);
        return course;
    }
}
