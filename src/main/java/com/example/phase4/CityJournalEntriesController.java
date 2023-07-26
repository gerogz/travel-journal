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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class CityJournalEntriesController implements Initializable {

    @FXML
    private TableView<CityEntries> table_entries;
    @FXML
    private TableColumn<CityEntries, String> column_date;
    @FXML
    private TableColumn<CityEntries, Integer> column_rating;
    @FXML
    private TableColumn<CityEntries, String> column_note;

    @FXML
    private Button button_createjournalentry;

    @FXML
    private Button button_back;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_date.setCellValueFactory(new PropertyValueFactory<CityEntries, String>("date"));
        column_rating.setCellValueFactory(new PropertyValueFactory<CityEntries, Integer>("rating"));
        column_note.setCellValueFactory(new PropertyValueFactory<CityEntries, String>("note"));

        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");

            psSelect = connection.prepareStatement("SELECT date as Date, rating as Rating, note as Note, username as User\n" +
                    "FROM entry join city on entry.locationID = city.locationID\n" +
                    "WHERE city.name = ? AND  privacyLevel = ?");
            psSelect.setString(1, DBUtils.city.getName());
            psSelect.setString(2, "public");
            ResultSet rs = psSelect.executeQuery();
            ObservableList<CityEntries> o = FXCollections.observableArrayList();
            while (rs.next()) {
                CityEntries ce = new CityEntries(rs.getString("Date"), rs.getInt("Rating"), rs.getString("Note"), rs.getString("User"));
                o.add(ce);
                System.out.println(rs.getString("Date") + " " + rs.getInt("Rating") + " " + rs.getString("Note"));
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
        table_entries.setOnMouseClicked(this::onTableRowClicked);

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "search-result.fxml", "City!", null);
            }
        });

        button_createjournalentry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "create-city-journal-entry.fxml", "City!", null);
            }
        });
    }

    // Click event handler for the TableView rows
    private void onTableRowClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Get the selected item from the TableView
            CityEntries ce = table_entries.getSelectionModel().getSelectedItem();

            // You can now perform actions based on the selected row data
            if (ce != null) {
                System.out.println("Clicked on row: " + ce.getDate());
                DBUtils.cityEntry = ce;
                DBUtils.changeScene(event, "view-city-entry.fxml", "Sign up!", null);
            }
        }
    }

    public void addEntry(ObservableList<CityEntries> o) {
        table_entries.setItems(o);
        System.out.println(o);
    }

    public void test(String s) {
        button_createjournalentry.setText(s);
    }

}
