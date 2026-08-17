package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.IAccountDAO;
import com.geraj.assignment.model.SqliteAccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

public class TosController {
    private final IAccountDAO accountDAO;
    private Account newAccount;

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

    public void setAccountData(Account account) {
        this.newAccount = account;
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "credentials-view.fxml");
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {
        accountDAO.createAccount(newAccount);
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
