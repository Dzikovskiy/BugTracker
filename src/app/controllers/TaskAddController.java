package app.controllers;

import app.Commands;
import app.animations.Shaker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskAddController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField creator_field;

    @FXML
    private Button createTaskButton;

    @FXML
    private TextArea task_field;

    @FXML
    void initialize() {
        createTaskButton.setOnAction(event -> {
            String taskText = task_field.getText().trim();
            String creatorText = creator_field.getText().trim();
            if (!taskText.equals("") && !creatorText.equals("")) {
                saveTask(taskText, creatorText);
            } else {
                System.out.println("Login error: empty fields ");
                Shaker.shakeFields(task_field, creator_field);
            }
        });

    }

    private void saveTask(String taskText, String creatorText) {
        ServerAgent.sendDataToServer(Commands.SAVE_TASK);
        ServerAgent.sendDataToServer(taskText + "," + creatorText);

        String[] data = ServerAgent.getDataFromServer().split(" ");

        if (data[0].equalsIgnoreCase("saved")) {
            System.out.println("Task saved");
            createTaskButton.getScene().getWindow().hide();


        } else if (data[0].equalsIgnoreCase("error")) {
            System.out.println("Saving error.");
            Shaker.shakeFields(task_field, creator_field);
        } else {
            System.out.println("Saving error: wrong data from server ");
            Shaker.shakeFields(task_field, creator_field);
        }
    }
}
