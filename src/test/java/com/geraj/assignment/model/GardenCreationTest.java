package com.geraj.assignment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GardenCreationTest {
    private Account organiser() {
        return new Account("manny", "manny@example.com", "Manny", "Test", "", "test-hash");
    }
    @Test void validCreationTrimsNamesAndStoresLayout() {
        Garden garden = Garden.create(" Garden ", " Brisbane ", 12, 8, 4, organiser());
        assertEquals("Garden", garden.getName());
        assertEquals("Brisbane", garden.getLocation());
        assertEquals(12.0, garden.getWidth().doubleValue());
        assertEquals(8.0, garden.getLength().doubleValue());
        assertEquals(4, garden.getPlanterBoxCount());
    }
    @Test void creatorIsAdminByUsernameRatherThanObjectIdentity() {
        Garden garden = Garden.create("Garden", "Brisbane", 12, 8, 4, organiser());
        assertTrue(garden.isAdmin(organiser()));
        assertFalse(garden.isAdmin(new Account("other", "other@example.com", "Other", "Test", "", "hash")));
        assertFalse(garden.isAdmin(null));
    }
    @Test void missingOrganiserIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Garden.create("Garden", "Brisbane", 12, 8, 4, null));
    }
    @Test void invalidLayoutIsRejectedBeforePersistence() {
        assertThrows(IllegalArgumentException.class, () -> Garden.create("Garden", "Brisbane", 0, 8, 4, organiser()));
    }
    @Test void legacyConstructorRemainsAvailable() {
        Garden garden = new Garden("Existing", "Brisbane", 20.0, 10.0, 60, organiser());
        assertNull(garden.getWidth());
        assertEquals(20.0, garden.getTemperature().doubleValue());
        assertEquals(0, garden.getPlanterBoxCount());
    }
}
