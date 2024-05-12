package org.example.userandcoursemanagement;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/users") // Define the base path for user-related endpoints
@Stateless
public class UserController {

    @PersistenceContext
    private EntityManager entityManager;

    @POST
    @Path("/signUp")
    @Id
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
}
