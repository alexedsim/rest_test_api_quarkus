package com.alex.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAgentCreationExceptionHandler implements ExceptionMapper<UserAgentCreationException> {

    @Override
    public Response toResponse(UserAgentCreationException ex) {
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Failed to create user agent: " + ex.getMessage())
                .build();
    }
}
