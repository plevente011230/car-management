package com.levi.carmanagement.entity;

import com.levi.carmanagement.config.AbstractEntityListener;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AbstractEntityListener.class})
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime created;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime lastUpdated;

    public AbstractEntity() {
    }

    public AbstractEntity(Long id, LocalDateTime created, LocalDateTime lastUpdated) {
        this.id = id;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
