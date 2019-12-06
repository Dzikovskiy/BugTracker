package app.controllers;

import app.ClientSocket;
import app.Commands;
import app.DatabaseHandler;
import app.animations.Shaker;
import app.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField login_field;

    @FXML
    private Button loginSignInButton;

    @FXML
    void initialize() {


        loginSignInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String passwordText = password_field.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                loginUser(loginText, passwordText);
            } else {
                System.out.println("Login error: empty fields ");
                Shaker.shakeFields(login_field, password_field);
            }
        });

        loginSignUpButton.setOnAction(event -> {
            loginSignUpButton.getScene().getWindow().hide();//hiding current window
            SceneOpener.openNewScene("/app/view/signUp.fxml");
        });

    }

    private void loginUser(String loginText, String passwordText) {


        ServerAgent.sendDataToServer(Commands.LOGIN);
        ServerAgent.sendDataToServer(loginText + " " + passwordText);

        String[] data = ServerAgent.getDataFromServer().split(" ");

        if (data[0].equalsIgnoreCase("logged")) {
            System.out.println("User logged in");
            loginSignUpButton.getScene().getWindow().hide();
            SceneOpener.openNewScene("/app/view/board.fxml");
        } else if (data[0].equalsIgnoreCase("error")) {
            System.out.println("Login error: wrong login or password ");
            Shaker.shakeFields(login_field, password_field);
        } else {
            System.out.println("Login error: wrong data from server ");
            Shaker.shakeFields(login_field, password_field);
        }

    }


}

