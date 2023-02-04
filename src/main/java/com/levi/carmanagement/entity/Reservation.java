package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = Reservation.GET_RESERVATIONS_FOR_CAR,
        query = "select r from Reservation r where r.car.id = :carId")
@NamedQuery(name = Reservation.GET_RESERVATIONS_FOR_USER,
        query = "select r from Reservation r where r.user.username = :username")
@NamedQuery(name = Reservation.GET_RESERVATION_BY_ID,
        query = "select r from Reservation r where r.id = :reservationId")
public class Reservation extends AbstractEntity {

    public static final String GET_RESERVATIONS_FOR_CAR = "Reservation.getReservationsForCar";
    public static final String GET_RESERVATIONS_FOR_USER = "Reservation.getReservationsForUser";
    public static final String GET_RESERVATION_BY_ID = "Reservation.getReservationById";

    private LocalDateTime from;

    private LocalDateTime to;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "CAR_ID")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private ApplicationUser user;

    public Reservation() {
    }

    public Reservation(Long id, LocalDateTime created, LocalDateTime lastUpdated, LocalDateTime from, LocalDateTime TO, Car car, ApplicationUser user) {
        super(id, created, lastUpdated);
        this.from = from;
        this.to = TO;
        this.car = car;
        this.user = user;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
