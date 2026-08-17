package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
        char[] password = passwordField.getText().toCharArray();

        String hash = hashPassword(password);

        Account newAccount = new Account(username, hash);

        TosController controller = SceneSwitcher.switchScene(actionEvent, "tos-view.fxml");

        assert controller != null;
        controller.setAccountData(newAccount);
    }

    private String hashPassword(char[] password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        try {
            return argon2.hash(2, 65536, 1, password);
        }
        finally {
            argon2.wipeArray(password);
        }
    }
}
