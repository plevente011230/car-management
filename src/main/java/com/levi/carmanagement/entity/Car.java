package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NamedQuery(name = Car.FIND_CAR_BY_ID, query = "select c from Car c where c.id = :filter and c.owner.username = :loggedInUser")
@NamedQuery(name = Car.FILTER_CARS_BY_PLATE_NUMBER,
        query = "select c from Car c where c.plateNumber like :filter and c.owner.username = :loggedInUser")
@NamedQuery(name = Car.GET_OWNED_CARS,
        query = "select c from Car c where c.owner.username = :filter")
@NamedQuery(name = Car.GET_DRIVEN_CARS,
        query = "select c from Car c join ApplicationUser u where u.username = :username and u member of c.drivers")
@NamedQuery(name = Car.GET_CAR_DRIVERS,
        query = "select u.username from ApplicationUser u join Car c where c.id = :carId and u member of c.drivers")
public class Car extends AbstractEntity {

    public static final String FIND_CAR_BY_ID = "Cars.findById";
    public static final String FILTER_CARS_BY_PLATE_NUMBER = "Cars.filterByPlateNumber";
    public static final String GET_OWNED_CARS = "Cars.findAllForUser";
    public static final String GET_DRIVEN_CARS = "Cars.getDrivenCars";
    public static final String GET_CAR_DRIVERS = "Cars.getDrivers";

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    @Column(unique = true)
    private String plateNumber;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "OWNER_ID")
    private ApplicationUser owner;

    @ManyToMany
    @JsonbTransient
    private final Collection<ApplicationUser> drivers = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Reservation> reservations = new ArrayList<>();

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Collection<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expense.setCar(this);
        this.expenses.add(expense);
    }

    public ApplicationUser getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUser owner) {
        this.owner = owner;
    }

    public Collection<ApplicationUser> getDrivers() {
        return drivers;
    }

    public void addDriver(ApplicationUser driver) {
        driver.addDrivenCar(this);
        this.drivers.add(driver);
    }

    public void setExpenses(Collection<Expense> expenses) {
        this.expenses = expenses;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Car() {
    }

    public Car(Long id, LocalDateTime created, LocalDateTime lastUpdated, String brand, String model, String plateNumber, ApplicationUser owner, Collection<Expense> expenses, Collection<Reservation> reservations) {
        super(id, created, lastUpdated);
        this.brand = brand;
        this.model = model;
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.expenses = expenses;
        this.reservations = reservations;
    }
}
