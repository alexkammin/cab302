package com.geraj.assignment.dao;

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
    public void createAccount(String username, String hash) {
        String query = "INSERT INTO accounts (username, hash) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hash);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
