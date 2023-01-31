package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.Car;
import com.levi.carmanagement.service.PersistenceService;
import com.levi.carmanagement.service.QueryService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("user/cars")
public class CarResource {

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

    @Context
    UriInfo uriInfo;

    @GET
    @Secure
    @Path("owned")
    public Response getOwnedCars() {
        return Response.ok(queryService.getOwnedCars()).build();
    }

    @GET
    @Secure
    @Path("driven")
    public Response getDrivenCars() {
        return Response.ok(queryService.getDrivenCars()).build();
    }

    @GET
    @Secure
    @Path("{carId}/drivers")
    public Response getDrivers(@PathParam("carId") Long carId) {
        return Response.ok(queryService.getDrivers(carId)).build();
    }

    @GET
    @Secure
    public Response findCarsByPlateNumber(@QueryParam("plateNumber") String plateNumber) {
        return Response.ok(queryService.findCarsByPlateNumber(plateNumber)).status(Response.Status.FOUND).build();
    }

    @POST
    @Secure
    @Path("add")
    public Response saveCar(@RequestBody Car car) {
        persistenceService.saveCar(car);
        URI uri = uriInfo.getBaseUriBuilder().path("user/cars").path(car.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }

    @POST
    @Secure
    @Path("{carId}/add-driver")
    public Response addDriver(@PathParam("carId") Long carId, @QueryParam("username") String username) {
        persistenceService.addDriverToCar(username, carId);
        return Response.ok().status(Response.Status.ACCEPTED).build();
    }
}
