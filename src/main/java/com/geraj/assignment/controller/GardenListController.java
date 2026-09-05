package com.geraj.assignment.controller;

import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.IGardenDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Garden;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class GardenListController {
    private final IGardenDAO gardenDAO = new SqliteGardenDAO();

    @FXML private TextField gardenNameTextField;
    @FXML private TextField gardenLocationTextField;
    @FXML private ListView<Garden> gardenListView;

    private final ObservableList<Garden> gardensObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        gardenListView.setItems(gardensObservableList);
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) {
        String nameToSearch = gardenNameTextField.getText();
        String locationToSearch = gardenLocationTextField.getText();

        ArrayList<Garden> searchResultGardens = gardenDAO.findGardens(nameToSearch, locationToSearch);

        gardensObservableList.setAll(searchResultGardens);
    }
}
