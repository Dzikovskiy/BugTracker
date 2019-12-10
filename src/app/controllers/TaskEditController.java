package app.controllers;

import app.Commands;
import app.animations.Shaker;
import app.model.TaskBuffer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class TaskEditController {
    @FXML
    private ResourceBundle resources;


    @FXML
    private TextField creator_field;

    @FXML
    private Button editTaskButton;

    @FXML
    private TextArea task_field;

    @FXML
    void initialize() {
        task_field.setText(TaskBuffer.getTask());
        creator_field.setText(TaskBuffer.getCreator());
        editTaskButton.setOnAction(event -> {
            String taskText = task_field.getText().trim();
            String creatorText = creator_field.getText().trim();
            if (!taskText.equals("") && !creatorText.equals("")) {
                editTask(taskText, creatorText);
            } else {
                System.out.println("Edit error: empty fields ");
                Shaker.shakeFields(task_field, creator_field);
            }
        });


    }

    private void editTask(String task, String creator) {
        String id = TaskBuffer.getId();

        ServerAgent.sendDataToServer(Commands.EDIT_TASK);
        ServerAgent.sendDataToServer(task + "," + creator + " " + id);
        String[] result = ServerAgent.getDataFromServer().split(" ");

        if (result[0].equalsIgnoreCase("edited")) {
            System.out.println("Task edited");
            editTaskButton.getScene().getWindow().hide();
        } else if (result[0].equalsIgnoreCase("error")) {
            System.out.println("Error while editing");
        } else {
            System.out.println("Error: wrong data from server");
        }
    }

    ;


}
