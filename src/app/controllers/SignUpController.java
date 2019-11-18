package app.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUplogin;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpEmail;

    @FXML
    void initialize() {

        DatabaseHandler dbHandler = new DatabaseHandler();
        signUpButton.setOnAction(event -> {

                dbHandler.signUpUser(signUpName.getText(),signUplogin.getText(),signUpPassword.getText(),signUpEmail.getText());


        });

    }
}
