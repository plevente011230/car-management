package com.levi.carmanagement.exception;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {

    @Override
    public Response toResponse(NoResultException exception) {
        String message = "No search result!";
        return Response.ok(message).status(Response.Status.NO_CONTENT).entity(message).build();
    }
}
