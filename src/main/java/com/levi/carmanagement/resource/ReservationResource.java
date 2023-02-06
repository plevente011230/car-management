package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.Reservation;
import com.levi.carmanagement.exception.NoAccessException;
import com.levi.carmanagement.service.PersistenceService;
import com.levi.carmanagement.service.QueryService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

@Path("user/reservations")
public class ReservationResource {

    @Context
    UriInfo uriInfo;

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

    @POST
    @Secure
    public Response addReservation(@QueryParam("carId") Long carId, @RequestBody Reservation reservation) throws NoAccessException {
        persistenceService.saveReservation(carId, reservation);
        URI uri = uriInfo.getBaseUriBuilder().path("user/reservations").path(reservation.getId().toString()).build();
        return Response.ok().header("Location", uri).status(Response.Status.CREATED).build();
    }

    @GET
    @Secure
    @Path("{reservationId}")
    public Response getReservationById(@PathParam("reservationId") Long reservationId) throws NoAccessException {
        Reservation reservation = queryService.getReservationById(reservationId);
        return Response.ok(reservation).status(Response.Status.OK).build();
    }

    @GET
    @Secure
    public Response getReservationsForCar(@QueryParam("carId") Long carId) {
        Collection<Reservation> reservations = queryService.getReservationForCar(carId);
        return Response.ok(reservations).status(Response.Status.OK).build();
    }

    @GET
    @Secure
    public Response getReservationsForUser() {
        Collection<Reservation> reservations = queryService.getReservationForUser();
        return Response.ok(reservations).status(Response.Status.OK).build();
    }

    @DELETE
    @Secure
    public Response deleteReservation(@QueryParam("reservationId") Long reservationId) throws NoAccessException {
        persistenceService.deleteReservation(reservationId);
        return Response.ok().status(Response.Status.OK).build();
    }
}
