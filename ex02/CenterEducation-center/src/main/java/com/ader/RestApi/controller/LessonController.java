package com.ader.RestApi.controller;

import java.util.List;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RepositoryRestController
// @RequestMapping("/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Controller", description = "Endpoints for managing lessons")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/lessons")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Create a new lesson")
    public ResponseEntity<Lesson> createLesson(
            @RequestBody LessonDto lessonDto) {
                return ResponseEntity.ok(lessonService.createLesson(lessonDto));
            }
            
            @PutMapping("/lessons/{lessonId}")
            @PreAuthorize("hasAuthority('ADMINISTRATOR')")
            @Operation(summary = "Update a lesson")
            public ResponseEntity<Lesson> updateLesson(
                @PathVariable Long lessonId,
                @RequestBody LessonDto lessonDto) {
                    // System.out.println("LessonController.updateLesson()" + "lessonDto.courseId: "+ lessonDto.getCourseId() );
            return ResponseEntity.ok(lessonService.updateLesson(lessonDto, lessonId));
        }
    @GetMapping("/lessons/course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all lessons for a specific course")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(courseId));
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a lesson")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teachers/{teacherId}/lessons")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get lessons taught by a teacher")
    public ResponseEntity<List<Lesson>> getLessonsByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok(lessonService.getLessonsByTeacherId(teacherId));
    }
}
