package com.ader.RestApi.repositories;

import java.util.List;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;

public interface LessonRepository extends CrudRepository<Lesson> {
    public Lesson saveDto(LessonDto entity, Long courseId);
    public List<Lesson> findByCourseId(Long courseId);
}
