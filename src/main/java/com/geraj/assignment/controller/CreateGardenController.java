package com.geraj.assignment.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller for the Create Garden screen.
 *
 * The controller currently exposes the JavaFX controls required by US-17.
 * Garden validation and persistence will be implemented in the next stage.
 */
public class CreateGardenController {

    // Required garden details
    @FXML
    private TextField gardenNameField;

    @FXML
    private TextField locationField;

    // Garden dimensions
    @FXML
    private TextField widthField;

    @FXML
    private TextField lengthField;

    // Number of empty planter boxes to create
    @FXML
    private TextField planterBoxField;

    // Validation messages
    @FXML
    private Label planterErrorLabel;

    @FXML
    private VBox formErrorBox;

    // Form buttons
    @FXML
    private Button cancelButton;

    @FXML
    private Button createGardenButton;


    /**
     * Called automatically by JavaFX after the FXML has been loaded.
     */
    @FXML
    private void initialize() {

        // Validation messages should not appear
        // when the page is first opened.
        planterErrorLabel.setVisible(false);
        planterErrorLabel.setManaged(false);

        formErrorBox.setVisible(false);
        formErrorBox.setManaged(false);
    }
}