package com.geraj.assignment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SqliteAccountDAO implements IAccountDAO {
    private Connection connection;

    public SqliteAccountDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS accounts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                hash TEXT NOT NULL
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (username, hash) VALUES (?, ?)");
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getHashedPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
