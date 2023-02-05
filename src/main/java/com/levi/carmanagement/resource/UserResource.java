package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.ApplicationUser;
import com.levi.carmanagement.entity.DrivingLicence;
import com.levi.carmanagement.service.ApplicationState;
import com.levi.carmanagement.service.PersistenceService;
import com.levi.carmanagement.service.QueryService;
import com.levi.carmanagement.service.SecurityService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Path("user")
public class UserResource {

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

    @Inject
    SecurityService securityService;

    @Inject
    ApplicationState applicationState;

    @Context
    UriInfo uriInfo;

    @POST
    @Path("register")
    public Response saveUser(@NotNull @RequestBody ApplicationUser user) {
        persistenceService.saveUser(user);
        URI uri = uriInfo.getAbsolutePathBuilder().path(user.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Secure
    @Path("edit")
    public Response editUser(@RequestBody ApplicationUser user) {

        //TODO
        return Response.ok().build();
    }

    @GET
    @Secure
    public Response getLoggedInUserDetails() {
        ApplicationUser user = queryService.getUserDetails();
        return Response.ok(user).status(Response.Status.FOUND).build();
    }

    @GET
    public Response filterUsersByUsername(
            @QueryParam("filter") @Size(min = 3, message = "Filter parameter must be at least 3 characters long.") String filter) {
        Collection<String> usernames = queryService.filterUserByUsername(filter);
        return Response.ok(usernames).status(Response.Status.OK).build();
    }

    @GET
    @Path("logout")
    public Response logout() {
        applicationState.setUserId(null);
        applicationState.setUsername(null);
        return Response.ok("Logged out successfully").build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        if(!securityService.authenticateUser(username, password)) {
            throw new SecurityException("Email or password incorrect");
        }
        ApplicationUser user = queryService.getUserByUsername(username);
        applicationState.setUserId(user.getId());
        applicationState.setUsername(username);
//        logger.log(Level.INFO, "User logged in: {}", username);
        String token = getToken(username);
        return Response.ok("Logged in successfully").header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    }

    private String getToken(String username) {
        Key key = securityService.generateKey(username);
        return Jwts.builder().setSubject(username).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).setExpiration(securityService.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString())
                .compact();
    }
}
