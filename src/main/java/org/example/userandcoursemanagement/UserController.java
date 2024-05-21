package org.example.userandcoursemanagement;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/users") // Define the base path for user-related endpoints
@Stateful
public class UserController {
    @EJB
    private courseService courseService;

    @PersistenceContext
    private EntityManager entityManager;

    @Context
    private HttpServletRequest request;

    @POST
    @Path("/signUp")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpUser(Users users) {
        try {
            entityManager.persist(users);
            entityManager.flush();
            return Response.status(Response.Status.CREATED).entity("User added successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET // Define the HTTP method for this endpoint
    @Path("/getAllUsers")
    @Produces(MediaType.APPLICATION_JSON) // Specify the response media type
    public Response getAllUsers() {
        try {
            List<Users> userList = entityManager.createQuery("SELECT u FROM Users u", Users.class).getResultList();
            return Response.status(Response.Status.OK).entity(userList).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(Users us) {
        try {
            Users user = entityManager.createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class)
                    .setParameter("email", us.getEmail())
                    .getSingleResult();

            if (user != null && user.getPassword().equals(us.getPassword())) {
                // Store user ID and role in session upon successful login
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("role", user.getRole());

                return Response.status(Response.Status.OK).entity("Login successful").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/deleteUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long id) {
        try {
            // Check if the user has the role of "admin"
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
                // Retrieve the user
                Users user = entityManager.find(Users.class, id);
                if (user != null) {
                    // Check if the user is not an admin
                    if (!user.getRole().equals("admin")) {
                        // Remove the user
                        entityManager.remove(user);
                        return Response.status(Response.Status.OK).entity("User deleted successfully").build();
                    } else {
                        // Return a forbidden response if trying to delete another admin
                        return Response.status(Response.Status.FORBIDDEN).entity("Cannot delete another admin user").build();
                    }
                } else {
                    // Return a not found response if the user doesn't exist
                    return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
                }
            } else {
                // Return an unauthorized response if the user is not an admin
                return Response.status(Response.Status.UNAUTHORIZED).entity("Only admins can delete users").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/addCourse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(Course course) {
        try {
            // Check if the user has the role of "instructor"
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
                // User is an instructor, proceed with adding the course
                String result = courseService.addCourse(course);
                return Response.status(Response.Status.OK).entity(result).build();
            } else {
                // User does not have the required permission
                return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have permission to add a course").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getAllCourses")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        try {
            List<Course> result = courseService.getAllCourses();
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/updateCourse/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(@PathParam("courseId") Long courseId, Course updatedCourse) {
        try {
            // Check if the user has the role of "admin"
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
                // User is an admin, proceed with updating the course
                String result = courseService.updateCourse(courseId, updatedCourse);
                return Response.status(Response.Status.OK).entity(result).build();
            } else {
                // User does not have the required permission
                return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have permission to update a course").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/deleteCourse/{courseId}")
    public Response deleteCourse(@PathParam("courseId") Long courseId) {
        try {
            // Check if the user has the role of "admin"
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
                // User is an admin, proceed with deleting the course
                String result = courseService.deleteCourse(courseId);
                if (result.equals("Course deleted successfully")) {
                    return Response.status(Response.Status.OK).entity(result).build();
                } else if (result.equals("Course not found")) {
                    return Response.status(Response.Status.NOT_FOUND).entity(result).build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
                }
            } else {
                // User does not have the required permission
                return Response.status(Response.Status.UNAUTHORIZED).entity("Only admin can delete courses").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/searchByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCoursesByName(@QueryParam("name") String name) {
        try {
            List<Course> courses = courseService.searchCoursesByName(name);
            return Response.status(Response.Status.OK).entity(courses).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/searchByCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCoursesByCategory(@QueryParam("category") String category) {
        try {
            List<Course> courses = courseService.searchCoursesByCategory(category);
            return Response.status(Response.Status.OK).entity(courses).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/sortByRating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortCoursesByRating() {
        try {
            List<Course> sortedCourses = courseService.sortCoursesByRating();
            return Response.status(Response.Status.OK).entity(sortedCourses).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to sort courses by rating").build();
        }
    }

    @POST
    @Path("/enrollments/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEnrollment(@QueryParam("courseID") long courseID) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                long studentID = (long) session.getAttribute("userId");

                // Check if the user has the role of "student"
                if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
                    // User is a student, proceed with adding enrollment
                    String result = courseService.addEnrollment(courseID, studentID);
                    return Response.status(Response.Status.OK).entity(result).build();
                } else {
                    // User does not have the required permission
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Only students can add enrollments").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not logged in").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/enrollments/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnrollment(@PathParam("id") Long id, Map<String, String> request, @Context HttpServletRequest httpRequest) {
        try {
            HttpSession session = httpRequest.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
                String accepted = request.get("accepted");
                if (accepted != null) {
                    String result = courseService.updateEnrollment(id, accepted);
                    return Response.status(Response.Status.OK).entity(result).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Accepted field is missing").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Only instructors can update enrollments").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update enrollment").build();
        }
    }

    @DELETE
    @Path("/enrollments/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEnrollment(@PathParam("id") Long id, @Context HttpServletRequest httpRequest) {
        try {
            HttpSession session = httpRequest.getSession(false);
            if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
                String result = courseService.deleteEnrollment(id);
                return Response.status(Response.Status.OK).entity(result).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Only students can delete enrollments").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @POST
    @Path("/reviews/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@QueryParam("courseID") Long courseID, @QueryParam("studentID") Long studentID, Map<String, String> requestBody) {
        String reviewText = requestBody.get("review");
        if (reviewText != null) {
            String result = courseService.addReview(courseID, studentID, reviewText);
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Review text is missing").build();
        }
    }

    @GET
    @Path("/reviews/getbyid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewsByCourseID(@QueryParam("courseID") Long courseID) {
        try {
            List<Review> reviews = courseService.getReviewsByCourseID(courseID);
            return Response.ok(reviews).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch reviews").build();
        }
    }
}
