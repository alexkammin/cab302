package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class CredentialsController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button nextButton;

    @FXML
    public void initialize() {
        nextButton.disableProperty().bind(
                Bindings.or(
                        usernameTextField.textProperty().isEmpty(),
                        passwordField.textProperty().isEmpty()
                )
        );
    }

    @FXML
    public void onNext(ActionEvent actionEvent) {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText();

        Account newAccount = new Account(username, password);

        TosController controller = SceneSwitcher.switchScene(actionEvent, "tos-view.fxml");

        assert controller != null;
        controller.setAccountData(newAccount);
    }
}
