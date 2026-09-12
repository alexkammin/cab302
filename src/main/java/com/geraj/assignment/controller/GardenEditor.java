package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GardenEditor {
    private Garden current_garden;

    public void setGarden(Garden garden) {
        current_garden = garden;
    }

    @FXML
    private void createNewGardenPlot(ActionEvent actionEvent) {

        // 1. Open the form and wait for it to close.
        // Be sure the path to your FXML file is correct!
        GardenPlotFormController formController = SceneSwitcher.openModalAndWait(
                actionEvent,
                "/GardenPlotForm.fxml",
                "Create New Garden Plot"
        );

        // 2. Check if a controller was successfully returned
        if (formController != null) {

            // 3. Extract the data
            GardenPlot newPlot = formController.getNewPlot();

            // 4. Add it to the garden (if the user didn't click cancel)
            if (newPlot != null) {
                current_garden.addGardenPlot(newPlot);
                System.out.println("New plot added successfully!");
                // TODO: Update your list view or UI to show the new plot
            }
        }
    }
}
