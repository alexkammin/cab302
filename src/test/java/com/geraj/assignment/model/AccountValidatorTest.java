package com.geraj.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountValidatorTest {
    private static final String FIRST_NAME = "Harresh";
    private static final String LAST_NAME = "Patel";
    private static final String EMAIL = "harresh@example.com";
    private static final String PHONE_NUMBER = "0412345678";
    private static final String POSTCODE = "4000";

    @Test
    void validPersonalInformationShouldReturnNoErrors() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void namesWithSurroundingWhitespaceShouldReturnNoErrors() {
        AccountValidator.ValidationResult result = validate(
                "  Harresh  ", "  Patel  ", EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertTrue(result.isValid());
    }

    @Test
    void firstNameWithOneHundredCharactersShouldReturnNoError() {
        AccountValidator.ValidationResult result = validate(
                "a".repeat(100), LAST_NAME, EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertFalse(result.hasError("firstName"));
    }

    @Test
    void firstNameWithOneHundredAndOneCharactersShouldReturnError() {
        AccountValidator.ValidationResult result = validate(
                "a".repeat(101), LAST_NAME, EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertTrue(result.hasError("firstName"));
    }

    @Test
    void nullFirstNameShouldReturnError() {
        assertTrue(validate(null, LAST_NAME, EMAIL, PHONE_NUMBER, POSTCODE)
                .hasError("firstName"));
    }

    @Test
    void blankFirstNameShouldReturnError() {
        assertTrue(validate("   ", LAST_NAME, EMAIL, PHONE_NUMBER, POSTCODE)
                .hasError("firstName"));
    }

    @Test
    void lastNameWithOneHundredCharactersShouldReturnNoError() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, "a".repeat(100), EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertFalse(result.hasError("lastName"));
    }

    @Test
    void lastNameWithOneHundredAndOneCharactersShouldReturnError() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, "a".repeat(101), EMAIL, PHONE_NUMBER, POSTCODE
        );

        assertTrue(result.hasError("lastName"));
    }

    @Test
    void nullLastNameShouldReturnError() {
        assertTrue(validate(FIRST_NAME, null, EMAIL, PHONE_NUMBER, POSTCODE)
                .hasError("lastName"));
    }

    @Test
    void emptyLastNameShouldReturnError() {
        assertTrue(validate(FIRST_NAME, "", EMAIL, PHONE_NUMBER, POSTCODE)
                .hasError("lastName"));
    }

    @Test
    void emailWithTwoHundredAndFiftyFourCharactersShouldReturnNoError() {
        String email = "a".repeat(242) + "@example.com";

        assertFalse(validate(FIRST_NAME, LAST_NAME, email, PHONE_NUMBER, POSTCODE)
                .hasError("email"));
    }

    @Test
    void emailWithTwoHundredAndFiftyFiveCharactersShouldReturnError() {
        String email = "a".repeat(243) + "@example.com";

        assertTrue(validate(FIRST_NAME, LAST_NAME, email, PHONE_NUMBER, POSTCODE)
                .hasError("email"));
    }

    @Test
    void nullEmailShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, null, PHONE_NUMBER, POSTCODE)
                .hasError("email"));
    }

    @Test
    void emailWithoutAtSymbolShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh.example.com", PHONE_NUMBER, POSTCODE
        ).hasError("email"));
    }

    @Test
    void emailWithoutDomainDotShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh@example", PHONE_NUMBER, POSTCODE
        ).hasError("email"));
    }

    @Test
    void emailContainingWhitespaceShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh @example.com", PHONE_NUMBER, POSTCODE
        ).hasError("email"));
    }

    @Test
    void emptyPhoneNumberShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "", POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberContainingSpacesShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "0412 345 678", POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithAustralianCountryCodeShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "+61412345678", POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void nullPhoneNumberShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, null, POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithIncorrectPrefixShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, "1412345678", POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithTooFewDigitsShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, "041234567", POSTCODE)
                .hasError("phoneNumber"));
    }

    @Test
    void postcodeWithFourDigitsShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, "4000")
                .hasError("postcode"));
    }

    @Test
    void nullPostcodeShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, null)
                .hasError("postcode"));
    }

    @Test
    void emptyPostcodeShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, "")
                .hasError("postcode"));
    }

    @Test
    void postcodeWithThreeDigitsShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, "400")
                .hasError("postcode"));
    }

    @Test
    void postcodeContainingLettersShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, "4A00")
                .hasError("postcode"));
    }

    private AccountValidator.ValidationResult validate(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String postcode
    ) {
        return AccountValidator.validatePersonalInformation(
                firstName,
                lastName,
                email,
                phoneNumber,
                postcode
        );
    }
}
