package com.levi.carmanagement.resource;

import com.levi.carmanagement.config.Secure;
import com.levi.carmanagement.entity.Expense;
import com.levi.carmanagement.service.ApplicationState;
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

@Path("user/cars/expenses")
public class ExpenseResource {

    @Inject
    ApplicationState applicationState;

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

    @Context
    UriInfo uriInfo;

    @GET
    @Secure
    @Path("{id}")
    public Response getExpenseById(@PathParam("id") Long expenseId) {
        Expense expense = queryService.getExpenseById(expenseId);
        if(expense.getCar().getOwner().getUsername().equals(applicationState.getUsername())
                || expense.getCar().getDrivers().contains(queryService.getUserByUsername(applicationState.getUsername()))) {
            return Response.ok(queryService.getExpenseById(expenseId)).status(Response.Status.FOUND).build();
        }
        return Response.ok().status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @GET
    @Secure
    @Path("all")
    public Response getAllExpense(@QueryParam("carId") Long carId) {
        return Response.ok(queryService.getAllExpense(carId)).status(Response.Status.FOUND).build();
    }

    @POST
    @Secure
    @Path("add")
    public Response addExpense(@QueryParam("carId") Long carId, @RequestBody Expense expense) {
        persistenceService.saveExpense(carId, expense);
        URI uri = uriInfo.getBaseUriBuilder().path("user/cars/expenses").path(expense.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("sum")
    @Produces({MediaType.APPLICATION_JSON})
    public Response sumExpenses(@QueryParam("carId") Long carId) {
        return Response.ok(queryService.sumExpensesForCar(carId)).status(Response.Status.OK).build();
    }
}
