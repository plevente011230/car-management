package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.Reservation;
import com.levi.carmanagement.exception.NoAccessException;
import com.levi.carmanagement.service.PersistenceService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("user/reservations")
public class ReservationResource {

    @Context
    UriInfo uriInfo;

    @Inject
    PersistenceService persistenceService;

    @POST
    @Secure
    public Response addReservation(@QueryParam("carId") Long carId, @RequestBody Reservation reservation) throws NoAccessException {
        persistenceService.addReservation(carId, reservation);
        URI uri = uriInfo.getBaseUriBuilder().path("user/reservations").path(reservation.getId().toString()).build();
        return Response.ok().header("Location", uri).status(Response.Status.CREATED).build();
    }
}
