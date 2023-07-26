package com.example.phase4;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.*;

//hello

public class DBUtils {
    public static String user;
    public static String email;
    private static String sqlURL = "jdbc:mysql://localhost:3306/database1";
    private static String sqlPassword = "lapiz2026";
    public static City city;
    public static adminPageEntry flaggedEntry;
    public static CityEntries cityEntry;
    public static Trip trip;
    public static BigEntry bigEntry;

    public static void changeScene(Event event, String fxmlFile, String title, String username) {
        Parent root = null;
        try {
            if (fxmlFile.equals("user-settings.fxml")) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                Connection connection = null;
                PreparedStatement psSelect = null;
                PreparedStatement psSelectP = null;
                ResultSet resultSet = null;
                ResultSet resultSetP = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "lapiz2026");
                    psSelect = connection.prepareStatement("SELECT fname, lname, email, pwd FROM account WHERE username = ?");
                    psSelectP = connection.prepareStatement("SELECT privacyLevel FROM user WHERE username = ?");
                    psSelect.setString(1, user);
                    psSelectP.setString(1, user);
                    resultSet = psSelect.executeQuery();
                    resultSetP = psSelectP.executeQuery();
                    if (resultSet.next()) {
                        String fname = resultSet.getString("fname");
                        String lname = resultSet.getString("lname");
                        String email = resultSet.getString("email");
                        String pwd = resultSet.getString("pwd");


                        UserSettingsController userSettingsController = loader.getController();
                        userSettingsController.fillUserInformation(fname, lname, email, pwd);
                    }
                    if (resultSetP.next()) {
                        String pLevel = resultSetP.getString("privacyLevel");
                        UserSettingsController userSettingsController = loader.getController();
                        if (pLevel == null) {
                            userSettingsController.privacyInformation("private");
                        } else {
                            userSettingsController.privacyInformation(pLevel);
                        }
                    }
                    else {
                        UserSettingsController userSettingsController = loader.getController();
                        userSettingsController.privacyInformation("private");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) resultSet.close();
                        if (psSelect != null) psSelect.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }
    }

    public static void createAccount(ActionEvent event, String fname, String lname, String email1, String username,
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
            psInsert.setString(3, email1);
            psInsert.setString(4, username);
            psInsert.setString(5, password);
            psInsertAdminUser.setString(1, email1);
            psInsertAdminUser.setString(2, username);
            psInsertAdminUser.setString(3, today);
            psInsert.executeUpdate();
            psInsertAdminUser.executeUpdate();
            if (adminOrUser == 2) {
                changeScene(event, "user-home-screen.fxml", "Welcome!", username);
                user = username;
            } else if (adminOrUser == 1) {
                email = email1;
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
                        preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=?;");
                        preparedStatement.setString(1, username);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (!rs.isBeforeFirst()) {
                            preparedStatement = connection.prepareStatement("SELECT email FROM admin WHERE username = ?");
                            preparedStatement.setString(1, username);
                            rs = preparedStatement.executeQuery();
                            rs.next();
                            email = rs.getString("email");
                            changeScene(event, "admin-flags.fxml", "Welcome!", username);
                        } else {
                            preparedStatement = connection.prepareStatement("SELECT bannerEmail FROM user WHERE username = ?");
                            preparedStatement.setString(1, username);
                            rs = preparedStatement.executeQuery();
                            if (!rs.isBeforeFirst()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("You've been banned.");
                                alert.show();
                            } else {
                                changeScene(event, "user-home-screen.fxml", "Welcome!", username);
                            }
                        }
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

    public static void updateUser(ActionEvent event, String firstname, String lastname, String email, String password, String privacyLevel) {
        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psSelectPwd = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);
            psSelect = connection.prepareStatement("UPDATE account SET fname = ?, lname = ?, email = ?, pwd = ? WHERE username = ?");
            psSelectPwd = connection.prepareStatement("UPDATE user SET privacyLevel = ? WHERE username = ?");
            psSelectPwd.setString(1, privacyLevel);
            psSelectPwd.setString(2, user);
            psSelect.setString(1, firstname);
            psSelect.setString(2, lastname);
            psSelect.setString(3, email);
            psSelect.setString(4, password);
            psSelect.setString(5, user);
            psSelectPwd.executeUpdate();
            psSelect.executeUpdate();
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

    public static void deleteUser() {
        Connection connection = null;
        PreparedStatement psSelect = null;
        try {
            connection = DriverManager.getConnection(sqlURL, "root", sqlPassword);
            psSelect = connection.prepareStatement("DELETE FROM account WHERE username = ?");
            psSelect.setString(1, user);
            psSelect.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
