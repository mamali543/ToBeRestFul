package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;

import com.ader.RestApi.pojo.Course;

public interface CourseService {
    List<Course> getAllCourses(int page, int size);
    Course createCourse(Course course);
    Optional<Course> getCourseById(Long id);
    Course updateCourse(Course course);
    Course saveCourse(Course course);
    void deleteCourse(Long id);
}
