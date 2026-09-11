package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Garden;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/** Minimal US17 handoff: displays saved empty boxes. Retains the existing class spelling. */
public class GardenContoller {
    @FXML private ComboBox<Garden> gardenSelector;
    @FXML private Label statusLabel, gardenName, gardenDetails, roleLabel;
    @FXML private VBox overviewCard;
    @FXML private FlowPane boxPane;

    @FXML
    private void initialize() {
        if (!AccountSession.isLoggedIn()) {
            statusLabel.setText("Sign in to view your garden.");
            return;
        }
        try {
            gardenSelector.getItems().setAll(new SqliteGardenDAO().findGardens(null, null));
            if (gardenSelector.getItems().isEmpty()) statusLabel.setText("No gardens yet. Create one to get started.");
            else gardenSelector.getSelectionModel().selectFirst();
        } catch (RuntimeException exception) {
            statusLabel.setText("Gardens could not be loaded. Please check the database connection and try again.");
            exception.printStackTrace();
        }
    }

    /** Selects the garden just created, using its database identity. */
    public void showGarden(Garden garden) {
        for (Garden existing : gardenSelector.getItems()) {
            if (existing.getId() != null && existing.getId().equals(garden.getId())) {
                gardenSelector.getSelectionModel().select(existing);
                render(existing);
                return;
            }
        }
        gardenSelector.getItems().add(garden);
        gardenSelector.getSelectionModel().select(garden);
        render(garden);
    }

    @FXML
    private void onGardenSelected() {
        Garden selected = gardenSelector.getValue();
        if (selected != null) render(selected);
    }

    private void render(Garden garden) {
        overviewCard.setVisible(true);
        overviewCard.setManaged(true);
        statusLabel.setText("");
        gardenName.setText(garden.getName());
        String size = garden.getWidth() == null || garden.getLength() == null
                ? "Dimensions not recorded for this older garden"
                : garden.getWidth() + " m × " + garden.getLength() + " m";
        gardenDetails.setText(garden.getLocation() + " · " + size + " · " + garden.getPlanterBoxCount() + " planter boxes");
        boolean admin = AccountSession.isLoggedIn() && garden.isAdmin(AccountSession.getInstance().getAccount());
        roleLabel.setText(admin ? "Your role: Garden admin" : "Viewing garden details");
        boxPane.getChildren().clear();
        int number = 1;
        for (int boxId : garden.getPlanterBoxIds()) {
            VBox box = new VBox(6, new Label("Planter box " + number++), new Label("Empty"));
            box.getStyleClass().add("garden-box");
            box.setPrefWidth(160);
            // US18 can use this stable database identity when wiring crop selection.
            box.setUserData(boxId);
            boxPane.getChildren().add(box);
        }
        if (garden.getPlanterBoxCount() == 0) boxPane.getChildren().add(new Label("No planter boxes recorded for this garden."));
    }

    @FXML private void onCreateGarden(ActionEvent event) { SceneSwitcher.switchScene(event, "create-garden-view.fxml"); }
    @FXML private void onBack(ActionEvent event) { SceneSwitcher.switchScene(event, "garden-list-view.fxml"); }
}
