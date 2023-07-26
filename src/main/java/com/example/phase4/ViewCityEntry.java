package com.example.phase4;
import com.example.phase4.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;
public class ViewCityEntry implements Initializable {
    @FXML
    private Label label_city;

    @FXML
    private Label label_date;

    @FXML
    private Label label_rating;

    @FXML
    private Label label_note;

    @FXML
    private Button button_back;

    @FXML
    private Button button_report;

    public void initialize(URL location, ResourceBundle resources) {
        label_city.setText(DBUtils.city.getName());
        label_date.setText(DBUtils.cityEntry.getDate());
        label_rating.setText("" + DBUtils.cityEntry.getRating());
        label_note.setText(DBUtils.cityEntry.getNote());

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "city-journal-entries.fxml", "City!", null);
            }
        });

        button_report.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "report.fxml", "City!", null);
            }
        });
    }
}
