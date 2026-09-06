package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Garden;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GardenInfoContoller {
    @FXML private Label GardenName;
    @FXML private Label GardenLocation;

    public void setInfo(Garden garden) {
        GardenName.setText(garden.getName());
        GardenLocation.setText(garden.getLocation());
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }
}
