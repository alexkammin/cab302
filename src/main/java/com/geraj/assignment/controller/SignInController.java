package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {

        BooleanBinding emptyField =
                usernameTextField.textProperty().isEmpty()
                        .or(passwordField.textProperty().isEmpty());

        signInButton.disableProperty().bind(emptyField);
    }

    @FXML
    private void onSignIn(ActionEvent actionEvent) {

        String username = usernameTextField.getText().trim();
        String password = passwordField.getText();

        messageLabel.setText("");

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText(
                    "Please enter your username and password."
            );
            return;
        }

        /*
         * Add database authentication here.
         * Once authentication succeeds, switch to the main page:
         *
         * SceneSwitcher.switchScene(
         *         actionEvent,
         *         "main-view.fxml"
         * );
         */

        messageLabel.setText(
                "Sign-in authentication still needs to be connected."
        );
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "landing-page-view.fxml"
        );
    }
}
