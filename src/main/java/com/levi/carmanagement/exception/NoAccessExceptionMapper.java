package com.levi.carmanagement.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NoAccessExceptionMapper implements ExceptionMapper<NoAccessException> {

    @Override
    public Response toResponse(NoAccessException exception) {
        return Response.ok(exception.getMessage()).status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
