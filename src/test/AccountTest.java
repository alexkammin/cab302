import com.geraj.assignment.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Name", "email@example.com", "hash_string");
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
    public void testConstructorWithNullName() {
        assertThrows(NullPointerException.class, () -> new User(null, "email@example.com", "hash_string"));
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
    public void testConstructorWithNullEmail() {
        assertThrows(NullPointerException.class, () -> new User("Name", null, "hash_string"));
    }

    @Test
    public void testGetHash() {
        assertEquals("hash_string", user.getHash());
    }

    @Test
    public void testSetHash() {
        user.setHash("new_hash_string");
        assertEquals("new_hash_string", user.getHash());
    }

    @Test
    public void testConstructorWithNullHash() {
        assertThrows(NullPointerException.class, () -> new User("Name", "email@example.com", null));
    }
}