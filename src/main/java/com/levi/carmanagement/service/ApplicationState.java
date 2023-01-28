package com.levi.carmanagement.service;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class ApplicationState implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
