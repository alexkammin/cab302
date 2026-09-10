package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class NavController {

    @FXML private Label gardenLabel;
    @FXML private Label scheduleLabel;
    @FXML private Label accountLabel;
    @FXML private Label mainLabel;

    @FXML
    public void initialize() {
        highlightActiveTab(SceneSwitcher.getCurrentView());
    }

    private void highlightActiveTab(String currentView) {
        if (currentView == null) return;

        switch (currentView) {
            case "garden-view.fxml" -> gardenLabel.getStyleClass().add("active");
//            case "schedule-view.fxml" -> scheduleLabel.getStyleClass().add("active");
            case "account-profile-view.fxml" -> accountLabel.getStyleClass().add("active");
            case "main-view.fxml" -> mainLabel.getStyleClass().add("active");
        }
    }

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
                "schedule-view.fxml"
        );
    }

    @FXML
    private void goToAccount(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "account-profile-view.fxml"
        );
    }

    @FXML
    private void goToMain(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "main-view.fxml"
        );
    }
}
