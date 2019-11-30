package app.controllers;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private ResourceBundle resources;

    @FXML
    private URL location;

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
/*        list.add(recTask_1);
        list.add(recTask_2);
        list.add(recTask_3);
        list.add(rec4);
        list.add(rec5);
        list.add(rec6);*/
        EventHandler<ActionEvent> handler = event -> {
            System.out.println("Button pressed");
        };

        for (int i = 0; i < 10; i++) {
            final Rectangle rectangle = new Rectangle(200, 50);
            rectangle.setFill(Color.WHITE);
            Button loginSignUpButton = new Button();
            loginSignUpButton.setOnAction(handler);
            final Text text = new Text(String.valueOf(i));
            final AnchorPane stackPane = new AnchorPane();

            AnchorPane.setTopAnchor(text, 20.0);
            stackPane.setTopAnchor(loginSignUpButton, 20.0);
            stackPane.setRightAnchor(loginSignUpButton, 15.0);
            stackPane.getChildren().addAll(rectangle, text, loginSignUpButton);


            list.add(stackPane);
        }

        Pane pane = new Pane();
        VBox box = new VBox();
        box.setSpacing(15);
        box.getChildren().addAll(list);


        scrollPane.setContent(box);

    }
}
