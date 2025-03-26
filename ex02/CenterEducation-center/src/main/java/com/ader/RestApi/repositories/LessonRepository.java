package com.ader.RestApi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ader.RestApi.pojo.Lesson;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "lessons")
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Custom query method - not exposed as endpoint
    @RestResource(exported = false)
    List<Lesson> findByCourse_CourseId(Long courseId);
    
    // Secure findAll (GET /lessons) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Page<Lesson> findAll(Pageable pageable);
    
    // Secure findById (GET /lessons/{id}) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Optional<Lesson> findById(Long id);
    
    // Hide standard save methods - we'll use our controller for this
    @Override
    @RestResource(exported = false)
    <S extends Lesson> S save(S entity);
    
    // Hide standard delete methods - we'll use our controller for this
    @Override
    @RestResource(exported = false)
    void delete(Lesson entity);
    
    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
    
    // @RestResource(exported = false)
    // void deleteByTeacher_UserId(Long teacherId);
}
