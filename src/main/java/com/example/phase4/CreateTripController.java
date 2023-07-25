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

public class CreateTripController implements Initializable{
    @FXML
    private Button button_createtrip;
    @FXML
    private Button button_back;
    @FXML
    private TextField tf_tripname;

    @FXML
    private TextField tf_startdate;

    @FXML
    private TextField tf_enddate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_createtrip.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Create Trip Button Clicked!");
                DBUtils.createTrip(event, tf_tripname.getText(), tf_startdate.getText(), tf_enddate.getText());
            }
        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "user-home-screen.fxml", "City!", null);
            }
        });
    }
}
