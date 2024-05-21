package org.example.userandcoursemanagement;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class courseService {
    @SpringBootClientQualifier
    @Inject
    private SpringBootAPIClient springBootAPIClient;

    public String addCourse(Course course){
        return springBootAPIClient.addCourse(course);
    }
    public List<Course> getAllCourses() {return springBootAPIClient.getAllCourses();}
    public String updateCourse(Long id, Course updatedCourse) {return springBootAPIClient.updateCourse(id,updatedCourse);}
    public String deleteCourse(Long courseId) {return springBootAPIClient.deleteCourse(courseId);}

    public List<Course> searchCoursesByName(String name) {return springBootAPIClient.searchCoursesByName(name);}

    public List<Course> searchCoursesByCategory(String category) {return springBootAPIClient.searchCoursesByCategory(category);}

    public List<Course> sortCoursesByRating() {return springBootAPIClient.sortCoursesByRating();}

    public String addEnrollment(Long courseID, Long studentID) {return springBootAPIClient.addEnrollment(courseID,studentID);}

    public String updateEnrollment(Long id, String accepted) {return springBootAPIClient.updateEnrollment(id,accepted);}

    public String deleteEnrollment(Long id) {return springBootAPIClient.deleteEnrollment(id);}

    public String addReview(Long courseID, Long studentID, String reviewText) {return springBootAPIClient.addReview(courseID,studentID,reviewText);}

    public List<Review> getReviewsByCourseID(Long courseID) {return springBootAPIClient.getReviewsByCourseID(courseID);}




    }
