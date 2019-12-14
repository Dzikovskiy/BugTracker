package app.controllers;


import app.Commands;
import app.model.Task;
import app.model.TaskBuffer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class trashController {

    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Task> tasksArray = new ArrayList<>();
    private List<Pane> firstList = new ArrayList<>();
    private VBox box_1 = new VBox();
    private EventHandler<ActionEvent> editTaskHandler = event -> {
        for (Task array : tasksArray) {
            if (array.getId().equalsIgnoreCase(((Control) event.getSource()).getId())) {
                TaskBuffer.setTask(array.getTask());
                TaskBuffer.setCreator(array.getCreator());
                TaskBuffer.setId(array.getId());
                break;
            }

        }
        SceneOpener.openNewScene("/app/view/edit.fxml");
    };
    private EventHandler<ActionEvent> backTaskHandler = event -> {
        System.out.println("pressed");
        String id = ((Control) event.getSource()).getId();
        ServerAgent.sendDataToServer(Commands.BACK_TASK);
        ServerAgent.sendDataToServer(id);
        String[] result = ServerAgent.getDataFromServer().split(" ");

        if (result[0].equalsIgnoreCase("backed")) {
            System.out.println("Task backed");
            updateTasks();
        } else if (result[0].equalsIgnoreCase("error")) {
            System.out.println("Error while backed");
        } else {
            System.out.println("Error: wrong data from server");
        }

    };
    private EventHandler<ActionEvent> deleteTaskHandler = event -> {
        String id = ((Control) event.getSource()).getId();

        ServerAgent.sendDataToServer(Commands.DELETE_TASK);
        ServerAgent.sendDataToServer(id);
        String[] result = ServerAgent.getDataFromServer().split(" ");

        if (result[0].equalsIgnoreCase("deleted")) {
            System.out.println("Task deleted");
            updateTasks();
        } else if (result[0].equalsIgnoreCase("error")) {
            System.out.println("Error while deleting");
        } else {
            System.out.println("Error: wrong data from server");
        }
    };
    @FXML
    void initialize() {

        updateTasks();
    }

    private void updateTasks() {
        tasksArray.clear();
        firstList.clear();
        scrollPane.setContent(null);
        box_1.getChildren().clear();
        loadTasks();
    }

    private void loadTasks() {
        BoardController.getTasks(tasksArray);
        for (int i = 0; i < tasksArray.size(); i++) {
            AnchorPane anchorPane = new AnchorPane();
            Rectangle rectangle = new Rectangle(220, 100);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.GRAY);
            rectangle.setStrokeWidth(3.0);
            rectangle.setArcWidth(20.0);
            rectangle.setArcHeight(20.0);

            AnchorPane.setTopAnchor(rectangle, 10.0);
            AnchorPane.setLeftAnchor(rectangle, 15.0);

            Button editButton = new Button();
            editButton.setOnAction(editTaskHandler);
            editButton.setId(tasksArray.get(i).getId());
            editButton.setText("\uD83D\uDEE0");
            editButton.setCache(false);

            Button deleteButton = new Button();
            deleteButton.setOnAction(deleteTaskHandler);
            deleteButton.setId(tasksArray.get(i).getId());
            deleteButton.setText("X");
            deleteButton.setCache(false);

            Button backButton = new Button();
            backButton.setOnAction(backTaskHandler);
            backButton.setId(tasksArray.get(i).getId());
            backButton.setText("вернуть");
            backButton.setCache(false);


            Text creatorText = new Text(tasksArray.get(i).getCreator());
            creatorText.setFont(Font.font("Segoe WP Bold", 15));
            creatorText.setCache(false);
            creatorText.setFontSmoothingType(FontSmoothingType.LCD);
            creatorText.setStrokeType(StrokeType.OUTSIDE);
            creatorText.setStrokeWidth(0.0);

            Text taskText = new Text(tasksArray.get(i).getTask());
            taskText.setFont(Font.font("Segoe WP Light", 13));
            taskText.setCache(false);
            taskText.setFontSmoothingType(FontSmoothingType.LCD);
            taskText.setStrokeType(StrokeType.OUTSIDE);
            taskText.setStrokeWidth(0.0);

            creatorText.setWrappingWidth(170);
            taskText.setWrappingWidth(180);
            AnchorPane.setTopAnchor(taskText, 20.0);
            AnchorPane.setLeftAnchor(taskText, 20.0);
            AnchorPane.setTopAnchor(creatorText, 85.0);
            AnchorPane.setLeftAnchor(creatorText, 30.0);
            AnchorPane.setTopAnchor(backButton, 80.0);
            AnchorPane.setRightAnchor(backButton, 10.0);
            AnchorPane.setTopAnchor(deleteButton, 20.0);
            AnchorPane.setRightAnchor(deleteButton, 10.0);
            AnchorPane.setTopAnchor(editButton, 50.0);
            AnchorPane.setRightAnchor(editButton, 8.0);
            anchorPane.getChildren().addAll(rectangle, taskText, backButton, creatorText, deleteButton, editButton);
            anchorPane.setCache(false);

            if (tasksArray.get(i).getStage().equalsIgnoreCase("4")) {
                firstList.add(anchorPane);
            }
        }
        if (!firstList.isEmpty()) {
            box_1.setSpacing(15);
            box_1.getChildren().addAll(firstList);
            box_1.setCache(false);
            scrollPane.setContent(box_1);
            scrollPane.setCache(false);
        }


    }
}