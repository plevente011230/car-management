package com.levi.carmanagement.config;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {

    @Override
    public Response toResponse(NoResultException exception) {
        String message = "No search result!";
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
