package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;

public interface LessonService {
    List<Lesson> getAllLessons(int page, int size);
    Lesson createLesson(LessonDto lessonDto);
    Optional<Lesson> getLessonById(Long id);
    Lesson updateLesson(Lesson Lesson);
    Lesson saveLesson(Lesson Lesson);
    void deleteLesson(Long id);
}
