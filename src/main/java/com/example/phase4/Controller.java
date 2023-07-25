package com.example.phase4;
import com.example.phase4.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;

public class Controller implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private Button button_createaccount;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Login button clicked");
                DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
            }
        });

        button_createaccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "createaccount.fxml", "Sign up!", null);
            }
        });

    }
}