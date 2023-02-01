package com.levi.carmanagement.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Embeddable
public class Insurance {

    @Enumerated(EnumType.STRING)
    private InsuranceProvider insuranceProvider;

    private BigDecimal annualFee;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
