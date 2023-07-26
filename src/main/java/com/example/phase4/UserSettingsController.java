package com.example.phase4;
import com.example.phase4.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.phase4.DBUtils;
import javafx.scene.control.ToggleButton;

public class UserSettingsController implements Initializable {
    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @FXML
    private ToggleButton togglebutton_public;

    @FXML
    private ToggleButton togglebutton_private;

    @FXML
    private Button button_updateaccount;

    @FXML
    private Button button_deleteaccount;

    @FXML
    private Button button_back;

    private String privacyLevel = "private";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_updateaccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("'Update account' button clicked");
                DBUtils.updateUser(event, tf_firstname.getText(), tf_lastname.getText(), tf_email.getText(), tf_password.getText(), privacyLevel);
                DBUtils.changeScene(event, "user-home-screen.fxml", "Back", null);
            }
        });

        button_deleteaccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("'Delete account' button clicked");
                DBUtils.deleteUser();
                DBUtils.changeScene(event, "login.fxml", "Back", null);
            }
        });


        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button Back Clicked!");
                DBUtils.changeScene(event, "user-home-screen.fxml", "Back", null);
            }
        });

        togglebutton_public.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                privacyLevel = "public";
                togglebutton_public.setSelected(true);
                togglebutton_private.setSelected(false);
            }
        });

        togglebutton_private.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                privacyLevel = "private";
                togglebutton_public.setSelected(false);
                togglebutton_private.setSelected(true);
            }
        });

    }

    public void fillUserInformation(String firstname, String lastname, String email, String password) {
        tf_firstname.setText(firstname);
        tf_lastname.setText(lastname);
        tf_email.setText(email);
        tf_password.setText(password);
    }

    public void privacyInformation(String privacyLevel) {
        if (privacyLevel.equals("private")) {
            togglebutton_public.setSelected(false);
            togglebutton_private.setSelected(true);
        } else {
            togglebutton_public.setSelected(true);
            togglebutton_private.setSelected(false);
        }
    }

}