package com.alex.controller;

import com.alex.exception.UserAgentCreationException;
import com.alex.model.UserAgentMutiny;
import com.alex.service.UserAgentServiceMutiny;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/mutiny/useragents")
public class UserAgentResource {

    @Inject
    UserAgentServiceMutiny userAgentServiceMutiny;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllUserAgents() {
        return userAgentServiceMutiny.getAllUserAgents()
                .map(userAgents -> Response.ok(userAgents).build());
    }

    @GET
    @Path("/findwithhash")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getUserWithhash(@HeaderParam("User-Agent-Hash") String userAgentHash) {
        return userAgentServiceMutiny.findWithHash(userAgentHash)
                .map(userAgent -> Response.ok(userAgent).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getLastTenUserAgents() {
        return userAgentServiceMutiny.getLastTenUserAgents()
                .map(lastTenUserAgents -> Response.ok(lastTenUserAgents).build());
    }

    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createOrUpdateUserAgent(@HeaderParam("User-Agent") String userAgent) {
        return userAgentServiceMutiny.createOrUpdateUserAgent(userAgent).
                map(userAgentItem -> Response.ok(userAgentItem).build())
                .onFailure()
                .recoverWithItem(throwable -> {
                    throw new UserAgentCreationException(""+throwable);
                });
        }
    */

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createOrUpdateUserAgent(@HeaderParam("User-Agent") String userAgent) {
        return userAgentServiceMutiny.createOrUpdateUserAgent(userAgent)
                .map( result ->{
                    boolean isCreated = result.getItem1();
                    UserAgentMutiny userAgentItem = result.getItem2();
                    if(isCreated){
                        return Response.status(Response.Status.CREATED).entity(userAgentItem).build();
                    }else{
                        return Response.status(Response.Status.OK).entity(userAgentItem).build();
                    }
                }).onFailure()
                .recoverWithItem(throwable -> {
                    throw new UserAgentCreationException(""+throwable);
                });

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteAllUserAgents(){
        return userAgentServiceMutiny.removeAllUsers()
                .map(deleteOperation -> Response.status(Response.Status.OK).build())
                .onFailure()
                .recoverWithItem(throwable ->{
                    throw new RuntimeException("Could not delete users!");
                }
                );
    }

}
