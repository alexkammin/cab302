package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dto.RegistrationDTO;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

public class TosController {
    private final IAccountDAO accountDAO;
    private RegistrationDTO registration;

    @FXML public TextArea termsAndConditions;
    @FXML public CheckBox agreeCheckBox;
    @FXML public Button backButton;
    @FXML public Button createAccountButton;

    public TosController() { accountDAO = new SqliteAccountDAO(); }

    @FXML
    public void initialize() {
        createAccountButton.disableProperty().bind(
                agreeCheckBox.selectedProperty().not()
        );
    }

    public void setRegistrationData(RegistrationDTO registration) {
        this.registration = registration;
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "credentials-view.fxml");
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {
        accountDAO.createAccount(registration);
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
