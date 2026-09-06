package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GardenInfoContoller {
    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }
}
