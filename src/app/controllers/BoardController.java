package app.controllers;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BoardController {

    @FXML
    private ScrollPane scrollPane;

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

            Button btn = (Button) event.getSource();
            String id = btn.getId();

            System.out.println("Button pressed: "+id);
            System.out.println(((Control)event.getSource()).getId());
        };

        for (int i = 0; i < 10; i++) {
            final Rectangle rectangle = new Rectangle(200, 50);
            rectangle.setFill(Color.WHITE);
            Button Button = new Button();
            Button.setOnAction(handler);
            Button.setId(String.valueOf(i)+"ToDo");
            final Text text = new Text("Text from "+String.valueOf(i));
            final AnchorPane stackPane = new AnchorPane();

            AnchorPane.setTopAnchor(text, 20.0);
            stackPane.setTopAnchor(Button, 20.0);
            stackPane.setRightAnchor(Button, 15.0);
            stackPane.getChildren().addAll(rectangle, text, Button);


            list.add(stackPane);
        }

        Pane pane = new Pane();
        VBox box = new VBox();
        box.setSpacing(15);
        box.getChildren().addAll(list);


        scrollPane.setContent(box);

    }
}
