package com.ader.RestApi.repositories;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;

@Repository
public class LessonRepositoryImpl implements LessonRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final UserRepository userRepository;

    public LessonRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        String sql = "SELECT l.lessonId, l.startTime, l.endTime, l.dayOfWeek, " +
                        "u.userId as teacherId, u.firstName, u.lastName " +
                        "FROM spring.lessons l " +
                        "JOIN spring.users u ON u.userId = l.teacherId " +
                        "WHERE l.lessonId = ?";
        return Optional.ofNullable( jdbcTemplate.queryForObject(sql, new LessonMapper(), id));
    }

    @Override
    public List<Lesson> findAll(int page, int size) {
        String sql = "SELECT l.lessonId, l.startTime, l.endTime, l.dayOfWeek, " +
                        "u.userId as teacherId, u.firstName, u.lastName , u.login, u.password, u.role " +
                        "FROM spring.lessons l " +
                        "JOIN spring.users u ON u.userId = l.teacherId " +
                        "ORDER BY l.lessonId " +
                        "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new LessonMapper(), size, page * size);
    }

    @Override
    public Lesson saveDto(LessonDto entity) {
        String sql = "INSERT INTO spring.lessons (startTime, endTime, dayOfWeek, teacherId) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"lessonid"});
            ps.setTime(1, Time.valueOf(entity.getStartTime()));
            ps.setTime(2, Time.valueOf(entity.getEndTime()));
            ps.setString(3, entity.getDayOfWeek());
            ps.setLong(4, entity.getTeacherId());
            return ps;
        }, keyHolder);

        Optional<User> user = userRepository.findById(entity.getTeacherId());
        User teacher = user.get();

        Lesson lesson = new Lesson(
            keyHolder.getKey().longValue(),
            LocalTime.parse(entity.getStartTime()),
            LocalTime.parse(entity.getEndTime()),
            entity.getDayOfWeek(),
            teacher
        );
        return lesson;
    }

    @Override
    public Lesson update(Lesson entity) {
        String sql = "UPDATE spring.lessons SET startTime = ?, endTime = ?, dayOfWeek = ?, teacherId = ? WHERE lessonId = ?";
        int rowsAffected = jdbcTemplate.update(sql, entity.getStartTime(), entity.getEndTime(), entity.getDayOfWeek(), entity.getTeacher().getId(), entity.getLessonId());
        if (rowsAffected == 0) {
            throw new RuntimeException("Lesson with id " + entity.getLessonId() + " not found");
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.lessons WHERE lessonId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Lesson with id " + id + " not found");
        }
    }

    @Override
    public Lesson save(Lesson entity) {
        String sql = "INSERT INTO spring.lessons (startTime, endTime, dayOfWeek, teacherId) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getStartTime(), entity.getEndTime(), entity.getDayOfWeek(), entity.getTeacher().getId());
        return entity;
    }
}
