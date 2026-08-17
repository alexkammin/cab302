package com.geraj.assignment.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SqliteAccountDAO implements IAccountDAO {
    private final Connection connection;

    public SqliteAccountDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS accounts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                hash TEXT NOT NULL
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAccount(Account account) {
        String query = "INSERT INTO accounts (username, hash) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account.getUsername());
            statement.setString(2, hashPassword(account.getPassword()));
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
