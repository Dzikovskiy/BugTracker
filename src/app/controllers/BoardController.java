package app.controllers;


import app.Commands;
import app.model.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    void initialize() {

        loadTasks();
        addTaskButton.setOnAction(event -> {
            SceneOpener.openNewScene("/app/view/task.fxml");

        });
        updateButton.setOnAction(event -> {
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
        });

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

    ;

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

            Button Button = new Button();
            Button.setOnAction(saveTaskHandler);

            Button.setId(tasksArray.get(i).getId());
            Button.setText(">");

            Text text = new Text(tasksArray.get(i).getTask());
            text.setFont(Font.font("Segoe WP Light", 14));
            text.setCache(false);
            text.setFontSmoothingType(FontSmoothingType.LCD);

            text.setWrappingWidth(180);
            AnchorPane.setTopAnchor(text, 20.0);
            AnchorPane.setLeftAnchor(text, 20.0);
            AnchorPane.setTopAnchor(Button, 80.0);
            AnchorPane.setRightAnchor(Button, 10.0);
            anchorPane.getChildren().addAll(rectangle, text, Button);
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

    private EventHandler<ActionEvent> saveTaskHandler = event -> {

        //Button btn = (Button) event.getSource();
        //String id = btn.getId();//bt = pane = text = array = id

        // System.out.println("Button pressed: "+id);
        //System.out.println(((Control)event.getSource()).getId());
        String task = "thistaskv ver yimp ort and";
        String creator = "jonny";
        String column = "2";
        saveTask(task, creator, column);// separating text with comma ","
    };


}
