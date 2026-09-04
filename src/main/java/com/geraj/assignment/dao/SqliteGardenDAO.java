package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SqliteGardenDAO implements IGardenDAO {
    private final Connection connection;

    public SqliteGardenDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS gardens (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                location TEXT NOT NULL,
                temperature REAL,
                precipitation REAL,
                atmosphericHumidity REAL,
                owner_name INTEGER,
                FOREIGN KEY (owner_name) REFERENCES accounts(name)
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createGarden(Garden garden) {
        String query = """
            INSERT INTO gardens (name, location, temperature, precipitation, atmosphericHumidity, owner_name)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, garden.getName());
            statement.setString(2, garden.getLocation());

            if (garden.getTemperature() != null) {
                statement.setDouble(3, garden.getTemperature());
            } else {
                statement.setNull(3, java.sql.Types.DOUBLE);
            }

            if (garden.getPrecipitation() != null) {
                statement.setDouble(4, garden.getPrecipitation());
            } else {
                statement.setNull(4, java.sql.Types.DOUBLE);
            }

            if (garden.getAtmosphericHumidity() != null) {
                statement.setInt(5, garden.getAtmosphericHumidity());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }

            if (garden.getOwner() != null) {
                statement.setString(6, garden.getOwner().getName());
            } else {
                statement.setNull(6, java.sql.Types.VARCHAR);
            }

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
