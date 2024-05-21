package com.example.coursemicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return ResponseEntity.ok("Course added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElse(null);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setDuration(updatedCourse.getDuration());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setCapacity(updatedCourse.getCapacity());
            existingCourse.setStatus(updatedCourse.isStatus());
            existingCourse.setRating(updatedCourse.getRating());
            existingCourse.setEnrolledStudents(updatedCourse.getEnrolledStudents());
            existingCourse.setListOfReviews(updatedCourse.getListOfReviews());

            courseRepository.save(existingCourse);
            return ResponseEntity.ok("Course updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {

        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.ok("Course deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<Course>> searchCoursesByName(@RequestParam String name) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Course>> searchCoursesByCategory(@RequestParam String category) {
        List<Course> courses = courseRepository.findByCategoryContainingIgnoreCase(category);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/sortByRating")
    public ResponseEntity<List<Course>> sortCoursesByRating() {
        List<Course> courses = courseRepository.findAllCourses(Sort.by(Sort.Direction.DESC, "rating"));
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/enrollments/add")
    public ResponseEntity<String> addEnrollment(@RequestParam Long courseID, @RequestParam Long studentID) {
        // Create a new Enrollment object with the provided IDs
        Enrollment enrollment = new Enrollment(studentID, courseID, "pending");
        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok("Enrollment added successfully");
    }

//     @PutMapping("/enrollments/update/{id}")
//     public ResponseEntity<String> updateEnrollment(@PathVariable Long id, @RequestBody String accepted) {
//     Enrollment existingEnrollment = enrollmentRepository.findById(id).orElse(null);
//     if (existingEnrollment != null) {
        
//         existingEnrollment.setAccepted(accepted);
//         enrollmentRepository.save(existingEnrollment);
//         return ResponseEntity.ok("Enrollment updated successfully");
//     } else {
//         return ResponseEntity.notFound().build();
//     }
// }

@PutMapping("/enrollments/update/{id}")
public ResponseEntity<String> updateEnrollment(@PathVariable Long id, @RequestBody Map<String, String> request) {
    Enrollment existingEnrollment = enrollmentRepository.findById(id).orElse(null);
    if (existingEnrollment != null) {
        String accepted = request.get("accepted");
        if (accepted != null) {
            existingEnrollment.setAccepted(accepted);
            enrollmentRepository.save(existingEnrollment);
            return ResponseEntity.ok("Enrollment updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Accepted field is missing");
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/enrollments/delete/{id}")
public ResponseEntity<String> deleteEnrollment(@PathVariable Long id) {
    System.out.println(id);
    if (enrollmentRepository.existsById(id)) {
        enrollmentRepository.deleteById(id);
        return ResponseEntity.ok("Enrollment deleted successfully");
    } else {
        return ResponseEntity.notFound().build();
    }
}

@PostMapping("/reviews/add")
    public ResponseEntity<String> addReview(@RequestParam Long courseID, @RequestParam Long studentID, @RequestBody Map <String, String> requestBody) {
        String reviewText = requestBody.get("review");
        if (reviewText != null) {
            Review review = new Review(courseID, studentID, reviewText);
            reviewRepository.save(review);
            return ResponseEntity.ok("Review added successfully");
        } else {
            return ResponseEntity.badRequest().body("Review text is missing");
        }
    }

    @GetMapping("/reviews/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviewsByCourseID(@RequestParam Long courseID) {
        List<Review> reviews = reviewRepository.findByCourseID(courseID);
        return ResponseEntity.ok(reviews);
    }


}