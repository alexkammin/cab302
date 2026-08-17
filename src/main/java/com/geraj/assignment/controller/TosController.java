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
    private RegistrationDTO registration;

    @FXML public TextArea termsAndConditions;
    @FXML public CheckBox agreeCheckBox;
    @FXML public Button backButton;
    @FXML public Button createAccountButton;

    public TosController() {  }

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
        CredentialsController controller = SceneSwitcher.switchScene(actionEvent, "credentials-view.fxml");
        if (controller != null && registration != null) {
            controller.setRegistrationData(registration);
        }
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {
        IAccountDAO accountDAO = new SqliteAccountDAO();
        accountDAO.createAccount(registration);
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
