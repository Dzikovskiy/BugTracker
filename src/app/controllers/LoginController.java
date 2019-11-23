package app.controllers;

import app.DatabaseHandler;
import app.animations.Shake;
import app.animations.Shaker;
import app.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
                Shaker.shakeFields(login_field,password_field);
            }
        });

        loginSignUpButton.setOnAction(event -> {
            openNewScene("/app/view/signUp.fxml");
        });

    }

    private void loginUser(String loginText, String passwordText) {

        DatabaseHandler handler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);

        ResultSet resultSet = handler.getUser(user);

        int counter = 0;

        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter >= 1) {
            System.out.println("User logged in");
            openNewScene("/app/view/board.fxml");
        } else {
            System.out.println("Login error: wrong login or password ");
            Shaker.shakeFields(login_field,password_field);

        }

    }

    public void openNewScene(String window) {
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }


}

