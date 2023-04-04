package com.alex.controller;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/headers/mirror")
public class HeadersController {



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHeaders(@Context HttpHeaders headers) {
        return Response.ok(headers.getRequestHeaders()).build();
    }
}
