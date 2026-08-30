import com.geraj.assignment.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Name", "email@example.com", "password123");
    }

    @Test
    public void testGetName() {
        assertEquals("Name", user.getName());
    }

    @Test
    public void testSetName() {
        user.setName("NewName");
        assertEquals("NewName", user.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("newEmail@example.com");
        assertEquals("newEmail@example.com", user.getEmail());
    }

    @Test
    public void testVerifyPassword() {
        assertTrue(user.verifyPassword("password123"));
    }

    @Test
    public void testPasswordVerificationFailsWithIncorrectPassword() {
        assertFalse(user.verifyPassword("wrongPassword"));
    }

    @Test
    public void testUpdatePasswordNewPasswordSucceeds() {
        user.updatePassword("newPassword123");
        assertTrue(user.verifyPassword("newPassword123"));
    }

    @Test
    public void testUpdatePasswordOldPasswordFails() {
        user.updatePassword("newPassword123");
        assertFalse(user.verifyPassword("password123"));
    }

    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, "email@example.com", "password123"));
    }

    @Test
    public void testConstructorWithNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> new User("Name", null, "password123"));
    }

    @Test
    public void testConstructorWithNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> new User("Name", "email@example.com", null));
    }

    @Test
    public void testVerifyPasswordWithNullInput() {
        assertFalse(user.verifyPassword(null));
    }

    @Test
    public void testUpdatePasswordWithNullFails() {
        assertThrows(IllegalArgumentException.class, () -> user.updatePassword(null));

        // ensure old password still works if update failed
        assertTrue(user.verifyPassword("password123"));
    }

    @Test
    public void testPasswordVerificationIsCaseSensitive() {
        assertFalse(user.verifyPassword("PASSWORD123"));
    }
}