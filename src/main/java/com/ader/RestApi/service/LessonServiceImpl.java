package com.ader.RestApi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.repositories.LessonRepository;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> getAllLessons(int page, int size) {
        return lessonRepository.findAll(page, size);
    }

    // @Override
    // public Lesson createLesson(LessonDto lessonDto) {
    //     return lessonRepository.saveDto(lessonDto);
    // }

    @Override
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public Lesson updateLesson(Lesson Lesson) {
        return lessonRepository.update(Lesson);
    }

    @Override
    public Lesson saveLesson(Lesson Lesson) {
        return lessonRepository.save(Lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.delete(id);
    }
}
