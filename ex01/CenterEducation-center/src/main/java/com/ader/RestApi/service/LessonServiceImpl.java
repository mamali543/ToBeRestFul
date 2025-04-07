package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;
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

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public Page<Lesson> getAllLessons(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Override
    public Lesson createLesson(LessonDto lessonDto) {
        User teacher = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

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
    @Transactional
    public Lesson updateLesson(LessonDto lessonDto, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        User teacher = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // If teacher isn't associated with course, add them
        if (!course.getTeachers().contains(teacher)) {
            course.getTeachers().add(teacher);
            teacher.getTaughtCourses().add(course);
        }

        // Check time conflicts with other lessons in the course
        boolean hasConflict = course.getLessons().stream()
                .filter(existingLesson -> !existingLesson.getLessonId().equals(lessonId)) // Exclude current lesson
                .anyMatch(existingLesson -> existingLesson.getDayOfWeek().equals(lessonDto.getDayOfWeek()) &&
                        (lessonDto.getStartTime().isBefore(existingLesson.getEndTime()) &&
                                lessonDto.getEndTime().isAfter(existingLesson.getStartTime())));

        if (hasConflict) {
            throw new BadRequestException("Time conflict with existing lesson in the course");
        }

        // Remove from old course if changing courses
        if (!lesson.getCourse().getCourseId().equals(course.getCourseId())) {
            // lesson.getCourse().getLessons().remove(lesson);
            lesson.setCourse(course); // Set the new course

            // Add to the new course's lessons
            course.getLessons().add(lesson); // Add to the new course's lessons
        }

        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(teacher);
        lesson.setCourse(course);
        course.getLessons().add(lesson);

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new BadRequestException("Lesson not found");
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
