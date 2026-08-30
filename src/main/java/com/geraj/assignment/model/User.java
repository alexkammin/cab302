package com.geraj.assignment.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Objects;

public class User {
    private String name;
    private String email;
    private String hash;

    public User(String name, String email, String password) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");

        this.hash = hashPassword(password);
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

    public boolean verifyPassword(String password) {
        if (password == null || this.hash == null) {
            return false;
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        char[] passwordArray = password.toCharArray();

        try {
            return argon2.verify(this.hash, passwordArray);
        }
        finally {
            argon2.wipeArray(passwordArray);
        }
    }

    public void updatePassword(String password) {
        Objects.requireNonNull(password, "Password cannot be null");

        this.hash = hashPassword(password);
    }

    private String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        char[] passwordArray = password.toCharArray();

        try {
            return argon2.hash(2, 65536, 1, passwordArray);
        }
        finally {
            argon2.wipeArray(passwordArray);
        }
    }
}
