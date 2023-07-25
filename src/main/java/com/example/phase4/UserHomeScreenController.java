package com.example.phase4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

//push
public class UserHomeScreenController implements Initializable {
    @FXML
    private Button button_logout;

    @FXML
    private Button button_settings;

    @FXML
    private Label label_userhomescreen;

    @FXML
    private Button button_createtrip;

    @FXML
    private Button button_viewtrips;

    @FXML
    private Button button_searchcities;

    @FXML
    private Button button_createjournalentry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", "log in", null);
            }
        });

        button_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "user-settings.fxml", "log in", null);
            }
        });

        button_createtrip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "create-trip.fxml", "log in", null);
            }
        });

        button_viewtrips.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "view-my-trips.fxml", "log in", null);
            }
        });

        button_searchcities.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "search-result.fxml", "log in", null);
            }
        });

        button_createjournalentry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "create-city-journal-entry.fxml", "log in", null);
            }
        });

    }

    public void setUserInformation(String username) {
        label_userhomescreen.setText(username);
    }
}
