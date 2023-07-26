package com.example.phase4;
import com.example.phase4.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;
public class adminReviewEntryController implements Initializable {
    @FXML
    private TextArea textfield_reviewflags_city;
    @FXML
    private TextArea textfield_reviewflags_date;
    @FXML
    private TextArea textfield_reviewflags_rating;
    @FXML
    private TextArea textfield_reviewflags_note;
    @FXML
    private Button button_reviewflags_banuser;
    @FXML
    private Button button_reviewflags_back;
    @FXML
    private Button button_reviewflags_clearflag;
    @FXML
    private Button button_reviewflags_deleteentry;

    public void initialize(URL location, ResourceBundle resources) {
        textfield_reviewflags_city.setText(DBUtils.flaggedEntry.getCity());
        textfield_reviewflags_date.setText(DBUtils.flaggedEntry.getDate());
        textfield_reviewflags_rating.setText("" + DBUtils.flaggedEntry.getRating());
        textfield_reviewflags_note.setText(DBUtils.flaggedEntry.getNote());
        String sqlURL = "jdbc:mysql://localhost:3306/new_schema";
        String sqlPassword = "L2O2Z/Hb7k9rf3";
            button_reviewflags_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "admin-flags.fxml", "Welcome!", null);
            }
        });
        button_reviewflags_clearflag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection connection = null;
                PreparedStatement psSelect = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("DELETE FROM flags\n" +
                            "WHERE username = ? AND email = ? AND entry_username = ? AND entry_email = ? AND flag_date = ? AND location_ID = ?;");
                    psSelect.setString(1, DBUtils.flaggedEntry.getFlagger());
                    psSelect.setString(2, DBUtils.flaggedEntry.getFlaggerEmail());
                    psSelect.setString(3, DBUtils.flaggedEntry.getUser());
                    psSelect.setString(4, DBUtils.flaggedEntry.getEmail());
                    psSelect.setString(5, DBUtils.flaggedEntry.getDate());
                    psSelect.setInt(6, DBUtils.flaggedEntry.getLocationId());
                    psSelect.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        button_reviewflags_deleteentry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection connection = null;
                PreparedStatement psSelect = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("DELETE FROM entry\n" +
                            "WHERE date = ? AND locationID = ? AND email = ? AND username = ?;");
                    psSelect.setString(1, DBUtils.flaggedEntry.getDate());
                    psSelect.setInt(2, DBUtils.flaggedEntry.getLocationId());
                    psSelect.setString(3, DBUtils.flaggedEntry.getEmail());
                    psSelect.setString(4, DBUtils.flaggedEntry.getUser());
                    psSelect.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        button_reviewflags_banuser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection connection = null;
                PreparedStatement psSelect = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("UPDATE user\n" +
                            "SET bannerEmail = ?, bannerUsername = ?\n" +
                            "WHERE username = ? AND email = ?;");
                    psSelect.setString(1, DBUtils.email);
                    psSelect.setString(2, DBUtils.user);
                    psSelect.setString(3, DBUtils.flaggedEntry.getUser());
                    psSelect.setString(4, DBUtils.flaggedEntry.getEmail());
                    psSelect.executeUpdate();
                    psSelect = connection.prepareStatement("DELETE FROM entry\n" +
                            "WHERE username = ? AND email = ?;");
                    psSelect.setString(1, DBUtils.flaggedEntry.getUser());
                    psSelect.setString(2, DBUtils.flaggedEntry.getEmail());
                    psSelect.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


//        button_back.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DBUtils.changeScene(event, "city-journal-entries.fxml", "City!", null);
//            }
//        });
    }

}
