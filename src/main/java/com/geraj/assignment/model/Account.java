package com.geraj.assignment.model;

import java.util.Objects;

public class Account {

    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String hash;

    /*
     * Constructor used by the Create Account form.
     */
    public Account(
            String name,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            String hash
    ) {
        this.name = Objects.requireNonNull(
                name,
                "Account name cannot be null"
        );

        this.email = Objects.requireNonNull(
                email,
                "Account email cannot be null"
        );

        this.firstName = Objects.requireNonNull(
                firstName,
                "Account first name cannot be null"
        );

        this.lastName = Objects.requireNonNull(
                lastName,
                "Account last name cannot be null"
        );

        this.phoneNumber = Objects.requireNonNull(
                phoneNumber,
                "Account phone number cannot be null"
        );

        this.hash = Objects.requireNonNull(
                hash,
                "Account password hash cannot be null"
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(
                name,
                "Account name cannot be null"
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(
                email,
                "Account email cannot be null"
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(
                firstName,
                "Account first name cannot be null"
        );
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(
                lastName,
                "Account last name cannot be null"
        );
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(
                phoneNumber,
                "Account phone number cannot be null"
        );
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = Objects.requireNonNull(
                hash,
                "Account password hash cannot be null"
        );
    }
}
