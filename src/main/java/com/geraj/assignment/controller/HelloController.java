package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.GerajApplication;
import com.geraj.assignment.model.IAccountDAO;
import com.geraj.assignment.model.SqliteAccountDAO;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private final IAccountDAO accountDAO;

    private static String enteredUsername;
    private static char[] enteredPassword;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private CheckBox agreeCheckBox;

    @FXML
    private Button backButton;

    public HelloController() {
        accountDAO = new SqliteAccountDAO();
    }

    @FXML
    public void initialize() {

        // for credentials page
        if (usernameTextField != null && passwordField != null && nextButton != null) {
            nextButton.disableProperty().bind(
                    Bindings.or(
                            usernameTextField.textProperty().isEmpty(),
                            passwordField.textProperty().isEmpty()
                    )
            );
        }

        // for tos page
        if (agreeCheckBox != null && createAccountButton != null) {
            createAccountButton.disableProperty().bind(
                    agreeCheckBox.selectedProperty().not()
            );
        }

    }

    @FXML
    public void onNext(ActionEvent actionEvent) throws IOException {
        enteredUsername = usernameTextField.getText().trim();
        enteredPassword = passwordField.getText().toCharArray();

//        Stage stage = (Stage) nextButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource("tos-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), GerajApplication.WIDTH, GerajApplication.HEIGHT);
//        stage.setScene(scene);

        SceneSwitcher.switchScene(actionEvent, "tos-view.fxml");
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
//        Stage stage = (Stage) backButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource("credentials-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), GerajApplication.WIDTH, GerajApplication.HEIGHT);
//        stage.setScene(scene);

        SceneSwitcher.switchScene(actionEvent, "credentials-view.fxml");
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) throws IOException {
        if (enteredUsername == null || enteredPassword == null) {
            if (messageLabel != null) messageLabel.setText("Error: Missing credentials.");
            return;
        }

        String hash = hashPassword(enteredPassword);
        Account newAccount = new Account(enteredUsername, hash);
        accountDAO.createAccount(newAccount);

        if (messageLabel != null) {
            messageLabel.setText("Account successfully created!");
        }

//        Stage stage = (Stage) createAccountButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource("main-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), GerajApplication.WIDTH, GerajApplication.HEIGHT);
//        stage.setScene(scene);

        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
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
