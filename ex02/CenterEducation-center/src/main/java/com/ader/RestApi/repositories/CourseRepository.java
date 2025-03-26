package com.ader.RestApi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ader.RestApi.pojo.Course;
import java.util.Optional;

@RepositoryRestResource(path = "courses")
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Secure findAll (GET /courses) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Page<Course> findAll(Pageable pageable);
    
    // Secure findById (GET /courses/{id}) - Only authenticated users
    @Override
    @PreAuthorize("isAuthenticated()")
    Optional<Course> findById(Long id);
    
    // Hide standard save methods - we'll use our controller for this
    @Override
    @RestResource(exported = false)
    <S extends Course> S save(S entity);
    
    // Hide standard delete methods - we'll use our controller for this
    @Override
    @RestResource(exported = false)
    void delete(Course entity);
    
    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
}
