package app.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

        Rectangle rectangle = new Rectangle(30,40, Color.web("#ed4b00"));
        for(int i = 0;i<10;i++) {
            Group
            scrollPane.setContent();
            scrollPane.setContent(rectangle);
        }

    }
}
