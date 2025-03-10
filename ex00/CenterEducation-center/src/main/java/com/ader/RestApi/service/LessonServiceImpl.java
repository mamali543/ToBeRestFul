package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.UserRepository;

/**
 * Implementation of the LessonService interface that provides CRUD operations
 * for Lesson entities.
 */
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new LessonServiceImpl with the specified LessonRepository.
     *
     * @param lessonRepository the repository to handle lesson data persistence
     */
    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository,
            UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a paginated list of all lessons.
     *
     * @param page the page number
     * @param size the size of each page
     * @return a list of lessons for the specified page
     */
    @Override
    public List<Lesson> getAllLessons(int page, int size) {
        return lessonRepository.findAll(page, size);
    }

    /**
     * Creates a new lesson from the provided DTO.
     *
     * @param lessonDto the DTO containing lesson data
     * @return the created lesson entity
     */
    @Override
    public Lesson createLesson(LessonDto lessonDto) {
        System.out.println("LessonDto teacherId: " + lessonDto.getTeacherId());
        System.out.println("LessonDto courseId: " + lessonDto.getCourseId());
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course with id " + lessonDto.getCourseId() + " not found"));
        User user = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(
                        () -> new BadRequestException("Teacher with id " + lessonDto.getTeacherId() + " not found"));
        return lessonRepository.saveDto(lessonDto);
    }

    /**
     * Retrieves a lesson by its ID.
     *
     * @param id the ID of the lesson to retrieve
     * @return an Optional containing the found lesson, or empty if not found
     */
    @Override
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    /**
     * Updates an existing lesson with the provided DTO data.
     *
     * @param lessonDto the DTO containing updated lesson data
     * @param lesson_id the ID of the lesson to update
     * @return the updated lesson entity
     * @throws RuntimeException if the lesson with the given ID is not found
     */
    @Override
    public Lesson updateLesson(LessonDto lessonDto, Long lesson_id) {
        Lesson lesson = lessonRepository.findById(lesson_id)
                .orElseThrow(() -> new RuntimeException("Lesson with id " + lesson_id + " not found"));
        return lessonRepository.updateDto(lessonDto, lesson_id);
    }

    /**
     * Saves a lesson entity.
     *
     * @param Lesson the lesson entity to save
     * @return the saved lesson entity
     */
    @Override
    public Lesson saveLesson(Lesson Lesson) {
        return lessonRepository.save(Lesson);
    }

    /**
     * Deletes a lesson by its ID.
     *
     * @param id the ID of the lesson to delete
     */
    @Override
    public void deleteLesson(Long id) {
        lessonRepository.delete(id);
    }
}
