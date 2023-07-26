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
public class SearchResultController implements Initializable{
    @FXML
    private TableView<City> table_results;
    @FXML
    private TableColumn<City, String> column_city;
    @FXML
    private TableColumn<City, String> column_countr;
    @FXML
    private TableColumn<City, Double> column_averagerating;

    @FXML
    private Button button_back;

    @FXML
    private Button button_search_searchresult;
    @FXML
    private TextField textfield_searchforcity;

    @FXML
    private Button button_reset_searchresult;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_city.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
        column_countr.setCellValueFactory(new PropertyValueFactory<City, String>("country"));
        column_averagerating.setCellValueFactory(new PropertyValueFactory<City, Double>("avg"));

        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");

            psSelect = connection.prepareStatement("SELECT name as Name, country as Country, avg(rating) as Rating\n" +
                    "FROM city, entry\n" +
                    "WHERE city.locationID = entry.locationID\n" +
                    "GROUP BY city.locationID;");
            ResultSet rs = psSelect.executeQuery();
            ObservableList<City> o = FXCollections.observableArrayList();
            while (rs.next()) {
                City c = new City(rs.getString("Name"), rs.getString("Country"), rs.getDouble("Rating"));
                o.add(c);
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
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "user-home-screen.fxml", "City!", null);
            }
        });

        table_results.setOnMouseClicked(this::onTableRowClicked);

        button_search_searchresult.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = textfield_searchforcity.getText();

                Connection connection = null;
                PreparedStatement psSelect = null;
                PreparedStatement psInsert = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");

                    psSelect = connection.prepareStatement("SELECT name as Name, country as Country, avg(rating) as Rating\n" +
                            "FROM city, entry\n" +
                            "WHERE city.locationID = entry.locationID AND city.name = ?\n" +
                            "GROUP BY city.locationID;");
                    psSelect.setString(1,name);
                    ResultSet rs = psSelect.executeQuery();
                    ObservableList<City> o = FXCollections.observableArrayList();
                    while (rs.next()) {
                        City c = new City(rs.getString("Name"), rs.getString("Country"), rs.getDouble("Rating"));
                        o.add(c);
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
            }
        });

        button_reset_searchresult.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                column_city.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
                column_countr.setCellValueFactory(new PropertyValueFactory<City, String>("country"));
                column_averagerating.setCellValueFactory(new PropertyValueFactory<City, Double>("avg"));

                Connection connection = null;
                PreparedStatement psSelect = null;
                PreparedStatement psInsert = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");

                    psSelect = connection.prepareStatement("SELECT name as Name, country as Country, avg(rating) as Rating\n" +
                            "FROM city, entry\n" +
                            "WHERE city.locationID = entry.locationID\n" +
                            "GROUP BY city.locationID;");
                    ResultSet rs = psSelect.executeQuery();
                    ObservableList<City> o = FXCollections.observableArrayList();
                    while (rs.next()) {
                        City c = new City(rs.getString("Name"), rs.getString("Country"), rs.getDouble("Rating"));
                        o.add(c);
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
            }
        });
    }

        public void addEntry (ObservableList < City > o) {
            table_results.setItems(o);
            System.out.println(o);
        }

    private void onTableRowClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Get the selected item from the TableView
            City c = table_results.getSelectionModel().getSelectedItem();

            // You can now perform actions based on the selected row data
            if (c != null) {
                DBUtils.city = c;
                DBUtils.changeScene(event,"city-journal-entries.fxml", "Sign up!", null);
            }
        }
    }

}
