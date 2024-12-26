package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;

public interface CourseService {
    List<Course> getAllCourses(int page, int size);

    Course createCourse(Course course);

    Optional<Course> getCourseById(Long id);

    Course updateCourse(Course course);

    Course saveCourse(Course course);

    void deleteCourse(Long id);

    Lesson addLessonToCourse(LessonDto lessonDto);

    List<Lesson> getLessonsByCourseId(Long course_id);

    Lesson updateLessonByCourse(Long lesson_id, LessonDto lessonDto);

    void deleteLessonByCourse(Long lesson_id, Long course_id);

    User addStudentToCourse(Long student_id, Long course_id);

    List<User> getStudentsByCourseId(Long course_id);

    void deleteStudentFromCourse(Long student_id, Long course_id);

    User addTeacherToCourse(Long teacher_id, Long course_id);

    List<User> getTeachersByCourseId(Long course_id);

    void deleteTeacherFromCourse(Long teacher_id, Long course_id);
}
