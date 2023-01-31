package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.DrivingLicence;
import com.levi.carmanagement.service.PersistenceService;
import com.levi.carmanagement.service.QueryService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("user/licence")
public class DrivingLicenseResource {

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

    @Context
    UriInfo uriInfo;

    @GET
    @Secure
    public Response getDrivingLicence() {
        return Response.ok(queryService.getDrivingLicence()).status(Response.Status.FOUND).build();
    }


    @POST
    @Secure
    public Response addDrivingLicence(@RequestBody DrivingLicence drivingLicence) {
        persistenceService.saveDrivingLicence(drivingLicence);
        return Response.created(uriInfo.getAbsolutePath()).status(Response.Status.CREATED).build();
    }

    @PUT
    @Secure
    public Response updateDrivingLicence(@RequestBody DrivingLicence drivingLicence) {
        persistenceService.saveDrivingLicence(drivingLicence);
        return Response.ok().header("Location", uriInfo.getAbsolutePath()).status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Secure
    public Response deleteDrivingLicence() {
        persistenceService.deleteDrivingLicence();
        return Response.ok().build();
    }
}
