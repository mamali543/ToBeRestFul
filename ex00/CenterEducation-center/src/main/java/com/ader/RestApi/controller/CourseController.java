package com.ader.RestApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.service.CourseService;

/**
 * REST controller for managing courses and their associated resources (lessons,
 * students, teachers).
 * Provides endpoints for CRUD operations on courses and managing relationships
 * with other entities.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Retrieves a paginated list of all courses.
     * 
     * @param page The page number (zero-based)
     * @param size The number of items per page
     * @return ResponseEntity containing a list of courses
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getAllCourses(page, size));
    }

    /**
     * Creates a new course.
     * 
     * @param course The course object to create
     * @return ResponseEntity containing the created course
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    /**
     * Retrieves a specific course by its ID.
     * 
     * @param courseId The ID of the course to retrieve
     * @return ResponseEntity containing the course if found, or 404 if not found
     */
    @GetMapping("/{course_id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long course_id) {
        return courseService.getCourseById(course_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing course.
     * 
     * @param courseId The ID of the course to update
     * @param course   The updated course information
     * @return ResponseEntity containing the updated course
     */
    @PutMapping("/{course_id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long course_id, @RequestBody Course course) {
        course.setCourseId(course_id);
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    /**
     * Deletes a course by its ID.
     * 
     * @param courseId The ID of the course to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long course_id) {
        courseService.deleteCourse(course_id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a new lesson to a course.
     * 
     * @param courseId  The ID of the course
     * @param lessonDto The lesson information to add
     * @return ResponseEntity containing the created lesson
     */
    @PostMapping("/{course_id}/lessons")
        public ResponseEntity<Lesson> addLessonToCourse(@PathVariable Long course_id, @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(course_id);
        return ResponseEntity.ok(courseService.addLessonToCourse(lessonDto));
    }

    /**
     * Retrieves all lessons for a specific course.
     * 
     * @param courseId The ID of the course
     * @return ResponseEntity containing a list of lessons
     */
    @GetMapping("/{course_id}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long course_id) {
        return ResponseEntity.ok(courseService.getLessonsByCourseId(course_id));
    }

    /**
     * Updates a lesson within a course.
     * 
     * @param courseId  The ID of the course
     * @param lesson_id  The ID of the lesson to update
     * @param lessonDto The updated lesson information
     * @return ResponseEntity containing the updated lesson
     */
    @PutMapping("/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<Lesson> updateLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id,
            @RequestBody LessonDto lessonDto) {
            lessonDto.setCourseId(course_id);
        return ResponseEntity.ok(courseService.updateLessonByCourse(lesson_id, lessonDto));
    }

    /**
     * Deletes a lesson from a course.
     * 
     * @param courseId The ID of the course
     * @param lesson_id The ID of the lesson to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{course_id}/lessons/{lesson_id}")
        public ResponseEntity<Void> deleteLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id) {
        courseService.deleteLessonByCourse(lesson_id, course_id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a student to a course.
     * 
     * @param course_id  The ID of the course
     * @param student_id The ID of the student to add
     * @return ResponseEntity containing the added student
     */
    @PostMapping("/{course_id}/students/{student_id}")
    public ResponseEntity<User> addStudentToCourse(@PathVariable Long course_id, @PathVariable Long student_id) {
        return ResponseEntity.ok(courseService.addStudentToCourse(student_id, course_id));
    }

    /**
     * Retrieves all students enrolled in a course.
     * 
     * @param courseId The ID of the course
     * @return ResponseEntity containing a list of students
     */
    @GetMapping("/{course_id}/students")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long course_id) {
        return ResponseEntity.ok(courseService.getStudentsByCourseId(course_id));
    }

    /**
     * Removes a student from a course.
     * 
     * @param course_id  The ID of the course
     * @param student_id The ID of the student to remove
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{course_id}/students/{student_id}")
    public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable Long course_id, @PathVariable Long student_id) {
        courseService.deleteStudentFromCourse(student_id, course_id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a teacher to a course.
     * 
     * @param course_id  The ID of the course
     * @param teacher_id The ID of the teacher to add
     * @return ResponseEntity containing the added teacher
     */
    @PostMapping("/{course_id}/teachers/{teacher_id}")
    public ResponseEntity<User> addTeacherToCourse(@PathVariable Long course_id, @PathVariable Long teacher_id) {
        return ResponseEntity.ok(courseService.addTeacherToCourse(teacher_id, course_id));
    }

    /**
     * Retrieves all teachers assigned to a course.
     * 
     * @param course_id The ID of the course
     * @return ResponseEntity containing a list of teachers
     */
    @GetMapping("/{course_id}/teachers")
    public ResponseEntity<List<User>> getTeachersByCourseId(@PathVariable Long course_id) {
        return ResponseEntity.ok(courseService.getTeachersByCourseId(course_id));
    }

    /**
     * Removes a teacher from a course.
     * 
     * @param course_id  The ID of the course
     * @param teacher_id The ID of the teacher to remove
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{course_id}/teachers/{teacher_id}")
    public ResponseEntity<Void> deleteTeacherFromCourse(@PathVariable Long course_id, @PathVariable Long teacher_id) {
        courseService.deleteTeacherFromCourse(teacher_id, course_id);
        return ResponseEntity.noContent().build();
    }
}
