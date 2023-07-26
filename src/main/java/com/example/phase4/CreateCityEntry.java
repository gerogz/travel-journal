package com.example.phase4;
import com.example.phase4.DBUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;
public class CreateCityEntry implements Initializable {
    String privacy = null;

    @FXML
    private TextField tf_city;
    @FXML
    private ComboBox<Integer> combo_rating;
    @FXML
    private TextField tf_date;
    @FXML
    private TextField tf_note;

    @FXML
    private Button button_back;

    @FXML
    private Button button_private;

    @FXML
    private Button button_public;

    @FXML
    private Button button_save;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo_rating.setItems(FXCollections.observableArrayList(1,2,3,4,5));

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psSelect = null;
        PreparedStatement psInsertAdminUser = null;
        //PreparedStatement psCheckUserExists = null;
        //ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
            psSelect = connection.prepareStatement("SELECT privacyLevel FROM user WHERE username = ?");

            psSelect.setString(1, DBUtils.user);
            ResultSet rs = psSelect.executeQuery();
            rs.next();
            privacy = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(privacy);
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "user-home-screen.fxml", "City!", null);
            }
        });

        button_private.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                privacy = "private";
                System.out.println(privacy);
            }
        });

        button_public.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                privacy = "public";
                System.out.println(privacy);
            }
        });

        button_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Connection connection = null;
                PreparedStatement psSelect = null;
                PreparedStatement psInsert = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("SELECT locationID FROM city WHERE name = ?");
                    psSelect.setString(1, tf_city.getText());
                    ResultSet rs = psSelect.executeQuery();
                    rs.next();
                    int i = rs.getInt(1);

                    psSelect = connection.prepareStatement("SELECT email FROM user WHERE username = ?");
                    psSelect.setString(1, DBUtils.user);
                    rs = psSelect.executeQuery();
                    rs.next();
                    String s = rs.getString(1);
                    psInsert = connection.prepareStatement("INSERT INTO entry (note, rating, date, privacyLevel, locationID, email, username)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
                    psInsert.setString(1, tf_note.getText());
                    psInsert.setInt(2, combo_rating.getValue());
                    psInsert.setString(3, tf_date.getText());
                    psInsert.setString(4, privacy);
                    psInsert.setInt(5, i);
                    psInsert.setString(6, s);
                    psInsert.setString(7, DBUtils.user);

                    psInsert.executeUpdate();
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
                DBUtils.changeScene(event, "user-home-screen.fxml", "City!", null);
            }
        });
    }
}
