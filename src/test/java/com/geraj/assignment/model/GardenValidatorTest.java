package com.geraj.assignment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** US17: field-level validation, independently of JavaFX and the database. */
class GardenValidatorTest {
    @Test void validDetailsHaveNoErrors() {
        assertTrue(GardenValidator.validate("QUT Garden", "Brisbane", "12", "8.5", "4").isEmpty());
    }
    @Test void surroundingWhitespaceIsAccepted() {
        assertTrue(GardenValidator.validate(" QUT Garden ", " Brisbane ", " 12 ", " 8 ", " 4 ").isEmpty());
    }
    @Test void blankNameIsRejected() {
        assertTrue(GardenValidator.validate("  ", "Brisbane", "12", "8", "4").containsKey("name"));
    }
    @Test void missingLocationIsRejected() {
        assertTrue(GardenValidator.validate("Garden", null, "12", "8", "4").containsKey("location"));
    }
    @Test void zeroAndNegativeDimensionsAreRejected() {
        var errors = GardenValidator.validate("Garden", "Brisbane", "0", "-2", "4");
        assertTrue(errors.containsKey("width"));
        assertTrue(errors.containsKey("length"));
    }
    @Test void nonNumericDimensionsAreRejected() {
        assertTrue(GardenValidator.validate("Garden", "Brisbane", "abc", "8", "4").containsKey("width"));
    }
    @Test void nonFiniteDimensionsAreRejected() {
        var errors = GardenValidator.validate("Garden", "Brisbane", "NaN", "Infinity", "4");
        assertTrue(errors.containsKey("width"));
        assertTrue(errors.containsKey("length"));
    }
    @Test void zeroBoxesAreRejected() {
        assertTrue(GardenValidator.validate("Garden", "Brisbane", "12", "8", "0").containsKey("boxes"));
    }
    @Test void fractionalBoxesAreRejected() {
        assertTrue(GardenValidator.validate("Garden", "Brisbane", "12", "8", "2.5").containsKey("boxes"));
    }
    @Test void overflowingBoxCountIsRejected() {
        assertTrue(GardenValidator.validate("Garden", "Brisbane", "12", "8", "999999999999").containsKey("boxes"));
    }
    @Test void oneBoxIsValid() {
        assertTrue(GardenValidator.validate("Garden", "Brisbane", "0.5", "0.5", "1").isEmpty());
    }
    @Test void allMissingFieldsAreReportedTogether() {
        assertEquals(5, GardenValidator.validate(null, null, null, null, null).size());
    }
}
