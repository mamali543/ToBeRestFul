package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;

public interface CourseService {
    Page<Course> getAllCourses(Pageable pageable);
    Course createCourse(Course course);
    Optional<Course> getCourseById(Long id);
    Course updateCourse(Course course);
    void deleteCourse(Long id);

    // Lesson management
    Lesson addLessonToCourse(LessonDto lessonDto);
    List<Lesson> getLessonsByCourseId(Long courseId);
    Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto);
    void deleteLessonByCourse(Long lessonId, Long courseId);

    // Student management
    User addStudentToCourse(Long studentId, Long courseId);
    List<User> getStudentsByCourseId(Long courseId);
    void deleteStudentFromCourse(Long studentId, Long courseId);

    // Teacher management
    User addTeacherToCourse(Long teacherId, Long courseId);
    List<User> getTeachersByCourseId(Long courseId);
    void deleteTeacherFromCourse(Long teacherId, Long courseId);
}
