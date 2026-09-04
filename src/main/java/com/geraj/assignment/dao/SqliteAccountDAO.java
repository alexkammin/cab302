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
                email TEXT NOT NULL UNIQUE,
                firstName TEXT NOT NULL,
                lastName TEXT NOT NULL,
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
        String query = """
            INSERT INTO accounts (name, email, firstName, lastName, hash)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getFirstName());
            statement.setString(4, account.getLastName());
            statement.setString(5, account.getHash());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccount(int id) {
        String query = """
            SELECT name, email, firstName, lastName, hash
            FROM accounts
            WHERE id = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String hash = resultSet.getString("hash");

                    return new Account(name, email, firstName, lastName, hash);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
