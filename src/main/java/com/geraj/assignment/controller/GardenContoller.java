package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GardenContoller {
    @FXML private Button searchButton;
    @FXML private Button createButton;
    @FXML private Button selectButton;

    @FXML
    private void onSearchButton(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }

    @FXML
    private void onCreateButton(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "create-garden-view.fxml");
    }

    @FXML
    private void onSelectButton(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
