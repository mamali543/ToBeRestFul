package com.ader.RestApi.repositories;

import java.util.List;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.pojo.Lesson;

public interface LessonRepository extends CrudRepository<Lesson> {
    public Lesson saveDto(LessonDto entity);

    public Lesson updateDto(LessonDto entity, Long lesson_id);

    public List<Lesson> findByCourseId(Long course_id);
}
