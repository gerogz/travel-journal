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
public class AdminFlagsController implements Initializable {
    @FXML
    private TableView<adminPageEntry> table_adminflags;
    @FXML
    private TableColumn<adminPageEntry, String> column_adminflags_user;
    @FXML
    private TableColumn<adminPageEntry, String> column_adminflags_city;
    @FXML
    private TableColumn<adminPageEntry, String> column_adminflags_note;
    @FXML
    private TableColumn<adminPageEntry, String> column_adminflags_reason;
    @FXML
    private TableColumn<adminPageEntry, String> column_adminflags_flagger;
    @FXML
    private Button button_adminflags_logoff;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_adminflags_user.setCellValueFactory(new PropertyValueFactory<adminPageEntry, String>("user"));
        column_adminflags_city.setCellValueFactory(new PropertyValueFactory<adminPageEntry, String>("city"));
        column_adminflags_note.setCellValueFactory(new PropertyValueFactory<adminPageEntry, String>("note"));
        column_adminflags_reason.setCellValueFactory(new PropertyValueFactory<adminPageEntry, String>("reason"));
        column_adminflags_flagger.setCellValueFactory(new PropertyValueFactory<adminPageEntry, String>("flagger"));

        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");

            psSelect = connection.prepareStatement("SELECT entry_username AS user, entry_email AS email, city.name AS city, entry.note AS note, reason.reason AS reason, username AS flagger, email AS flaggerEmail, locationID, entry.date as Date, rating\n" +
                    "FROM reason\n" +
                    "NATURAL JOIN entry\n" +
                    "NATURAL JOIN flags\n" +
                    "NATURAL JOIN city\n" +
                    "ORDER BY entry_username, location_ID;");
            ResultSet rs = psSelect.executeQuery();
            ObservableList<adminPageEntry> o = FXCollections.observableArrayList();
            while (rs.next()) {
                adminPageEntry ce = new adminPageEntry(rs.getString("user"), rs.getString("city"), rs.getString("note"), rs.getString("reason"), rs.getString("flagger"),
                        rs.getDate("Date").toString(), rs.getInt("rating"), rs.getString("email"), rs.getString("flaggerEmail"), rs.getInt("locationID"));
                o.add(ce);
                //System.out.println(rs.getString("Date") + " " + rs.getInt("Rating") + " " + rs.getString("Note"));
            }

            addEntry(o);

            //test("Hello");
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
        table_adminflags.setOnMouseClicked(this::onTableRowClicked);

        button_adminflags_logoff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", "log in", null);
            }
        });
    }

    // Click event handler for the TableView rows
    private void onTableRowClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Get the selected item from the TableView
            adminPageEntry ce = table_adminflags.getSelectionModel().getSelectedItem();

            // You can now perform actions based on the selected row data
            if (ce != null) {
                //System.out.println("Clicked on row: " + ce.getDate());
                DBUtils.flaggedEntry = ce;
                DBUtils.changeScene(event, "review-flags.fxml", "Review", null);
            }
        }
    }

    public void addEntry(ObservableList<adminPageEntry> o) {
        table_adminflags.setItems(o);
        System.out.println(o);
    }
}
