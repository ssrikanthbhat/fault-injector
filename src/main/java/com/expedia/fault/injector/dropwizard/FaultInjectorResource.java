package com.expedia.fault.injector.dropwizard;


import com.expedia.fault.injector.FaultInjector;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/__fault")
public class FaultInjectorResource {
    private final FaultInjector faultInjector;

    public FaultInjectorResource(FaultInjector faultInjector) {
        Preconditions.checkNotNull(faultInjector, "Injector cannot be null");
        this.faultInjector = faultInjector;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response root() {
        return Response.ok().entity(ImmutableMap.of("ok", true,
                "message", "It's not your fault")).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/cpu")
    public Response cpu() {
        faultInjector.cpu();
        return Response.ok().entity(ImmutableMap.of("ok", true)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/cpu/bounded")
    public Response cpuBounded(@QueryParam("target") double target, @QueryParam("duration") int duration) {
        faultInjector.cpu(target, duration, TimeUnit.SECONDS);
        return Response.ok().entity(ImmutableMap.of("ok", true)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/memory")
    public Response memory() {
        faultInjector.memory();
        return Response.ok().entity(ImmutableMap.of("ok", true)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/memory/reset")
    public Response resetMemoryFault() {
        faultInjector.resetMemoryFault();
        return Response.ok().entity(ImmutableMap.of("ok", true)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/disk")
    public Response disk() {
        faultInjector.disk();
        return Response.ok().entity(ImmutableMap.of("ok", true)).build();
    }


}
