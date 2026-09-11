package com.geraj.assignment.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** US17 form rules, kept independent of JavaFX so they can be unit tested. */
public final class GardenValidator {
    private GardenValidator() { }

    /** Returns errors keyed by name, location, width, length and boxes; empty means valid. */
    public static Map<String, String> validate(String name, String location,
                                              String width, String length, String boxes) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (name == null || name.isBlank()) errors.put("name", "Enter a garden name.");
        if (location == null || location.isBlank()) errors.put("location", "Enter a location.");
        validateDimension(width, "width", "width", errors);
        validateDimension(length, "length", "length", errors);
        try {
            if (Integer.parseInt(boxes == null ? "" : boxes.trim()) < 1) {
                errors.put("boxes", "Enter at least 1 planter box.");
            }
        } catch (NumberFormatException exception) {
            errors.put("boxes", "Enter a whole number of planter boxes (at least 1).");
        }
        return Collections.unmodifiableMap(errors);
    }

    private static void validateDimension(String input, String key, String label,
                                          Map<String, String> errors) {
        try {
            double value = Double.parseDouble(input == null ? "" : input.trim());
            if (!Double.isFinite(value) || value <= 0) throw new NumberFormatException();
        } catch (NumberFormatException exception) {
            errors.put(key, "Enter a " + label + " greater than 0 metres.");
        }
    }
}
