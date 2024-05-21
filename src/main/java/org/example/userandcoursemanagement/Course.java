package org.example.userandcoursemanagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int instructorID;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int enrolledStudents;

    @Column(nullable = true)
    private String listOfReviews;
    // Constructors
    public Course(Long id, String name, int instructorID, int duration, String category, boolean status, float rating, int capacity, int enrolledStudents, String listOfReviews) {
        this.id = id;
        this.name = name;
        this.instructorID = instructorID;
        this.duration = duration;
        this.category = category;
        this.status = status;
        this.rating = rating;
        this.capacity = capacity;
        this.enrolledStudents = enrolledStudents;
        this.listOfReviews = listOfReviews;
    }

    public Course() {

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getListOfReviews() {
        return listOfReviews;
    }

    public void setListOfReviews(String listOfReviews) {
        listOfReviews = listOfReviews;
    }
}

//@POST
//@Path("/addCourse")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public Response addCourse(Course course) {
//    try {
//        // Check if the user has the role of "instructor"
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
//            // Retrieve the instructor ID from the session
//            Long instructorId = (Long) session.getAttribute("userId");
//
//            // Set the instructor ID for the course
//            course.setInstructorID(instructorId.intValue());
//
//            // Persist the course object
//            entityManager.persist(course);
//            entityManager.flush();
//            return Response.status(Response.Status.CREATED).entity("Course added successfully").build();
//        } else {
//            // Return an unauthorized response if the user is not an instructor
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Only instructors can add courses").build();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//    }
//}
//
//@GET
//@Path("/getAllCourses")
//@Produces(MediaType.APPLICATION_JSON)
//public Response getAllCourses() {
//    try {
//        List<Course> courseList = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
//        return Response.status(Response.Status.OK).entity(courseList).build();
//    } catch (Exception e) {
//        e.printStackTrace();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//    }
//}
//
//@DELETE
//@Path("/removeCourse/{courseId}")
//@Produces(MediaType.APPLICATION_JSON)
//public Response removeCourse(@PathParam("courseId") Long id) {
//    try {
//        // Check if the user has the role of "admin"
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
//            // Retrieve the course
//            Course course = entityManager.find(Course.class, id);
//            if (course != null) {
//                // Remove the course
//                entityManager.remove(course);
//                return Response.status(Response.Status.OK).entity("Course removed successfully").build();
//            } else {
//                // Return a not found response if the course doesn't exist
//                return Response.status(Response.Status.NOT_FOUND).entity("Course not found").build();
//            }
//        } else {
//            // Return an unauthorized response if the user is not an admin
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Only admins can remove courses").build();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//    }
//}
//
//@PUT
//@Path("/updateCourse/{courseId}")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public Response updateCourse(@PathParam("courseId") Long courseId, Course updatedCourse) {
//    try {
//        // Check if the user has the role of "admin"
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
//            // Retrieve the existing course
//            Course course = entityManager.find(Course.class, courseId);
//            if (course != null) {
//                // Update the course details
//                course.setName(updatedCourse.getName());
//                course.setDuration(updatedCourse.getDuration());
//                course.setCategory(updatedCourse.getCategory());
//                course.setCapacity(updatedCourse.getCapacity());
//                course.setStatus(updatedCourse.isStatus());
//
//                // Persist the updated course
//                entityManager.merge(course);
//
//                return Response.status(Response.Status.OK).entity("Course updated successfully").build();
//            } else {
//                // Return a not found response if the course doesn't exist
//                return Response.status(Response.Status.NOT_FOUND).entity("Course not found").build();
//            }
//        } else {
//            // Return an unauthorized response if the user is not an admin
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Only admins can update courses").build();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//    }
//}