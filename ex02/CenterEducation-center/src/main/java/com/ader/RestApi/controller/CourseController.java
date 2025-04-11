package com.ader.RestApi.controller;

import java.util.List;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@RepositoryRestController
// @RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller", description = "Endpoints for managing courses")
public class CourseController {

    private final CourseService courseService;

    /*
     * --------------------------------- Managing Courses *
     * ---------------------------------
     */
    // GET endpoints - accessible by all authenticated users
    // POST, PUT, DELETE endpoints - only for ADMINISTRATOR
    @PostMapping("/courses") // Create a new course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Create a new course")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/courses/{courseId}") // Update a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a course")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody Course course) {
        course.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @DeleteMapping("/courses/{courseId}") // Delete a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/courses/{courseId}/publish") // Publish a draft course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Publish a draft course")
    public ResponseEntity<Course> publishCourse(@PathVariable Long courseId) {
        System.out.println("publish cOURSE METHOD CALLED <<<<<>>>>>>");
        Course publishedCourse = courseService.publishCourse(courseId);
        return ResponseEntity.ok(publishedCourse);
    }

    /*
     * --------------------------------- Managing course Lessons *
     * ---------------------------------
     */
    // Lesson Management
    @PostMapping("/courses/{courseId}/add-lesson") // Add a lesson to a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a lesson to a course")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.addLessonToCourse(lessonDto));
    }

    @GetMapping("/courses/{courseId}/lessons") // Get all lessons for a course
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all lessons for a course")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourseId(courseId));
    }

    @PutMapping("/courses/{courseId}/lessons/{lessonId}") // Update a lesson in a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a lesson in a course")
    public ResponseEntity<Lesson> updateLessonInCourse(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateLessonByCourse(lessonId, lessonDto));
    }

    @DeleteMapping("/courses/{courseId}/lessons/{lessonId}") // Delete a lesson from a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a lesson from a course")
    public ResponseEntity<Void> deleteLessonFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long lessonId) {
        courseService.deleteLessonByCourse(lessonId, courseId);
        return ResponseEntity.noContent().build();
    }

    /*
     * --------------------------------- Managing course Students *
     * ---------------------------------
     */
    // Student Management
    @PostMapping("/courses/{courseId}/students/{studentId}") // Add a student to a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a student to a course")
    public ResponseEntity<User> addStudentToCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.addStudentToCourse(studentId, courseId));
    }

    @GetMapping("/courses/{courseId}/students") // Get all students in a course
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all students in a course")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getStudentsByCourseId(courseId));
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}") // Remove a student from a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Remove a student from a course")
    public ResponseEntity<Void> removeStudentFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    /*
     * --------------------------------- Managing course Teachers *
     * ---------------------------------
     */
    // Teacher Management
    @PostMapping("/courses/{courseId}/teachers/{teacherId}") // Add a teacher to a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a teacher to a course")
    public ResponseEntity<User> addTeacherToCourse(
            @PathVariable Long courseId,
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.addTeacherToCourse(teacherId, courseId));
    }

    @GetMapping("/courses/{courseId}/teachers") // Get all teachers in a course
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all teachers in a course")
    public ResponseEntity<List<User>> getTeachersByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getTeachersByCourseId(courseId));
    }

    @DeleteMapping("/courses/{courseId}/teachers/{teacherId}") // Remove a teacher from a course
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Remove a teacher from a course")
    public ResponseEntity<Void> removeTeacherFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long teacherId) {
        courseService.deleteTeacherFromCourse(teacherId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses/student/{studentId}") // Get courses enrolled by a student
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get courses enrolled by a student")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.getCoursesByStudentId(studentId));
    }

    @GetMapping("/courses/teacher/{teacherId}") // Get courses taught by a teacher
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get courses taught by a teacher")
    public ResponseEntity<List<Course>> getTaughtCourses(@PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacherId(teacherId));
    }

    /* associate existing lesson with a course */
    // @PostMapping("/{courseId}/lessons/{lessonId}")
    // @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    // @Operation(summary = "Associate an existing lesson with a course")
    // public ResponseEntity<Lesson> associateLessonWithCourse(
    // @PathVariable Long courseId,
    // @PathVariable Long lessonId) {
    // return ResponseEntity.ok(courseService.associateLessonWithCourse(courseId,
    // lessonId));
    // }
}
