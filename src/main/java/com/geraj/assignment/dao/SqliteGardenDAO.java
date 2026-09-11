package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Persists gardens and their empty US17 planter boxes using the existing SQLite database. */
public class SqliteGardenDAO implements IGardenDAO {
    private final Connection connection;
    private final IAccountDAO accountDAO;

    public SqliteGardenDAO() { this(SqliteConnection.getInstance()); }

    /** Inject a connection for isolated tests; production uses the existing shared connection. */
    public SqliteGardenDAO(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Garden database connection cannot be null");
        this.accountDAO = new SqliteAccountDAO(connection);
        createTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS gardens (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    location TEXT NOT NULL,
                    temperature REAL,
                    precipitation REAL,
                    atmosphericHumidity REAL,
                    owner_name TEXT,
                    width REAL,
                    length REAL,
                    FOREIGN KEY (owner_name) REFERENCES accounts(name)
                )
                """);
            // Add only missing columns. Existing gardens and accounts are retained.
            addColumnIfMissing("width");
            addColumnIfMissing("length");
            statement.execute("""
                CREATE TABLE IF NOT EXISTS garden_planter_boxes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    garden_id INTEGER NOT NULL,
                    box_number INTEGER NOT NULL CHECK (box_number > 0),
                    UNIQUE (garden_id, box_number),
                    FOREIGN KEY (garden_id) REFERENCES gardens(id) ON DELETE CASCADE
                )
                """);
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not prepare garden storage", exception);
        }
    }

    private void addColumnIfMissing(String column) throws SQLException {
        boolean exists = false;
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("PRAGMA table_info(gardens)")) {
            while (result.next()) if (column.equals(result.getString("name"))) exists = true;
        }
        if (!exists) {
            try (Statement statement = connection.createStatement()) {
                // column is one of the two fixed internal names above, never user input.
                statement.execute("ALTER TABLE gardens ADD COLUMN " + column + " REAL");
            }
        }
    }

    /** Saves the garden and all boxes together, or rolls back every insert on failure. */
    @Override
    public void createGarden(Garden garden) {
        Objects.requireNonNull(garden, "Garden cannot be null");
        if (garden.getOwner() == null || !GardenValidator.validate(garden.getName(), garden.getLocation(),
                String.valueOf(garden.getWidth()), String.valueOf(garden.getLength()),
                Integer.toString(garden.getPlanterBoxCount())).isEmpty()) {
            throw new IllegalArgumentException("A garden requires valid details and an organiser");
        }
        if (garden.getId() != null) throw new IllegalArgumentException("This garden is already saved");
        if (accountDAO.getAccountByName(garden.getOwner().getName()) == null) {
            throw new IllegalArgumentException("The organiser must have a saved account");
        }
        try {
            if (!connection.getAutoCommit()) {
                throw new IllegalStateException("Garden creation requires an idle database connection");
            }
            connection.setAutoCommit(false);
            int gardenId;
            List<Integer> boxIds = new ArrayList<>();
            try {
                try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO gardens (name, location, temperature, precipitation,
                                         atmosphericHumidity, owner_name, width, length)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """)) {
                    statement.setString(1, garden.getName().trim());
                    statement.setString(2, garden.getLocation().trim());
                    statement.setObject(3, garden.getTemperature());
                    statement.setObject(4, garden.getPrecipitation());
                    statement.setObject(5, garden.getAtmosphericHumidity());
                    statement.setString(6, garden.getOwner().getName());
                    statement.setDouble(7, garden.getWidth());
                    statement.setDouble(8, garden.getLength());
                    statement.executeUpdate();
                    gardenId = lastInsertedId();
                }
                try (PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO garden_planter_boxes (garden_id, box_number) VALUES (?, ?)")) {
                    for (int index = 0; index < garden.getPlanterBoxCount(); index++) {
                        statement.setInt(1, gardenId);
                        statement.setInt(2, index + 1);
                        statement.executeUpdate();
                        boxIds.add(lastInsertedId());
                    }
                }
                connection.commit();
            } catch (SQLException | RuntimeException exception) {
                try { connection.rollback(); } catch (SQLException rollback) { exception.addSuppressed(rollback); }
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
            garden.restoreLayout(gardenId, garden.getWidth(), garden.getLength(), boxIds);
        } catch (SQLException exception) {
            // The controller must know the save failed; printing alone would report false success.
            throw new IllegalStateException("Could not save the garden", exception);
        }
    }

    private int lastInsertedId() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT last_insert_rowid()")) {
            if (!result.next()) throw new SQLException("No inserted ID returned");
            return result.getInt(1);
        }
    }

    @Override
    public ArrayList<Garden> findGardens(String searchName, String searchLocation) {
        ArrayList<Garden> gardens = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM gardens
            WHERE (? IS NULL OR name LIKE ?) AND (? IS NULL OR location LIKE ?)
            ORDER BY name
            """)) {
            String name = searchName == null || searchName.isBlank() ? null : searchName.trim();
            String location = searchLocation == null || searchLocation.isBlank() ? null : searchLocation.trim();
            statement.setString(1, name);
            statement.setString(2, name == null ? null : "%" + name + "%");
            statement.setString(3, location);
            statement.setString(4, location == null ? null : "%" + location + "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) gardens.add(mapGarden(result));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not load gardens", exception);
        }
        return gardens;
    }

    /** Returns a saved garden with its stable box IDs, or null if it no longer exists. */
    public Garden getGardenById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM gardens WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapGarden(result) : null;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not load the garden", exception);
        }
    }

    private Garden mapGarden(ResultSet result) throws SQLException {
        Account owner = accountDAO.getAccountByName(result.getString("owner_name"));
        Garden garden = new Garden(result.getString("name"), result.getString("location"),
                nullableDouble(result, "temperature"), nullableDouble(result, "precipitation"),
                result.getObject("atmosphericHumidity") == null ? null : result.getInt("atmosphericHumidity"), owner);
        int id = result.getInt("id");
        List<Integer> boxes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM garden_planter_boxes WHERE garden_id = ? ORDER BY box_number")) {
            statement.setInt(1, id);
            try (ResultSet boxResult = statement.executeQuery()) {
                while (boxResult.next()) boxes.add(boxResult.getInt("id"));
            }
        }
        garden.restoreLayout(id, nullableDouble(result, "width"), nullableDouble(result, "length"), boxes);
        return garden;
    }

    private Double nullableDouble(ResultSet result, String column) throws SQLException {
        double value = result.getDouble(column);
        return result.wasNull() ? null : value;
    }
}
