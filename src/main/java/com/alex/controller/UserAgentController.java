package com.alex.controller;

import com.alex.model.UserAgent;
import com.alex.service.UserAgentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/useragents")
public class UserAgentController {
    @Inject
    UserAgentService userAgentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserAgent(@HeaderParam("User-Agent") String userAgent){
       try{
           userAgentService.createUserAgent(userAgent);
           return Response.status(Response.Status.CREATED).build();
       }catch (Exception e){
           throw new RuntimeException("Failed to create user agent");
       }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastTenUserAgents(){
        List<UserAgent> lastTenUserAgents = userAgentService.getLastTenUserAgents();
        return Response.ok(lastTenUserAgents).build();
    }


}
