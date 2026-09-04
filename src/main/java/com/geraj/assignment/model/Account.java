package com.geraj.assignment.model;

import java.util.Objects;

public class Account {
    private String name;
    private String email;
    private String hash;

    public Account(String name, String email, String hash) {
        this.name = Objects.requireNonNull(name, "Account name cannot be null");
        this.email = Objects.requireNonNull(email, "Account email cannot be null");
        this.hash = Objects.requireNonNull(hash, "Account password hash cannot be null");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Account name cannot be null");
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "Account email cannot be null");
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = Objects.requireNonNull(hash, "Account password hash cannot be null");
    }
}
