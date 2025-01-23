package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;

public interface LessonService {
    Page<Lesson> getAllLessons(Pageable pageable);
    Lesson createLesson(LessonDto lessonDto);
    Optional<Lesson> getLessonById(Long id);
    Lesson updateLesson(LessonDto lessonDto, Long lessonId);
    void deleteLesson(Long id);
    List<Lesson> getLessonsByCourseId(Long courseId);
}
