package com.levi.carmanagement.config;

import com.levi.carmanagement.entity.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AbstractEntityListener {

    @PrePersist
    public void setCreated(AbstractEntity abstractEntity) {
        abstractEntity.setCreated(LocalDateTime.now());
        abstractEntity.setLastUpdated(LocalDateTime.now());
    }

    @PreUpdate
    public void setLastUpdated(AbstractEntity abstractEntity) {
        abstractEntity.setLastUpdated(LocalDateTime.now());
    }
}
