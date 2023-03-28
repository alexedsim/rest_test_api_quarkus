package com.alex.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.smallrye.mutiny.Uni;

@Path("/headers/mirror")
public class HeadersController {

    @Inject
    io.vertx.mutiny.core.eventbus.EventBus eventBus;

    @GET
    public Uni<Response> getHeaders(@Context HttpHeaders headers) {
        Map<String, String> headersMap = headers.getRequestHeaders()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        return eventBus.request("mirror-headers", headersMap)
                .onItem().transform(msg -> Response.status(Status.OK).entity(msg.body()).build());
    }
}
