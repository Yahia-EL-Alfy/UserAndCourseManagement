package com.example.coursemicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByCategoryContainingIgnoreCase(String category);
    @Query("SELECT c FROM Course c")
    List<Course> findAllCourses(org.springframework.data.domain.Sort sort);
}