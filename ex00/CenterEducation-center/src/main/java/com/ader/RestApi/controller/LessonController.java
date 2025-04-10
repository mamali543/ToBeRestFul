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
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.service.LessonService;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonDto lesson) {
        return ResponseEntity.ok(lessonService.createLesson(lesson));
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(lessonService.getAllLessons(page, size));
    }

    @GetMapping("/{lesson_id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long lesson_id) {
        return lessonService.getLessonById(lesson_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{lesson_id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long lesson_id, @RequestBody LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonDto, lesson_id));
    }

    @DeleteMapping("/{lesson_id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lesson_id) {
        lessonService.deleteLesson(lesson_id);
        return ResponseEntity.noContent().build();
    }
}
