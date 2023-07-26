package com.example.phase4;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.*;

//hello

public class DBUtils {
    public static String user;
    private static String sqlURL = "jdbc:mysql://localhost:3306/test6";
    private static String sqlPassword = "SaintLouis16#";
    public static City city;
    public static CityEntries cityEntry;
    public static BigEntry bigEntry;

    public static Trip trip;

    public static void changeScene(Event event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                if (fxmlFile == "user-home-screen.fxml") {
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                    root = loader.load();
                    UserHomeScreenController userHomeScreenController = loader.getController();
                    userHomeScreenController.setUserInformation(username);
                } else if (fxmlFile == "admin-flags.fxml") {
                    // FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                    // root = loader.load();
                    // UserHomeScreenController userHomeScreenController = loader.getController();
                    // userHomeScreenController.setUserInformation(username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void createAccount(ActionEvent event, String fname, String lname, String email, String username,
            String password, int adminOrUser) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psInsertAdminUser = null;
        // PreparedStatement psCheckUserExists = null;
        // ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);

            psInsert = connection.prepareStatement(
                    "INSERT INTO account (fname, lname, email, username, pwd) VALUES (?, ?, ?, ?, ?)");
            // ZoneId zoneId = ZoneId.of();
            String today = "" + java.time.LocalDate.now();
            if (adminOrUser == 1) {
                psInsertAdminUser = connection
                        .prepareStatement("INSERT INTO admin (email, username, startDate) VALUES(?, ?, ?)");
            } else if (adminOrUser == 2) {
                psInsertAdminUser = connection.prepareStatement(
                        "INSERT INTO user (email, username, memberDate, privacyLevel) VALUES(?, ?, ?, \"private\")");
            }
            psInsert.setString(1, fname);
            psInsert.setString(2, lname);
            psInsert.setString(3, email);
            psInsert.setString(4, username);
            psInsert.setString(5, password);
            psInsertAdminUser.setString(1, email);
            psInsertAdminUser.setString(2, username);
            psInsertAdminUser.setString(3, today);
            psInsert.executeUpdate();
            psInsertAdminUser.executeUpdate();
            if (adminOrUser == 2) {
                changeScene(event, "user-home-screen.fxml", "Welcome!", username);
                user = username;
            } else if (adminOrUser == 1) {
                changeScene(event, "admin-flags.fxml", "Welcome!", null);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
            ;
        }
    }

    public static void logInUser(ActionEvent event, String username, String pwd) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);

            preparedStatement = connection.prepareStatement("SELECT pwd FROM account WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Account not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("pwd");
                    if (retrievedPassword.equals(pwd)) {
                        changeScene(event, "user-home-screen.fxml", "Welcome!", username);
                        user = username;
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
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
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
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

    public static void createTrip(ActionEvent event, String tname, String start, String end) {
        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);

            psSelect = connection.prepareStatement("SELECT email FROM user WHERE username = ?");
            psSelect.setString(1, user);
            ResultSet rs = psSelect.executeQuery();
            rs.next();
            String s = rs.getString(1);

            psInsert = connection.prepareStatement(
                    "INSERT INTO trip (start_date, end_date, name, email, username) VALUES (?, ?, ?, ?, ?)");
            psInsert.setString(1, start);
            psInsert.setString(2, end);
            psInsert.setString(3, tname);
            psInsert.setString(4, s);
            psInsert.setString(5, user);
            System.out.println(s + " " + user);
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
    }

    public static void viewTrips(ActionEvent event) {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);

            psSelect = connection.prepareStatement("SELECT name FROM trip WHERE username = ?");
            psSelect.setString(1, user);

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

    public static void deleteEntry(ActionEvent event, String cityName, String countryName, String entryDate) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        PreparedStatement psGetLocationID = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);
            psGetLocationID = connection.prepareStatement(
                    "SELECT locationID FROM city WHERE name = ? AND country = ?;");
            psGetLocationID.setString(1, cityName);
            psGetLocationID.setString(2, countryName);
            System.out.println(psGetLocationID);
            resultSet = psGetLocationID.executeQuery();
            resultSet.next();
            int locationID = resultSet.getInt(1);
            System.out.println(locationID);
            psDelete = connection.prepareStatement(
                    "DELETE FROM entry WHERE date = ? AND locationID = ? AND username = ?;");

            psDelete.setString(1, entryDate);
            psDelete.setInt(2, locationID);
            psDelete.setString(3, user);
            psDelete.executeUpdate();
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
}
