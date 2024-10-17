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
    List<Lesson> getLessonsByCourseId(Long courseId);
    Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto);
    public void deleteLessonByCourse(Long lessonId, Long courseId);
	User addStudentToCourse(Long studentId, Long courseId);
	List<User> getStudentsByCourseId(Long courseId);

    void deleteStudentFromCourse(Long studentId, Long courseId);
    User addTeacherToCourse(Long teacherId, Long courseId);
    List<User> getTeachersByCourseId(Long courseId);
    void deleteTeacherFromCourse(Long teacherId, Long courseId);
}
