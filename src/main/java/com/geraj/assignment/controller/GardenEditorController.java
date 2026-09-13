package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GardenEditorController {
    private Garden current_garden;

    public void setGarden(Garden garden) {
        current_garden = garden;
    }

    @FXML
    private void onCreateNewGardenPlot(ActionEvent actionEvent) {
        GardenPlotFormController formController = SceneSwitcher.openModalAndWait(
                actionEvent,
                "garden-plot-form-view.fxml",
                "Create New Garden Plot"
        );

        if (formController != null) {
            GardenPlot newPlot = formController.getNewPlot();

            if (newPlot != null) {
                current_garden.addGardenPlot(newPlot);
            }
        }
    }
}
