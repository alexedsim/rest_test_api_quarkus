package com.alex.controller;



import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/headers/mirror")
public class HeadersController {

    @Inject
    HttpHeaders headers;

    @GET
    public Response getHeaders(@HeaderParam("headers") HttpHeaders headers) {
        return Response.ok(headers.getRequestHeaders()).build();
    }
}
