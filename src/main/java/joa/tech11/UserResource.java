package joa.tech11;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import joa.tech11.event.AddUserEvent;
import joa.tech11.event.DeleteUserEvent;
import joa.tech11.event.UserEvent;
import joa.tech11.qualifier.AddEvent;
import joa.tech11.qualifier.DeleteEvent;

@Path("/v1/user")
@Transactional
public class UserResource {


    @Inject
    private @AddEvent Event<UserEvent> addUserEvent;

    @Inject
    private @DeleteEvent Event<UserEvent> deleteUserEvent;

    @Inject
    private UserRepository userRepository;

    @GET
    public Response getUsers() {
        try {
            return Response.ok(userRepository.findUsers()).build();
        } catch (Exception ex) {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Long id) {
        try {
            return Response.ok(userRepository.findById(id)).build();
        } catch (Exception ex) {
            return Response.
                    status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    public Response addUSer(UserEntity user) {
        try {
            addUserEvent.fire(new AddUserEvent(user));
            // userRepository.create(user);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        } catch (Exception ex) {
            return Response
                    .status(Response.Status.NOT_MODIFIED)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response editUser(@PathParam("id") Long id, UserEntity user) {
        try {
            return Response.ok(userRepository.update(id, user)).build();
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            UserEntity user = userRepository.findById(id);
            deleteUserEvent.fire(new DeleteUserEvent(user));
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified("false").build();
        }
    }
}
