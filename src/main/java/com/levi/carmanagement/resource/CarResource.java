package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.Car;
import com.levi.carmanagement.service.PersistenceService;
import com.levi.carmanagement.service.QueryService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

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
    @Path("all")
    public Response getAllCars() {
        Collection<Car> cars = queryService.getAllCarsForUser();
        return Response.ok(cars).build();
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
        URI uri = uriInfo.getAbsolutePathBuilder().replacePath("car-management/api/v1/user/cars/all").build();
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
