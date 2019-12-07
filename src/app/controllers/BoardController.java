package app.controllers;


import app.Commands;
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
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BoardController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ScrollPane scrollPane_2;

    @FXML
    private ScrollPane scrollPane_3;

    @FXML
    private Rectangle recTask_2;

    @FXML
    private Rectangle recTask_1;

    @FXML
    private Rectangle recTask_3;

    @FXML
    private Rectangle rec4;

    @FXML
    private Rectangle rec5;

    @FXML
    private Rectangle rec6;

    @FXML
    void initialize() {

        List<Pane> list = new ArrayList<>();

        EventHandler<ActionEvent> handler = event -> {

            //Button btn = (Button) event.getSource();
            //String id = btn.getId();

            // System.out.println("Button pressed: "+id);
            //System.out.println(((Control)event.getSource()).getId());
            String task = "thistaskv ver yimp ort and";
            String creator = "jonny";
            String column = "2";
            saveTask(task + ",", creator, column);// separating text with comma ","
        };

        for (int i = 0; i < 10; i++) {
            final AnchorPane stackPane = new AnchorPane();
            Rectangle rectangle = new Rectangle(220, 100);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.GRAY);
            rectangle.setStrokeWidth(3.0);
            rectangle.setArcWidth(20.0);
            rectangle.setArcHeight(20.0);

            AnchorPane.setTopAnchor(rectangle, 10.0);
            AnchorPane.setLeftAnchor(rectangle, 15.0);

            Button Button = new Button();
            Button.setOnAction(handler);
            Button.setId(String.valueOf(i));
            Button.setText(">");

            Text text = new Text("Text from d dfg j j j j jj j j jj j j jj j j jj j j j j j jffffffffffffffffff ffffffff fffff fffffffff fffffffffffffffffff " + String.valueOf(i));

            text.setWrappingWidth(180);
            AnchorPane.setTopAnchor(text, 20.0);
            AnchorPane.setLeftAnchor(text, 20.0);
            AnchorPane.setTopAnchor(Button, 80.0);
            AnchorPane.setRightAnchor(Button, 10.0);
            stackPane.getChildren().addAll(rectangle, text, Button);

            list.add(stackPane);
        }

        Pane pane = new Pane();
        VBox box = new VBox();
        box.setSpacing(15);
        box.getChildren().addAll(list);


        scrollPane.setContent(box);

    }

    private void saveTask(String task, String creator, String column) {
        ServerAgent.sendDataToServer(Commands.SAVE_TASK);
        ServerAgent.sendDataToServer(task + " " + creator + " " + column);

        String[] data = ServerAgent.getDataFromServer().split(" ");

        if (data[0].equalsIgnoreCase("saved")) {
            System.out.println("Task saved to database");

        } else if (data[0].equalsIgnoreCase("error")) {
            System.out.println("Saving error: wrong data ");

        } else {
            System.out.println("Saving error: wrong data from server ");

        }
    }
}
