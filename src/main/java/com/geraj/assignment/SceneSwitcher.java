package com.geraj.assignment;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneSwitcher {
    public static <T> T switchScene(Event event, String fxmlFile) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load(), GerajApplication.WIDTH, GerajApplication.HEIGHT);
            stage.setScene(scene);

            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}