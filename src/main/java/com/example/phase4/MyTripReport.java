package com.example.phase4;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MyTripReport implements Initializable {
    @FXML
    private TableView<BigEntry> table_mytripreport;

    @FXML
    private TableColumn<BigEntry, String> column_mytripreport_date;
    @FXML
    private TableColumn<BigEntry, String> column_mytripreport_city;
    @FXML
    private TableColumn<BigEntry, String> column_mytripreport_country;
    @FXML
    private TableColumn<BigEntry, Integer> column_mytripreport_rating;
    @FXML
    private TableColumn<BigEntry, String> note;

    @FXML
    private Button button_mytripreport_back;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("A");
        column_mytripreport_date.setCellValueFactory(new PropertyValueFactory<BigEntry, String>("date"));
        column_mytripreport_city.setCellValueFactory(new PropertyValueFactory<BigEntry, String>("city"));
        column_mytripreport_country.setCellValueFactory(new PropertyValueFactory<BigEntry, String>("country"));
        System.out.println("B1");
        column_mytripreport_rating.setCellValueFactory(new PropertyValueFactory<BigEntry, Integer>("rating"));
        System.out.println("B2");
        note.setCellValueFactory(new PropertyValueFactory<BigEntry, String>("note"));
        System.out.println("B3");

        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;
        System.out.println("B");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
            String start = "1989-01-01";
            String end = "2050-01-01";
            ResultSet rs;
            if (DBUtils.trip != null) {
                psSelect = connection
                        .prepareStatement("SELECT start_date, end_date FROM trip WHERE username = ? AND name = ?");
                psSelect.setString(1, DBUtils.user);
                psSelect.setString(2, DBUtils.trip.getName());
                rs = psSelect.executeQuery();
                rs.next();
                start = rs.getString("start_date");
                end = rs.getString("end_date");
            }

            psSelect = connection.prepareStatement("SELECT email FROM user WHERE username = ?");
            psSelect.setString(1, DBUtils.user);
            rs = psSelect.executeQuery();
            System.out.println("C");
            rs.next();
            String e = rs.getString(1);

            psSelect = connection.prepareStatement(
                    "SELECT entry.date as Date, city.name as City, city.country as Country, rating, note\n" +
                            "FROM entry\n" +
                            "NATURAL JOIN city\n" +
                            "WHERE email = ? AND username = ? AND entry.date <= ? AND entry.date >= ?\n" +
                            "ORDER BY Date;");
            psSelect.setString(1, e);
            psSelect.setString(2, DBUtils.user);
            psSelect.setString(4, start);
            psSelect.setString(3, end);
            rs = psSelect.executeQuery();
            System.out.println("D");
            ObservableList<BigEntry> o = FXCollections.observableArrayList();
            while (rs.next()) {
                BigEntry t = new BigEntry(rs.getString("Date"), rs.getString("City"), rs.getString("Country"),
                        rs.getInt("rating"), rs.getString("note"));
                o.add(t);
            }

            addEntry(o);

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
        table_mytripreport.setOnMouseClicked(this::onTableRowClicked);

        button_mytripreport_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "view-my-trips.fxml", "City!", null);
            }
        });

    }

    public void addEntry(ObservableList<BigEntry> o) {
        table_mytripreport.setItems(o);
        System.out.println(o);
    }

    // Click event handler for the TableView rows
    private void onTableRowClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Get the selected item from the TableView
            BigEntry ce = table_mytripreport.getSelectionModel().getSelectedItem();

            // You can now perform actions based on the selected row data
            if (ce != null) {
                System.out.println("Clicked on row: " + ce.getDate());
                DBUtils.bigEntry = ce;
                DBUtils.changeScene(event, "my-city-entry.fxml", "Sign up!", null);
            }
        }
    }
}
