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
        String sql = "SELECT l.lesson_id, l.start_time, l.end_time, l.day_of_week, " +
                        "u.user_id as teacher_id, u.first_name, u.last_name , u.login, u.password, u.role " +
                        "FROM spring.lessons l " +
                        "JOIN spring.users u ON u.user_id = l.teacher_id " +
                        "WHERE l.lesson_id = ?";
        return Optional.ofNullable( jdbcTemplate.queryForObject(sql, new LessonMapper(), id));
    }

    @Override
    public List<Lesson> findAll(int page, int size) {
        String sql = "SELECT l.lesson_id, l.start_time, l.end_time, l.day_of_week, " +
                        "u.user_id as teacher_id, u.first_name, u.last_name , u.login, u.password, u.role " +
                        "FROM spring.lessons l " +
                        "JOIN spring.users u ON u.user_id = l.teacher_id " +
                        "ORDER BY l.lesson_id " +
                        "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new LessonMapper(), size, page * size);
    }

    @Override
    public List<Lesson> findByCourseId(Long course_id) {
        String sql = "SELECT l.lesson_id, l.start_time, l.end_time, l.day_of_week, " +
                        "u.user_id as teacher_id, u.first_name, u.last_name , u.login, u.password, u.role " +
                        "FROM spring.lessons l " +
                        "JOIN spring.users u ON u.user_id = l.teacher_id " +
                        "WHERE l.course_id = ? ";
        return jdbcTemplate.query(sql, new LessonMapper(), course_id);
    }

    @Override
    /**
     * Saves a new lesson to the database using a LessonDto object
     * 
     * @param entity The LessonDto containing the lesson information to save
     * @return The created Lesson object with the generated ID and teacher information
     * @throws IllegalArgumentException if the teacher ID is invalid
     * @throws RuntimeException if there is an error saving to the database
     */
    public Lesson saveDto(LessonDto entity) {
        // SQL query to insert a new lesson with all required fields
        String sql = "INSERT INTO spring.lessons (start_time, end_time, day_of_week, teacher_id, course_id) VALUES (?, ?, ?, ?, ?)";
        
        // KeyHolder will store the auto-generated lessonId
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        // Execute the insert query with prepared statement
        jdbcTemplate.update(connection -> {
            // Create prepared statement that returns the generated lessonId
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"lesson_id"});
            
            // Set all the parameters for the insert
            ps.setTime(1, Time.valueOf(entity.getStartTime()));     // Convert string time to SQL Time
            ps.setTime(2, Time.valueOf(entity.getEndTime()));       // Convert string time to SQL Time
            ps.setString(3, entity.getDayOfWeek());                 // Day of week as string
            ps.setLong(4, entity.getTeacherId());                   // Teacher's ID
            ps.setLong(5, entity.getCourseId());                    // Course ID
            return ps;
        }, keyHolder);

        // Fetch the teacher information using teacherId
        Optional<User> user = userRepository.findById(entity.getTeacherId());
        User teacher = user.get();

        // Create and return a new Lesson object with all the saved information
        Lesson lesson = new Lesson(
            keyHolder.getKey().longValue(),      // Get the auto-generated lesson ID
            LocalTime.parse(entity.getStartTime()), // Convert string time to LocalTime
            LocalTime.parse(entity.getEndTime()),   // Convert string time to LocalTime
            entity.getDayOfWeek(),                 // Day of week
            teacher                                // Teacher object with full information
        );
        return lesson;
    }

    @Override
    public Lesson updateDto(LessonDto entity, Long lesson_id) {
        System.out.println(entity.toString());
        String sql = "UPDATE spring.lessons SET start_time = ?, end_time = ?, day_of_week = ?, teacher_id = ?, course_id = ? WHERE lesson_id = ?";
        jdbcTemplate.update(sql, Time.valueOf(entity.getStartTime()), Time.valueOf(entity.getEndTime()), entity.getDayOfWeek(), entity.getTeacherId(), entity.getCourseId(), lesson_id);
        Optional<User> user = userRepository.findById(entity.getTeacherId());
        User teacher = user.get();
        Lesson lesson = new Lesson(
            lesson_id,
            LocalTime.parse(entity.getStartTime()),
            LocalTime.parse(entity.getEndTime()),
            entity.getDayOfWeek(),
            teacher
        );
        return lesson;
    }

    @Override
    public Lesson update(Lesson entity) {
        String sql = "UPDATE spring.lessons SET start_time = ?, end_time = ?, day_of_week = ?, teacher_id = ? WHERE lesson_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, entity.getStartTime(), entity.getEndTime(), entity.getDayOfWeek(), entity.getTeacher().getId(), entity.getLessonId());
        if (rowsAffected == 0) {
            throw new RuntimeException("Lesson with id " + entity.getLessonId() + " not found");
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM spring.lessons WHERE lesson_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Lesson with id " + id + " not found");
        }
    }

    @Override
    public Lesson save(Lesson entity) {
        String sql = "INSERT INTO spring.lessons (start_time, end_time, day_of_week, teacher_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getStartTime(), entity.getEndTime(), entity.getDayOfWeek(), entity.getTeacher().getId());
        return entity;
    }


}
