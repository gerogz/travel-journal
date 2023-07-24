package com.example.signupmysql;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;
import com.example.signupmysql.DBUtils;


public class LoggedInController implements Initializable {
    @FXML
    private Button button_logout;

    @FXML
    private Label label_homescreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "log in", "gi");
            }
        });
    }

    public void setUserInformation(String username) {
        label_homescreen.setText(username);
    }
}
