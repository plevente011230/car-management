package com.levi.carmanagement.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final Map<String, String> constraintViolations = new HashMap<>();
        for(ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            constraintViolations.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return Response.status(Response.Status.PRECONDITION_FAILED).entity(constraintViolations).build();
    }
}
