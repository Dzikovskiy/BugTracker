package app.controllers;

import app.DatabaseHandler;
import app.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpLogin;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpEmail;

    @FXML
    void initialize() {


        signUpButton.setOnAction(event -> {
            signUpNewUser();

        });

    }

    private void signUpNewUser() {//TODO add fields checking , because working with null
        String firstName = signUpName.getText();
        String login = signUpLogin.getText();
        String password = signUpPassword.getText();
        String email = signUpEmail.getText();

        DatabaseHandler dbHandler = new DatabaseHandler();

        User user = new User(firstName,login,password,email);
        dbHandler.signUpUser(user);
    }
}
