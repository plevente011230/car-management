package com.levi.carmanagement.config;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class Producer {

    @Produces
    @PersistenceContext(unitName = "pu")
    EntityManager entityManager;
}
