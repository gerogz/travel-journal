package com.example.phase4;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {
    @FXML
    private Button button_createAccount;
    @FXML
    private Button button_back;
    @FXML
    private TextField tf_fname;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_admin;
    @FXML
    private Button button_user;

    private int adminOrUser = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_admin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminOrUser = 1;
            }
        });
        button_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminOrUser = 2;
            }
        });
        button_createAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_fname.getText().trim().isEmpty() && !tf_lname.getText().trim().isEmpty()
                        && !tf_email.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()
                        && adminOrUser != 0) {
                    DBUtils.createAccount(event, tf_fname.getText(), tf_lname.getText(), tf_email.getText(), tf_username.getText(), tf_password.getText(), adminOrUser);
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to create an account!");
                    alert.show();
                }
            }
        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button Back Clicked!");
                DBUtils.changeScene(event, "login.fxml", "Back", null);
            }
        });
    }
}
