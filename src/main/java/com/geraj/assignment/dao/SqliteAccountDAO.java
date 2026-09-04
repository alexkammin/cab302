package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                name TEXT NOT NULL UNIQUE,
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
    public void createAccount(String name, String hash) {
        String query = """
            INSERT INTO accounts (name, hash)
            VALUES (?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, hash);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccount(int id) {
//        String query = """
//            SELECT id, name, hash
//            FROM accounts
//            WHERE id = ?
//            """;
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    String name = resultSet.getString("name");
//                    String hash = resultSet.getString("hash");
//
//                    // Adjust constructor parameters to match your Account class definition
//                    return new Account(name, hash);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return null; // Return null if no account exists with the provided id
    }
}
