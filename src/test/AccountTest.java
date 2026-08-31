import com.geraj.assignment.model.Account;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("Name", "email@example.com", "hash_string");
    }

    @Test
    public void testGetName() {
        assertEquals("Name", account.getName());
    }

    @Test
    public void testSetName() {
        account.setName("NewName");
        assertEquals("NewName", account.getName());
    }

    @Test
    public void testConstructorWithNullName() {
        assertThrows(NullPointerException.class, () -> new Account(null, "email@example.com", "hash_string"));
    }

    @Test
    public void testGetEmail() {
        assertEquals("email@example.com", account.getEmail());
    }

    @Test
    public void testSetEmail() {
        account.setEmail("newEmail@example.com");
        assertEquals("newEmail@example.com", account.getEmail());
    }

    @Test
    public void testConstructorWithNullEmail() {
        assertThrows(NullPointerException.class, () -> new Account("Name", null, "hash_string"));
    }

    @Test
    public void testGetHash() {
        assertEquals("hash_string", account.getHash());
    }

    @Test
    public void testSetHash() {
        account.setHash("new_hash_string");
        assertEquals("new_hash_string", account.getHash());
    }

    @Test
    public void testConstructorWithNullHash() {
        assertThrows(NullPointerException.class, () -> new Account("Name", "email@example.com", null));
    }
}