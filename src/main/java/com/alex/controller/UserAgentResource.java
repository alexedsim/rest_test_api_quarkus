package com.alex.controller;

import com.alex.exception.UserAgentCreationException;
import com.alex.service.UserAgentServiceMutiny;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/mutiny/useragents")
public class UserAgentResource {
    /*
    @Inject
    UserAgentService userAgentService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllUserAgents() {
        return Uni.createFrom().item(userAgentService.getAllUserAgents())
                .onItem().transform(userAgents -> Response.ok(userAgents).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getLastTenUserAgents() {
        return Uni.createFrom().item(userAgentService.getLastTenUserAgents())
                .onItem().transform(lastTenUserAgents -> Response.ok(lastTenUserAgents).build());
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createUserAgent(@HeaderParam("User-Agent") String userAgent) {
        return Uni.createFrom().deferred(() -> {
            try {
                userAgentService.createUserAgent(userAgent);
                return Uni.createFrom().item(Response.status(Response.Status.CREATED).build());
            } catch (Exception e) {
                return Uni.createFrom().failure(new UserAgentCreationException(""));
            }
        }).onFailure().recoverWithItem(throwable -> {
            throw new UserAgentCreationException("");
        });
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteAllUserAgents() {
        return Uni.createFrom().deferred(() -> {
            try {
                userAgentService.removeAllUsers();
                return Uni.createFrom().item(Response.status(Response.Status.OK).build());
            } catch (Exception e) {
                return Uni.createFrom().failure(new Exception("Could not delete users"));
            }
        }).onFailure().recoverWithItem(throwable -> {
            throw (RuntimeException) throwable;
        });

    }

     */
    @Inject
    UserAgentServiceMutiny userAgentServiceMutiny;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllUserAgents() {
        return userAgentServiceMutiny.getAllUserAgents()
                .onItem().transform(userAgents -> Response.ok(userAgents).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getLastTenUserAgents() {
        return userAgentServiceMutiny.getLastTenUserAgents()
                .onItem().transform(lastTenUserAgents -> Response.ok(lastTenUserAgents).build());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createUserAgent(@HeaderParam("User-Agent") String userAgent) {
        return userAgentServiceMutiny.createUserAgent(userAgent)
                .onItem()
                .transform(ignore -> Response.status(Response.Status.CREATED).build())
                .onFailure()
                .recoverWithItem(throwable -> {
                    throw new UserAgentCreationException(""+throwable);
                });

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteAllUserAgents(){
        return userAgentServiceMutiny.removeAllUsers()
                .onItem()
                .transform(ignore -> Response.status(Response.Status.OK).build())
                .onFailure()
                .recoverWithItem(throwable ->{
                    throw new RuntimeException("Could not delte users!");
                }
                );
    }

}
