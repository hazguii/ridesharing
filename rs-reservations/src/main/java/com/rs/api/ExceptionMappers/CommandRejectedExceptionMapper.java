package com.rs.api.ExceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.rs.exceptions.CommandRejectedException;
@Provider
public class CommandRejectedExceptionMapper implements ExceptionMapper<CommandRejectedException> {

    @Override
    public Response toResponse(CommandRejectedException exception) {
        return Response.status(Response.Status.CONFLICT).entity(exception.getMessage()).build();
    }
}
