package com.geraj.assignment.model;

import java.util.Objects;

public class User {
    private String name;
    private String email;
    private String hash;

    public User(String name, String email, String hash) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.hash = Objects.requireNonNull(hash, "Hash cannot be null");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = Objects.requireNonNull(hash, "Hash cannot be null");
    }
}
