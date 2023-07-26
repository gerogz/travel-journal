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
public class ViewMyTripsController implements Initializable{
    @FXML
    private TableView<Trip> table_viewmytrips;

    @FXML
    private TableColumn<Trip, String> column_viewmytrips;

    @FXML
    private Button button_viewmytrips_back;

    @FXML
    private Button button_viewmytrips_showall;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("A");

        column_viewmytrips.setCellValueFactory(new PropertyValueFactory<Trip, String>("name"));

        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psSelect2 = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
            System.out.println("B");
            psSelect2 = connection.prepareStatement("SELECT email \n" +
                    "FROM user\n" +
                    "WHERE username = ?;");
            System.out.println("C");
            psSelect2.setString(1, DBUtils.user);
            ResultSet rs = psSelect2.executeQuery();
            System.out.println("D");
            rs.next();
            String s = rs.getString(1);

            psSelect = connection.prepareStatement("SELECT name \n" +
                    "FROM trip\n" +
                    "WHERE email = ? AND username = ?;");
            psSelect.setString(1, s);
            psSelect.setString(2, DBUtils.user);
            rs = psSelect.executeQuery();
            ObservableList<Trip> o = FXCollections.observableArrayList();
            while (rs.next()) {
                Trip t = new Trip(rs.getString("name"));
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
        button_viewmytrips_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "user-home-screen.fxml", "City!", null);
            }
        });

        button_viewmytrips_showall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.trip = null;
                DBUtils.changeScene(event, "my-trip-report.fxml", "City!", null);
            }
        });

        table_viewmytrips.setOnMouseClicked(this::onTableRowClicked);
    }
    private void onTableRowClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Get the selected item from the TableView
            Trip c = table_viewmytrips.getSelectionModel().getSelectedItem();

            // You can now perform actions based on the selected row data
            if (c != null) {
                DBUtils.trip = c;
                DBUtils.changeScene(event,"my-trip-report.fxml", "Sign up!", null);
            }
        }
    }
        public void addEntry (ObservableList < Trip > o) {
            table_viewmytrips.setItems(o);
            System.out.println(o);
        }
}
