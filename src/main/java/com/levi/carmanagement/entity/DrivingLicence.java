package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = DrivingLicence.GET_DRIVING_LICENCE,
        query = "select l from DrivingLicence l where l.applicationUser.username = :loggedInUser")
public class DrivingLicence extends AbstractEntity {

    public static final String GET_DRIVING_LICENCE = "DrivingLicence.get";

    @Size(min = 6, message = "License number must contain at lest 6 characters")
    private String licenceNumber;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate validFrom;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate validTo;

    @OneToOne(mappedBy = "drivingLicence")
    @JsonbTransient
    private ApplicationUser applicationUser;

    public DrivingLicence() {
    }

    public DrivingLicence(Long id, LocalDateTime created, LocalDateTime lastUpdated, String licenceNumber, LocalDate validFrom, LocalDate validTo, byte[] picture, ApplicationUser applicationUser) {
        super(id, created, lastUpdated);
        this.licenceNumber = licenceNumber;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.applicationUser = applicationUser;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate valaidFrom) {
        this.validFrom = valaidFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

}
