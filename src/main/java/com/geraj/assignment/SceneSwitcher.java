package com.geraj.assignment;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneSwitcher {
    private static String currentView = GerajApplication.LANDING_PAGE;

    public static String getCurrentView() {
        return currentView;
    }

    public static <T> T switchScene(Event event, String fxmlFile) {
        currentView = fxmlFile;
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            stage.getScene().setRoot(root);

            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}