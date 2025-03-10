package com.ader.RestApi.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@Tag(name = "Course Controller", description = "Endpoints for managing courses")
public class CourseController {

    private final CourseService courseService;

    // GET endpoints - accessible by all authenticated users
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all courses with pagination")
    public ResponseEntity<Page<Course>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getAllCourses(PageRequest.of(page, size)));
    }

    // POST, PUT, DELETE endpoints - only for ADMINISTRATOR
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Create a new course")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a course by ID")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a course")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody Course course) {
        course.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    // Lesson Management
    @PostMapping("/{courseId}/lessons")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a lesson to a course")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.addLessonToCourse(lessonDto));
    }

    @GetMapping("/{courseId}/lessons")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all lessons for a course")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourseId(courseId));
    }

    @PutMapping("/{courseId}/lessons/{lessonId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a lesson in a course")
    public ResponseEntity<Lesson> updateLessonInCourse(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateLessonByCourse(lessonId, lessonDto));
    }

    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a lesson from a course")
    public ResponseEntity<Void> deleteLessonFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long lessonId) {
        courseService.deleteLessonByCourse(lessonId, courseId);
        return ResponseEntity.noContent().build();
    }

    /*associate existing lesson with a course*/
    // @PostMapping("/{courseId}/lessons/{lessonId}")
    // @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    // @Operation(summary = "Associate an existing lesson with a course")
    // public ResponseEntity<Lesson> associateLessonWithCourse(
    //         @PathVariable Long courseId,
    //         @PathVariable Long lessonId) {
    //     return ResponseEntity.ok(courseService.associateLessonWithCourse(courseId, lessonId));
    // }

    // Student Management
    @PostMapping("/{courseId}/students/{studentId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a student to a course")
    public ResponseEntity<User> addStudentToCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.addStudentToCourse(studentId, courseId));
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all students in a course")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getStudentsByCourseId(courseId));
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Remove a student from a course")
    public ResponseEntity<Void> removeStudentFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    // Teacher Management
    @PostMapping("/{courseId}/teachers/{teacherId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add a teacher to a course")
    public ResponseEntity<User> addTeacherToCourse(
            @PathVariable Long courseId,
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.addTeacherToCourse(teacherId, courseId));
    }

    @GetMapping("/{courseId}/teachers")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all teachers in a course")
    public ResponseEntity<List<User>> getTeachersByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getTeachersByCourseId(courseId));
    }

    @DeleteMapping("/{courseId}/teachers/{teacherId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Remove a teacher from a course")
    public ResponseEntity<Void> removeTeacherFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long teacherId) {
        courseService.deleteTeacherFromCourse(teacherId, courseId);
        return ResponseEntity.noContent().build();
    }
}
