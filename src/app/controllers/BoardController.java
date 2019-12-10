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
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BoardController {

    @FXML
    private Button updateButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane_3;
    @FXML
    private ScrollPane scrollPane_2;


    private ArrayList<Task> tasksArray = new ArrayList<>();
    private List<Pane> firstList = new ArrayList<>();
    private List<Pane> secondList = new ArrayList<>();
    private List<Pane> thirdList = new ArrayList<>();
    private VBox box_1 = new VBox();
    private VBox box_2 = new VBox();
    private VBox box_3 = new VBox();
    private EventHandler<ActionEvent> moveTaskHandler = event -> {
        String stage = "";
        String id = "";
        for (Task array : tasksArray) {
            if (array.getId().equalsIgnoreCase(((Control) event.getSource()).getId())) {
                stage = array.getStage();
                id = array.getId();
                break;
            }

        }
        ServerAgent.sendDataToServer(Commands.MOVE_TASK);
        ServerAgent.sendDataToServer(id + " " + stage);
        String[] result = ServerAgent.getDataFromServer().split(" ");

        if (result[0].equalsIgnoreCase("edited")) {
            updateTasks();
            System.out.println("Task stage changed");
        } else if (result[0].equalsIgnoreCase("erroe")) {
            System.out.println("Error while moving");
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

    @FXML
    void initialize() {

        loadTasks();
        addTaskButton.setOnAction(event -> {
            SceneOpener.openNewScene("/app/view/task.fxml");
            updateTasks();

        });
        updateButton.setOnAction(event -> {
            updateTasks();
        });

    }

    private void updateTasks() {
        tasksArray.clear();
        firstList.clear();
        secondList.clear();
        thirdList.clear();
        scrollPane.setContent(null);
        scrollPane_2.setContent(null);
        scrollPane_3.setContent(null);
        box_1.getChildren().clear();
        box_2.getChildren().clear();
        box_3.getChildren().clear();
        loadTasks();

    }

    private void saveTask(String task, String creator, String column) {
        ServerAgent.sendDataToServer(Commands.SAVE_TASK);
        ServerAgent.sendDataToServer(task + "," + creator + " " + column);

        String[] data = ServerAgent.getDataFromServer().split(" ");

        if (data[0].equalsIgnoreCase("saved")) {
            System.out.println("Task saved to database");

        } else if (data[0].equalsIgnoreCase("error")) {
            System.out.println("Saving error: wrong data ");

        } else {
            System.out.println("Saving error: wrong data from server ");

        }
    }

    private void getTasks(ArrayList tasksArr) {

        // sending command to server
        ServerAgent.sendDataToServer(Commands.GET_TASKS);
        // receiving data from server
        // and splitting lines with comma
        String[] result = ServerAgent.getDataFromServer().split(" ");
        if (result[0].equalsIgnoreCase("tasks")) {
            System.out.println("Tasks received from server");
            String[] dataCommaSplitted = ServerAgent.getDataFromServer().split(",");
            String[] data;
            for (int i = 0; i < dataCommaSplitted.length; i++) {//saving data to Tasks array
                Task task = new Task();
                data = dataCommaSplitted[i].split("\\+");
                String[] otherData = data[1].split(" ");
                // data = dataCommaSplitted[i].split(" ");

                task.setTask(data[0]);
                task.setId(otherData[0]);

                task.setCreator(otherData[1]);
                task.setStage(otherData[2]);
                tasksArr.add(task);

            }//задача состоит из нескольих слов - по этому не правильно загружает так как я не поделил на части
        } else if (result[0].equalsIgnoreCase("error")) {

        }

    }

    private void loadTasks() {

        getTasks(tasksArray);
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

            Button deleteButton = new Button();
            deleteButton.setOnAction(deleteTaskHandler);
            deleteButton.setId(tasksArray.get(i).getId());
            deleteButton.setText("X");

            Button moveButton = new Button();
            moveButton.setOnAction(moveTaskHandler);
            moveButton.setId(tasksArray.get(i).getId());
            moveButton.setText(">");

            Text creatorText = new Text(tasksArray.get(i).getCreator());
            creatorText.setFont(Font.font("Segoe WP Bold", 15));
            creatorText.setCache(false);
            //creatorText.setFontSmoothingType(FontSmoothingType.LCD);

            Text taskText = new Text(tasksArray.get(i).getTask());
            taskText.setFont(Font.font("Segoe WP Light", 13));
            taskText.setCache(false);
            // taskText.setFontSmoothingType(FontSmoothingType.LCD);

            creatorText.setWrappingWidth(170);
            taskText.setWrappingWidth(180);
            AnchorPane.setTopAnchor(taskText, 20.0);
            AnchorPane.setLeftAnchor(taskText, 20.0);
            AnchorPane.setTopAnchor(creatorText, 85.0);
            AnchorPane.setLeftAnchor(creatorText, 30.0);
            AnchorPane.setTopAnchor(moveButton, 80.0);
            AnchorPane.setRightAnchor(moveButton, 10.0);
            AnchorPane.setTopAnchor(deleteButton, 20.0);
            AnchorPane.setRightAnchor(deleteButton, 10.0);
            AnchorPane.setTopAnchor(editButton, 50.0);
            AnchorPane.setRightAnchor(editButton, 8.0);
            anchorPane.getChildren().addAll(rectangle, taskText, moveButton, creatorText, deleteButton, editButton);
            anchorPane.setCache(false);

            if (tasksArray.get(i).getStage().equalsIgnoreCase("1")) {
                firstList.add(anchorPane);
            } else if (tasksArray.get(i).getStage().equalsIgnoreCase("2")) {
                secondList.add(anchorPane);
            } else {
                thirdList.add(anchorPane);
            }
        }
        if (!firstList.isEmpty()) {
            box_1.setSpacing(15);
            box_1.getChildren().addAll(firstList);
            box_1.setCache(false);
            scrollPane.setContent(box_1);
            scrollPane.setCache(false);
        }
        if (!secondList.isEmpty()) {
            box_2.setSpacing(15);
            box_2.getChildren().addAll(secondList);
            box_2.setCache(false);
            scrollPane_2.setContent(box_2);
            scrollPane_2.setCache(false);
        }
        if (!thirdList.isEmpty()) {
            box_3.setSpacing(15);
            box_3.getChildren().addAll(thirdList);
            box_3.setCache(false);
            scrollPane_3.setContent(box_3);
            scrollPane_3.setCache(false);
        }


    }


}
