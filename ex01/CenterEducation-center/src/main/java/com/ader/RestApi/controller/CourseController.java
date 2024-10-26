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

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getAllCourses(page, size));
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        course.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(@PathVariable Long courseId, @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.addLessonToCourse(lessonDto));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourseId(courseId));
    }
    
    @PutMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Lesson> updateLessonByCourse(@PathVariable Long courseId, @PathVariable Long lessonId, @RequestBody LessonDto lessonDto) {
        lessonDto.setCourseId(courseId);
        return ResponseEntity.ok(courseService.updateLessonByCourse(lessonId, lessonDto));
    }

    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLessonByCourse(@PathVariable Long courseId, @PathVariable Long lessonId) {
        courseService.deleteLessonByCourse(lessonId, courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<User> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.addStudentToCourse(studentId, courseId));
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getStudentsByCourseId(courseId));
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<User> addTeacherToCourse(@PathVariable Long courseId, @PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.addTeacherToCourse(teacherId, courseId));
    }

    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<List<User>> getTeachersByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getTeachersByCourseId(courseId));
    }

    @DeleteMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Void> deleteTeacherFromCourse(@PathVariable Long courseId, @PathVariable Long teacherId) {
        courseService.deleteTeacherFromCourse(teacherId, courseId);
        return ResponseEntity.noContent().build();
    }
}
