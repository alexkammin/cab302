package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

/** US17 integration tests. These use isolated databases, never communityPlant.db. */
class SqliteGardenDAOTest {
    private Connection connection;
    private SqliteGardenDAO dao;
    private Account organiser;

    @BeforeEach void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (Statement statement = connection.createStatement()) { statement.execute("PRAGMA foreign_keys = ON"); }
        organiser = new Account("manny", "manny@example.com", "Manny", "Test", "", "test-hash");
        new SqliteAccountDAO(connection).createAccount(organiser);
        dao = new SqliteGardenDAO(connection);
    }
    @AfterEach void tearDown() throws SQLException { connection.close(); }

    @Test void savesDimensionsAndExactNumberOfEmptyBoxes() throws SQLException {
        // Arrange: a valid unsaved garden. Act: persist it. Assert: reload and inspect storage.
        Garden garden = garden("QUT Garden", 4);
        dao.createGarden(garden);
        Garden loaded = dao.getGardenById(garden.getId());
        assertNotNull(loaded);
        assertEquals(12.0, loaded.getWidth().doubleValue());
        assertEquals(8.0, loaded.getLength().doubleValue());
        assertEquals(4, loaded.getPlanterBoxCount());
        assertEquals(4, count("garden_planter_boxes"));
        assertEquals(4, loaded.getPlanterBoxIds().stream().distinct().count());
    }
    @Test void reloadedCreatorHasAdminRole() {
        Garden garden = garden("Admin Garden", 1);
        dao.createGarden(garden);
        assertTrue(dao.getGardenById(garden.getId()).isAdmin(organiser));
    }
    @Test void duplicateNameDoesNotCreateExtraGardenOrBoxes() throws SQLException {
        dao.createGarden(garden("Same name", 2));
        assertThrows(IllegalStateException.class, () -> dao.createGarden(garden("Same name", 3)));
        assertEquals(1, count("gardens"));
        assertEquals(2, count("garden_planter_boxes"));
        assertTrue(connection.getAutoCommit());
    }
    @Test void failureDuringBoxInsertRollsBackEntireGarden() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TRIGGER fail_second_box BEFORE INSERT ON garden_planter_boxes
                WHEN NEW.box_number = 2 BEGIN SELECT RAISE(ABORT, 'test failure'); END
                """);
        }
        Garden garden = garden("Rollback garden", 3);
        assertThrows(IllegalStateException.class, () -> dao.createGarden(garden));
        assertEquals(0, count("gardens"));
        assertEquals(0, count("garden_planter_boxes"));
        assertNull(garden.getId());
        assertTrue(connection.getAutoCommit());
    }
    @Test void invalidMutatedDetailsDoNotSaveAnything() throws SQLException {
        Garden garden = garden("Garden", 2);
        garden.setName("  ");
        assertThrows(IllegalArgumentException.class, () -> dao.createGarden(garden));
        assertEquals(0, count("gardens"));
    }
    @Test void unknownOrganiserIsRejected() throws SQLException {
        Garden garden = Garden.create("Garden", "Brisbane", 12, 8, 1,
                new Account("unknown", "unknown@example.com", "U", "T", "", "hash"));
        assertThrows(IllegalArgumentException.class, () -> dao.createGarden(garden));
        assertEquals(0, count("gardens"));
    }
    @Test void oldSchemaMigratesWithoutLosingExistingGarden() throws SQLException {
        try (Connection old = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            new SqliteAccountDAO(old).createAccount(organiser);
            try (Statement statement = old.createStatement()) {
                statement.execute("""
                    CREATE TABLE gardens (id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE, location TEXT NOT NULL, temperature REAL,
                    precipitation REAL, atmosphericHumidity REAL, owner_name TEXT)
                    """);
                statement.execute("INSERT INTO gardens(name, location, owner_name) VALUES ('Old Garden', 'Brisbane', 'manny')");
            }
            SqliteGardenDAO migrated = new SqliteGardenDAO(old);
            Garden legacy = migrated.findGardens("Old Garden", null).get(0);
            assertNull(legacy.getWidth());
            assertEquals(0, legacy.getPlanterBoxCount());
            migrated.createGarden(garden("New Garden", 3));
            assertEquals(2, migrated.findGardens(null, null).size());
            new SqliteGardenDAO(old); // Migration can be run again safely.
            assertEquals(2, migrated.findGardens(null, null).size());
        }
    }
    @Test void gardenAndAdminSurviveClosingAndReopeningDatabase(@TempDir Path directory) throws SQLException {
        String url = "jdbc:sqlite:" + directory.resolve("us17-test.db");
        int id;
        try (Connection first = DriverManager.getConnection(url)) {
            new SqliteAccountDAO(first).createAccount(organiser);
            Garden garden = garden("Restart Garden", 3);
            new SqliteGardenDAO(first).createGarden(garden);
            id = garden.getId();
        }
        try (Connection reopened = DriverManager.getConnection(url)) {
            Garden loaded = new SqliteGardenDAO(reopened).getGardenById(id);
            assertEquals("Restart Garden", loaded.getName());
            assertEquals(3, loaded.getPlanterBoxCount());
            assertTrue(loaded.isAdmin(organiser));
        }
    }
    private Garden garden(String name, int boxes) { return Garden.create(name, "Brisbane", 12, 8, boxes, organiser); }
    private int count(String table) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {
            result.next();
            return result.getInt(1);
        }
    }
}
