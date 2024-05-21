package com.example.coursemicroservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Basic CRUD methods are inherited from JpaRepository
    // Additional custom queries can be added here if needed

    // Example of custom query to find all enrollments by studentID
    List<Enrollment> findByStudentID(Long studentID);

    // Example of custom query to find all enrollments by accepted status
    List<Enrollment> findByAccepted(boolean accepted);
}