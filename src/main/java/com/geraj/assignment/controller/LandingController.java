package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LandingController {

    @FXML
    private void onSignIn(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "sign-in-view.fxml"
        );
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "create-account-view.fxml"
        );
    }
}