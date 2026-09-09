package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class NavController {
    @FXML
    private void goToGarden(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "garden-view.fxml"
        );
    }

    @FXML
    private void goToSchedule(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "schedule.fxml"
        );
    }

    @FXML
    private void goToAccount(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "account-view.fxml"
        );
    }
}
