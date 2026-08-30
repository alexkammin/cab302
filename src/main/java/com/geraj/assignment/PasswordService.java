package com.geraj.assignment;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordService {
    private static final PasswordService INSTANCE = new PasswordService(2, 65536, 1);

    private final int iterations;
    private final int memory;
    private final int parallelism;
    private final Argon2 argon2;

    private PasswordService(int iterations, int memory, int parallelism) {
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = parallelism;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public static PasswordService getInstance() {
        return INSTANCE;
    }

    public String hashPassword(char[] password) {
        try {
            return argon2.hash(iterations, memory, parallelism, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    public boolean verifyPassword(String hash, char[] password) {
        if (hash == null || password == null) {
            return false;
        }
        try {
            return argon2.verify(hash, password);
        } finally {
            argon2.wipeArray(password);
        }
    }
}