package com.ader.RestApi.repositories;

import java.util.List;

import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;
public interface CourseRepository extends CrudRepository<Course> {

    void addStudentToCourse(Long student_id, Long course_id);
    // Lesson addLessonToCourse(Long courseId, LessonDto lessonDto);

    List<User> getStudentsByCourseId(Long course_id);

    void deleteStudentFromCourse(Long student_id, Long course_id);

    void addTeacherToCourse(Long teacher_id, Long course_id);
    List<User> getTeachersByCourseId(Long course_id);
    void deleteTeacherFromCourse(Long teacher_id, Long course_id);
}
