package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Reservation extends AbstractEntity {

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
