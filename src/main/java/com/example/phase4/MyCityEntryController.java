package com.example.phase4;

import com.example.phase4.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;

public class MyCityEntryController implements Initializable {
    @FXML
    private Label textfield_mycityentry_city;

    @FXML
    private Label textfield_mycityentry_date;

    @FXML
    private Label textfield_mycityentry_rating;

    @FXML
    private Label textfield_mycityentry_note;

    @FXML
    private Button button_mycityentry_back;

    @FXML
    private Button button_mycityentry_deleteentry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Test");
        textfield_mycityentry_city.setText(DBUtils.bigEntry.getCity());
        textfield_mycityentry_date.setText(DBUtils.bigEntry.getDate());
        textfield_mycityentry_rating.setText("" + DBUtils.bigEntry.getRating());
        textfield_mycityentry_note.setText(DBUtils.bigEntry.getNote());

        button_mycityentry_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "my-trip-report.fxml", "City!", null);
            }
        });

        button_mycityentry_deleteentry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.deleteEntry(event, DBUtils.bigEntry.getCity(), DBUtils.bigEntry.getCountry(),
                        DBUtils.bigEntry.getDate());
            }
        });
    }
}
