package com.expedia.fault.injector.dropwizard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
public class RootResource {

    @GET
    @Produces({APPLICATION_JSON})
    public Response root() {
        return Response.ok().build();
    }
}
