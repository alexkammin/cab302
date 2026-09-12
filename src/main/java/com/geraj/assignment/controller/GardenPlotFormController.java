package com.geraj.assignment.controller;

import com.geraj.assignment.model.GardenPlot;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GardenPlotFormController {

    // FXML fields matching your UI form
    @FXML private TextField widthField;
    @FXML private TextField lengthField;
    @FXML private TextField phField;
    @FXML private TextField lightField;
    @FXML private TextField nutrimentsField;
    @FXML private TextField salinityField;
    @FXML private TextField textureField;
    @FXML private TextField depthField;
    @FXML private TextField soilHumidityField;

    @FXML private Button saveButton;

    private GardenPlot newPlot = null;

    @FXML
    private void handleSave() {
        try {
            // Parse inputs from TextFields
            double width = Double.parseDouble(widthField.getText());
            double length = Double.parseDouble(lengthField.getText());
            double ph = Double.parseDouble(phField.getText());
            int light = Integer.parseInt(lightField.getText());
            int nutriments = Integer.parseInt(nutrimentsField.getText());
            int salinity = Integer.parseInt(salinityField.getText());
            int texture = Integer.parseInt(textureField.getText());
            double depth = Double.parseDouble(depthField.getText());
            int soilHumidity = Integer.parseInt(soilHumidityField.getText());

            // Create the plot using your specific constructor
            newPlot = new GardenPlot(
                    width, length, ph, light, nutriments,
                    salinity, texture, depth, soilHumidity
            );

            closeWindow();

        } catch (NumberFormatException e) {
            // Show an error if the user typed text instead of numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please enter valid numbers");
            alert.setContentText("Width, length, ph, and depth must be decimals. The rest must be whole numbers.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        newPlot = null; // Ensure nothing gets passed back
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public GardenPlot getNewPlot() {
        return newPlot;
    }
}