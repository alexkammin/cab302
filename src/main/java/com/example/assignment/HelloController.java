package com.example.assignment;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {
    private IAccountDAO accountDAO;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button createAccountButton;

    public HelloController() {
        accountDAO = new SqliteAccountDAO();
    }

    @FXML
    public void initialize() {
        createAccountButton.disableProperty().bind(
                Bindings.or(
                        usernameTextField.textProperty().isEmpty(),
                        passwordField.textProperty().isEmpty()
                )
        );
    }

    @FXML
    private void onCreateAccount() {
        String username = usernameTextField.getText().trim();
        char[] password = passwordField.getText().toCharArray();;

        String hash = hashPassword(password);

        Account newAccount = new Account(username, hash);

        accountDAO.createAccount(newAccount);
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
