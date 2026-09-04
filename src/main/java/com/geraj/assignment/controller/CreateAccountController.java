package com.geraj.assignment.controller;

import com.geraj.assignment.PasswordService;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.model.Account;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class CreateAccountController {

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button createAccountButton;

    @FXML
    private void initialize() {
        createAccountButton.disableProperty().bind(
                Bindings.or(
                        usernameTextField.textProperty().isEmpty(),
                        passwordField.textProperty().isEmpty()
                )
        );
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {
        IAccountDAO accountDAO = new SqliteAccountDAO();
        PasswordService passwordService = PasswordService.getInstance();

        String username = usernameTextField.getText().trim();
        char[] password = passwordField.getText().toCharArray();
        String hash = passwordService.hashPassword(password);

        Account account = new Account(username, "TEMP_EMAIL_REPLACE_LATER", "TEMP_FIRST_NAME", "TEMP_LAST_NAME", hash);
        accountDAO.createAccount(account);
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
