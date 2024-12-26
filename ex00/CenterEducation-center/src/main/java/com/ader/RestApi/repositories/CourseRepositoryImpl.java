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
        String sql = "SELECT * FROM spring.courses WHERE course_id = ?";
        Course course = jdbcTemplate.queryForObject(sql, new CourseMapper(), id);
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> findAll(int page, int size) {
        String sql = "SELECT * FROM spring.courses ORDER BY course_id LIMIT ? OFFSET ?";
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
        String sql = "UPDATE spring.courses SET start_date = ?, end_date = ?, name = ?, description = ? WHERE course_id = ?";
        jdbcTemplate.update(sql, entity.getStartDate(), entity.getEndDate(), entity.getName(), entity.getDescription(), entity.getCourseId());
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.courses WHERE course_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void addStudentToCourse(Long student_id, Long course_id) {
        String sql = "INSERT INTO spring.course_students (course_id, student_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, course_id, student_id);
    }

    @Override
    public List<User> getStudentsByCourseId(Long course_id) {
        String sql = "SELECT * FROM spring.users WHERE user_id IN (SELECT student_id FROM spring.course_students WHERE course_id = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), course_id);
    }

    @Override
    public void deleteStudentFromCourse(Long student_id, Long course_id) {
        String sql = "DELETE FROM spring.course_students WHERE course_id = ? AND student_id = ?";
        jdbcTemplate.update(sql, course_id, student_id);
    }

    @Override
    public void addTeacherToCourse(Long teacher_id, Long course_id) {
        String sql = "INSERT INTO spring.course_teachers (course_id, teacher_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, course_id, teacher_id);
    }

    @Override
    public List<User> getTeachersByCourseId(Long course_id) {
        String sql = "SELECT * FROM spring.users WHERE user_id IN (SELECT teacher_id FROM spring.course_teachers WHERE course_id = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), course_id);
    }

    @Override
    public void deleteTeacherFromCourse(Long teacher_id, Long course_id) {
        String sql = "DELETE FROM spring.course_teachers WHERE course_id = ? AND teacher_id = ?";
        jdbcTemplate.update(sql, course_id, teacher_id);
    }
}
