package app.controllers;

import app.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class TableController {

    @FXML
    private TableView<Task> table;

    private ArrayList<Task> tasksArray = new ArrayList<>();

    @FXML
    void initialize() {

        loadTasks();
    }


    private void loadTasks() {

        BoardController.getTasks(tasksArray);
        ObservableList<Task> tasks = FXCollections.observableArrayList(tasksArray);

        TableColumn<Task, String> taskColumn = new TableColumn<>("Задача");
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));

        TableColumn<Task, String> creatorColumn = new TableColumn<>("Создатель");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        TableColumn<Task, String> stageColumn = new TableColumn<>("Создатель");
        stageColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));

        table.setItems(tasks);
        table.getColumns().add(taskColumn);
        table.getColumns().add(creatorColumn);
        table.getColumns().add(stageColumn);

    }
}
