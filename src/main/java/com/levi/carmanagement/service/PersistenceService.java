package com.levi.carmanagement.service;

import com.levi.carmanagement.entity.ApplicationUser;
import com.levi.carmanagement.entity.Car;
import com.levi.carmanagement.entity.DrivingLicence;
import com.levi.carmanagement.entity.Expense;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

@DataSourceDefinition(
        name = "java:app/CarManagement/MyDS",
        className = "org.postgresql.ds.PGConnectionPoolDataSource",
        url = "jdbc:postgresql://localhost:5432/CarsApp",
        user = "postgres",
        password = "postgres"
)
@Stateless
public class PersistenceService {

    @Inject
    EntityManager entityManager;

    @Inject
    QueryService queryService;

    @Inject
    SecurityService securityService;

    @Inject
    ApplicationState applicationState;

    public void saveCar(Car car) {
        ApplicationUser user = queryService.getUserByUsername(applicationState.getUsername());
        user.addOwnedCar(car);
        entityManager.persist(car);
        entityManager.merge(user);
    }

    public void addDriverToCar(String driverUsername, Long carId) {
        ApplicationUser driver = queryService.getUserByUsername(driverUsername);
        Car car = queryService.findCarById(carId);
        car.addDriver(driver);
        entityManager.merge(car);
    }

    public void saveUser(ApplicationUser user) {
        Map<String, String> credMap =  securityService.hashPassword(user.getPassword());
        user.setPassword(credMap.get("hashedPassword"));
        user.setSalt(credMap.get("salt"));
        if(user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        credMap = null;
    }

    public void saveDrivingLicence(DrivingLicence drivingLicence) {
        ApplicationUser user = queryService.getUserByUsername(applicationState.getUsername());
        if(drivingLicence.getId() == null) {
            user.setDrivingLicence(drivingLicence);
            entityManager.merge(user);
        } else {
            entityManager.merge(drivingLicence);
        }
    }

    public void deleteDrivingLicence() {
        ApplicationUser user = queryService.getUserByUsername(applicationState.getUsername());
        DrivingLicence drivingLicence = user.getDrivingLicence();
        user.removeDrivingLicence();
        entityManager.remove(drivingLicence);
    }

    public void saveExpense(Long carId, Expense expense) {
        Car car = queryService.findCarById(carId);
        car.addExpense(expense);
        entityManager.persist(expense);
        entityManager.merge(car);
    }

}
