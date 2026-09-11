package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenValidator;
import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

/** Connects the US17 form to validation, the signed-in organiser and persistence. */
public class CreateGardenController {
    private static final PseudoClass INVALID = PseudoClass.getPseudoClass("invalid");
    @FXML private TextField nameField, locationField, widthField, lengthField, boxesField;
    @FXML private Label nameError, locationError, widthError, lengthError, boxesError;
    @FXML private Label messageLabel, organiserLabel;
    @FXML private Button createButton;
    @FXML private ScrollPane formScroll;
    @FXML private VBox detailsCard, helpColumn;
    private final Map<String, TextField> fields = new LinkedHashMap<>();
    private final Map<String, Label> errorLabels = new LinkedHashMap<>();
    private boolean validationAttempted;
    private Garden savedGarden;

    @FXML
    private void initialize() {
        fields.put("name", nameField); fields.put("location", locationField);
        fields.put("width", widthField); fields.put("length", lengthField); fields.put("boxes", boxesField);
        errorLabels.put("name", nameError); errorLabels.put("location", locationError);
        errorLabels.put("width", widthError); errorLabels.put("length", lengthError); errorLabels.put("boxes", boxesError);
        // At smaller window sizes the help column wraps underneath the form.
        detailsCard.prefWidthProperty().bind(Bindings.max(240, Bindings.min(600, formScroll.widthProperty().subtract(52))));
        helpColumn.prefWidthProperty().bind(Bindings.max(240, Bindings.min(260, formScroll.widthProperty().subtract(52))));
        for (TextField field : fields.values()) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (validationAttempted) displayErrors(validate());
                showMessage("");
            });
        }
        if (AccountSession.isLoggedIn()) {
            organiserLabel.setText("Signed in as " + AccountSession.getInstance().getAccount().getName());
        } else {
            createButton.setDisable(true);
            showMessage("Please sign in before creating a garden.");
        }
    }

    @FXML
    private void onCreateGarden(ActionEvent event) {
        // A failed navigation after a successful save must never create a duplicate.
        if (savedGarden != null) { openSavedGarden(event); return; }
        validationAttempted = true;
        Map<String, String> errors = validate();
        displayErrors(errors);
        if (!errors.isEmpty()) {
            showMessage("Garden not saved. Fix the highlighted details and try again.");
            fields.get(errors.keySet().iterator().next()).requestFocus();
            return;
        }
        if (!AccountSession.isLoggedIn()) {
            showMessage("Your session has ended. Sign in before creating a garden.");
            return;
        }
        createButton.setDisable(true);
        try {
            Garden garden = Garden.create(nameField.getText(), locationField.getText(),
                    Double.parseDouble(widthField.getText().trim()), Double.parseDouble(lengthField.getText().trim()),
                    Integer.parseInt(boxesField.getText().trim()), AccountSession.getInstance().getAccount());
            new SqliteGardenDAO().createGarden(garden);
            savedGarden = garden;
            fields.values().forEach(field -> field.setDisable(true));
            createButton.setText("Open saved garden");
            openSavedGarden(event);
        } catch (RuntimeException exception) {
            if (isDuplicateName(exception)) {
                displayErrors(Map.of("name", "A garden with this name already exists. Choose another name."));
                nameField.requestFocus();
                showMessage("Garden not saved. Choose a different garden name.");
            } else {
                showMessage("The garden could not be saved. Please try again. If this continues, check the database connection.");
            }
            exception.printStackTrace();
        } finally {
            createButton.setDisable(false);
        }
    }

    private void openSavedGarden(ActionEvent event) {
        try {
            GardenContoller controller = SceneSwitcher.switchScene(event, "garden-view.fxml");
            if (controller != null) controller.showGarden(savedGarden);
            else showMessage("Your garden was saved, but the overview could not open. Try Open saved garden or find it under View Gardens.");
        } catch (RuntimeException exception) {
            showMessage("Your garden was saved. Reopen it from the Garden tab if the overview could not be displayed.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void onCancel(ActionEvent event) {
        SceneSwitcher.switchScene(event, "garden-list-view.fxml");
    }

    private Map<String, String> validate() {
        return GardenValidator.validate(nameField.getText(), locationField.getText(),
                widthField.getText(), lengthField.getText(), boxesField.getText());
    }

    private void displayErrors(Map<String, String> errors) {
        fields.forEach((key, field) -> {
            field.pseudoClassStateChanged(INVALID, errors.containsKey(key));
            Label label = errorLabels.get(key);
            label.setText(errors.getOrDefault(key, ""));
            label.setVisible(errors.containsKey(key));
            label.setManaged(errors.containsKey(key));
        });
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setVisible(!message.isEmpty());
        messageLabel.setManaged(!message.isEmpty());
    }

    private boolean isDuplicateName(Throwable exception) {
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause.getMessage() != null && cause.getMessage().contains("UNIQUE constraint failed: gardens.name")) return true;
        }
        return false;
    }
}
