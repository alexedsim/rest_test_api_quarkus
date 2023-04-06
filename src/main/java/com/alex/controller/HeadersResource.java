package com.alex.controller;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mutiny/headers/mirror")
public class HeadersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getHeaders(@Context HttpHeaders headers){
        return Uni.createFrom().item(headers.getRequestHeaders())
                .onItem().transform(headerRequest ->Response.ok(headerRequest).build());
    }
}
