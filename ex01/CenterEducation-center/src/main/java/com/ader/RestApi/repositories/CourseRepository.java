package com.ader.RestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ader.RestApi.pojo.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // JpaRepository provides all basic CRUD operations
    // Additional custom queries can be added here if needed
}
