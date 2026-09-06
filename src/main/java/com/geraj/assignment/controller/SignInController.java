package com.geraj.assignment.controller;

import com.geraj.assignment.PasswordService;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SignInController {

    @FXML private Button signInButton;

    @FXML
    private void onSignIn(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-view.fxml");
    }
}
