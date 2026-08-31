package com.geraj.assignment.controller;

import com.geraj.assignment.PasswordService;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class CredentialsController {

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button createAccountButton;

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
    public void onCreateAccount(ActionEvent actionEvent) {
        IAccountDAO accountDAO = new SqliteAccountDAO();
        PasswordService passwordService = PasswordService.getInstance();

        String username = usernameTextField.getText().trim();
        char[] password = passwordField.getText().toCharArray();
        String hash = passwordService.hashPassword(password);

        accountDAO.createAccount(username, hash);
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
