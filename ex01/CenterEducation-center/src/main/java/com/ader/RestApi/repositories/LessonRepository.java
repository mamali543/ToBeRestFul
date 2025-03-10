package com.ader.RestApi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ader.RestApi.pojo.Lesson;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse_CourseId(Long courseId);
    Page<Lesson> findAll(Pageable pageable);
    void deleteByTeacher_UserId(Long teacherId);
}
