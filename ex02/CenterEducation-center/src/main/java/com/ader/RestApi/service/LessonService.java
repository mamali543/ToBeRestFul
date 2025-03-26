package com.ader.RestApi.service;

import java.util.List;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;

public interface LessonService {
    Lesson createLesson(LessonDto lessonDto);
    Lesson updateLesson(LessonDto lessonDto, Long lessonId);
    void deleteLesson(Long id);
    List<Lesson> getLessonsByCourseId(Long courseId);
}
