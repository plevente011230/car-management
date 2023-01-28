package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NamedQuery(name = Car.GET_ALL_CARS, query = "select c from Car c")
@NamedQuery(name = Car.FIND_CAR_BY_ID, query = "select c from Car c where c.id = :filter and c.owner.username = :loggedInUser")
@NamedQuery(name = Car.FIND_CARS_BY_PLATE_NUMBER,
        query = "select c from Car c where c.plateNumber like :filter and c.owner.username = :loggedInUser")
@NamedQuery(name = Car.FIND_CARS_FOR_USER, query = "select c from Car c where c.owner.username = :filter")
public class Car extends AbstractEntity {

    public static final String GET_ALL_CARS = "Cars.findAll";
    public static final String FIND_CAR_BY_ID = "Cars.findById";
    public static final String FIND_CARS_BY_PLATE_NUMBER = "Cars.findByPlateNumber";
    public static final String FIND_CARS_FOR_USER = "Cars.findAllForUser";

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
    private Collection<ApplicationUser> drivers = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Expense> expenses = new ArrayList<>();

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

    public Car() {
    }

    public Car(Long id, LocalDateTime created, LocalDateTime lastUpdated, String brand, String model, String plateNumber, ApplicationUser owner, Collection<Expense> expenses) {
        super(id, created, lastUpdated);
        this.brand = brand;
        this.model = model;
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.expenses = expenses;
    }
}
