package com.alex.controller;

import com.alex.exception.UserAgentCreationException;
import com.alex.model.UserAgentMutiny;
import com.alex.service.UserAgentServiceMutiny;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;


@Path("/mutiny/useragents")
public class UserAgentResource {


    @Inject
    UserAgentServiceMutiny userAgentServiceMutiny;

    private static final Logger LOG = Logger.getLogger("UserAgentResource");

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllUserAgents() {
        LOG.info("getAllUserAgents() called.");
        return userAgentServiceMutiny.getAllUserAgents()
                .map(userAgents -> Response.ok(userAgents).build());
    }

    @GET
    @Path("/findwithhash")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getUserWithHash(@HeaderParam("User-Agent-Hash") String userAgentHash) {
        LOG.info("getUserWithHash() called.");
        return userAgentServiceMutiny.findWithHash(userAgentHash)
                .map(userAgent -> Response.ok(userAgent).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getLastTenUserAgents() {
        LOG.info("getLastTenUserAgents() called.");
        return userAgentServiceMutiny.getLastTenUserAgents()
                .map(lastTenUserAgents -> Response.ok(lastTenUserAgents).build());
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createOrUpdateUserAgent(@HeaderParam("User-Agent") String userAgent) {
        LOG.info("createOrUpdateUserAgent() called with HeaderParam: "+userAgent);
        return userAgentServiceMutiny.createOrUpdateUserAgent(userAgent)
                .map( result ->{
                    boolean isCreated = result.getItem1();
                    UserAgentMutiny userAgentItem = result.getItem2();
                    if(isCreated){
                        return Response.status(Response.Status.CREATED).entity(userAgentItem).build();
                    }else{
                        return Response.status(Response.Status.OK).entity(userAgentItem).build();
                    }
                });

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteAllUserAgents(){
        LOG.info("deleteAllUserAgents() called.");
        return userAgentServiceMutiny.removeAllUsers()
                .map(deleteOperation -> Response.status(Response.Status.OK).build())
                .onFailure()
                .recoverWithItem(throwable ->{
                    throw new RuntimeException("Could not delete users!");
                }
                );
    }

}
