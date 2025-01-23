package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<Lesson> getAllLessons(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Override
    public Lesson createLesson(LessonDto lessonDto) {
        User teacher = userRepository.findById(lessonDto.getTeacherId())
            .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        
        Course course = courseRepository.findById(lessonDto.getCourseId())
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(teacher);
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public Lesson updateLesson(LessonDto lessonDto, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        User teacher = userRepository.findById(lessonDto.getTeacherId())
            .orElseThrow(() -> new BadRequestException("Teacher not found"));
        
        Course course = courseRepository.findById(lessonDto.getCourseId())
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(teacher);
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("Lesson not found");
        }
        lessonRepository.deleteById(id);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new BadRequestException("Course not found with id: " + courseId);
        }
        return lessonRepository.findByCourse_CourseId(courseId);
    }
}
