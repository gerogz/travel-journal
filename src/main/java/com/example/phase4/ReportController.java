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
import java.sql.*;
import java.util.ResourceBundle;

public class ReportController implements Initializable{
    @FXML
    private Button report_button_back;
    @FXML
    private Button report_button_submit;
    @FXML
    private CheckBox box_hate;
    @FXML
    private CheckBox box_nudity;
    @FXML
    private CheckBox box_other;



    public void initialize(URL location, ResourceBundle resources) {

        report_button_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Submit Button Clicked!");
                Connection connection = null;
                PreparedStatement psSelect = null;
                ResultSet resultSet = null;

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("SELECT email FROM user WHERE username = ?");
                    psSelect.setString(1, DBUtils.user);
                    ResultSet rs = psSelect.executeQuery();
                    rs.next();
                    String e = rs.getString("email");
                    System.out.println("A");

                    psSelect = connection.prepareStatement("SELECT email FROM user WHERE username = ?");
                    psSelect.setString(1, DBUtils.cityEntry.getUser());
                    rs = psSelect.executeQuery();
                    rs.next();
                    String ee = rs.getString("email");
                    System.out.println("B");

                    psSelect = connection.prepareStatement("SELECT locationID FROM city WHERE name = ?");
                    psSelect.setString(1, DBUtils.city.getName());
                    rs = psSelect.executeQuery();
                    rs.next();
                    String id = rs.getString("locationID");
                    System.out.println("C");

                    psSelect = connection.prepareStatement("INSERT INTO flags\n" +
                            "(username, email, entry_username, entry_email, flag_date, location_ID)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?, ?);");
                    psSelect.setString(1, DBUtils.user);
                    psSelect.setString(2, e);
                    psSelect.setString(3, DBUtils.cityEntry.getUser());
                    psSelect.setString(4, ee);
                    psSelect.setString(5, DBUtils.cityEntry.getDate());
                    psSelect.setString(6, id);
                    System.out.println("D");

                    psSelect.executeUpdate();

                    if (box_hate.isSelected()) {
                        psSelect = connection.prepareStatement("INSERT INTO reason\n" +
                                "(username, email, entry_username, entry_email, flag_date, location_ID, reason)\n" +
                                "VALUES\n" +
                                "(?, ?, ?, ?, ?, ?, ?);");
                        psSelect.setString(1, DBUtils.user);
                        psSelect.setString(2, e);
                        psSelect.setString(3, DBUtils.cityEntry.getUser());
                        psSelect.setString(4, ee);
                        psSelect.setString(5, DBUtils.cityEntry.getDate());
                        psSelect.setString(6, id);
                        psSelect.setString(7,"Hate Speech");
                        System.out.println("D");

                        psSelect.executeUpdate();
                    }

                    if (box_nudity.isSelected()) {
                        psSelect = connection.prepareStatement("INSERT INTO reason\n" +
                                "(username, email, entry_username, entry_email, flag_date, location_ID, reason)\n" +
                                "VALUES\n" +
                                "(?, ?, ?, ?, ?, ?, ?);");
                        psSelect.setString(1, DBUtils.user);
                        psSelect.setString(2, e);
                        psSelect.setString(3, DBUtils.cityEntry.getUser());
                        psSelect.setString(4, ee);
                        psSelect.setString(5, DBUtils.cityEntry.getDate());
                        psSelect.setString(6, id);
                        psSelect.setString(7,"Nudity");
                        System.out.println("D");

                        psSelect.executeUpdate();
                    }

                    if (box_other.isSelected()) {
                        psSelect = connection.prepareStatement("INSERT INTO reason\n" +
                                "(username, email, entry_username, entry_email, flag_date, location_ID, reason)\n" +
                                "VALUES\n" +
                                "(?, ?, ?, ?, ?, ?, ?);");
                        psSelect.setString(1, DBUtils.user);
                        psSelect.setString(2, e);
                        psSelect.setString(3, DBUtils.cityEntry.getUser());
                        psSelect.setString(4, ee);
                        psSelect.setString(5, DBUtils.cityEntry.getDate());
                        psSelect.setString(6, id);
                        psSelect.setString(7,"Other");
                        System.out.println("D");

                        psSelect.executeUpdate();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        report_button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "view-city-entry.fxml", "City!", null);
            }
        });
    }
}
