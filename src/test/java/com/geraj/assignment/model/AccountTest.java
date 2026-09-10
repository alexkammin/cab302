package com.geraj.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    private static final String USERNAME = "harresh";
    private static final String EMAIL = "harresh@example.com";
    private static final String FIRST_NAME = "Harresh";
    private static final String LAST_NAME = "Patel";
    private static final String PHONE_NUMBER = "0412345678";
    private static final String HASH = "stored-password-hash";

    @Test
    void constructingAccountShouldPreserveAccountAndProfileInformation() {
        Account account = createAccount();

        assertAll(
                () -> assertEquals(USERNAME, account.getName()),
                () -> assertEquals(EMAIL, account.getEmail()),
                () -> assertEquals(FIRST_NAME, account.getFirstName()),
                () -> assertEquals(LAST_NAME, account.getLastName()),
                () -> assertEquals(PHONE_NUMBER, account.getPhoneNumber()),
                () -> assertEquals(HASH, account.getHash())
        );
    }

    @Test
    void updatingPersonalInformationShouldPreserveUsernameAndHash() {
        Account account = createAccount();

        account.setFirstName("Harry");
        account.setLastName("Smith");
        account.setEmail("harry.smith@example.com");
        account.setPhoneNumber("+61412345678");

        assertAll(
                () -> assertEquals("Harry", account.getFirstName()),
                () -> assertEquals("Smith", account.getLastName()),
                () -> assertEquals("harry.smith@example.com", account.getEmail()),
                () -> assertEquals("+61412345678", account.getPhoneNumber()),
                () -> assertEquals(USERNAME, account.getName()),
                () -> assertEquals(HASH, account.getHash())
        );
    }

    @Test
    void nullFirstNameShouldBeRejectedWithDescriptiveMessage() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Account(
                        USERNAME, EMAIL, null, LAST_NAME, PHONE_NUMBER, HASH
                )
        );

        assertEquals("Account first name cannot be null", exception.getMessage());
    }

    @Test
    void nullLastNameShouldBeRejectedWithDescriptiveMessage() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Account(
                        USERNAME, EMAIL, FIRST_NAME, null, PHONE_NUMBER, HASH
                )
        );

        assertEquals("Account last name cannot be null", exception.getMessage());
    }

    @Test
    void nullPhoneNumberShouldBeRejectedWithDescriptiveMessage() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Account(
                        USERNAME, EMAIL, FIRST_NAME, LAST_NAME, null, HASH
                )
        );

        assertEquals("Account phone number cannot be null", exception.getMessage());
    }

    private Account createAccount() {
        return new Account(
                USERNAME,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                PHONE_NUMBER,
                HASH
        );
    }
}
