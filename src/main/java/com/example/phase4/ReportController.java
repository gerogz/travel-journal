package com.example.phase4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController {
    ObservableList<String> choiceboxList = FXCollections.observableArrayList("Hate Speech", "Nudity", "Other");
    @FXML
    private Button report_button_back;
    @FXML
    private Button report_button_submit;
    @FXML
    private ChoiceBox report_choicebox;

    public void initialize(URL location, ResourceBundle resources) {

        report_choicebox.setItems(choiceboxList);
        report_button_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Submit Button Clicked!");
                DBUtils.createReport(event, report_checkbox.getReason());
            }
        });
        report_button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "view-city-entry.fxml", "", null);
            }
        });
    }
}
