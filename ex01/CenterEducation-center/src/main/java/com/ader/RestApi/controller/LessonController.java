package com.ader.RestApi.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Controller", description = "Endpoints for managing lessons")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Create a new lesson")
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.createLesson(lessonDto));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all lessons with pagination")
    public ResponseEntity<Page<Lesson>> getAllLessons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(lessonService.getAllLessons(PageRequest.of(page, size)));
    }

    @GetMapping("/{lessonId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a lesson by ID")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long lessonId) {
        return lessonService.getLessonById(lessonId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all lessons for a specific course")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(courseId));
    }

    @PutMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update a lesson")
    public ResponseEntity<Lesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonDto, lessonId));
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete a lesson")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}
