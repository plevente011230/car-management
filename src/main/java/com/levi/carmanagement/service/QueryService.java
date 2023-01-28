package com.levi.carmanagement.service;

import com.levi.carmanagement.entity.ApplicationUser;
import com.levi.carmanagement.entity.Car;
import com.levi.carmanagement.entity.DrivingLicence;
import com.levi.carmanagement.entity.Expense;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Stateless
public class QueryService {

    @Inject
    EntityManager entityManager;

    @Inject
    SecurityService securityService;

    @Inject
    ApplicationState applicationState;

    // Car related queries

    public Collection<Car> findCarsByPlateNumber(String plateNumber) {
        return entityManager.createNamedQuery(Car.FIND_CARS_BY_PLATE_NUMBER, Car.class)
                .setParameter("filter", '%' + plateNumber + '%')
                .setParameter("loggedInUser", applicationState.getUsername())
                .getResultList();
    }

    public Car findCarById(Long id) {
        return entityManager.createNamedQuery(Car.FIND_CAR_BY_ID, Car.class)
                .setParameter("filter", id)
                .setParameter("loggedInUser", applicationState.getUsername())
                .getSingleResult();
    }

    public Collection<Car> getAllCarsForUser() {
        return entityManager.createNamedQuery(Car.FIND_CARS_FOR_USER, Car.class)
                .setParameter("filter", applicationState.getUsername())
                .getResultList();
    }

    //User related queries

    public ApplicationUser getUserDetails() {
        return entityManager.createNamedQuery(ApplicationUser.FIND_LOGGED_IN_USER, ApplicationUser.class)
                .setParameter("filter", applicationState.getUsername())
                .getSingleResult();
    }

    public DrivingLicence getDrivingLicence() {
        return entityManager.createNamedQuery(DrivingLicence.GET_DRIVING_LICENCE, DrivingLicence.class)
                .setParameter("loggedInUser", applicationState.getUsername())
                .getSingleResult();
    }

    public ApplicationUser getUserByUsername(String username) {
        return entityManager.createNamedQuery(ApplicationUser.FIND_USER_BY_CREDENTIALS, ApplicationUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public boolean authenticateUser(String username, String plainTextPassword) {
        ApplicationUser user = entityManager.createNamedQuery(ApplicationUser.FIND_USER_BY_CREDENTIALS, ApplicationUser.class)
                .setParameter("username", username)
                .getSingleResult();
        if(user != null) {
            return securityService.passwordsMatch(user.getPassword(), user.getSalt(), plainTextPassword);
        }
        return false;
    }

    // Expense related queries

    public Collection<Expense> getAllExpense(Long carId) {
        return entityManager.createNamedQuery(Expense.GET_ALL_EXPENSE_FOR_CAR, Expense.class)
                .setParameter("carId", carId)
                .setParameter("loggedInUser", applicationState.getUsername())
                .getResultList();
    }

    public Collection<Object[]> sumExpensesForCar(Long carId) {
        Collection<Object[]> queryResult = entityManager.createNamedQuery(Expense.SUM_EXPENSES_FOR_CAR, Object[].class)
                .setParameter("carId", carId)
                .setParameter("loggedInUser", applicationState.getUsername())
                .getResultList();
        // TODO: Convert queryResult to Map
        return queryResult;
    }
}
