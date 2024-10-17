package com.ader.RestApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> findById(Long id) {
        String sql = "SELECT * FROM spring.courses WHERE courseId = ?";
        Course course = jdbcTemplate.queryForObject(sql, new CourseMapper(), id);
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> findAll(int page, int size) {
        String sql = "SELECT * FROM spring.courses ORDER BY courseId LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new CourseMapper(), size, page * size);
    }

    @Override
    public Course save(Course entity) {
        String sql = "INSERT INTO spring.courses (startDate, endDate, name, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getStartDate(), entity.getEndDate(), entity.getName(), entity.getDescription());
        return entity;
    }

    @Override
    public Course update(Course entity) {
        String sql = "UPDATE spring.courses SET startDate = ?, endDate = ?, name = ?, description = ? WHERE courseId = ?";
        jdbcTemplate.update(sql, entity.getStartDate(), entity.getEndDate(), entity.getName(), entity.getDescription(), entity.getCourseId());
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.courses WHERE courseId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void addStudentToCourse(Long studentId, Long courseId) {
        String sql = "INSERT INTO spring.course_students (courseId, studentId) VALUES (?, ?)";
        jdbcTemplate.update(sql, courseId, studentId);
    }

    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        String sql = "SELECT * FROM spring.users WHERE userId IN (SELECT studentId FROM spring.course_students WHERE courseId = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), courseId);
    }

    @Override
    public void deleteStudentFromCourse(Long studentId, Long courseId) {
        String sql = "DELETE FROM spring.course_students WHERE courseId = ? AND studentId = ?";
        jdbcTemplate.update(sql, courseId, studentId);
    }

    @Override
    public void addTeacherToCourse(Long teacherId, Long courseId) {
        String sql = "INSERT INTO spring.course_teachers (courseId, teacherId) VALUES (?, ?)";
        jdbcTemplate.update(sql, courseId, teacherId);
    }

    @Override
    public List<User> getTeachersByCourseId(Long courseId) {
        String sql = "SELECT * FROM spring.users WHERE userId IN (SELECT teacherId FROM spring.course_teachers WHERE courseId = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), courseId);
    }

    @Override
    public void deleteTeacherFromCourse(Long teacherId, Long courseId) {
        String sql = "DELETE FROM spring.course_teachers WHERE courseId = ? AND teacherId = ?";
        jdbcTemplate.update(sql, courseId, teacherId);
    }
}
