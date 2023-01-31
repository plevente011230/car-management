package com.levi.carmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NamedQuery(name = ApplicationUser.FIND_USER_BY_USERNAME,
        query = "select u from ApplicationUser u where u.username = :filter")
@NamedQuery(name = ApplicationUser.FILTER_USER_BY_USERNAME,
        query = "select u.username from ApplicationUser u where u.username like :username")
public class ApplicationUser extends AbstractEntity {

    public static final String FIND_USER_BY_USERNAME = "User.findUserByUsername";
    public static final String FILTER_USER_BY_USERNAME = "User.filterByUsername";

    @Column(unique = true)
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters long!")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String salt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVING_LICENSE_ID")
    private DrivingLicence drivingLicence;

    @OneToMany(mappedBy = "owner")
    private Collection<Car> ownedCars = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "DRIVER_CAR",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CAR_ID")
    )
    private Collection<Car> drivenCars = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Collection<Car> getOwnedCars() {
        return ownedCars;
    }

    public void addOwnedCar(Car car) {
        car.setOwner(this);
        this.ownedCars.add(car);
    }

    public DrivingLicence getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(DrivingLicence drivingLicence) {
        drivingLicence.setApplicationUser(this);
        this.drivingLicence = drivingLicence;
    }

    public void setOwnedCars(Collection<Car> ownedCars) {
        this.ownedCars = ownedCars;
    }

    public Collection<Car> getDrivenCars() {
        return drivenCars;
    }

    public void addDrivenCar(Car car) {
        this.drivenCars.add(car);
    }

    public ApplicationUser() {
    }

    public ApplicationUser(Long id, LocalDateTime created, LocalDateTime lastUpdated, String username, String password, String salt, DrivingLicence drivingLicence, Collection<Car> ownedCars) {
        super(id, created, lastUpdated);
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.drivingLicence = drivingLicence;
        this.ownedCars = ownedCars;
    }
}
